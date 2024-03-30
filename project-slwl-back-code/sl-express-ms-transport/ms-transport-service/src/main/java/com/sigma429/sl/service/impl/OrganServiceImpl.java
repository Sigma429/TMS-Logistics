package com.sigma429.sl.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sigma429.sl.domain.OrganDTO;
import com.sigma429.sl.enums.ExceptionEnum;
import com.sigma429.sl.exception.SLException;
import com.sigma429.sl.repository.OrganRepository;
import com.sigma429.sl.service.OrganService;
import com.sigma429.sl.util.BeanUtil;
import com.sigma429.sl.util.ObjectUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName:OrganServiceImpl
 * Package:com.sigma429.sl.service.impl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/30 - 22:27
 * @Version:v1.0
 */
@Service
public class OrganServiceImpl implements OrganService {
    @Resource
    private OrganRepository organRepository;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public OrganDTO findByBid(Long bid) {
        OrganDTO organDTO = organRepository.findByBid(bid);
        if (ObjectUtil.isNotEmpty(organDTO)) {
            return organDTO;
        }
        throw new SLException(ExceptionEnum.ORGAN_NOT_FOUND);
    }

    @Override
    public List<OrganDTO> findByBids(List<Long> bids) {
        List<OrganDTO> organDTOS = organRepository.findByBids(bids);
        if (ObjectUtil.isNotEmpty(organDTOS)) {
            return organDTOS;
        }
        throw new SLException(ExceptionEnum.ORGAN_NOT_FOUND);
    }

    @Override
    public List<OrganDTO> findAll(String name) {
        return organRepository.findAll(name);
    }

    @Override
    public String findAllTree() {
        List<OrganDTO> organList = findAll(null);
        if (CollUtil.isEmpty(organList)) {
            return "";
        }

        // 构造树结构
        List<Tree<Long>> treeNodes = TreeUtil.build(organList, 0L,
                (organDTO, tree) -> {
                    tree.setId(organDTO.getId());
                    tree.setParentId(organDTO.getParentId());
                    tree.putAll(BeanUtil.beanToMap(organDTO));
                    tree.remove("bid");
                });

        try {
            return objectMapper.writeValueAsString(treeNodes);
        } catch (JsonProcessingException e) {
            throw new SLException("序列化json出错！", e);
        }
    }
}
