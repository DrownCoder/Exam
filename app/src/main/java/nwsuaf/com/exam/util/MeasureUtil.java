package nwsuaf.com.exam.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/7/5.
 */
public class MeasureUtil {

    public static int dp2px(int dpVal,Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal
                , context.getResources().getDisplayMetrics());
    }

    public static int sp2px(int spVal,Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal
                , context.getResources().getDisplayMetrics());
    }
    public static int getWindowHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return  wm.getDefaultDisplay().getHeight();
    }
}
