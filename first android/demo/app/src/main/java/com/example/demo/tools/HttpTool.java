package com.example.demo.tools;


import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.example.demo.data.BaseBean;
import com.example.demo.data.BaseListBean;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

    /**
     * Encapsulation http request
     * 参考：https://blog.csdn.net/lixiong0713/article/details/94132402
     */
public class HttpTool {
        /**
         * Request object data
         *
         * @param url       Requested URL address
         * @param params    Requested query parameters
         * @param classOfT  Class object
         * @param listener  Http listener
         */
    public static void postObject(final String url, HttpParams params, final Class classOfT, final HttpListener listener) {
       //Print log
        CommonTool.showLog(url + "请求参数==" + getParam(params));
        //Post request
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
                        listener.onFailed("Network link failed, please check the network!");
                    }
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean appResObj = null;
                        //Print the URL of the interface and the response body.
                        CommonTool.showLog("接口" + url + "===" + response.body());
                        try {
                            //Convert response body from json to object.
                            appResObj = (BaseBean) GsonUtils.json2Object(response.body(), classOfT);
                        } catch (Exception e) {
                            //print the error.
                            e.printStackTrace();
                        }
                        if (listener != null) {
                            if (appResObj == null) {
                                listener.onFailed("The server is busy. Please try again later");
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


        /**
         * Get parameters
         *
         * @param params    Requested query parameters
         *
         */
    private static String getParam(HttpParams params) {
        String s = "";
        //a set view of the keys contained in this map
        for (String str : params.urlParamsMap.keySet()) {
            //Returns the value to which the specified key is mapped,
            //or null if this map contains no mapping for the key.
            s += "&" + str + "=" + params.urlParamsMap.get(str).get(0);
        }
        return s;
    }

    public interface HttpListener {
        void onComplected(Object... result);

        void onFailed(String msg);

    }

    /**
     * Request list data
     *
     * @param url        Requested URL address
     * @param params     Requested query parameters
     * @param listener   Http listener
     */
    public static void postList(final String url, HttpParams params, Type typeOfSrc, final HttpListener listener) {
        CommonTool.showLog(url + "请求参数==" + getParam(params));
        OkGo.<String>post(UrlConfig.URL+url)
                .params(params)
                .execute(new StringCallback() {

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        listener.onFailed("Network link failed, please check the network!");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        CommonTool.showLog("接口" + url + "===" + response.body());
                        BaseListBean appResObj = null;
                        try {
                            ////Convert response body from json to object.
                            appResObj = (BaseListBean) GsonUtils.json2Object(response.body(), BaseListBean.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (listener != null) {
                            if (appResObj == null) {
                                listener.onFailed("服务器忙请稍后再试");
                                return;
                            }
                            if (appResObj.code == 0) {
                                if (appResObj.data != null) {
                                    Gson gson = new Gson();
                                    CommonTool.showLog(appResObj.data+"===");
                                    List tempBeans = gson.fromJson(gson.toJson(appResObj.data), typeOfSrc);
                                    listener.onComplected(tempBeans);
                                } else {
                                    listener.onFailed("No data");
                                }
                            } else {
                                listener.onFailed(appResObj.msg);
                            }
                        }
                    }
                });
    }

    public static void postFile(String path, final Class classOfT, final HttpListener listener) {
        CommonTool.showLog(  "上传文件=="+path );
        HttpParams params = new HttpParams();
        params.put("file", new File(path));
        OkGo.<String>post(UrlConfig.URL + UrlConfig.upload_URL)
                .isMultipart(true)
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
                        CommonTool.showLog("接口" + UrlConfig.URL + UrlConfig.upload_URL + "===" + response.body());
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
}


