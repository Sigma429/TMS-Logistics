package com.sigma429.sl.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.json.JSONObject;
import com.sigma429.sl.MemberFeign;
import com.sigma429.sl.constant.Constants;
import com.sigma429.sl.dto.MemberDTO;
import com.sigma429.sl.enums.StatusEnum;
import com.sigma429.sl.exception.SLWebException;
import com.sigma429.sl.service.MemberService;
import com.sigma429.sl.service.RealNameVerifyService;
import com.sigma429.sl.service.TokenService;
import com.sigma429.sl.service.WechatService;
import com.sigma429.sl.util.BeanUtil;
import com.sigma429.sl.util.ObjectUtil;
import com.sigma429.sl.util.UserThreadLocal;
import com.sigma429.sl.vo.user.MemberVO;
import com.sigma429.sl.vo.user.RealNameVerifyVO;
import com.sigma429.sl.vo.user.UserLoginRequestVO;
import com.sigma429.sl.vo.user.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * ClassName:MemberServiceImpl
 * Package:com.sigma429.sl.service.impl
 * Description:用户管理
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/06 - 13:33
 * @Version:v1.0
 */
@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberFeign memberFeign;

    @Resource
    private TokenService tokenService;

    @Resource
    private WechatService wechatService;

    @Resource
    private RealNameVerifyService realNameVerifyService;
    // 实名认证默认关闭
    @Value("${real-name-registration.enable}")
    private String realNameVerify;

    @Override
    public MemberVO detail(Long userId) {
        log.info("查找用户信息:{}", userId);
        MemberDTO member = memberFeign.detail(userId);
        log.info("查找用户信息:{} Result:{}", userId, member);
        MemberVO memberVO = BeanUtil.toBean(member, MemberVO.class);
        memberVO.setName(DesensitizedUtil.chineseName(member.getName()));
        memberVO.setIdCardNo(DesensitizedUtil.idCardNum(memberVO.getIdCardNo(), 6, 4));
        return memberVO;
    }

    @Override
    public void save(MemberDTO user) {
        memberFeign.save(user);
    }

    @Override
    public MemberDTO getByOpenid(String openid) {
        return memberFeign.detailByOpenId(openid);
    }

    @Override
    public UserLoginVO login(UserLoginRequestVO userLoginRequestVO) throws IOException {
        // 1 调用微信开放平台小程序的api，根据code获取openid
        JSONObject jsonObject = wechatService.getOpenid(userLoginRequestVO.getCode());
        // 2 若code不正确，则获取不到openid，响应失败
        if (ObjectUtil.isNotEmpty(jsonObject.getInt("errorcode"))) {
            throw new SLWebException(jsonObject.getStr("errmsg"));
        }
        String openId = jsonObject.getStr("openId");
        /*
         * 3 根据openid从数据库查询用户
         * 3.1 如果为新用户，此处返回为null
         * 3.2 如果为已经登录过的老用户，此处返回为user对象 （包含openId,phone,unionId等字段）
         */
        MemberDTO user = getByOpenid(openId);
        /*
         * 4 构造用户数据，设置openId,unionId
         * 4.1 如果user为null，则为新用户，需要构建新的user对象，并设置openId,unionId
         * 4.2 如果user不为null，则为老用户，无需设置openId,unionId
         */
        user = ObjectUtil.isNotEmpty(user) ? user : MemberDTO.builder()
                // openId
                .openId(openId)
                // 平台唯一ID
                .authId(jsonObject.getStr("unionid"))
                .build();
        // 5 调用微信开放平台小程序的api获取微信绑定的手机号
        String phone = wechatService.getPhone(userLoginRequestVO.getPhoneCode());
        /*
         * 6 新用户绑定手机号或者老用户更新手机号
         * 6.1 如果user.getPhone()为null，则为新用户，需要设置手机号，并保存数据库
         * 6.2 如果user.getPhone()不为null，但是与微信获取到的手机号不一样 则表示用户改了微信绑定的手机号，需要设置手机号，并保存数据库
         * 以上俩种情况，都需要重新设置手机号，并保存数据库
         */
        if (ObjectUtil.notEqual(user.getPhone(), phone)) {
            user.setPhone(phone);
            save(user);
        }
        // 7 如果为新用户，查询数据库获取用户ID
        if (ObjectUtil.isEmpty(user.getId())) {
            user = getByOpenid(openId);
        }
        // 8 将用户ID存入token
        Map<String, Object> claims =
                MapUtil.<String, Object>builder().put(Constants.GATEWAY.USER_ID, user.getId()).build();
        // 9 封装用户信息和双token，响应结果
        return UserLoginVO
                .builder()
                .openid(openId)
                .accessToken(tokenService.createAccessToken(claims))
                .refreshToken(tokenService.createRefreshToken(claims))
                .binding(StatusEnum.NORMAL.getCode())
                .build();
    }

    @Override
    public RealNameVerifyVO realNameVerify(RealNameVerifyVO verifyVo) {
        return null;
    }

    @Override
    public void del() {
        Long userId = UserThreadLocal.getUserId();
        memberFeign.del(userId);
    }

    @Override
    public void update(MemberVO vo) {
        Long userId = UserThreadLocal.getUserId();
        MemberDTO memberDTO = BeanUtil.toBean(vo, MemberDTO.class);
        memberDTO.setId(userId);
        memberFeign.update(userId, memberDTO);
    }

    @Override
    public UserLoginVO refresh(String refreshToken) {
        return tokenService.refreshToken(refreshToken);
    }
}
