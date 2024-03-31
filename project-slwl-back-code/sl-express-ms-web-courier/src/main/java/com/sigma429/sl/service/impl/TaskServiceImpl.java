package com.sigma429.sl.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.itheima.auth.sdk.dto.UserDTO;
import com.sigma429.sl.*;
import com.sigma429.sl.common.AreaFeign;
import com.sigma429.sl.common.MQFeign;
import com.sigma429.sl.common.MessageFeign;
import com.sigma429.sl.constant.Constants;
import com.sigma429.sl.service.RealNameVerifyService;
import com.sigma429.sl.service.TaskService;
import com.sigma429.sl.util.PageResponse;
import com.sigma429.sl.util.UserThreadLocal;
import com.sigma429.sl.vo.CourierMsg;
import com.sigma429.sl.vo.task.*;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Resource
    private PickupDispatchTaskFeign pickupDispatchTaskFeign;
    @Resource
    private CarriageFeign carriageFeign;
    @Resource
    private AreaFeign areaFeign;
    @Resource
    private OrderFeign orderFeign;
    @Resource
    private CargoFeign cargoFeign;
    @Resource
    private TransportOrderFeign transportOrderFeign;
    @Resource
    private MemberFeign memberFeign;
    @Resource
    private MessageFeign messageFeign;
    @Resource
    private TransportInfoFeign transportInfoFeign;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RealNameVerifyService realNameVerifyService;
    @Resource
    private MQFeign mqFeign;
    // @Resource
    // private CourierTaskFeign courierTaskFeign;

    /**
     * 实名认证开关，默认关闭
     */
    @Value("${real-name-registration.enable}")
    private String realNameVerify;

    /**
     * 计算运费
     * @param calculateVO 运费计算对象
     * @return 运费 体积 重量
     */
    @Override
    public CarriageVO calculate(CarriageCalculateVO calculateVO) {
        return null;
    }

    /**
     * 分页查询任务列表
     * @param vo 取/派件查询模型
     * @return 任务列表
     */
    @Override
    public PageResponse<TaskVO> pageQuery(TaskQueryVO vo) {
        return null;
    }


    /**
     * 任务详情
     * @param id 任务id
     * @return 任务详情
     */
    @Override
    public TaskInfoVO detail(String id) {
        return null;
    }

    /**
     * 身份验证
     * @param vo 身份信息
     * @return 是否通过验证
     */
    @Override
    public RealNameVerifyVO idCardCheck(TaskIdCardCheckVO vo) {
        return null;
    }

    /**
     * 取件
     * @param vo 取件对象
     * @return 是否成功
     */
    @Override
    @GlobalTransactional
    public boolean pickup(TaskPickupVO vo) {
        // 7.发送取件成功的消息
        String msg = "";
        this.mqFeign.sendMsg(Constants.MQ.Exchanges.COURIER, Constants.MQ.RoutingKeys.COURIER_PICKUP, msg);
        return true;
    }

    /**
     * 批量转单
     * @param dto 转单对象
     */
    @Override
    public void batchTransfer(TaskBatchTransferVO dto) {

    }

    /**
     * 取消任务
     * @param vo 取消任务对象
     */
    @Override
    @GlobalTransactional
    public void cancel(TasksCancelVO vo) {

    }

    /**
     * 删除任务(逻辑删除)
     * @param id 任务id
     */
    @Override
    public void delete(String id) {

    }

    /**
     * 批量删除任务
     * @param taskBatchVO 任务id列表
     */
    @Override
    public void batchDelete(TaskBatchVO taskBatchVO) {

    }

    /**
     * 签收任务
     * @param vo 签收对象
     */
    @Override
    public void sign(TaskSignVO vo) {
    }

    /**
     * 拒收任务
     * @param id 任务id
     */
    @Override
    public void reject(String id) {

    }

    /**
     * 运单跟踪
     * @param id 运单id
     * @return 运单跟踪信息
     */
    @Override
    public List<TransportOrderPointVO> tracks(String id) {
        return null;
    }

    /**
     * 今日任务数据统计
     * @return 今日任务统计数据
     */
    @Override
    public TaskStatisticsVO taskStatistics() {
        return null;
    }


    /**
     * 展示最近查找运单号
     * @return 最近查找运单号
     */
    @Override
    public List<String> recentSearch() {
        return null;
    }

    /**
     * 标记为最近查找
     * @param transportOrderId 运单号
     */
    @Override
    public void markRecent(String transportOrderId) {

    }

    /**
     * 清空最近查找
     */
    @Override
    public void recentSearchDeleteAll() {
    }

    /**
     * 搜索任务
     * @param taskSearchVO 搜索条件
     * @return 分页结果
     */
    @Override
    public PageResponse<TaskVO> search(TaskSearchVO taskSearchVO) {
        return null;
    }
}








