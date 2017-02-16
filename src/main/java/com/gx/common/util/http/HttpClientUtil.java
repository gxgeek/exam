package com.gx.common.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/12/4.
 */
public class HttpClientUtil {
    private static Logger log = Logger.getLogger(HttpClientUtil.class);

    private static PoolingHttpClientConnectionManager cm;
    private static String EMPTY_STR = "";
    private static String CHARSET = "UTF-8";


    private static void init(){
        if(cm == null){
            cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(30);
            cm.setDefaultMaxPerRoute(5);
        }
    }

    /** 
     * 通过连接池获取HttpClient
     * @return 
     */
    private static CloseableHttpClient getHttpClient(){
        init();
        return  HttpClients.custom().setConnectionManager(cm)
                .setRetryHandler(new DefaultHttpRequestRetryHandler())
                .build();
    }

    /**
     * get请求
     * @param url
     * @return
     */
    public static String httpGetRequest(String url){
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet);
    }

    public static String httpGetRequest(String url,Map<String,Object> params) {
        String res = "";
        try {
            URIBuilder ub = new URIBuilder();
            ub.setPath(url);

            ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
            ub.setParameters(pairs);

            HttpGet httpGet = new HttpGet(ub.build());
            res = getResult(httpGet);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return res;
    }

    public static String httpGetRequest(String url,Map<String,Object> headers,Map<String,Object> params){
        String res = "";
        try {
            URIBuilder ub = new URIBuilder();
            ub.setPath(url);

            ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
            ub.setParameters(pairs);
            HttpGet httpGet = new HttpGet(ub.build());
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
            res = getResult(httpGet);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return res;
    }

    /**
     * post请求
     * @param url
     * @return
     */
    public static String httpPostRequest(String url){
        HttpPost httpPost=new HttpPost(url);
        return getResult(httpPost);
    }

    public static String httpPostRequest(String url,Map<String,Object> params){
        String res = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            ArrayList<NameValuePair> pairs=covertParams2NVPS(params);
            httpPost.setEntity(new UrlEncodedFormEntity(pairs,CHARSET));
            res = getResult(httpPost);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return res;
    }

    public static String httpPostRequest(String url,Map<String,Object> headers,Map<String,Object> params) {
        String res = "";
        try {
            HttpPost httpPost = new HttpPost(url);

            for (Map.Entry<String, Object> param : headers.entrySet()) {
                httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
            ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
            res = getResult(httpPost);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return res;
    }

    /**
     * 获取结果流
     * @param url
     * @return
     */
    public static InputStream httpIoRequest(String url){
        HttpGet httpGet = new HttpGet(url);
        return getResultIo(httpGet);
    }

    /**
     * 代理转发
     * @param url
     * @param headers
     * @param params
     * @param multipartRequest
     * @return
     */
    public static String httpPostRequestProxy(String url,Map<String,Object> headers,Map<String,Object> params,MultipartHttpServletRequest multipartRequest) {
        String res = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            if (multipartRequest!=null){
                MultiValueMap<String,MultipartFile> map = multipartRequest.getMultiFileMap();
                for(Map.Entry<String, List<MultipartFile>> entry : map.entrySet()){
                    for(MultipartFile file : entry.getValue()) {
                        ContentBody fileBody = new InputStreamBody(file.getInputStream(), file.getOriginalFilename());
                        multipartEntityBuilder.addPart(entry.getKey(), fileBody);
                    }
                }
//                List<MultipartFile> pics = multipartRequest.getFiles("pic");
//                List<MultipartFile> videos = multipartRequest.getFiles("video");
//                List<MultipartFile> voices = multipartRequest.getFiles("voice");
//                for(MultipartFile pic : pics){
//                    ContentBody fileBody = new InputStreamBody(pic.getInputStream(),pic.getOriginalFilename());
//                    multipartEntityBuilder.addPart("pic",fileBody);
//                }
//                for(MultipartFile video : videos){
//                    ContentBody fileBody = new InputStreamBody(video.getInputStream(),video.getOriginalFilename());
//                    multipartEntityBuilder.addPart("video",fileBody);
//                }
//                for(MultipartFile voice : voices){
//                    ContentBody fileBody = new InputStreamBody(voice.getInputStream(),voice.getOriginalFilename());
//                    multipartEntityBuilder.addPart("voice",fileBody);
//                }

            }

            for(Map.Entry<String,Object> param : params.entrySet()){
                multipartEntityBuilder.addTextBody(param.getKey(), String.valueOf(param.getValue()));
            }
            HttpEntity reqEntity = multipartEntityBuilder.setCharset(CharsetUtils.get("UTF-8")).build();
            httpPost.setEntity(reqEntity);
            res = getResult(httpPost);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return res;
    }
    public static String httpPostRequestProxy(String url,MultipartFile file) throws Exception {
        String res = "";
        HttpPost httpPost = new HttpPost(url);
        ContentBody fileBody = new InputStreamBody(file.getInputStream(), file.getOriginalFilename());
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntityBuilder.addPart("file", fileBody);
        HttpEntity reqEntity = multipartEntityBuilder.setCharset(CharsetUtils.get("UTF-8")).build();
        httpPost.setEntity(reqEntity);
        res = getResult(httpPost);
        return res;
    }




        /**
         * 組裝参数
         * @param params
         * @return
         */
    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String,Object> params){
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for(Map.Entry<String,Object>param:params.entrySet()){
            pairs.add(new BasicNameValuePair(param.getKey(),String.valueOf(param.getValue())));
        }
        return pairs;
    }

    /**
     * 处理Http请求
     * @param request
     * @return  string
     */
    private static String getResult(HttpRequestBase request) {
        HttpEntity entity = getResultEntity(request);
        if(entity!=null){
            String result = null;
            try {
                result = EntityUtils.toString(entity, CHARSET);
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
            return result;
        }
        return EMPTY_STR;
    }

    /**
     * 处理Http请求
     * @param request
     * @return inputstream
     */
    private static InputStream getResultIo(HttpRequestBase request) {
        HttpEntity entity = getResultEntity(request);
        if(entity!=null){
            InputStream result = null;
            try {
                result = entity.getContent();
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
            return result;
        }
        return null;
    }

    private static HttpEntity getResultEntity(HttpRequestBase request){
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        int i=1;
        while (true) {
            try {
                response = httpClient.execute(request);
                break;
            } catch (Exception e1) {
                log.error("send http "+i+"; fail,---"+e1.getMessage());
                if(i>=3){
                    break;
                }else{
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                i++;
            }
        }
        if(response==null){
            log.error("httpclient fail ------------------------------"+request.getRequestLine()+"---"+ Arrays.toString(request.getAllHeaders())+"---");
            if(request.getMethod().equals("POST")) {
                HttpPost post = (HttpPost) request;
                try {
                    log.error("post params ----------------:"+EntityUtils.toString(post.getEntity(), CHARSET));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        try {
            HttpEntity entity = response.getEntity();
            return entity;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式
     * @param apiUrl API接口URL
     * @param xmlStr xmlStr
     * @return
     */
    public static String doPostSSL(String apiUrl, String xmlStr) {
        HttpPost httpPost = new HttpPost(apiUrl);
        String httpStr = null;
        try {
            StringEntity stringEntity = new StringEntity(xmlStr,"UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/xml");
            httpPost.setEntity(stringEntity);
            httpStr = getResult(httpPost);
//            response = httpClient.execute(httpPost);
//            int statusCode = response.getStatusLine().getStatusCode();
//            if (statusCode != HttpStatus.SC_OK) {
//                return null;
//            }
//            HttpEntity entity = response.getEntity();
//            if (entity == null) {
//                return null;
//            }
//            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return httpStr;
    }


}
