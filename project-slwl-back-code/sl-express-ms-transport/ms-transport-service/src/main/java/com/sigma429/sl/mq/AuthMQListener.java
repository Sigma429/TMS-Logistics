package com.sigma429.sl.mq;

/**
 * ClassName:AuthMQListener
 * Package:com.sigma429.sl.mq
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/30 - 21:41
 * @Version:v1.0
 */

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sigma429.sl.constant.Constants;
import com.sigma429.sl.entity.node.AgencyEntity;
import com.sigma429.sl.entity.node.BaseEntity;
import com.sigma429.sl.entity.node.OLTEntity;
import com.sigma429.sl.entity.node.TLTEntity;
import com.sigma429.sl.enums.OrganTypeEnum;
import com.sigma429.sl.service.IService;
import com.sigma429.sl.utils.OrganServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 对于权限管家系统消息的处理
 */
@Slf4j
@Component
public class AuthMQListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.MQ.Queues.AUTH_TRANSPORT),
            exchange = @Exchange(name = "${rabbitmq.exchange}", type = ExchangeTypes.TOPIC),
            key = "#"
    ))
    public void listenAgencyMsg(String msg) {
        //{"type":"ORG","operation":"ADD","content":[{"id":"977263044792942657","name":"55","parentId":"0",
        // "managerId":null,"status":true}]}
        log.info("接收到消息 -> {}", msg);
        JSONObject jsonObject = JSONUtil.parseObj(msg);
        String type = jsonObject.getStr("type");
        if (!StrUtil.equalsIgnoreCase(type, "ORG")) {
            // 非机构消息
            return;
        }
        String operation = jsonObject.getStr("operation");
        JSONObject content = (JSONObject) jsonObject.getJSONArray("content").getObj(0);
        String name = content.getStr("name");
        Long parentId = content.getLong("parentId");

        IService iService;
        BaseEntity entity;
        if (StrUtil.endWith(name, "转运中心")) {
            // 一级转运中心
            iService = OrganServiceFactory.getBean(OrganTypeEnum.OLT.getCode());
            entity = new OLTEntity();
            entity.setParentId(0L);
        } else if (StrUtil.endWith(name, "分拣中心")) {
            // 二级转运中心
            iService = OrganServiceFactory.getBean(OrganTypeEnum.TLT.getCode());
            entity = new TLTEntity();
            entity.setParentId(parentId);
        } else if (StrUtil.endWith(name, "营业部")) {
            // 网点
            iService = OrganServiceFactory.getBean(OrganTypeEnum.AGENCY.getCode());
            entity = new AgencyEntity();
            entity.setParentId(parentId);
        } else {
            return;
        }

        // 设置参数
        entity.setBid(content.getLong("id"));
        entity.setName(name);
        entity.setStatus(content.getBool("status"));

        switch (operation) {
            case "ADD": {
                iService.create(entity);
                break;
            }
            case "UPDATE": {
                iService.update(entity);
                break;
            }
            case "DEL": {
                iService.deleteByBid(entity.getBid());
                break;
            }
        }

    }

}