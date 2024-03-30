package com.sigma429.sl.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.sigma429.sl.properties.JwtProperties;
import com.sigma429.sl.service.TokenService;
import com.sigma429.sl.util.JwtUtils;
import com.sigma429.sl.util.ObjectUtil;
import com.sigma429.sl.vo.user.UserLoginVO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Map;

/**
 * ClassName:TokenServiceImpl
 * Package:com.sigma429.sl.service.impl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/06 - 13:33
 * @Version:v1.0
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public static final String REDIS_REFRESH_TOKEN_PREFIX = "SL_CUSTOMER_REFRESH_TOKEN_";

    @Override
    public String createAccessToken(Map<String, Object> claims) {
        // 生成短令牌的有效期时间单位为：分钟
        return JwtUtils.createToken(claims, jwtProperties.getPrivateKey(), jwtProperties.getAccessTtl(),
                DateField.MINUTE);
    }

    @Override
    public String createRefreshToken(Map<String, Object> claims) {
        // 生成长令牌的有效期时间单位为：小时
        Integer ttl = jwtProperties.getRefreshTtl();
        String refreshToken = JwtUtils.createToken(claims, jwtProperties.getPrivateKey(), ttl);
        // 长令牌只能使用一次，需要将其存储到redis中，变成有状态的
        String redisKey = getRedisRefreshToken(refreshToken);
        stringRedisTemplate.opsForValue().set(redisKey, refreshToken, Duration.ofHours(ttl));
        return refreshToken;
    }

    @Override
    public UserLoginVO refreshToken(String refreshToken) {
        if (StrUtil.isEmpty(refreshToken)) {
            return null;
        }
        Map<String, Object> originClaims = JwtUtils.checkToken(refreshToken, jwtProperties.getPublicKey());
        if (ObjectUtil.isEmpty(originClaims)) {
            // token无效
            return null;
        }
        // 通过redis校验，原token是否使用过，来确保token只能使用一次
        String redisKey = getRedisRefreshToken(refreshToken);
        Boolean bool = stringRedisTemplate.hasKey(redisKey);
        if (ObjectUtil.notEqual(bool, Boolean.TRUE)) {
            // 原token过期或已经使用过
            return null;
        }
        // 删除原token
        stringRedisTemplate.delete(redisKey);
        // 重新生成长短令牌
        String accessToken = createAccessToken(originClaims);
        String newRefreshToken = createRefreshToken(originClaims);
        return UserLoginVO.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    private String getRedisRefreshToken(String refreshToken) {
        // md5是为了缩短key的长度
        return REDIS_REFRESH_TOKEN_PREFIX + SecureUtil.md5(refreshToken);
    }
}
