package com.sigma429.sl;

import cn.hutool.json.JSONUtil;
import com.sigma429.sl.mq.CourierMQListener;
import com.sigma429.sl.vo.CourierMsg;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class CourierMQListenerTest {

    @Resource
    private CourierMQListener courierMQListener;

    @Test
    void listenCourierTaskMsg() {
    }

    @Test
    void listenCourierPickupMsg() {
        CourierMsg courierMsg = new CourierMsg();
        // 目前只用到订单id
        courierMsg.setOrderId(1564170062718373889L);

        String msg = JSONUtil.toJsonStr(courierMsg);
        this.courierMQListener.listenCourierPickupMsg(msg);
    }
}