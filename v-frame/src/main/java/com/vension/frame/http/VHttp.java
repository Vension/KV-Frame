package com.vension.frame.http;


import com.vension.frame.utils.handler.VHandler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class VHttp implements IHttpEngine {

    private static IHttpEngine httpEngine;
    private static VHttp vHttp;
    public static VHandler handler = new VHandler();

    public static void init(IHttpEngine engine){
        httpEngine=engine;
    }

    public static VHttp obtain(){
        if (httpEngine==null){
            throw new NullPointerException("Call XFrame.initXHttp(IHttpEngine httpEngine) within your Application onCreate() method." +
                    "Or extends XApplication");
        }
        if (vHttp == null) {
            vHttp = new VHttp();
        }
        return vHttp;
    }

    /**
     * 获取实体类的类型
     * @param obj
     * @return
     */
    public static Class<?> analysisClassInfo(Object obj){
        Type genType=obj.getClass().getGenericSuperclass();
        Type[] params=((ParameterizedType)genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }

    @Override
    public void get(String url, Map<String, Object> params, HttpCallBack callBack) {
        httpEngine.get(url,params,callBack);
    }

    @Override
    public void post(String url, Map<String, Object> params, HttpCallBack callBack) {
        httpEngine.post(url,params,callBack);
    }
}
