package com.sigma429.sl.service;


import com.sigma429.sl.dto.SendResultDTO;
import com.sigma429.sl.dto.SmsInfoDTO;

import java.util.List;

public interface SmsService {

    /**
     * 发送短信
     * @param smsInfoDTO 发送短信信息
     * @return 发送记录列表
     */
    List<SendResultDTO> sendSms(SmsInfoDTO smsInfoDTO);
}
