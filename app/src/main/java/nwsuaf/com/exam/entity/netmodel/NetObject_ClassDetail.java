package nwsuaf.com.exam.entity.netmodel;



import java.util.List;


public class NetObject_ClassDetail {
    private String code;
    private List<StudentState> data;
    private String msg;

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

    public List<StudentState> getData() {
        return data;
    }

    public void setData(List<StudentState> data) {
        this.data = data;
    }
}
