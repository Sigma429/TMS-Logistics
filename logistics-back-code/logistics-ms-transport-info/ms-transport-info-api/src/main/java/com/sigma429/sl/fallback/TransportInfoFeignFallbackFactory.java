package com.sigma429.sl.fallback;

import com.sigma429.sl.TransportInfoFeign;
import com.sigma429.sl.dto.TransportInfoDTO;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @author zzj
 * @version 1.0
 */
public class TransportInfoFeignFallbackFactory implements FallbackFactory<TransportInfoFeign> {
    @Override
    public TransportInfoFeign create(Throwable cause) {
        return new TransportInfoFeign() {
            @Override
            public TransportInfoDTO queryByTransportOrderId(String transportOrderId) {
                return null;
            }
        };
    }
}
