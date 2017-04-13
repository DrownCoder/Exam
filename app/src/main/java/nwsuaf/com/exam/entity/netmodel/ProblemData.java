package nwsuaf.com.exam.entity.netmodel;

import java.util.List;

/**
 * Created by dengzhaoxuan on 2017/4/13.
 * 新数据类型，不要问为什么，因为改需求
 */

public class ProblemData {
    private String id;
    private List<String> imgList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
