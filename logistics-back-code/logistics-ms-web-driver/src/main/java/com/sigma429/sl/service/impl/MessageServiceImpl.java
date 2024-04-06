package com.sigma429.sl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.sigma429.sl.base.MessageDTO;
import com.sigma429.sl.base.MessageQueryDTO;
import com.sigma429.sl.common.MessageFeign;
import com.sigma429.sl.constants.MessageConstants;
import com.sigma429.sl.enums.MessageBussinessTypeEnum;
import com.sigma429.sl.service.MessageService;
import com.sigma429.sl.util.PageResponse;
import com.sigma429.sl.util.UserThreadLocal;
import com.sigma429.sl.vo.response.MessageVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageFeign messageFeign;

    /**
     * 分页查询消息列表
     * @param contentType 消息类型，200：司机端公告，201：司机端系统通知
     * @param page        页码
     * @param pageSize    页面大小
     * @return 分页数据
     */
    @Override
    public PageResponse<MessageVO> page(Integer contentType, Integer page, Integer pageSize) {
        // 构建查询条件
        MessageQueryDTO messageQueryDTO = new MessageQueryDTO();
        messageQueryDTO.setPage(page);
        messageQueryDTO.setPageSize(pageSize);
        messageQueryDTO.setBussinessType(MessageBussinessTypeEnum.DRIVER.getCode());
        messageQueryDTO.setContentType(contentType);
        messageQueryDTO.setUserId(UserThreadLocal.getUserId());

        // 查询消息数据
        PageResponse<MessageDTO> pageResponse = messageFeign.page(messageQueryDTO);

        // dto转为vo.组装分页数据
        return PageResponse.of(pageResponse, MessageVO.class);
    }

    /**
     * 标记已读
     * @param id 消息id
     */
    @Override
    public void update2Read(Long id) {
        messageFeign.update2Read(id);
    }

    /**
     * 根据类型查询未读消息数量
     * @param contentType 消息类型，200：司机端公告，201：司机端系统通知;特别的，0代表查询全部未读
     * @return 未读消息条数
     */
    @Override
    public Integer countType(Integer contentType) {
        // 构件查询条件
        MessageQueryDTO messageQueryDTO = new MessageQueryDTO();
        messageQueryDTO.setBussinessType(MessageBussinessTypeEnum.DRIVER.getCode());
        messageQueryDTO.setUserId(UserThreadLocal.getUserId());
        messageQueryDTO.setIsRead(MessageConstants.UNREAD);

        // 0代表查询全部未读,否则传入具体类型
        if (ObjectUtil.notEqual(contentType, 0)) {
            messageQueryDTO.setContentType(contentType);
        }

        // 查询未读消息条数
        return messageFeign.countType(messageQueryDTO);
    }
}
