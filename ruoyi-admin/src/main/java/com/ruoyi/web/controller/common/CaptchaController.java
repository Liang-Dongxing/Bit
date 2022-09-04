package com.ruoyi.web.controller.common;

import cn.hutool.captcha.ICaptcha;
import cn.hutool.core.math.Calculator;
import com.google.code.kaptcha.Producer;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.sign.Base64;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.ChineseGifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author ruoyi
 */
@RestController
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Resource(name = "hutoolMathCaptcha")
    private ICaptcha hutoolMathCaptcha;

    @Resource(name = "hutoolRandomCaptcha")
    private ICaptcha hutoolRandomCaptcha;

    @Autowired
    private RuoYiConfig ruoYiConfig;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysConfigService configService;

    /**
     * 生成验证码
     */
    @GetMapping("/a/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) throws IOException {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled) {
            return ajax;
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = RuoYiConfig.getCaptchaType();
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }

    @GetMapping("/b/captchaImage")
    public AjaxResult getHutoolsCaptchaCode(HttpServletResponse response) throws IOException {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled) {
            return ajax;
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = RuoYiConfig.getCaptchaType();
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        if ("math".equals(captchaType)) {
            hutoolMathCaptcha.createCode();
            hutoolMathCaptcha.write(os);
            capStr = hutoolMathCaptcha.getCode();
            code = String.valueOf((int) Calculator.conversion(capStr));
        } else if ("char".equals(captchaType)) {
            hutoolRandomCaptcha.createCode();
            hutoolRandomCaptcha.write(os);
            capStr = code = hutoolRandomCaptcha.getCode();
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }

    @GetMapping("/c/captchaImage")
    public AjaxResult getEasyCaptchaCode(HttpServletResponse response) throws IOException {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled) {
            return ajax;
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        String base64 = null;

        // 验证码图片格式
        String captchaImageFormat = RuoYiConfig.getCaptchaImageFormat();
        // 生成验证码
        String captchaType = RuoYiConfig.getCaptchaType();
        if ("math".equals(captchaType)) {
            ArithmeticCaptcha captcha = new ArithmeticCaptcha(ruoYiConfig.getCaptchaWidth(), ruoYiConfig.getCaptchaHeight());
            capStr = captcha.getArithmeticString();
            code = captcha.text();
            base64 = captcha.toBase64("");
        } else if ("char".equals(captchaType)) {
            Captcha captcha = null;
            if ("png".equals(captchaImageFormat)) {
                captcha = new SpecCaptcha(ruoYiConfig.getCaptchaWidth(), ruoYiConfig.getCaptchaHeight());
            } else if ("gif".equals(captchaImageFormat)) {
                captcha = new com.wf.captcha.GifCaptcha(ruoYiConfig.getCaptchaWidth(), ruoYiConfig.getCaptchaHeight());
            }
            captcha.setLen(4);
            capStr = code = captcha.text();
            base64 = captcha.toBase64("");
        } else if ("chinese".equals(captchaType)) {
            Captcha captcha = null;
            if ("png".equals(captchaImageFormat)) {
                captcha = new ChineseCaptcha(ruoYiConfig.getCaptchaWidth(), ruoYiConfig.getCaptchaHeight());
            } else if ("gif".equals(captchaImageFormat)) {
                captcha = new ChineseGifCaptcha(ruoYiConfig.getCaptchaWidth(), ruoYiConfig.getCaptchaHeight());
            }
            capStr = code = captcha.text();
            base64 = captcha.toBase64("");
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        ajax.put("uuid", uuid);
        ajax.put("img", base64);
        return ajax;
    }
}
