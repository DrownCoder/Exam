package nwsuaf.com.exam.util;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/25.
 * 网络上传工具类
 */
public class WebToolUtils {
    /*
 * 向服务器发送数据
 * */
    public static String HttpSentMessage(final List<NameValuePair> content, final String url){
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                HttpSentList(content, url);
            }
        }).start();*/
        return HttpSentList(content, url);
    }
    public static void HttpSentObjectMessage(final Object obj,final String url){
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                HttpSentObject(obj, url);
            }
        }).start();*/
        HttpSentObject(obj, url);
    }
    public static void HttpSentListObjMessage(final ArrayList<Object> obj,final String url){
      /*  new Thread(new Runnable() {
            @Override
            public void run() {
                HttpSentListObj(obj, url);
            }
        }).start();*/
        HttpSentListObj(obj, url);
    }

    /**
     * 网络传递键值对
     * @param content
     * @param url
     * @return
     */

    private static String HttpSentList(List<NameValuePair> content,String url){
        String result = null;
        HttpPost httpRequest = new HttpPost(url);
        try {
            HttpEntity httpEntity = new UrlEncodedFormEntity(content,"utf-8");
            httpRequest.setEntity(httpEntity);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            int i = httpResponse.getStatusLine().getStatusCode();

            if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                result = EntityUtils.toString(httpResponse.getEntity());
                /*Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result",result);
                msg.setData(bundle);
                msg.what = 0x111;
                handler.sendMessage(msg);*/
                return result;
            }else{
                //tv.setText("request error");
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 网络传输对象流
     * @param obj
     * @param urlpos
     * @return
     */
    private static String HttpSentObject(Object obj,String urlpos){
        String line = "";
        URL url = null;
        ObjectOutputStream oos = null;
        try {
            url = new URL(urlpos);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestMethod("POST");
            oos = new ObjectOutputStream(connection.getOutputStream());
            oos.writeObject(obj);
            InputStreamReader read = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(read);
            while ((line = br.readLine()) != null) {
                Log.d("TAG", "line is " + line);
            }
            br.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return line;
    }
    private static String HttpSentListObj(ArrayList<Object> obj, String urlpos){
        String line = "";
        URL url = null;
        ObjectOutputStream oos = null;
        try {
            url = new URL(urlpos);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestMethod("POST");
            oos = new ObjectOutputStream(connection.getOutputStream());
            oos.writeObject(obj);
            InputStreamReader read = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(read);
            while ((line = br.readLine()) != null) {
                Log.d("TAG", "line is " + line);
            }
            br.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return line;
    }
}
