package com.cjlr.entity;

import java.util.List;

/**
 * 教学班班级信息
 * @author lianyi
 *
 */
public class JxjhglJxbbjxxBean {

	private String bjdm; // 班级代码
	private String bjmc; // 班级名称
	private String jxbh; // 教学班号
	private String bz;   // 备注
	private String hbzt; // 合班状态(0主班,1从班,2作废)

	private List<CjglXscjjlBean> cjjlList; // 该行政班的成绩记录

	public String getBjdm() {
		return bjdm;
	}
	public void setBjdm(String bjdm) {
		this.bjdm = bjdm;
	}
	public String getBjmc() {
		return bjmc;
	}
	public void setBjmc(String bjmc) {
		this.bjmc = bjmc;
	}
	public String getJxbh() {
		return jxbh;
	}
	public void setJxbh(String jxbh) {
		this.jxbh = jxbh;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getHbzt() {
		return hbzt;
	}
	public void setHbzt(String hbzt) {
		this.hbzt = hbzt;
	}
	public List<CjglXscjjlBean> getCjjlList() {
		return cjjlList;
	}
	public void setCjjlList(List<CjglXscjjlBean> cjjlList) {
		this.cjjlList = cjjlList;
	}

}
