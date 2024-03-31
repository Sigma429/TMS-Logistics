package com.sigma429.sl.service.impl;

import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.itheima.em.sdk.EagleMapTemplate;
import com.itheima.em.sdk.enums.ProviderEnum;
import com.itheima.em.sdk.vo.Coordinate;
import com.sigma429.sl.domain.DispatchConfigurationDTO;
import com.sigma429.sl.domain.OrganDTO;
import com.sigma429.sl.domain.TransportLineNodeDTO;
import com.sigma429.sl.domain.TransportLineSearchDTO;
import com.sigma429.sl.entity.line.TransportLine;
import com.sigma429.sl.entity.node.AgencyEntity;
import com.sigma429.sl.entity.node.BaseEntity;
import com.sigma429.sl.entity.node.OLTEntity;
import com.sigma429.sl.entity.node.TLTEntity;
import com.sigma429.sl.enums.DispatchMethodEnum;
import com.sigma429.sl.enums.ExceptionEnum;
import com.sigma429.sl.enums.TransportLineEnum;
import com.sigma429.sl.exception.SLException;
import com.sigma429.sl.repository.TransportLineRepository;
import com.sigma429.sl.service.CostConfigurationService;
import com.sigma429.sl.service.DispatchConfigurationService;
import com.sigma429.sl.service.OrganService;
import com.sigma429.sl.service.TransportLineService;
import com.sigma429.sl.util.BeanUtil;
import com.sigma429.sl.util.ObjectUtil;
import com.sigma429.sl.util.PageResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ClassName:TransportLineServiceImpl
 * Package:com.sigma429.sl.service.impl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/31 - 15:59
 * @Version:v1.0
 */
@Service
public class TransportLineServiceImpl implements TransportLineService {
    @Resource
    private TransportLineRepository transportLineRepository;
    @Resource
    private EagleMapTemplate eagleMapTemplate;
    @Resource
    private OrganService organService;
    @Resource
    private DispatchConfigurationService dispatchConfigurationService;
    @Resource
    private CostConfigurationService costConfigurationService;

    // 新增路线业务规则：干线：起点终点无顺序，支线：起点必须是二级转运中心，接驳路线：起点必须是网点
    @Override
    public Boolean createLine(TransportLine transportLine) {
        TransportLineEnum transportLineEnum = TransportLineEnum.codeOf(transportLine.getType());
        if (transportLineEnum == null) {
            throw new SLException(ExceptionEnum.TRANSPORT_LINE_TYPE_ERROR);
        }

        if (ObjectUtil.equal(transportLine.getStartOrganId(), transportLine.getEndOrganId())) {
            // 起点终点不能相同
            throw new SLException(ExceptionEnum.TRANSPORT_LINE_ORGAN_CANNOT_SAME);
        }

        BaseEntity firstNode;
        BaseEntity secondNode;
        switch (transportLineEnum) {
            case TRUNK_LINE: {
                // 干线
                firstNode = OLTEntity.builder().bid(transportLine.getStartOrganId()).build();
                secondNode = OLTEntity.builder().bid(transportLine.getEndOrganId()).build();
                break;
            }
            case BRANCH_LINE: {
                // 支线，起点必须是 二级转运中心
                firstNode = TLTEntity.builder().bid(transportLine.getStartOrganId()).build();
                secondNode = OLTEntity.builder().bid(transportLine.getEndOrganId()).build();
                break;
            }
            case CONNECT_LINE: {
                // 接驳路线，起点必须是 网点
                firstNode = AgencyEntity.builder().bid(transportLine.getStartOrganId()).build();
                secondNode = TLTEntity.builder().bid(transportLine.getEndOrganId()).build();
                break;
            }
            default: {
                throw new SLException(ExceptionEnum.TRANSPORT_LINE_TYPE_ERROR);
            }
        }

        if (ObjectUtil.hasEmpty(firstNode, secondNode)) {
            throw new SLException(ExceptionEnum.START_END_ORGAN_NOT_FOUND);
        }

        // 判断路线是否已经存在
        Long count = this.transportLineRepository.queryCount(firstNode, secondNode);
        if (count > 0) {
            throw new SLException(ExceptionEnum.TRANSPORT_LINE_ALREADY_EXISTS);
        }

        transportLine.setId(null);
        transportLine.setCreated(System.currentTimeMillis());
        transportLine.setUpdated(transportLine.getCreated());
        // 补充信息
        this.infoFromMap(firstNode, secondNode, transportLine);

        count = this.transportLineRepository.create(firstNode, secondNode, transportLine);
        return count > 0;
    }

