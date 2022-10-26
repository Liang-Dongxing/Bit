package com.bit.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author bit
 */
@Component
@ConfigurationProperties(prefix = "bit")
@Data
public class BitConfig {
    /**
     * 上传路径
     */
    private static String profile;
    /**
     * 获取地址开关
     */
    private static Boolean addressEnabled;
    /**
     * 验证码类型
     */
    private static String captchaType;
    /**
     * 验证码图片格式
     */
    private static String captchaImageFormat;
    /**
     * 验证码长
     */
    private Integer captchaWidth;
    /**
     * 验证码宽
     */
    private Integer captchaHeight;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 版本
     */
    private String version;
    /**
     * 版权年份
     */
    private String copyrightYear;
    /**
     * 实例演示开关
     */
    private Boolean demoEnabled;

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        BitConfig.profile = profile;
    }

    public static boolean isAddressEnabled() {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled) {
        BitConfig.addressEnabled = addressEnabled;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath() {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getProfile() + "/upload";
    }

    public static String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        BitConfig.captchaType = captchaType;
    }

    public static String getCaptchaImageFormat() {
        return captchaImageFormat;
    }

    public void setCaptchaImageFormat(String captchaImageFormat) {
        BitConfig.captchaImageFormat = captchaImageFormat;
    }

}
