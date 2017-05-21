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
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

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

    public static String sendFile(File filePath, String fileName, String sendUrl, String sendMethod) {
        HttpURLConnection conn = null;
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";
        String result = "";

        Map<String, File> files = new HashMap<String, File>();
        files.put(fileName,new File(filePath,fileName));
        try {
            URL uri = new URL(sendUrl);
            conn = (HttpURLConnection) uri.openConnection();
            conn.setReadTimeout(5 * 1000); // 缓存的最长时间
            conn.setDoInput(true);// 允许输入
            conn.setDoOutput(true);// 允许输出
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                    + ";boundary=" + BOUNDARY);

            DataOutputStream outStream = new DataOutputStream(
                    conn.getOutputStream());

            // 发送文件数据
            if (files != null)
                for (Map.Entry<String, File> file : files.entrySet()) {
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(PREFIX);
                    sb1.append(BOUNDARY);
                    sb1.append(LINEND);
                    sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                            + file.getKey() + "\"" + LINEND);
                    sb1.append("Content-Type: application/octet-stream; charset="
                            + CHARSET + LINEND);
                    sb1.append(LINEND);
                    outStream.write(sb1.toString().getBytes());

                    InputStream is = new FileInputStream(
                            file.getValue());
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {
                        outStream.write(buffer, 0, len);
                    }

                    is.close();
                    outStream.write(LINEND.getBytes());
                }

            // 请求结束标志
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND)
                    .getBytes();
            outStream.write(end_data);
            outStream.flush();
            // 得到响应码
            int res = conn.getResponseCode();
            if (res == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn
                        .getInputStream(), "UTF-8"));
                String line = "";
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            }
            outStream.close();
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
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
