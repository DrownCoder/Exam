package nwsuaf.com.exam.service;

import android.content.Context;

import org.greenrobot.greendao.query.WhereCondition;

import java.io.IOException;
import java.util.List;

import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.db.DBController;
import nwsuaf.com.exam.db.ExamDBHelper;
import nwsuaf.com.exam.entity.examdate;
import nwsuaf.com.exam.entity.examdateDao;
import nwsuaf.com.exam.entity.lcerror;
import nwsuaf.com.exam.entity.lcerrorDao;
import nwsuaf.com.exam.entity.lcproblem;
import nwsuaf.com.exam.entity.lcproblemDao;


public class ExamDateService {
	private ExamDBHelper dbHelper = null;
	private Context mContext;
    private examdateDao dateDao;

	public ExamDateService(Context context) {
		this.mContext = context;

		dbHelper = new ExamDBHelper(mContext);
		try {
            dbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
        dateDao = DBController.getDaoSession(AppConstants.LOCAL_DB_NAME).getExamdateDao();
    }

    /**
     * 获得未通过的记录
     * @return lcerror
     */
	public List<examdate> getUnpassData(String date){
		return dateDao.queryBuilder().where(examdateDao.Properties.Ispass.eq(0)).list();
	}
    /**
     * 获取通过的记录
     * return lcproblem
     */
    public List<examdate> getPassData(){
        return dateDao.queryBuilder().where(examdateDao.Properties.Ispass.eq(1)).list();
    }

    /**
     * 获得所有做题记录
     * @return
     */
    public List<examdate> getTotalData(){
        return dateDao.queryBuilder().list();
    }

    /**
     * 插入一条做题记录
     * @param data
     */

    public void addExamDateToDB(examdate data){
        dateDao.insert(data);
    }

}
