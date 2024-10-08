package com.sigma429.sl.service.impl;

import com.itheima.auth.sdk.AuthTemplate;
import com.itheima.auth.sdk.common.Result;
import com.itheima.auth.sdk.dto.LoginDTO;
import com.sigma429.sl.service.LoginService;
import com.sigma429.sl.vo.R;
import com.sigma429.sl.vo.login.AccountLoginVO;
import com.sigma429.sl.vo.login.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthTemplate authTemplate;

    /**
     * 根据用户名和密码进行登录
     * @param accountLoginVO 登录信息
     * @return token
     */
    @Override
    public R<LoginVO> accountLogin(AccountLoginVO accountLoginVO) {
        // 账号密码登录
        Result<LoginDTO> loginResult = authTemplate.opsForLogin().token(accountLoginVO.getAccount(),
                accountLoginVO.getPassword());

        // 校验登录是否成功
        if (loginResult.getCode() != Result.success().getCode()) {
            return R.error(loginResult.getMsg());
        }

        return R.success(new LoginVO(loginResult.getData().getToken().getToken()));
    }
}
