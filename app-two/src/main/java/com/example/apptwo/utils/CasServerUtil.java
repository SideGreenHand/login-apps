package com.example.apptwo.utils;

import com.example.apptwo.properties.SsoProperties;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * CAS - Server通信服务
 */
public class CasServerUtil {

    public static void main(String[] args) {
        try {
 //           String tgt = getTGT("admin", "123456");
   //         System.out.println("TGT：" + tgt);
//
            String tgt = "TGT-2-WJ01rkw43cOF675NUnr2CcfFgc1ZabeLHGnVuH53QJgaVd995IcEpUG6Key6aPfoSUQ-LAPTOP-LGDP1N1T";
            String service = "http://apptwo.server.com:8084/resource";
            String st = getST(tgt, service);
            System.out.println("ST：" + st);
//
//            System.out.println(service + "?ticket=" + st);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取TGT
     */
    public static String getTGT(String username, String password) {
        try{
            CookieStore httpCookieStore = new BasicCookieStore();
            CloseableHttpClient client = HttpClients.createDefault();

            HttpPost httpPost = new HttpPost(SsoProperties.tokenUrl);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);

            Header headerLocation = response.getFirstHeader("Location");
            String location = headerLocation == null ? null : headerLocation.getValue();

            System.out.println("Location：" + location);

            if (location != null) {
                return location.substring(location.lastIndexOf("/") + 1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取ST
     */
    public static String getST(String TGT, String service){
        try {
            CloseableHttpClient client = HttpClients.createDefault();

            HttpPost httpPost = new HttpPost(SsoProperties.tokenUrl + "/" + TGT);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("service", service));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);

            String st = readResponse(response);
            return st == null ? null : (st == "" ? null : st);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取 response body 内容为字符串
     *
     * @param response
     * @return
     * @throws IOException
     */
    private static String readResponse(HttpResponse response) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String result = new String();
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        return result;
    }

}

