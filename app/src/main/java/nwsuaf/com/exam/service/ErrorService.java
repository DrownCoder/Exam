package nwsuaf.com.exam.service;

import android.content.Context;

import org.greenrobot.greendao.query.WhereCondition;

import java.io.IOException;
import java.util.List;

import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.db.DBController;
import nwsuaf.com.exam.db.ExamDBHelper;
import nwsuaf.com.exam.entity.lcerror;
import nwsuaf.com.exam.entity.lcerrorDao;
import nwsuaf.com.exam.entity.lcproblem;
import nwsuaf.com.exam.entity.lcproblemDao;


public class ErrorService {
	private ExamDBHelper dbHelper = null;
	private Context mContext;
    private lcerrorDao errorDao;
    private lcproblemDao lcproblemDao;

	public ErrorService(Context context) {
		this.mContext = context;

		dbHelper = new ExamDBHelper(mContext);
		try {
            dbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
        errorDao = DBController.getDaoSession(AppConstants.LOCAL_DB_NAME).getLcerrorDao();
        lcproblemDao = DBController.getDaoSession(AppConstants.LOCAL_DB_NAME).getLcproblemDao();
    }

    /**
     * 获得不同时间的错题
     * @return lcerror
     */
	public List<lcerror> getGroupData(String date){
		return errorDao.queryBuilder().where(lcerrorDao.Properties.Date.eq(date)).list();
	}
    /**获得不同时间的错题数据
     * return lcproblem
     */
    public List<lcproblem> getErrorData(String date){
        List<lcproblem> data;
        data =lcproblemDao.queryBuilder().where(
                new WhereCondition.StringCondition("ID IN " +
                        "(SELECT TID FROM LCERROR WHERE DATE = '"+date+"')")
        ).build().list();
        return data;
    }

    /**
     * 获得所有错题
     * @return
     */
    public List<lcproblem> getTotalData(){
        List<lcproblem> datas;
        datas =lcproblemDao.queryBuilder().where(
                new WhereCondition.StringCondition("ID IN " +
                        "(SELECT TID FROM LCERROR)")
        ).build().list();
        return datas;
    }

    /**
     * 插入一个错题集
     * @param data
     */

    public void addErrorListToDB(List<lcerror> data){
        for(lcerror item:data){
            errorDao.insert(item);
        }
    }

}
