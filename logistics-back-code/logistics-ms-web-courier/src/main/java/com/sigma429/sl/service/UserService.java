package com.sigma429.sl.service;


import com.sigma429.sl.util.PageResponse;
import com.sigma429.sl.vo.user.ServiceScopeVO;
import com.sigma429.sl.vo.user.UserSimpleInfoVO;
import com.sigma429.sl.vo.user.UsersInfoVO;

public interface UserService {
    /**
     * 通过token获取用户信息
     * @return 用户信息
     */
    UsersInfoVO get();

    /**
     * 查询今天同网点有排班的其他快递员列表
     * @param page     页码
     * @param pageSize 页面大小
     * @param keyword  关键词
     * @return 快递员列表
     */
    PageResponse<UserSimpleInfoVO> sameAgency(Integer page, Integer pageSize, String keyword);

    /**
     * 获取作业范围
     * @return 作业范围数据
     */
    ServiceScopeVO findScope();
}
