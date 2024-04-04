package com.sigma429.sl.vo.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel("消息列表")
@Data
public class MessageVO {
    @ApiModelProperty("消息id")
    private Long id;

    @ApiModelProperty("消息标题")
    private String title;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty(value = "1：用户端，2：司机端，3：快递员端，4：后台管理系统")
    private Integer bussinessType;

    @ApiModelProperty("消息接受者")
    private Long userId;

    @ApiModelProperty("消息类型，300：快递员端公告，301：寄件相关消息，302：签收相关消息，303：快件取消消息，200：司机端公告，201：司机端系统通知")
    private Integer contentType;

    @ApiModelProperty("消息是否已读，0：未读，1：已读")
    private Integer isRead;

    @ApiModelProperty("读时间")
    private LocalDateTime readTime;

    @ApiModelProperty("相关id")
    private Long relevantId;

    @ApiModelProperty("创建时间")
    private LocalDateTime created;
}
