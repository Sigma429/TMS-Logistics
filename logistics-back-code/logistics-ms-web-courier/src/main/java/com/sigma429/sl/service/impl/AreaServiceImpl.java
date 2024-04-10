package com.sigma429.sl.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.sigma429.sl.base.AreaDto;
import com.sigma429.sl.common.AreaFeign;
import com.sigma429.sl.service.AreaService;
import com.sigma429.sl.util.BeanUtil;
import com.sigma429.sl.vo.area.AreaSimpleVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Resource
    private AreaFeign areaFeign;

    @Override
    public List<AreaSimpleVO> findChildrenAreaByParentId(Long parentId) {

        List<AreaDto> areas = areaFeign.findChildren(parentId);
        if (CollUtil.isEmpty(areas)) {
            return Collections.emptyList();
        }
        List<AreaSimpleVO> areaSimpleVOS = new ArrayList<>();
        areas.forEach(area -> {
            AreaSimpleVO areaSimpleVO = BeanUtil.toBean(area, AreaSimpleVO.class);
            areaSimpleVOS.add(areaSimpleVO);
        });
        return areaSimpleVOS;
    }
}
