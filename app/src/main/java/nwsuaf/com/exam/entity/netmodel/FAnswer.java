package nwsuaf.com.exam.entity.netmodel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dengzhaoxuan on 2017/4/13.
 */
@Entity
public class FAnswer implements Serializable{
    private static final long serialVersionUID = 1226755799531293257L;
    @Id
    private String id;
    private String ke;
    private String shu;
    private String zhong;
    @Transient
    private boolean isEmpty;
    @Generated(hash = 2047766152)
    public FAnswer(String id, String ke, String shu, String zhong) {
        this.id = id;
        this.ke = ke;
        this.shu = shu;
        this.zhong = zhong;
    }
    @Generated(hash = 668010316)
    public FAnswer() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getKe() {
        return this.ke;
    }
    public void setKe(String ke) {
        this.ke = ke;
    }
    public String getShu() {
        return this.shu;
    }
    public void setShu(String shu) {
        this.shu = shu;
    }
    public String getZhong() {
        return this.zhong;
    }
    public void setZhong(String zhong) {
        this.zhong = zhong;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
