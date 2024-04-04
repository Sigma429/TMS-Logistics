package com.sigma429.sl;

import com.sigma429.sl.entity.node.AgencyEntity;
import com.sigma429.sl.service.AgencyService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * ClassName:AgencyServiceTest
 * Package:com.sigma429.sl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/30 - 21:55
 * @Version:v1.0
 */
@SpringBootTest
class AgencyServiceTest {

    @Resource
    private AgencyService agencyService;

    @Test
    public void testQueryByBid() {
        AgencyEntity agencyEntity = this.agencyService.queryByBid(25073L);
        System.out.println(agencyEntity);
        // AgencyEntity(super=BaseEntity(id=18, parentId=null, bid=25073, name=江苏省南京市玄武区紫金墨香苑, managerName=null,
        // phone=025-58765331,025-83241955,025-83241881, address=栖霞区燕尧路100号, location=Point [x=32.117016, y=118
        // .863193], status=null, extra=null))
    }

}