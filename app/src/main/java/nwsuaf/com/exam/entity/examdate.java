package nwsuaf.com.exam.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/2/19.
 */
@Entity
public class examdate {
    @Id
    private Integer id;
    private String date;//考试时间
    private int totalnum;//题目总量
    private int right;//做对的题量
    private int error;//做错的题量
    private String time;//耗时
    private int ispass;//是否通过 0----未通过   1----通过
    @Generated(hash = 1713495476)
    public examdate(Integer id, String date, int totalnum, int right, int error,
            String time, int ispass) {
        this.id = id;
        this.date = date;
        this.totalnum = totalnum;
        this.right = right;
        this.error = error;
        this.time = time;
        this.ispass = ispass;
    }
    @Generated(hash = 543035541)
    public examdate() {
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getTotalnum() {
        return this.totalnum;
    }
    public void setTotalnum(int totalnum) {
        this.totalnum = totalnum;
    }
    public int getRight() {
        return this.right;
    }
    public void setRight(int right) {
        this.right = right;
    }
    public int getError() {
        return this.error;
    }
    public void setError(int error) {
        this.error = error;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public int getIspass() {
        return this.ispass;
    }
    public void setIspass(int ispass) {
        this.ispass = ispass;
    }
}
