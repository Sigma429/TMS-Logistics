package com.sigma429.sl;

import com.sigma429.sl.dto.WaybillDTO;
import com.sigma429.sl.entity.CarriageEntity;
import com.sigma429.sl.handler.CarriageChainHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * ClassName:com.sigma429.sl.CarriageChainHandlerTest
 * Package:PACKAGE_NAME
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/27 - 20:46
 * @Version:v1.0
 */
@SpringBootTest
class CarriageChainHandlerTest {

    @Resource
    private CarriageChainHandler carriageChainHandler;

    @Test
    void findCarriage() {
        WaybillDTO waybillDTO = WaybillDTO.builder()
                .senderCityId(2L) // 北京
                .receiverCityId(2L) // 上海
                .volume(1)
                .weight(1d)
                .build();

        CarriageEntity carriage = this.carriageChainHandler.findCarriage(waybillDTO);
        System.out.println(carriage);
    }
}