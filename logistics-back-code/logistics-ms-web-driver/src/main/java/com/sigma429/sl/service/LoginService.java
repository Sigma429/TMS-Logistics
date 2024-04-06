package com.sigma429.sl.service;


import com.sigma429.sl.vo.request.AccountLoginVO;

public interface LoginService {
    /**
     * 账号登录
     * @param accountLoginVO 账号登录请求
     * @return token
     */
    String accountLogin(AccountLoginVO accountLoginVO);
}
