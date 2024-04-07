package com.sigma429.sl.mq;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.sigma429.sl.OrganFeign;
import com.sigma429.sl.constant.Constants;
import com.sigma429.sl.domain.OrganDTO;
import com.sigma429.sl.entity.TransportInfoDetail;
import com.sigma429.sl.service.TransportInfoService;
import com.sigma429.sl.vo.TransportInfoMsg;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 物流信息消息
 */
@Component
public class TransportInfoMQListener {

    @Resource
    private OrganFeign organFeign;
    @Resource
    private TransportInfoService transportInfoService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.MQ.Queues.TRANSPORT_INFO_APPEND),
            exchange = @Exchange(name = Constants.MQ.Exchanges.TRANSPORT_INFO, type = ExchangeTypes.TOPIC),
            key = Constants.MQ.RoutingKeys.TRANSPORT_INFO_APPEND
    ))
    public void listenTransportInfoMsg(String msg) {
        //{"info":"您的快件已到达【$organId】", "status":"运输中", "organId":90001, "transportOrderId":920733749248 ,
        // "created":1653133234913}
        TransportInfoMsg transportInfoMsg = JSONUtil.toBean(msg, TransportInfoMsg.class);
        Long organId = transportInfoMsg.getOrganId();
        String transportOrderId = Convert.toStr(transportInfoMsg.getTransportOrderId());
        String info = transportInfoMsg.getInfo();

        // 查询机构信息
        if (StrUtil.contains(info, "$organId")) {
            OrganDTO organDTO = this.organFeign.queryById(organId);
            if (organDTO == null) {
                return;
            }
            info = StrUtil.replace(info, "$organId", organDTO.getName());
        }

        // 封装Detail对象
        TransportInfoDetail infoDetail = TransportInfoDetail.builder()
                .info(info)
                .status(transportInfoMsg.getStatus())
                .created(transportInfoMsg.getCreated()).build();

        // 存储到MongoDB
        this.transportInfoService.saveOrUpdate(transportOrderId, infoDetail);
    }
}
