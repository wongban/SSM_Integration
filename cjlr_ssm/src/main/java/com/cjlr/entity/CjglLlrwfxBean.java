package com.cjlr.entity;

/**
 * 录入任务分项
 * @author lianyi
 *
 */
public class CjglLlrwfxBean {
	private String zj;     // 主键
	private String rwbh;   // 录入任务号
	private String fxmc;   // 分项名称
	private String fxdm;   // 分项代码
	private String fxszbl; // 分项所占比例

	public String getZj() {
		return zj;
	}
	public void setZj(String zj) {
		this.zj = zj;
	}
	public String getRwbh() {
		return rwbh;
	}
	public void setRwbh(String rwbh) {
		this.rwbh = rwbh;
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
	public String getFxszbl() {
		return fxszbl;
	}
	public void setFxszbl(String fxszbl) {
		this.fxszbl = fxszbl;
	}

}
