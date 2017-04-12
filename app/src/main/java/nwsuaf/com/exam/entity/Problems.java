package nwsuaf.com.exam.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2016/12/24.
 */
@Entity
public class Problems {
    @Id
    private int id;
    private String title;
    private String answer;
    private String a;
    private String b;
    private String c;
    private String d;
    private int type; //1-单选  2-多选  3-判断  4-图片
    @Transient
    private String typename;
    @Transient
    private boolean isFalse;

    @Generated(hash = 1585148467)
    public Problems(int id, String title, String answer, String a, String b,
            String c, String d, int type) {
        this.id = id;
        this.title = title;
        this.answer = answer;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.type = type;
    }

    @Generated(hash = 1295844671)
    public Problems() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getTypename() {
        switch (type){
            case  3:
                setTypename("单选题");
                break;
            case  2:
                setTypename("判断题");
                break;
            case  1:
                setTypename("图片判断题");
                break;
            case  4:
                setTypename("多选题");
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
