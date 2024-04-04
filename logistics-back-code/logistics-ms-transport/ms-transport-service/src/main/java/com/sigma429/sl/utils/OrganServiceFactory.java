package com.sigma429.sl.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.sigma429.sl.enums.ExceptionEnum;
import com.sigma429.sl.enums.OrganTypeEnum;
import com.sigma429.sl.exception.SLException;
import com.sigma429.sl.service.AgencyService;
import com.sigma429.sl.service.IService;
import com.sigma429.sl.service.OLTService;
import com.sigma429.sl.service.TLTService;

/**
 * 根据type选择对应的service返回
 */
public class OrganServiceFactory {

    public static IService getBean(Integer type) {
        OrganTypeEnum organTypeEnum = OrganTypeEnum.codeOf(type);
        if (null == organTypeEnum) {
            throw new SLException(ExceptionEnum.ORGAN_TYPE_ERROR);
        }

        switch (organTypeEnum) {
            case AGENCY: {
                return SpringUtil.getBean(AgencyService.class);
            }
            case OLT: {
                return SpringUtil.getBean(OLTService.class);
            }
            case TLT: {
                return SpringUtil.getBean(TLTService.class);
            }
        }
        return null;
    }

}
