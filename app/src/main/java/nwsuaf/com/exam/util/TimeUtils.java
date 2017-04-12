package nwsuaf.com.exam.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class TimeUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE    = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATA_FORMAT_ID = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat DATA_FORMAT_DAY = new SimpleDateFormat("HH-mm");

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    public static String dtFormat(Date date, String dateFormat){
        return getFormat(dateFormat).format(date);
    }

    private static final DateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * 获得yyyyMMddHHmmss
     * @return
     */
    public static String getCurrentTimeInID(){
        return getTime(getCurrentTimeInLong(),DATA_FORMAT_ID);
    }
    /**
     * 获得HHmm
     * @return
     */
    public static String getCurrentTimeInDAY(){
        return getTime(getCurrentTimeInLong(),DATA_FORMAT_DAY);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    public static String getLongFromTimeToTime(long starttime , long endtime){
        int hours = (int) ((endtime - starttime)/(1000 * 60 * 60));
        int minutes = (int) ((endtime - starttime)/(1000 * 60));
        int sec = (int) (((endtime - starttime)%1000)%60);
        return String.format("%02d",hours)+":"+String.format("%02d",minutes)+":"+String.format("%02d",sec);
    }

    /**
     * 将yyyyMMddHHmmss 转换成yyyy年MM月dd日 HH:mm:ss
     * @param date
     * @return
     */
    public static String formatStringToDate(String date){
        try {
            Date date1 = new SimpleDateFormat("yyyyMMddHHmmss").parse(date);
            return new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
