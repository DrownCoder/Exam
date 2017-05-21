package nwsuaf.com.exam.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

@SuppressLint("WorldReadableFiles")
public class OutputUtil<T> {
    /**
     * 将对象保存到本地
     *
     * @param context
     * @param fileName 文件名
     * @param bean     对象
     * @return true 保存成功
     */
    public boolean writeObjectIntoLocal(Context context, String fileName, T bean) {
        try {
            // 通过openFileOutput方法得到一个输出流，方法参数为创建的文件名（不能有斜杠），操作模式
            @SuppressWarnings("deprecation")
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(bean);//写入
            fos.close();//关闭输入流
            oos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //Toast.makeText(WebviewTencentActivity.this, "出现异常1",Toast.LENGTH_LONG).show();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(WebviewTencentActivity.this, "出现异常2",Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean writeStringIntoSDcard(String fileName,Object obj){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();//获取sd卡目录
            File sdFile = new File(sdCardDir, fileName);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(sdFile);
                PrintStream ps = new PrintStream(new FileOutputStream(sdFile));
                Gson gson = new Gson();
                String str = gson.toJson(obj);
                ps.append(str);
                fileOutputStream.close();
                ps.close();
                return true;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
    /**
     * 将对象写入sd卡
     *
     * @param fileName 文件名
     * @param bean     对象
     * @return true 保存成功
     */
    public boolean writObjectIntoSDcard(String fileName, T bean) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();//获取sd卡目录
            File sdFile = new File(sdCardDir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(sdFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(bean);//写入
                fos.close();
                oos.close();
                return true;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 将集合写入sd卡
     *
     * @param fileName 文件名
     * @param list     集合
     * @return true 保存成功
     */
    public boolean writeListIntoSDcard(String fileName, List<T> list) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();//获取sd卡目录
            File sdFile = new File(sdCardDir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(sdFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(list);//写入
                fos.close();
                oos.close();
                return true;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
}