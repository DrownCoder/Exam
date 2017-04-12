package nwsuaf.com.exam.service;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import nwsuaf.com.exam.db.DBController;
import nwsuaf.com.exam.entity.Problems;
import nwsuaf.com.exam.db.ExamDBHelper;
import nwsuaf.com.exam.entity.ProblemsDao;


public class ProblemService {
	private ExamDBHelper dbHelper = null;
	private Context mContext;
    private ProblemsDao problemsDao;

	public ProblemService(Context context) {
		this.mContext = context;

		dbHelper = new ExamDBHelper(mContext);
		try {
            dbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
        problemsDao = DBController.getDaoSession(DBController.DATABASE_SCHOOL_NAME).getProblemsDao();
    }

    /**
     * 获得不同类型的题
     * @param type
     * @return
     */
	public List<Problems> getGroupData(String type){
		List<Problems> groupData;
		groupData = problemsDao.queryBuilder().where(ProblemsDao.Properties.Type.eq(type)).list();
		return groupData;
	}

    /**
     * 获得所有自测数据
     * @return
     */
    public List<Problems> getTotalData(){
        List<Problems> datas;
        datas = problemsDao.queryBuilder().list();
        return datas;
    }

}
