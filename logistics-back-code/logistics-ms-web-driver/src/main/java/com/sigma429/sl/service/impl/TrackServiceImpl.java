package com.sigma429.sl.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.sigma429.sl.DriverJobFeign;
import com.sigma429.sl.TrackFeign;
import com.sigma429.sl.dto.request.DriverJobPageQueryDTO;
import com.sigma429.sl.dto.response.DriverJobDTO;
import com.sigma429.sl.enums.DriverJobStatus;
import com.sigma429.sl.service.TrackService;
import com.sigma429.sl.util.PageResponse;
import com.sigma429.sl.util.UserThreadLocal;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {
    @Resource
    private DriverJobFeign driverJobFeign;
    @Resource
    private TrackFeign trackFeign;

    /**
     * 车辆上报位置
     * @param lng 经度
     * @param lat 纬度
     * @return 是否成功
     */
    @Override
    public Boolean uploadLocation(String lng, String lat) {
        // 1. 获取当前用户id
        Long userId = UserThreadLocal.getUserId();

        // 2. 查询司机id关联的在途状态司机作业单
        DriverJobPageQueryDTO pageQueryDTO = DriverJobPageQueryDTO.builder()
                .page(1)
                .pageSize(1)
                .driverId(userId)
                .statusList(List.of(DriverJobStatus.PROCESSING))
                .build();
        PageResponse<DriverJobDTO> pageResponse = driverJobFeign.pageQuery(pageQueryDTO);
        if (CollUtil.isEmpty(pageResponse.getItems())) {
            return true;
        }

        // 3. 对关联的运输任务上报位置
        return trackFeign.uploadFromTruck(pageResponse.getItems().get(0).getTransportTaskId(),
                Double.parseDouble(lng), Double.parseDouble(lat));
    }
}
