package nwsuaf.com.exam.entity.netmodel;



import java.io.Serializable;
import java.util.List;

import nwsuaf.com.exam.entity.lcproblem;


public class NetObject_Problem implements Serializable{
    private static final long serialVersionUID = 5226755799531293257L;
    private List<lcproblem> data;
    private int returncode;
    private List<ImageUrlList> img;
    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public List<lcproblem> getData() {
        return data;
    }

    public void setData(List<lcproblem> data) {
        this.data = data;
    }

    public List<ImageUrlList> getImg() {
        return img;
    }

    public void setImg(List<ImageUrlList> img) {
        this.img = img;
    }
}
