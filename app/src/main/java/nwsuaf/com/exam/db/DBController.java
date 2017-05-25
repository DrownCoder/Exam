package nwsuaf.com.exam.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.app.MainApplication;
import nwsuaf.com.exam.entity.DaoMaster;
import nwsuaf.com.exam.entity.DaoSession;


public class DBController
{
    private static DaoMaster daoMasterEcmc;

    private static DaoMaster daoMasterSchool;

    private static DaoSession daoSessionDefault;

    private static DaoSession daoSchoolSession;


    public static final String DATABASE_NAME = "exam.db";

    public static final String DATABASE_SCHOOL_NAME = "exam.db";

    private static DaoMaster obtainMaster(Context context, String dbName)
    {
        //return new DaoMaster(new DaoMaster.DevOpenHelper(context, dbName, null).getWritableDatabase());
        return new DaoMaster(SQLiteDatabase.openDatabase(AppConstants.DB_PATH, null, SQLiteDatabase.OPEN_READWRITE));
    }

    private static DaoMaster getDaoMaster(Context context, String dbName)
    {
        if (dbName == null)
            return null;
        if (daoMasterEcmc == null)
        {
            daoMasterEcmc = obtainMaster(context, dbName);
        }
        return daoMasterEcmc;
    }

    private static DaoMaster getSchoolDaoMaster(Context context, String dbName)
    {
        if (dbName == null)
            return null;
        if (daoMasterSchool == null)
        {
            daoMasterSchool = obtainMaster(context, dbName);
        }
        return daoMasterSchool;
    }


    public static DaoSession getDaoSession(String dbName)
    {

        if (daoSchoolSession == null)
        {
            daoSchoolSession = getSchoolDaoMaster(MainApplication.getInstance(), dbName).newSession();
        }
        return daoSchoolSession;
    }

    public static DaoSession getDaoSession()
    {

        if (daoSessionDefault == null)
        {
            daoSessionDefault = getDaoMaster(MainApplication.getInstance(), DATABASE_NAME).newSession();
        }
        return daoSessionDefault;
    }
}
