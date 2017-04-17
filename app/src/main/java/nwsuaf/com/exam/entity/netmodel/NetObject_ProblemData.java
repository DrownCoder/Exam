package nwsuaf.com.exam.entity.netmodel;



import java.io.Serializable;
import java.util.List;


public class NetObject_ProblemData implements Serializable {
    private String code;
    private long time;
    private List<ProblemData> data;
    private String msg;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ProblemData> getData() {
        return data;
    }

    public void setData(List<ProblemData> data) {
        this.data = data;
    }
}
