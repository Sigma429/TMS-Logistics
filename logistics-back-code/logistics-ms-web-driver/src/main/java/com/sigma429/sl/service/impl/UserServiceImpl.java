package com.sigma429.sl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.itheima.auth.sdk.common.Result;
import com.itheima.auth.sdk.dto.UserDTO;
import com.sigma429.sl.DriverJobFeign;
import com.sigma429.sl.dto.response.DriverJobStatisticsDTO;
import com.sigma429.sl.service.UserService;
import com.sigma429.sl.truck.TruckDto;
import com.sigma429.sl.truck.TruckFeign;
import com.sigma429.sl.truck.TruckTypeDto;
import com.sigma429.sl.truck.TruckTypeFeign;
import com.sigma429.sl.user.DriverFeign;
import com.sigma429.sl.user.TruckDriverDto;
import com.sigma429.sl.util.AuthTemplateThreadLocal;
import com.sigma429.sl.util.UserThreadLocal;
import com.sigma429.sl.vo.response.TaskReportVO;
import com.sigma429.sl.vo.response.TruckDetailVO;
import com.sigma429.sl.vo.response.UserDetailVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private DriverFeign driverFeign;
    @Resource
    private TruckFeign truckFeign;
    @Resource
    private TruckTypeFeign truckTypeFeign;
    @Resource
    private DriverJobFeign driverJobFeign;

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @Override
    public UserDetailVO userDetail() {
        // 获取用户id
        Long userId = UserThreadLocal.getUserId();

        // 根据用户id获取用户详细信息
        Result<UserDTO> result = AuthTemplateThreadLocal.get().opsForUser().getUserById(userId);

        // 将用户信息封装到vo
        UserDTO userDTO = result.getData();
        UserDetailVO userDetailVO = BeanUtil.toBean(userDTO, UserDetailVO.class);
        userDetailVO.setNumber(String.valueOf(userId));
        userDetailVO.setPhone(userDTO.getMobile());
        return userDetailVO;
    }

    /**
     * 获取车辆信息
     * @return 车辆信息
     */
    @Override
    public TruckDetailVO truckDetail() {
        // 获取用户id
        Long userId = UserThreadLocal.getUserId();
        // 根据用户id查询司机相关信息
        TruckDriverDto truckDriverDto = driverFeign.findOneDriver(userId);
        // 根据车辆id查询车辆信息
        TruckDto truckDto = truckFeign.fineById(truckDriverDto.getTruckId());
        // 根据车型id查询车型信息
        TruckTypeDto truckTypeDto = truckTypeFeign.fineById(truckDto.getTruckTypeId());

        // 车辆信息封装为vo
        TruckDetailVO truckDetailVO = BeanUtil.toBean(truckDto, TruckDetailVO.class);
        truckDetailVO.setTruckType(truckTypeDto.getName());
        return truckDetailVO;
    }

    /**
     * 任务数据
     * @param year  年份
     * @param month 月份
     * @return 任务相关统计
     */
    @Override
    public TaskReportVO taskReport(String year, String month) {
        Long userId = UserThreadLocal.getUserId();

        String dateTime = year + "-" + month;
        DriverJobStatisticsDTO driverJobStatisticsDTO = driverJobFeign.jobMonthlyStatistics(String.valueOf(userId),
                dateTime);
        return BeanUtil.toBean(driverJobStatisticsDTO, TaskReportVO.class);
    }
}
