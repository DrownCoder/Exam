package nwsuaf.com.exam.service;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import nwsuaf.com.exam.db.DBController;
import nwsuaf.com.exam.db.ExamDBHelper;
import nwsuaf.com.exam.entity.netmodel.FAnswer;
import nwsuaf.com.exam.entity.netmodel.FAnswerDao;

public class FAnswerService {
	private ExamDBHelper dbHelper = null;
	private Context mContext;
    private FAnswerDao answerDao;

	public FAnswerService(Context context) {
		this.mContext = context;

		dbHelper = new ExamDBHelper(mContext);
		try {
            dbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
        answerDao = DBController.getDaoSession(DBController.DATABASE_SCHOOL_NAME).getFAnswerDao();
    }

  /**
     * 获得备份缓存的答案
     * @return
     */
	public List<FAnswer> getGroupData(){
		List<FAnswer> groupData;
		groupData = answerDao.queryBuilder().list();
		return groupData;
	}

    /**
     * 缓存答案
     * @return
     */
    public void inserAnswer(List<FAnswer> data){
        for(FAnswer item:data){
            answerDao.insertOrReplace(item);
        }
    }

    /**
     * 删除所有数据
     */
    public void deleteAll(){
        answerDao.deleteAll();
    }
}
