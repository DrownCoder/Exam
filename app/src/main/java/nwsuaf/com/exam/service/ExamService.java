package nwsuaf.com.exam.service;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.db.DBController;
import nwsuaf.com.exam.db.ExamDBHelper;
import nwsuaf.com.exam.entity.lcproblem;
import nwsuaf.com.exam.entity.lcproblemDao;
import nwsuaf.com.exam.entity.ProblemsDao;


public class ExamService {
    private ExamDBHelper dbHelper = null;
    private Context mContext;
    private lcproblemDao problemsDao;

    public ExamService(Context context) {
        this.mContext = context;

        dbHelper = new ExamDBHelper(mContext);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        problemsDao = DBController.getDaoSession(AppConstants.LOCAL_DB_NAME).getLcproblemDao();
    }

    /**
     * 获得不同类型的题
     * @param type
     * @return
     */
    public List<lcproblem> getTypeGroupData(int type){
        List<lcproblem> groupData;
        groupData = problemsDao.queryBuilder().where(lcproblemDao.Properties.Type.eq(type)).list();
        return groupData;
    }

    /**
     * 获得所有自测数据
     * @return
     */
    public List<lcproblem> getTotalData(){
        List<lcproblem> datas;
        datas = problemsDao.queryBuilder().list();
        return datas;
    }

}
