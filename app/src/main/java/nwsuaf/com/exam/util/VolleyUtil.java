package nwsuaf.com.exam.util;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by gyzhong on 15/3/1.
 */
public class VolleyUtil {

    private static RequestQueue mRequestQueue ;

    public static synchronized void initialize(Context context){
        if (mRequestQueue == null){
            synchronized (VolleyUtil.class){
                if (mRequestQueue == null){
                    mRequestQueue = Volley.newRequestQueue(context) ;
                }
            }
        }
        mRequestQueue.start();
    }
    public static RequestQueue getRequestQueue(){
        if (mRequestQueue == null)
            throw new RuntimeException("请先初始化mRequestQueue") ;
        return mRequestQueue ;
    }
}


