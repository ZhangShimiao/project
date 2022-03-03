package com.example.demo.tools;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.example.demo.data.BaseBean;
import com.example.demo.data.BaseListBean;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public class HttpTool {

    public static void postObject(final String url, HttpParams params, final Class classOfT, final HttpListener listener) {
       CommonTool.showLog(url + "请求参数==" + getParam(params));
        OkGo.<String>post(UrlConfig.URL + url)
                .params(params)
                .execute(new StringCallback() {

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        listener.onFailed("网络链接失败,请检查网络！");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean appResObj = null;
                        CommonTool.showLog("接口" + url + "===" + response.body());
                        try {
                            appResObj = (BaseBean) GsonUtils.json2Object(response.body(), classOfT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (listener != null) {
                            if (appResObj == null) {
                                listener.onFailed("服务器忙请稍后再试");
                                return;
                            }
                            if (appResObj.code == 0) {
                                listener.onComplected(appResObj);
                            } else {
                                listener.onFailed(appResObj.msg);
                            }
                        }


                    }


                });
    }




    private static String getParam(HttpParams params) {
        String s = "";
        for (String str : params.urlParamsMap.keySet()) {
            s += "&" + str + "=" + params.urlParamsMap.get(str).get(0);
        }
        return s;
    }

    public interface HttpListener {
        void onComplected(Object... result);

        void onFailed(String msg);

    }

}


