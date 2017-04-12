package nwsuaf.com.exam.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

/**
 * Created by Administrator on 2017/2/1.
 */
@Entity
public class lcerror {
    @Id
    private Integer id;
    private Long tid;
    private String date;
    @Generated(hash = 901026259)
    public lcerror(Integer id, Long tid, String date) {
        this.id = id;
        this.tid = tid;
        this.date = date;
    }
    @Generated(hash = 872014937)
    public lcerror() {
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Long getTid() {
        return this.tid;
    }
    public void setTid(Long tid) {
        this.tid = tid;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
   }
