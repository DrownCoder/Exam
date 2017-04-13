package nwsuaf.com.exam.entity.netmodel;

/**
 * Created by dengzhaoxuan on 2017/4/13.
 */

public class StudentState {
    private String id;
    private String name;
    private boolean isJoin;
    private boolean isSubmit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isJoin() {
        return isJoin;
    }

    public void setJoin(boolean join) {
        isJoin = join;
    }

    public boolean isSubmit() {
        return isSubmit;
    }

    public void setSubmit(boolean submit) {
        isSubmit = submit;
    }
}
