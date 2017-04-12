package nwsuaf.com.exam.util;

/**
 * Created by Administrator on 2016/5/22.
 */
public  class GetUserInfo {
    private static String peo_name;
    private static String peo_id;
    private static String class_name;
    private static boolean isGet = false;

    public static String getPeo_name() {
        return peo_name;
    }

    public static void setPeo_name(String peo_name) {
        GetUserInfo.peo_name = peo_name;
    }

    public static String getPeo_id() {
        return peo_id;
    }

    public static void setPeo_id(String peo_id) {
        GetUserInfo.peo_id = peo_id;
    }

    public static String getClass_name() {
        return class_name;
    }

    public static void setClass_name(String class_name) {
        GetUserInfo.class_name = class_name;
    }

    public static boolean isGet() {
        return isGet;
    }

    public static void setIsGet(boolean isGet) {
        GetUserInfo.isGet = isGet;
    }
}
