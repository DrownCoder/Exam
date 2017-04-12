package nwsuaf.com.exam.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Administrator on 2017/1/13.
 */
@Entity
public class answer {
    //题号
    @Property(nameInDb = "num")
    @Id
    private Long num;
    //所答答案
    private String answer ;
    //是否为空
    private int isnull;
    //是否正确
    private int istrue;
    @Generated(hash = 1226474543)
    public answer(Long num, String answer, int isnull, int istrue) {
        this.num = num;
        this.answer = answer;
        this.isnull = isnull;
        this.istrue = istrue;
    }
    @Generated(hash = 1691201803)
    public answer() {
    }
    public Long getNum() {
        return this.num;
    }
    public void setNum(Long num) {
        this.num = num;
    }
    public String getAnswer() {
        return this.answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public int getIsnull() {
        return this.isnull;
    }
    public void setIsnull(int isnull) {
        this.isnull = isnull;
    }
    public int getIstrue() {
        return this.istrue;
    }
    public void setIstrue(int istrue) {
        this.istrue = istrue;
    }
}
