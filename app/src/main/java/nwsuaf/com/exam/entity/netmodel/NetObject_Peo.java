package nwsuaf.com.exam.entity.netmodel;



import java.util.List;


public class NetObject_Peo {
    private List<StudentInfo> data;
    private int returncode;

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public List<StudentInfo> getData() {
        return data;
    }

    public void setData(List<StudentInfo> data) {
        this.data = data;
    }
}
