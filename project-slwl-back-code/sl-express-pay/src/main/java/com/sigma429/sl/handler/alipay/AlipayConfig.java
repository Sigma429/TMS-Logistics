package com.sigma429.sl.handler.alipay;

import com.alipay.easysdk.kernel.Config;

/**
 * 支付宝支付的配置
 */
public class AlipayConfig {

    /**
     * 获取支付宝的配置（沙箱环境）
     * @return 支付宝的配置
     */
    public static Config getConfig() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi-sandbox.dl.alipaydev.com";
        config.signType = "RSA2";
        config.appId = "9021000134683566";
        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = "";
        // 注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
        // config.merchantCertPath = "<-- 请填写您的应用公钥证书文件路径，例如：/foo/appCertPublicKey_2019051064521003.crt -->";
        // config.merchantCertPath = "file/appPublicCert.crt";
        // config.alipayCertPath = "<-- 请填写您的支付宝公钥证书文件路径，例如：/foo/alipayCertPublicKey_RSA2.crt -->";
        // config.alipayCertPath = "file/alipayPublicCert.crt";
        // config.alipayRootCertPath = "<-- 请填写您的支付宝根证书文件路径，例如：/foo/alipayRootCert.crt -->";
        // config.alipayRootCertPath = "file/alipayRootCert.crt";
        // 注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        config.alipayPublicKey = "";
        // 可设置异步通知接收服务地址（可选）
        // config.notifyUrl = "https://7cdf-101-7-162-16.ngrok-free.app";
        // 可设置AES密钥，调用AES加解密相关接口时需要（可选）
        // config.encryptKey = "D/9fBsybuxt5ARPYcWDL5g==";
        return config;
    }

    /**
     * 获取支付宝的配置（正式环境）
     * @return 支付宝的配置
     */
    // public static Config getConfig() {
    //     Config config = new Config();
    //     config.protocol = "https";
    //     config.gatewayHost = "openapi.alipay.com";
    //     config.signType = "RSA2";
    //     config.appId = "2021003141676135";
    //     // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
    //     config.merchantPrivateKey = "MIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQC12YM9mR+HFQYTx
    //     /fHKHZbgszVtDHDB0B/ysWl3MbcPpGtjcZlDr5aynRMRLaoduRHT++A98IaNVIVGj9RHdXrX2j9I
    //     /Uz6fYDH63cdu6FZ6Pk82yPwNZW7pebprbVHInR/7gzsKQWSWEST70BgjCRqlbfAE6xzUZFTeYxciCjptm0rUQ2MC24xRdkvZByIDIYFnQ
    //     /AdmSFqNtKDR2WpEV/M8aBjyuPPomRJZ1X8oudWuJIU4ySdas04fCbDxD10TY/wyQcDHXuG1IrQpXme4DOGQeJZ0
    //     /aOFphBkDFUyPGfYMmLshOPNdBKi2IqWHPPs4XsV4Rv6+tvTSnMF2uGqHAgMBAAECggEAPa1sifPpcZN74DGupGng2uDeQI1BY3iOM8m
    //     +h6b9+61tE4RGifgaMAkCsOuNWE4a1uURwphFyUXUdTvVxdlsuMw/e7w6akUsH5sbCO99rtmcCQdXBtrM1
    //     +dMnIpK8LUhOYyWGVIMFVMGDYPmAyD5AC7aEAC2sC+DafYl4RdoYpidq1YxeE7DVw1aQHCI2mKhYjZG
    //     +3RDDGDfNFvdyH61MgdYjoGkeXNvARzEXgfWvfiTrHZ3H1SYgvOEHofzKDTrWsQL2dvaEsc55Jiw0AgNUVcgby7al8PUekTJoK3ZvrE3pSWaUirBcqsqWISHjeR7Xx501CHIha8EnZwlnDoM4QKBgQDtnYzwQ5mHg7cRHD8Z6QdTpvBvYSEPesiUT/HeI+AKQKDCVJxKiLvJagc6zZkOzV9bZDS/WLgzXWMyxUb+OTjht0jLWAMcf7NfFp3tPKq9wkmQQ/vQSBQ1lFmO6A4Zq1eoGKeUCB4pKBG6cSM+t8+ruhm7s1ZUt+6EBwCVN/izrQKBgQDD62jIm+6NFErdidUaIrGiFUrzqdR13w6JOexfk+O6Aau3wRqsr7Wz4nqQvVVxGMRpXbOH06zpeiS+vMmjwgO973VoLAmH+hJ0GZz8qj3zA2GEOFWjD2V7tqeRvGkQvz0v46pl+8sBJkrRHLN7DWNYY5NDI+b7exwqcTc/LL19gwJ/a6r4MeZvqvgD+7zQ2uy8ZSs/xzg7wsfgG1QeRIn8+qhOL8AnEZ7jeGCS5hJDSHHGw6KkRA/vZ1bpnBfIE2naXGywj3NR9Zfnry6QYO8cbt+adcRYVghTH/QYoKiFuxvonEKPrIQBJqUBY3ngforLjwTEpEie1cSCT1Dc8sBp8QKBgQCaz8fqzRyBKknGKQXVMxj+JKknRUl3IpzP3o9jLu9BqdRQzSwQzH9d91Y2TQXY6mM5hys35xG5JCUo+vCyj7p5OWCiwjl90yMFzr93/+YXwtIpsoIo6R+d1EUxKZoz+4mT7+hT0dUlwWZZOr6wO3IHBBf3c8UvbqZg+zlWmDnblQKBgEs6jwMkb5zaG2fyBJ7PJUN/8nIz8V+X0SxQfcEqIX0J+EC+7MAgFjcdZFp+lca3Vd9z+8Ksd4rMzMa5y856ositL2NZ+K0fs8i8EBaQPny61OgCFUuXEuv5keB2YuGMSns5FYRWuByrtDXl4PxzKXvq05iKWLKCaCq9v4momvKZ";
    //     //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
    //     // config.merchantCertPath = "<-- 请填写您的应用公钥证书文件路径，例如：/foo/appCertPublicKey_2019051064521003.crt -->";
    //     // config.alipayCertPath = "<-- 请填写您的支付宝公钥证书文件路径，例如：/foo/alipayCertPublicKey_RSA2.crt -->";
    //     // config.alipayRootCertPath = "<-- 请填写您的支付宝根证书文件路径，例如：/foo/alipayRootCert.crt -->";
    //     //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
    //     config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhUnjdAKwZApwZEcfq+5L0pa77Vg3mqcoXv
    //     +th8RR0SYotkPsH1f2JkbS48ySaSCM6YNWSMNfqp5qdOla2zUJOBnJ/yaBg7s7fVD6V3M2mEog8kCDYGKt
    //     /3P4VII3xYl8lFYMQ3IcFRELkxCBBCA8JDKmf5z2R4F/Z/jFFEuOwxaJvp+7Ke9OzZHYdWGNnU6QP8YYLYUeX7VNZLHEuly34ExAw6A
    //     +yJkNDsYEho2Lu31QjT2pLh9g+88MlRfiI92iN25O9NVdeM4f5RcpvBPrBQZQs9tlFmALYSFS3prIf3FAobWM
    //     +W7iwxT6J25nFIhst1DdJQfIBpaeRUJVTkn99QIDAQAB";
    //     //可设置异步通知接收服务地址（可选）
    //     config.notifyUrl = "https://www.test.com/callback";
    //     //可设置AES密钥，调用AES加解密相关接口时需要（可选）
    //     config.encryptKey = "VCS4bdmoAgXRaOq/TQ4MwA==";
    //     return config;
    // }

}
