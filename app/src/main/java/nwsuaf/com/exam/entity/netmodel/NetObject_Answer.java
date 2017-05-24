package nwsuaf.com.exam.entity.netmodel;



import java.io.Serializable;
import java.util.List;


public class NetObject_Answer implements Serializable{
    private String id;
    private List<AnswerItem> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AnswerItem> getData() {
        return data;
    }

    public void setData(List<AnswerItem> data) {
        this.data = data;
    }
}
