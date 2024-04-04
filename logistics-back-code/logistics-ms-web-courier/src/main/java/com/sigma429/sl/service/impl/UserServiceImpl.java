package com.sigma429.sl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.itheima.auth.sdk.dto.UserDTO;
import com.sigma429.sl.ServiceScopeFeign;
import com.sigma429.sl.common.WorkSchedulingFeign;
import com.sigma429.sl.service.UserService;
import com.sigma429.sl.util.PageResponse;
import com.sigma429.sl.vo.user.ServiceScopeVO;
import com.sigma429.sl.vo.user.UserSimpleInfoVO;
import com.sigma429.sl.vo.user.UsersInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private WorkSchedulingFeign workSchedulingFeign;

    @Resource
    private ServiceScopeFeign serviceScopeFeign;

    /**
     * 通过token获取用户信息
     * @return 用户信息
     */
    @Override
    public UsersInfoVO get() {
        return null;
    }

    /**
     * 查询今天同网点有排班的其他快递员列表
     * @param page     页码
     * @param pageSize 页面大小
     * @param keyword  关键词
     * @return 快递员列表
     */
    @Override
    public PageResponse<UserSimpleInfoVO> sameAgency(Integer page, Integer pageSize, String keyword) {
        return null;
    }

    /**
     * 获取作业范围
     * @return 作业范围数据
     */
    @Override
    public ServiceScopeVO findScope() {
        return null;
    }
}
