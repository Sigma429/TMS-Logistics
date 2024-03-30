package com.sigma429.sl.service.impl;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sigma429.sl.config.QRCodeConfig;
import com.sigma429.sl.enums.PayChannelEnum;
import com.sigma429.sl.service.QRCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    @Resource
    private QRCodeConfig qrCodeConfig;

    @Override
    public String generate(String content, PayChannelEnum payChannel) {
        QrConfig qrConfig = new QrConfig();
        // 设置边距
        qrConfig.setMargin(qrCodeConfig.getMargin());
        // 二维码颜色
        qrConfig.setForeColor(HexUtil.decodeColor(qrCodeConfig.getForeColor()));
        // 设置背景色
        qrConfig.setBackColor(HexUtil.decodeColor(qrCodeConfig.getBackColor()));
        // 纠错级别
        qrConfig.setErrorCorrection(ErrorCorrectionLevel.valueOf(qrCodeConfig.getErrorCorrectionLevel()));
        // 设置宽
        qrConfig.setWidth(qrCodeConfig.getWidth());
        // 设置高
        qrConfig.setHeight(qrCodeConfig.getHeight());
        if (ObjectUtil.isNotEmpty(payChannel)) {
            // 设置logo
            qrConfig.setImg(qrCodeConfig.getLogo(payChannel));
        }
        return QrCodeUtil.generateAsBase64(content, qrConfig, ImgUtil.IMAGE_TYPE_PNG);
    }

    @Override
    public String generate(String content) {
        return generate(content, null);
    }

    public static void main(String[] args) {
        QRCodeServiceImpl qrCodeService = new QRCodeServiceImpl();
        qrCodeService.qrCodeConfig = new QRCodeConfig();
        System.out.println(qrCodeService.generate("http://192.168.150.101:18096/qr", PayChannelEnum.ALI_PAY));
        System.out.println(qrCodeService.generate("http://192.168.150.101:18096/qr", PayChannelEnum.WECHAT_PAY));
    }
}
