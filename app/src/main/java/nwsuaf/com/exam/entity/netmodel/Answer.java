package nwsuaf.com.exam.entity.netmodel;

/**
 * Created by dengzhaoxuan on 2017/4/13.
 */

public class Answer {
    private String id;
    private String ke;
    private String shu;
    private String zhong;
    private boolean isEmpty;

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKe() {
        return ke;
    }

    public void setKe(String ke) {
        this.ke = ke;
    }

    public String getShu() {
        return shu;
    }

    public void setShu(String shu) {
        this.shu = shu;
    }

    public String getZhong() {
        return zhong;
    }

    public void setZhong(String zhong) {
        this.zhong = zhong;
    }
}
