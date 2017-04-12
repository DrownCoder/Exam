package nwsuaf.com.exam.entity.netmodel;

public class StudentInfo {
	private String stuid;
	private String stuname;
	private int stusex;
	private int stumajor;
	private int stuclass;
	private String stupasswd;
	public String getStuid() {
		return stuid;
	}
	public void setStuid(String stuid) {
		this.stuid = stuid;
	}
	public String getName() {
		return stuname;
	}
	public void setName(String name) {
		this.stuname = name;
	}
	public int getSex() {
		return stusex;
	}
	public void setSex(int sex) {
		this.stusex = sex;
	}
	public int getStumajor() {
		return stumajor;
	}
	public void setStumajor(int stumajor) {
		this.stumajor = stumajor;
	}
	public int getStuclass() {
		return stuclass;
	}
	public void setStuclass(int stuclass) {
		this.stuclass = stuclass;
	}
	public String getStupasswd() {
		return stupasswd;
	}
	public void setStupasswd(String stupasswd) {
		this.stupasswd = stupasswd;
	}
	@Override
	public String toString() {
		return "StudentInfo [name=" + stuname + ", sex=" + stusex + ", stuclass="
				+ stuclass + ", stuid=" + stuid + ", stumajor=" + stumajor
				+ ", stupasswd=" + stupasswd + "]";
	}
	
}
