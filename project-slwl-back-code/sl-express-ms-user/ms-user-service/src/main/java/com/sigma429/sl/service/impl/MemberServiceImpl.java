package com.sigma429.sl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sigma429.sl.entity.MemberEntity;
import com.sigma429.sl.mapper.MemberMapper;
import com.sigma429.sl.service.MemberService;
import org.springframework.stereotype.Service;


/**
 * 用户表  服务类实现
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity>
        implements MemberService {

}
