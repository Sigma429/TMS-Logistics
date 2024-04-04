package com.sigma429.sl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 失败消息记录表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sl_fail_msg")
public class FailMsgEntity extends BaseEntity {
    // 消息id
    private String msgId;
    // 交换机
    private String exchange;
    // 路由key
    private String routingKey;
    // 消息内容
    private String msg;
    // 失败原因
    private String reason;

}
