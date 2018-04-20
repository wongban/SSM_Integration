package com.cjlr.entity;

public class XtglRzxxBean {
	private String rzzj;   // 日志主键(操作人账号+操作日期(要加时分秒))
	private String czrxm;  // 操作人姓名
	private String czrq;   // 操作日期
	private String ywmc;   // 业务名称
	private String czbmc;  // 操作表名称
	private String czbzj;  // 操作表主键值
	private String czlx;   // 操作类型(1新增2修改3 删除)
	private String bz;     // 备注
	private String ip;

	private String yhzh;      // 用户账号（自定义字段）

	public String getRzzj() {
		return rzzj;
	}
	public void setRzzj(String rzzj) {
		this.rzzj = rzzj;
	}
	public String getCzrxm() {
		return czrxm;
	}
	public void setCzrxm(String czrxm) {
		this.czrxm = czrxm;
	}
	public String getCzrq() {
		return czrq;
	}
	public void setCzrq(String czrq) {
		this.czrq = czrq;
	}
	public String getYwmc() {
		return ywmc;
	}
	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}
	public String getCzbmc() {
		return czbmc;
	}
	public void setCzbmc(String czbmc) {
		this.czbmc = czbmc;
	}
	public String getCzbzj() {
		return czbzj;
	}
	public void setCzbzj(String czbzj) {
		this.czbzj = czbzj;
	}
	public String getCzlx() {
		return czlx;
	}
	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getYhzh() {
		return yhzh;
	}
	public void setYhzh(String yhzh) {
		this.yhzh = yhzh;
	}

}
