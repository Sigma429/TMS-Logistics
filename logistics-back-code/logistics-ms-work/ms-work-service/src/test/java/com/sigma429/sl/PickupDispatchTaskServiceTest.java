package com.sigma429.sl;

import com.sigma429.sl.entity.PickupDispatchTaskEntity;
import com.sigma429.sl.enums.pickupDispatchtask.PickupDispatchTaskAssignedStatus;
import com.sigma429.sl.enums.pickupDispatchtask.PickupDispatchTaskSignStatus;
import com.sigma429.sl.enums.pickupDispatchtask.PickupDispatchTaskType;
import com.sigma429.sl.service.PickupDispatchTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * ClassName:PickupDispatchTaskServiceTest
 * Package:com.sigma429.sl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/04/07 - 13:07
 * @Version:v1.0
 */
@SpringBootTest
class PickupDispatchTaskServiceTest {

    @Resource
    private PickupDispatchTaskService pickupDispatchTaskService;

    /**
     * 测试新增取件任务
     */
    @Test
    void saveTaskPickupDispatch() {
        PickupDispatchTaskEntity pickupDispatchTaskEntity = new PickupDispatchTaskEntity();
        pickupDispatchTaskEntity.setCourierId(1019618890088508577L);
        pickupDispatchTaskEntity.setOrderId(1564170062718373889L);
        pickupDispatchTaskEntity.setAgencyId(1015716681416180257L);
        pickupDispatchTaskEntity.setTaskType(PickupDispatchTaskType.PICKUP);
        pickupDispatchTaskEntity.setMark("带包装");
        pickupDispatchTaskEntity.setSignStatus(PickupDispatchTaskSignStatus.NOT_SIGNED);
        pickupDispatchTaskEntity.setAssignedStatus(PickupDispatchTaskAssignedStatus.DISTRIBUTED);
        PickupDispatchTaskEntity pickupDispatchTask =
                this.pickupDispatchTaskService.saveTaskPickupDispatch(pickupDispatchTaskEntity);
        System.out.println(pickupDispatchTask);
    }

}