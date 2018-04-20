package com.cjlr.entity;

/**
 * 成绩管理_成绩等级对照表
 * @author lianyi
 *
 */
public class CjglCjdjdzbBean {
	private String djdm;  // 手动输入，名称唯一
	private String djmc;  // 等级名称
	private String zxfsx; // 最小分数线
	private String zdfsx; // 最大分数线
	private String bz;    // 备注
	private String kslx;  // 考试类型

	public String getDjdm() {
		return djdm;
	}
	public void setDjdm(String djdm) {
		this.djdm = djdm;
	}
	public String getDjmc() {
		return djmc;
	}
	public void setDjmc(String djmc) {
		this.djmc = djmc;
	}
	public String getZxfsx() {
		return zxfsx;
	}
	public void setZxfsx(String zxfsx) {
		this.zxfsx = zxfsx;
	}
	public String getZdfsx() {
		return zdfsx;
	}
	public void setZdfsx(String zdfsx) {
		this.zdfsx = zdfsx;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getKslx() {
		return kslx;
	}
	public void setKslx(String kslx) {
		this.kslx = kslx;
	}
	
}
