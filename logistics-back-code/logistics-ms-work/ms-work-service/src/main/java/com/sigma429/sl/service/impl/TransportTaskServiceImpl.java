package com.sigma429.sl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sigma429.sl.DriverJobFeign;
import com.sigma429.sl.dto.TaskTransportUpdateDTO;
import com.sigma429.sl.dto.TransportTaskDTO;
import com.sigma429.sl.dto.request.*;
import com.sigma429.sl.dto.response.DriverJobDTO;
import com.sigma429.sl.dto.response.TransportTaskMonthlyDistanceDTO;
import com.sigma429.sl.dto.response.TransportTaskStatusCountDTO;
import com.sigma429.sl.entity.TransportOrderTaskEntity;
import com.sigma429.sl.entity.TransportTaskEntity;
import com.sigma429.sl.enums.WorkExceptionEnum;
import com.sigma429.sl.enums.transporttask.TransportTaskStatus;
import com.sigma429.sl.exception.SLException;
import com.sigma429.sl.mapper.TransportTaskMapper;
import com.sigma429.sl.service.TransportOrderTaskService;
import com.sigma429.sl.service.TransportTaskService;
import com.sigma429.sl.util.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 运输任务表 服务实现类
 */
@Service
public class TransportTaskServiceImpl extends
        ServiceImpl<TransportTaskMapper, TransportTaskEntity> implements TransportTaskService {

    @Resource
    private TransportOrderTaskService transportOrderTaskService;
    @Resource
    private DriverJobFeign driverJobFeign;

    @Override
    @Transactional
    public Boolean updateStatus(Long id, TransportTaskStatus status) {
        return null;
    }

    @Override
    public PageResponse<TransportTaskDTO> findByPage(TransportTaskPageQueryDTO pageQueryDTO) {
        return null;
    }

    @Override
    public List<TransportTaskEntity> findAll(List<Long> ids, Long id, Integer status, TransportTaskDTO dto) {
        return null;
    }

    /**
     * 完成运输任务
     * @param transportTaskCompleteDTO 交付对象
     */
    @Override
    public void completeTransportTask(TransportTaskCompleteDTO transportTaskCompleteDTO) {

    }

    /**
     * 开始运输任务
     * @param transportTaskStartDTO 提货对象
     */
    @Override
    public void startTransportTask(TransportTaskStartDTO transportTaskStartDTO) {

    }

    @Override
    public List<String> queryTransportOrderIdListById(Long id) {
        return null;
    }

    @Override
    public List<TransportTaskDTO> findAllByOrderIdOrTaskId(String transportOrderId, Long transportTaskId) {
        return null;
    }

    @Override
    public TransportTaskDTO findById(Long id) {
        return null;
    }

    @Override
    public List<TransportTaskStatusCountDTO> groupByStatus() {
        return null;
    }

    @Transactional
    @Override
    public void adjust(TaskTransportUpdateDTO dto) {

    }

    /**
     * 延迟提货
     * @param transportTaskDelayDeliveryDTO 延迟提货对象
     */
    @Override
    public void delayedDelivery(TransportTaskDelayDeliveryDTO transportTaskDelayDeliveryDTO) {

    }

    /**
     * 任务里程统计
     * @param transportTaskIds 运输任务id列表
     * @param month            月份，格式：2022-06
     * @return 每日里程数据
     */
    @Override
    public List<TransportTaskMonthlyDistanceDTO> monthlyDistanceStatistics(List<String> transportTaskIds,
                                                                           String month) {
        return null;
    }

    /**
     * 根据起始机构查询运输任务id列表
     * @param startAgencyId 起始机构id
     * @param endAgencyId   结束机构id
     * @return 运输任务id列表
     */
    @Override
    public List<Long> findByAgencyId(Long startAgencyId, Long endAgencyId) {
        return null;
    }

    /**
     * 根据车辆ID和状态统计
     * @param truckId 车辆ID
     * @return 个数
     */
    @Override
    public Long countByTruckId(Long truckId) {
        return null;
    }
}
