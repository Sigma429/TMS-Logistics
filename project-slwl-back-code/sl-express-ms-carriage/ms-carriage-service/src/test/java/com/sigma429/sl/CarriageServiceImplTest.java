package com.sigma429.sl;

import com.sigma429.sl.dto.CarriageDTO;
import com.sigma429.sl.dto.WaybillDTO;
import com.sigma429.sl.service.CarriageService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CarriageServiceImplTest {

    @Resource
    private CarriageService carriageService;

    @Test
    void saveOrUpdate() {
        CarriageDTO carriageDTO = new CarriageDTO();
        carriageDTO.setTemplateType(3);
        carriageDTO.setTransportType(1);
        carriageDTO.setAssociatedCityList(Arrays.asList("5"));
        carriageDTO.setFirstWeight(12d);
        carriageDTO.setContinuousWeight(1d);
        carriageDTO.setLightThrowingCoefficient(6000);

        CarriageDTO dto = this.carriageService.saveOrUpdate(carriageDTO);
        System.out.println(dto);
    }

    @Test
    void findAll() {
        List<CarriageDTO> all = carriageService.findAll();
        all.forEach(System.out::println);
    }

    @Test
    void compute() {
        WaybillDTO waybillDTO = new WaybillDTO();
        waybillDTO.setReceiverCityId(161793L); // 上海
        waybillDTO.setSenderCityId(2L); // 北京
        waybillDTO.setWeight(0.8); // 重量
        waybillDTO.setVolume(1); // 体积

        CarriageDTO compute = this.carriageService.compute(waybillDTO);
        System.out.println(compute);
    }

    @Test
    void findByTemplateType() {
    }
}