package com.sigma429.sl.service;


import com.sigma429.sl.vo.area.AreaSimpleVO;

import java.util.List;

public interface AreaService {
    List<AreaSimpleVO> findChildrenAreaByParentId(Long parentId);
}
