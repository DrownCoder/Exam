package nwsuaf.com.exam.entity.netmodel;



import java.io.Serializable;
import java.util.List;


public class NetObject_Answer implements Serializable{
    private String id;
    private List<FAnswer> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FAnswer> getData() {
        return data;
    }

    public void setData(List<FAnswer> data) {
        this.data = data;
    }
}
