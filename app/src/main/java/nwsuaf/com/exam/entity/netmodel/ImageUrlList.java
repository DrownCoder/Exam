package nwsuaf.com.exam.entity.netmodel;

import java.io.Serializable;
import java.util.List;

public class ImageUrlList implements Serializable{
    private static final long serialVersionUID = 6226755799531293257L;
	private int id;
	private List<String> urllist;
	public int getId() {
		return id;
	}
	public void setId(int i) {
		this.id = i;
	}
	public List<String> getUrllist() {
		return urllist;
	}
	public void setUrllist(List<String> urllist) {
		this.urllist = urllist;
	}
}
