package com.sigma429.sl;

import com.sigma429.sl.domain.request.JsapiPayDTO;
import com.sigma429.sl.domain.response.JsapiPayResponseDTO;
import com.sigma429.sl.fallback.JsapiPayFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "sl-express-ms-trade", contextId = "JsapiPay", path = "jsapi", fallbackFactory = JsapiPayFeignFallbackFactory.class)
public interface JsapiPayFeign {

    /***
     * 统一jsapi交易预创建
     * 商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易会话标识后再按Native、
     * JSAPI、APP等不同场景生成交易串调起支付。
     * @param jsapiPayDTO jsapi提交支付请求对象
     *
     * @return 交易单，支付串码
     */
    @PostMapping
    JsapiPayResponseDTO createJsapiTrading(@RequestBody JsapiPayDTO jsapiPayDTO);

}
