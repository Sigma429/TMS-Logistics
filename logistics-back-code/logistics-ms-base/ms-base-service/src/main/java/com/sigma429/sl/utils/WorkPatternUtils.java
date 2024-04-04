package com.sigma429.sl.utils;


import com.sigma429.sl.constant.WorkConstants;
import com.sigma429.sl.entity.base.WorkPatternEntity;
import com.sigma429.sl.enums.WorkPatternEnum;

public class WorkPatternUtils {

    public static String toWorkDate(WorkPatternEntity entity) {
        byte workPatternType = entity.getWorkPatternType();
        // 周期制
        if (workPatternType == WorkPatternEnum.Weeks.getType()) {
            String workDate = String.format(WorkConstants.WORK_DATE_WEEKS, entity.getMonday(), entity.getTuesday(),
                    entity.getWednesday(), entity.getThursday(), entity.getFriday(),
                    entity.getSaturday(), entity.getSunday());
            return workDate.replace("1", "上").replace("2", "休");
        } else {
            return String.format(WorkConstants.WORK_DATE_CONTINUITYS,
                    entity.getWorkDayNum(), entity.getRestDayNum());
        }
    }
}
