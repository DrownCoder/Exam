package nwsuaf.com.exam.entity.netmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class AnswerItem {
    private String id;
    private List<CustomAnswer> answer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CustomAnswer> getAnswers() {
        return answer;
    }

    public void setAnswers(List<CustomAnswer> answers) {
        this.answer = answers;
    }
}
