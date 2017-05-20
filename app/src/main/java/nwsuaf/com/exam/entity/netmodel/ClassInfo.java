package nwsuaf.com.exam.entity.netmodel;

import java.io.Serializable;

/**
 * Created by dengzhaoxuan on 2017/4/13.
 */

public class ClassInfo implements Serializable{
    private int id;
    private String classname;
    private int online;
    private int submit;
    private int unsubmit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getSubmit() {
        return submit;
    }

    public void setSubmit(int submit) {
        this.submit = submit;
    }

    public int getUnsubmit() {
        return unsubmit;
    }

    public void setUnsubmit(int unsubmit) {
        this.unsubmit = unsubmit;
    }
}
