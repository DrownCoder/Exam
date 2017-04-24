package nwsuaf.com.exam.service;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.db.DBController;
import nwsuaf.com.exam.db.ExamDBHelper;
import nwsuaf.com.exam.entity.answer;
import nwsuaf.com.exam.entity.answerDao;

public class AnswerService {
	private ExamDBHelper dbHelper = null;
	private Context mContext;
    private answerDao answerDao;

	public AnswerService(Context context) {
		this.mContext = context;

		dbHelper = new ExamDBHelper(mContext);
		try {
            dbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
        answerDao = DBController.getDaoSession(AppConstants.LOCAL_DB_NAME).getAnswerDao();
    }

    /**
     * 获得备份缓存的答案
     * @param type
     * @return
     */
	public List<answer> getGroupData(){
		List<answer> groupData;
		groupData = answerDao.queryBuilder().list();
		return groupData;
	}

    /**
     * 缓存答案
     * @return
     */
    public void inserAnswer(List<answer> data){
        for(answer item:data){
            answerDao.insertOrReplace(item);
            Log.i("cache", "" + item.getNum());
        }
    }

    /**
     * 删除所有数据
     */
    public void deleteAll(){
        answerDao.deleteAll();
    }

}
