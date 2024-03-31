package com.sigma429.sl.controller;

import com.sigma429.sl.service.LoginService;
import com.sigma429.sl.vo.R;
import com.sigma429.sl.vo.login.AccountLoginVO;
import com.sigma429.sl.vo.login.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "登录相关接口")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @ApiOperation(value = "账号登录", notes = "登录")
    @PostMapping(value = "/account")
    public R<LoginVO> accountLogin(@RequestBody AccountLoginVO accountLoginVO) {
        return loginService.accountLogin(accountLoginVO);
    }
}
