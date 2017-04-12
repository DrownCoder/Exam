package nwsuaf.com.exam.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import nwsuaf.com.exam.app.AppConstants;

public class ExamDBHelper extends SQLiteOpenHelper{
    //用户数据库文件的版本
    private static final int DB_VERSION = 1;
    //数据库文件目标存放路径为系统默认位置，nwsuaf.com.exam是你的包名
    @SuppressLint("SdCardPath")
    private static final String DB_PATH = "/data/data/nwsuaf.com.exam/databases/";
    //如果你想把数据库存放在SD卡的话
    //private static String DB_SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
    //		+ "/mobilesafe/database/";

    private static String DB_NAME = AppConstants.LOCAL_DB_NAME;
    private static String ASSETS_NAME = AppConstants.LOCAL_ASSETS_NAME;

    private SQLiteDatabase mDataBase = null;
    private final Context mContext;

    /*
     * 如果数据库文件较大，使用FileSplit分割为小于1M的小文件
     * 此例中分割为commonnum.db.101  commonnum.db.102 commonnum.db.103 ...
     */
    //第一个文件名后缀
    private static final int ASSETS_SUFFIX_BEGIN = 101;
    //最后一个文件名后缀
    private static final int ASSETS_SUFFIX_END = 103;

    /**
     * 在SQLiteOpenHelper的子类当中，必须有该构造函数
     * @param context 上下文对象
     * @param name     数据库名称
     * @param factory  一般都是null
     * @param version  当前数据库的版本，值必须是整数并且是递增的状态
     */
    public ExamDBHelper(Context context, String name,
                        CursorFactory factory, int version) {
        //必须通过super调用父类当中的构造函数
        super(context, name, factory, version);
        this.mContext = context;
    }

    public ExamDBHelper(Context context, String name, int version){
        this(context, name, null, version);
    }

    public ExamDBHelper(Context context, String name){
        this(context, name, DB_VERSION);
    }

    public ExamDBHelper(Context context){
        this(context, DB_PATH + DB_NAME);
    }

    public void createDataBase() throws IOException{
        //deleteDataBase();
        boolean dbExist = checkDataBase();
        if(dbExist){
            //数据库已存在，do nothing
        }else{
            File dir = new File(DB_PATH);
            if(!dir.exists()){
                dir.mkdirs();
            }
            File dbFile = new File(DB_PATH + DB_NAME);
            if(dbFile.exists()){
                dbFile.delete();
            }
            SQLiteDatabase.openOrCreateDatabase(dbFile, null);
            //复制asserts中的db文件到DB_PATH下
            copyDataBase();
        }
    }

    /**
     * 检查数据库是否有效
     * @return
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        String mPath = DB_PATH + DB_NAME;
        try{
            checkDB = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //database does't exist yet.
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
        //Open your local db as the input stream
        InputStream myInput = mContext.getAssets().open(ASSETS_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    //复制assets下的大数据库文件时用这个
    @SuppressWarnings("unused")
    private void copyBigDataBase() throws IOException{
        InputStream myInput;
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        for (int i = ASSETS_SUFFIX_BEGIN; i < ASSETS_SUFFIX_END+1; i++) {
            myInput = mContext.getAssets().open(ASSETS_NAME + "." + i);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myInput.close();
        }
        myOutput.close();
    }

    @Override
    public synchronized void close() {
        if(mDataBase != null){
            mDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}

