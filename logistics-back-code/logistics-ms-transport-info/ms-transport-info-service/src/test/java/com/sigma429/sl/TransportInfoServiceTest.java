package com.sigma429.sl;

import com.sigma429.sl.entity.TransportInfoDetail;
import com.sigma429.sl.entity.TransportInfoEntity;
import com.sigma429.sl.service.TransportInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class TransportInfoServiceTest {

    @Resource
    private TransportInfoService transportInfoService;

    @Test
    void saveOrUpdate() {
        String transportOrderId = "SL1000000001561";
        TransportInfoDetail transportInfoDetail = TransportInfoDetail.builder()
                .status("已取件")
                .info("神领快递员已取件，取件人【张三】，电话：13888888888")
                .created(System.currentTimeMillis())
                .build();
        TransportInfoEntity transportInfoEntity = this.transportInfoService.saveOrUpdate(transportOrderId,
                transportInfoDetail);
        System.out.println(transportInfoEntity);
    }

    @Test
    void queryByTransportOrderId() {
        String transportOrderId = "SL1000000001561";
        System.out.println(this.transportInfoService.queryByTransportOrderId(transportOrderId));
    }
}
