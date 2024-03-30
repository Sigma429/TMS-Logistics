package com.sigma429.sl.handler.wechat.response;

import cn.hutool.core.util.CharsetUtil;
import lombok.Data;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

@Data
public class WeChatResponse {
    // 响应状态码
    private int status;
    // 响应体数据
    private String body;

    public WeChatResponse() {

    }

    public WeChatResponse(CloseableHttpResponse response) {
        this.status = response.getStatusLine().getStatusCode();
        try {
            this.body = EntityUtils.toString(response.getEntity(), CharsetUtil.UTF_8);
        } catch (Exception e) {
            // 如果出现异常，响应体为null
        }
    }

    public Boolean isOk() {
        return this.status == 200;
    }

}
