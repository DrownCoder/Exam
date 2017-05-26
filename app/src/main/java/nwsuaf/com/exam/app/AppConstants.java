package nwsuaf.com.exam.app;

import android.os.Environment;

/**
 * Created by Xuan on 2017/1/22.
 */
public class AppConstants {
    public static final int PROBLEM_TYPE_SINGLE             = 1;                //单选题
    public static final int PROBLEM_TYPE_MULTI              = 2;                //多选题
    public static final int PROBLEM_TYPE_JUDGE              = 3;                //判断题
    public static final int PROBLEM_TYPE_PIC                = 4;                //图片判断题

    public static final String PROBLEM_NAME_SINGLE          = "单选题";
    public static final String PROBLEM_NAME_MULTI           = "多选题";
    public static final String PROBLEM_NAME_JUDGE           = "判断题";
    public static final String PROBLEM_NAME_PIC             = "图片判断题";

    public static final String LOCAL_DB_NAME                ="exam.db";         //数据库名
    public static final String LOCAL_ASSETS_NAME            ="exam.db";         //ASSETS文件名
    public static final String LOCAL_DATA_BAK               ="exambak.out";     //本地备份试题名
    public static final String LOCAL_ANSWER_BAK               ="answerbak.out";     //本地备份答案名

    //数据库保存路径
    public static final String DB_PATH                      ="/data/data/nwsuaf.com.exam/databases/exam.db";

    public static final int TYPE_MINEEXAM                   = 1;                //自测-考试
    public static final int TYPE_SEE_ERROR                  = 2;                //查看错题题目
    public static final int TYPE_SEE_EXAM                   = 3;                //查看试卷
    public static final int TYPE_SEE_ERRORLIST              = 4;                //查看所有错题
    public static final int TYPE_SINGLE                     = 5;                //单选题-考试
    public static final int TYPE_MULTIPLE                   = 6;                //多选题-考试
    public static final int TYPE_JUDGE                      = 7;                //判断题-考试
    public static final int TYPE_PIC                        = 8;                //图片选择题-考试
    public static final int TYPE_SEE_EXAM_SINGLE            = 9;                //查看单选题
    public static final int TYPE_SEE_EXAM_MULTI             = 10;               //查看多选题
    public static final int TYPE_SEE_EXAM_JUDGE             = 11;               //查看判断题
    public static final int TYPE_SEE_EXAM_PIC               = 12;               //查看图片判断题
    public static final int TYPE_EXAM                       = 13;               //联网获取题目


    /**
     * 服务器
     */
    public static final String LOCAL_HOST                  ="http://192.168.139.1:8080/exam";
    public static final String WEBSERVER                   ="/api";
    public final static String DONTEXIST = "100";
    public final static String WRONGPASSWORD = "200";
    public final static String SUCCESSLOGIN = "400";
    public static final String UPDATESUCCESS = "300";
    public static final String INSERTSUCCESS = "202";
    public static final String INSERTERROR = "203";
    public static final String NOTHINGFOUND = "111";
    public static final String SUCCESSFOUND = "201";
    public static final String SELECTERROR = "401";
    public static final String SUCCESS_CREATE_CLASS = "204";                    //创建班级成功
    public static final String SUCCESS_GETCLASSLIST = "205";                    //获得班级状态成功
    public static final String SUCCESS_GETPROBLEM = "206";                      //获得题目成功
    public static final String SUCCESS_SENTANSWER = "207";                      //提交成绩成功

    /**
     * 图片缓存
     */
    public static final String APP_DIR                    = Environment.getExternalStorageDirectory() + "/Exam";
    public static final String APP_TEMP                   = APP_DIR + "/temp";
    public static final String APP_IMAGE = APP_DIR + "/image";
    /**
     * 倒计时
     */
    public static final int WAIT_SENT_TIME = 10000;                             //发卷倒计时
    /**
     * 重复进入考试判断
     */
    public static boolean ISSTARTED = false;                               //是否能进入考试
}
