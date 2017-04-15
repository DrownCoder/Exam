package nwsuaf.com.exam.entity.netmodel;



import java.util.List;


public class NetObject_ClassList {
    private String code;
    private List<ClassInfo> data;
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

    public List<ClassInfo> getData() {
        return data;
    }

    public void setData(List<ClassInfo> data) {
        this.data = data;
    }
}
