package com.sigma429.sl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONUtil;
import com.sigma429.sl.mq.OrderMQListener;
import com.sigma429.sl.vo.OrderMsg;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * ClassName:OrderMQListenerTest
 * Package:com.sigma429.sl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/04/07 - 15:01
 * @Version:v1.0
 */
@SpringBootTest
class OrderMQListenerTest {

    @Resource
    private OrderMQListener orderMQListener;

    @Test
    void listenOrderMsg() {
        OrderMsg orderMsg = OrderMsg.builder()
                .agencyId(1024981239465110017L)
                .orderId(1590586236289646594L)
                .estimatedEndTime(LocalDateTimeUtil.parse("2023-01-13 23:00:00", "yyyy-MM-dd HH:mm:ss"))
                .longitude(116.41338)
                .latitude(39.91092)
                .created(System.currentTimeMillis())
                .taskType(1)
                .mark("带包装")
                .build();
        this.orderMQListener.listenOrderMsg(JSONUtil.toJsonStr(orderMsg));
    }
}