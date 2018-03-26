package com.kevin.vension.demo.http;


import com.vension.frame.VFrame;
import com.vension.frame.utils.VAppUtil;

/**
 * @author ：Created by vension on 2017/12/1 15:31.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
public class Api {

    public static String getHostUrl() {
        return VAppUtil.getVersionName(VFrame.getContext()).equals("test") ? SERVER_TEST_API_URI  : SERVER_API_URI;
    }

    /**获取web_url*/
    public static String createWebUrl(String url) {
        return (VAppUtil.getVersionName(VFrame.getContext()).equals("test") ? SERVER_TEST_API_URI  : SERVER_API_URI) + url;
    }

    static public class Code {
        /**Token invalid*/
        static public final int TOKEN_INVALID = 401;
        static public final int LOGIN_INVALID = 500;
    }

    //默认服务器地址
    static public final String SERVER_API_URI = "http://uc.zhiniaoyx.com";
    static public final String SERVER_TEST_API_URI = "http://uc.t.zhiniaoyx.com";
    //头像路径
    static public final String MT_IMAGE_URI = "http://huanxunedu.qiniudn.com/";

    // 每个请求需要传递的参数Token
    static public final String SERVER_TOKEN = "Bearer 33fa7be4f585b0b9a21f03a8f4a714b95fa7c498";
    // 获取获取最新Token需要(固定)
    static public final String SERVER_AUTHORIZAT = "Basic MW1HZm9CalFlSTo1MFBaMmNGZ2picktaR1lo";


    /** ================================ 以下是接口路径 ========================================= */

    //获取Token接口
    public static final String API_TOKEN = "/api/oauth/access_token";
    /**版本更新接口url */
    public  static String VERSION_UPDATE_DOWNLOAD_URL = "http://kewaimiao.com/static/miaotalk.apk";
}
