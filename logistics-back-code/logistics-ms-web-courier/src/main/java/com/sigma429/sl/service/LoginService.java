package com.sigma429.sl.service;


import com.sigma429.sl.vo.R;
import com.sigma429.sl.vo.login.AccountLoginVO;
import com.sigma429.sl.vo.login.LoginVO;

public interface LoginService {

    /**
     * 根据用户名和密码进行登录
     * @param accountLoginVO 登录信息
     * @return token
     */
    R<LoginVO> accountLogin(AccountLoginVO accountLoginVO);
}
