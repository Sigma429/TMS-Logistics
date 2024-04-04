package com.sigma429.sl.fallback;

import com.sigma429.sl.RefundRecordFeign;
import com.sigma429.sl.domain.RefundRecordDTO;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.List;


public class RefundRecordFeignFallbackFactory implements FallbackFactory<RefundRecordFeign> {
    @Override
    public RefundRecordFeign create(Throwable cause) {
        return new RefundRecordFeign() {
            @Override
            public List<RefundRecordDTO> findList(Long productOrderNo, Long tradingOrderNo) {
                return null;
            }
        };
    }
}
