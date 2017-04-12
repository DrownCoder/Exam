package nwsuaf.com.exam.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.List;

import android.content.Context;
import android.os.Environment;

public class InputUtil<T> {
    /**
     * 读取本地对象
     *
     * @param context
     * @param fielName 文件名
     * @return
     */
    @SuppressWarnings("unchecked")
    public T readObjectFromLocal(Context context, String fielName) {
        T bean;
        try {
            FileInputStream fis = context.openFileInput(fielName);//获得输入流
            ObjectInputStream ois = new ObjectInputStream(fis);
            bean = (T) ois.readObject();
            fis.close();
            ois.close();
            return bean;
        } catch (StreamCorruptedException e) {
            //Toast.makeText(ShareTencentActivity.this,"出现异常3",Toast.LENGTH_LONG).show();//弹出Toast消息
            e.printStackTrace();
            return null;
        } catch (OptionalDataException e) {
            //Toast.makeText(ShareTencentActivity.this,"出现异常4",Toast.LENGTH_LONG).show();//弹出Toast消息
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            //Toast.makeText(ShareTencentActivity.this,"出现异常5",Toast.LENGTH_LONG).show();//弹出Toast消息
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            //Toast.makeText(ShareTencentActivity.this,"出现异常6",Toast.LENGTH_LONG).show();//弹出Toast消息
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取sd卡对象
     *
     * @param fileName 文件名
     * @return
     */
    @SuppressWarnings("unchecked")
    public T readObjectFromSdCard(String fileName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  //检测sd卡是否存在
            T bean;
            File sdCardDir = Environment.getExternalStorageDirectory();
            File sdFile = new File(sdCardDir, fileName);
            try {
                FileInputStream fis = new FileInputStream(sdFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                bean = (T) ois.readObject();
                fis.close();
                ois.close();
                return bean;
            } catch (StreamCorruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            } catch (OptionalDataException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 读取sd卡对象
     *
     * @param fileName 文件名
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> readListFromSdCard(String fileName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  //检测sd卡是否存在
            List<T> list;
            File sdCardDir = Environment.getExternalStorageDirectory();
            File sdFile = new File(sdCardDir, fileName);
            try {
                FileInputStream fis = new FileInputStream(sdFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                list = (List<T>) ois.readObject();
                fis.close();
                ois.close();
                return list;
            } catch (StreamCorruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            } catch (OptionalDataException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

}