package com.sigma429.sl.service;


import com.sigma429.sl.util.PageResponse;
import com.sigma429.sl.vo.message.MessageQueryVO;
import com.sigma429.sl.vo.message.MessageVO;
import com.sigma429.sl.vo.message.MessagesHomeVO;
import com.sigma429.sl.vo.message.NewNoticeInfoVO;

public interface MessageService {
    /**
     * 首页相关消息
     * @return 首页消息对象
     */
    MessagesHomeVO homeMessage();

    /**
     * 获取新系统通知信息
     * @return 新系统通知消息
     */
    NewNoticeInfoVO notice();

    /**
     * 标记已读
     * @param id 消息id
     */
    void update2Read(Long id);

    /**
     * 全部已读
     * @param contentType 消息类型，300：快递员端公告，301：寄件相关消息，302：签收相关消息，303：快件取消消息，304派件消息
     */
    void readAll(Integer contentType);

    /**
     * 分页查询消息列表
     * @param messageQueryVO 消息查询对象
     * @return 分页数据
     */
    PageResponse<MessageVO> page(MessageQueryVO messageQueryVO);
}
