package com.cjlr.entity;

import java.math.BigDecimal;

/**
 * 成绩备注Bean
 * @author lianyi
 *
 */
public class CjglCjbzszbBean {
	private String bzdm;     // 手动输入，备注的名称唯一
	private String bzmc;     // 备注名称
	private BigDecimal dyfs; // 对应分数
	private String bz;       // 备注

	public String getBzdm() {
		return bzdm;
	}
	public void setBzdm(String bzdm) {
		this.bzdm = bzdm;
	}
	public String getBzmc() {
		return bzmc;
	}
	public void setBzmc(String bzmc) {
		this.bzmc = bzmc;
	}
	public BigDecimal getDyfs() {
		return dyfs;
	}
	public void setDyfs(BigDecimal dyfs) {
		this.dyfs = dyfs;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}

}
