package com.sigma429.sl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sigma429.sl.PickupDispatchTaskFeign;
import com.sigma429.sl.base.LatestMessageDTO;
import com.sigma429.sl.base.MessageDTO;
import com.sigma429.sl.base.MessageQueryDTO;
import com.sigma429.sl.common.MessageFeign;
import com.sigma429.sl.constants.MessageConstants;
import com.sigma429.sl.enums.MessageBussinessTypeEnum;
import com.sigma429.sl.enums.MessageContentTypeEnum;
import com.sigma429.sl.exception.SLWebException;
import com.sigma429.sl.service.MessageService;
import com.sigma429.sl.util.PageResponse;
import com.sigma429.sl.util.UserThreadLocal;
import com.sigma429.sl.vo.message.MessageQueryVO;
import com.sigma429.sl.vo.message.MessageVO;
import com.sigma429.sl.vo.message.MessagesHomeVO;
import com.sigma429.sl.vo.message.NewNoticeInfoVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageFeign messageFeign;
    @Resource
    private PickupDispatchTaskFeign pickupDispatchTaskFeign;

    /**
     * 首页相关消息
     * @return 首页消息对象
     */
    @Override
    public MessagesHomeVO homeMessage() {
        return null;
    }

    /**
     * 获取新系统通知信息
     * @return 新系统通知消息
     */
    @Override
    public NewNoticeInfoVO notice() {
        return null;
    }


    /**
     * 标记已读
     * @param id 消息id
     */
    @Override
    public void update2Read(Long id) {

    }

    /**
     * 全部已读
     * @param contentType 消息类型，300：快递员端公告，301：寄件相关消息，302：签收相关消息，303：快件取消消息，304派件消息
     */
    @Override
    public void readAll(Integer contentType) {

    }

    /**
     * 分页查询消息列表
     * @param messageQueryVO 消息查询对象
     * @return 分页数据
     */
    @Override
    public PageResponse<MessageVO> page(MessageQueryVO messageQueryVO) {
        return null;
    }
}
