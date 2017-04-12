package nwsuaf.com.exam.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

import static nwsuaf.com.exam.app.AppConstants.PROBLEM_NAME_JUDGE;
import static nwsuaf.com.exam.app.AppConstants.PROBLEM_NAME_MULTI;
import static nwsuaf.com.exam.app.AppConstants.PROBLEM_NAME_PIC;
import static nwsuaf.com.exam.app.AppConstants.PROBLEM_NAME_SINGLE;
import static nwsuaf.com.exam.app.AppConstants.PROBLEM_TYPE_JUDGE;
import static nwsuaf.com.exam.app.AppConstants.PROBLEM_TYPE_MULTI;
import static nwsuaf.com.exam.app.AppConstants.PROBLEM_TYPE_PIC;
import static nwsuaf.com.exam.app.AppConstants.PROBLEM_TYPE_SINGLE;

/**
 * Created by Administrator on 2017/1/22.
 */
@Entity
public class lcproblem implements Serializable{
    private static final long serialVersionUID = 4226755799531293257L;
    @Id
    private int id;
    private String title;
    private String answer;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private int type; //1-单选  2-多选  3-判断  4-图片
    @Transient
    private String typename;
    @Transient
    private boolean isFalse;
    @Generated(hash = 717455847)
    public lcproblem(int id, String title, String answer, String a, String b,
            String c, String d, String e, int type) {
        this.id = id;
        this.title = title;
        this.answer = answer;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.type = type;
    }
    @Generated(hash = 484119072)
    public lcproblem() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAnswer() {
        return this.answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getA() {
        return this.a;
    }
    public void setA(String a) {
        this.a = a;
    }
    public String getB() {
        return this.b;
    }
    public void setB(String b) {
        this.b = b;
    }
    public String getC() {
        return this.c;
    }
    public void setC(String c) {
        this.c = c;
    }
    public String getD() {
        return this.d;
    }
    public void setD(String d) {
        this.d = d;
    }
    public String getE() {
        return this.e;
    }
    public void setE(String e) {
        this.e = e;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public String getTypename() {
        switch (type){
            case PROBLEM_TYPE_SINGLE:
                setTypename(PROBLEM_NAME_SINGLE);
                break;
            case  PROBLEM_TYPE_MULTI:
                setTypename(PROBLEM_NAME_MULTI);
                break;
            case  PROBLEM_TYPE_JUDGE:
                setTypename(PROBLEM_NAME_JUDGE);
                break;
            case  PROBLEM_TYPE_PIC:
                setTypename(PROBLEM_NAME_PIC);
                break;

        }
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public boolean isFalse() {
        return isFalse;
    }

    public void setIsFalse(boolean isFalse) {
        this.isFalse = isFalse;
    }
}
