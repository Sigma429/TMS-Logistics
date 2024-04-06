package com.sigma429.sl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;

import com.sigma429.sl.CarriageFeign;
import com.sigma429.sl.dto.CarriageDTO;
import com.sigma429.sl.service.CarriageService;
import com.sigma429.sl.vo.carriage.CarriageVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 运费服务相关实现类
 */
@Service
public class CarriageServiceImpl implements CarriageService {
    @Resource
    private CarriageFeign carriageFeign;

    /**
     * 查询所有运费模板
     *
     * @return 运费模板列表
     */
    @Override
    public List<CarriageVO> findAll() {
        List<CarriageDTO> dtos = carriageFeign.findAll();
        return BeanUtil.copyToList(dtos, CarriageVO.class);
    }

    @Override
    public CarriageVO saveOrUpdate(CarriageDTO carriageDto) {
        CarriageDTO dto = carriageFeign.saveOrUpdate(carriageDto);
        if (ObjectUtil.isEmpty(dto)) {
            return null;
        }
        return BeanUtil.toBean(dto, CarriageVO.class);
    }

    /**
     * 删除运费模板
     *
     * @param id 运费模板id
     */
    @Override
    public void delete(Long id) {
        carriageFeign.delete(id);
    }
}
