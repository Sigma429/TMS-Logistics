package com.sigma429.sl.service.impl;

import com.sigma429.sl.base.AreaDto;
import com.sigma429.sl.service.BaseService;
import com.sigma429.sl.vo.base.AreaSimpleVO;
import com.sigma429.sl.vo.base.GoodsTypeVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:BaseServiceImpl
 * Package:com.sigma429.sl.service.impl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/06 - 13:32
 * @Version:v1.0
 */
@Service
public class BaseServiceImpl implements BaseService {
    @Override
    public List<AreaSimpleVO> findChildrenAreaByParentId(Long parentId) {
        return null;
    }

    @Override
    public AreaSimpleVO parseArea2Vo(AreaDto area) {
        return null;
    }

    @Override
    public GoodsTypeVO fineGoodsTypeById(Long id) {
        return null;
    }
}