    /**
     * 通过地图查询距离、时间，计算成本
     * @param firstNode     开始节点
     * @param secondNode    结束节点
     * @param transportLine 路线对象
     */
    private void infoFromMap(BaseEntity firstNode, BaseEntity secondNode, TransportLine transportLine) {
        // 查询节点数据
        OrganDTO startOrgan = this.organService.findByBid(firstNode.getBid());
        if (ObjectUtil.hasEmpty(startOrgan, startOrgan.getLongitude(), startOrgan.getLatitude())) {
            throw new SLException("请先完善机构信息");
        }
        OrganDTO endOrgan = this.organService.findByBid(secondNode.getBid());
        if (ObjectUtil.hasEmpty(endOrgan, endOrgan.getLongitude(), endOrgan.getLatitude())) {
            throw new SLException("请先完善机构信息");
        }

        // 查询地图服务商
        Coordinate origin = new Coordinate(startOrgan.getLongitude(), startOrgan.getLatitude());
        Coordinate destination = new Coordinate(endOrgan.getLongitude(), endOrgan.getLatitude());
        // 设置高德地图参数，默认是不返回预计耗时的，需要额外设置参数
        Map<String, Object> param = MapUtil.<String, Object>builder().put("show_fields", "cost").build();
        String driving = this.eagleMapTemplate.opsForDirection().driving(ProviderEnum.AMAP, origin, destination, param);
        if (StrUtil.isEmpty(driving)) {
            return;
        }
        JSONObject jsonObject = JSONUtil.parseObj(driving);
        // 时间，单位：秒
        Long duration = Convert.toLong(jsonObject.getByPath("route.paths[0].cost.duration"), -1L);
        transportLine.setTime(duration);
        // 距离，单位：米
        Double distance = Convert.toDouble(jsonObject.getByPath("route.paths[0].distance"), -1d);
        transportLine.setDistance(NumberUtil.round(distance, 0).doubleValue());

        // 总成本 = 每公里平均成本 * 距离（单位：米） / 1000
        Double cost = costConfigurationService.findCostByType(transportLine.getType());
        transportLine.setCost(NumberUtil.round(cost * distance / 1000, 2).doubleValue());
    }

    @Override
    public Boolean updateLine(TransportLine transportLine) {
        // 先查后改
        TransportLine transportLineData = this.queryById(transportLine.getId());
        if (transportLineData == null) {
            throw new SLException(ExceptionEnum.TRANSPORT_LINE_NOT_FOUND);
        }

        // 拷贝数据，忽略null值以及不能修改的字段
        BeanUtil.copyProperties(transportLine, transportLineData, CopyOptions.create().setIgnoreNullValue(true)
                .setIgnoreProperties("type", "startOrganId", "startOrganName", "endOrganId", "endOrganName"));

        transportLineData.setUpdated(System.currentTimeMillis());
        Long count = this.transportLineRepository.update(transportLineData);
        return count > 0;
    }

    @Override
    public Boolean deleteLine(Long id) {
        Long count = this.transportLineRepository.remove(id);
        return count > 0;
    }

    @Override
    public PageResponse<TransportLine> queryPageList(TransportLineSearchDTO transportLineSearchDTO) {
        return this.transportLineRepository.queryPageList(transportLineSearchDTO);
    }

    @Override
    public TransportLineNodeDTO queryShortestPath(Long startId, Long endId) {
        AgencyEntity start = AgencyEntity.builder().bid(startId).build();
        AgencyEntity end = AgencyEntity.builder().bid(endId).build();
        if (ObjectUtil.hasEmpty(start, end)) {
            throw new SLException(ExceptionEnum.START_END_ORGAN_NOT_FOUND);
        }
        return this.transportLineRepository.findShortestPath(start, end);
    }

    @Override
    public TransportLineNodeDTO findLowestPath(Long startId, Long endId) {
        AgencyEntity start = AgencyEntity.builder().bid(startId).build();
        AgencyEntity end = AgencyEntity.builder().bid(endId).build();

        if (ObjectUtil.hasEmpty(start, end)) {
            throw new SLException(ExceptionEnum.START_END_ORGAN_NOT_FOUND);
        }

        List<TransportLineNodeDTO> pathList = this.transportLineRepository.findPathList(start, end, 10, 1);
        if (CollUtil.isNotEmpty(pathList)) {
            return pathList.get(0);
        }
        return null;
    }

    @Override
    public TransportLineNodeDTO queryPathByDispatchMethod(Long startId, Long endId) {
        // 调度方式配置
        DispatchConfigurationDTO configuration = this.dispatchConfigurationService.findConfiguration();
        int method = configuration.getDispatchMethod();

        // 调度方式，1转运次数最少，2成本最低
        if (ObjectUtil.equal(DispatchMethodEnum.SHORTEST_PATH.getCode(), method)) {
            return this.queryShortestPath(startId, endId);
        } else {
            return this.findLowestPath(startId, endId);
        }
    }

    @Override
    public List<TransportLine> queryByIds(Long... ids) {
        return this.transportLineRepository.queryByIds(ids);
    }

    @Override
    public TransportLine queryById(Long id) {
        return this.transportLineRepository.queryById(id);
    }
}
