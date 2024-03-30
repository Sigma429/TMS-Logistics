package com.sigma429.sl.exception;

import com.sigma429.sl.entity.FailMsgEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MsgException extends RuntimeException {

    private FailMsgEntity failMsgEntity;

}
