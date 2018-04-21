package com.cjlr.entity;

public class CjglXscjfxbBean {
	private String zj;        // 主键
	private String cjbh;      // 成绩编号
	private String fxmc;      // 分项名称
	private String fxdm;      // 分项代码
	private String fxszbl;    // 分项所占比例
	private String hscj;      // 免考、缺考、旷考、作弊
	private String cj;        // 成绩

	public String getZj() {
		return zj;
	}
	public void setZj(String zj) {
		this.zj = zj;
	}
	public String getCjbh() {
		return cjbh;
	}
	public void setCjbh(String cjbh) {
		this.cjbh = cjbh;
	}
	public String getFxmc() {
		return fxmc;
	}
	public void setFxmc(String fxmc) {
		this.fxmc = fxmc;
	}
	public String getFxdm() {
		return fxdm;
	}
	public void setFxdm(String fxdm) {
		this.fxdm = fxdm;
	}
	public String getHscj() {
		return hscj;
	}
	public void setHscj(String hscj) {
		this.hscj = hscj;
	}
	public String getFxszbl() {
		return fxszbl;
	}
	public void setFxszbl(String fxszbl) {
		this.fxszbl = fxszbl;
	}
	public String getCj() {
		return cj;
	}
	public void setCj(String cj) {
		this.cj = cj;
	}
	@Override
	public String toString() {
		return "CjglXscjfxbBean [zj=" + zj + ", cjbh=" + cjbh + ", fxmc=" + fxmc + ", fxdm=" + fxdm + ", fxszbl="
				+ fxszbl + ", hscj=" + hscj + ", cj=" + cj + "]";
	}

}
