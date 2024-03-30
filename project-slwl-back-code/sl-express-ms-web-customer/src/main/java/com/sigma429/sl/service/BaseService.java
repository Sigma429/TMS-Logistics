package com.sigma429.sl.service;


import com.sigma429.sl.base.AreaDto;
import com.sigma429.sl.vo.base.AreaSimpleVO;
import com.sigma429.sl.vo.base.GoodsTypeVO;

import java.util.List;

/**
 * 基础服务
 * 文件上传 排班 工作模式 行政机构
 */
public interface BaseService {
    /**
     * 行政机构
     * 用于地址选择 省市县三级行政机构
     * 根据父行政机构节点查询子行政机构节点
     *
     * @param parentId 父节点ID
     * @return 子节点
     */
    List<AreaSimpleVO> findChildrenAreaByParentId(Long parentId);

    /**
     * 简要行政机构信息转换
     *
     * @param area 行政机构DTO
     * @return 简要行政机构VO
     */
    AreaSimpleVO parseArea2Vo(AreaDto area);

    /**
     * 根据id获取货物类型详情
     *
     * @param id 货物类型id
     * @return 货物类型信息
     */
    GoodsTypeVO fineGoodsTypeById(Long id);
}
