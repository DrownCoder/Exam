package nwsuaf.com.exam.entity.netmodel;



import java.util.List;


public class NetObject_Answer {
    private String id;
    private List<Answer> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Answer> getData() {
        return data;
    }

    public void setData(List<Answer> data) {
        this.data = data;
    }
}
