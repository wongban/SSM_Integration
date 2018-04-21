package com.cjlr.entity;

import java.util.List;

public class CjglCjllrwBean {
	private String rwbh;      // 录入任务号=学年学期+课程编码+流水号(2位)
	private String rwmc;      // 录入任务名称
	private String kcbh;      // 课程编号
	private String kcmc;      // 课程名称
	private String kcsx;      // 课程属性
	private String kcxz;      // 课程性质
	private String zxf;       // 总学分
	private String zxs;       // 总学时
	private String kcksdw;    // 课程开设单位
	private String kcksxn;    // 课程开课学年
	private String kcksxq;    // 课程开课学期
	private String khfs;      // 考核方式
	private String rkjsxm;    // 多个教师，用逗号隔开
	private String rkjsjgh;   // 多个教师，用逗号隔开
	private String cjjlfs;    // 成绩记录方式（1百分制，2五级制，3二级制）
	private String cjlljgh;   // 成绩录入教师工号
	private String cjlljjsxm; // 成绩录入教师姓名
	private String jgfsx;     // 及格分数线
	private String shzt;      // 审核状态
	private String tjrxm;     // 提交人姓名
	private String tjsj;      // 提交时间
	private String bz;        // 备注
	private String cjllmm;    // 成绩录入密码
	private String kcxmbh;    // 课程项目编号
	private String kcxmmc;    // 课程项目名称
	private String sfgxk;

	// 关联查询，显示用
	private String jxbmc;     // 教学班名称
	private String sfhb;      // 是否合班
	private String kcksdwmc;  // 课程开设单位名称
	private String khfsmc;    // 考核方式名称
	private String kcsxmc;    // 课程属性名称
	private String kcxzmc;    // 课程性质名称
	private String skrs;      // 上课人数
	private String ylrs;      // 已录人数

	private String zyfxmc;    // 专业方向名称

	private List<CjglXscjjlBean> cjjlList; // 成绩记录List
	private List<CjglLlrwfxBean> fxList;   // 分项List

	public String getRwbh() {
		return rwbh;
	}
	public void setRwbh(String rwbh) {
		this.rwbh = rwbh;
	}
	public String getJxbmc() {
		return jxbmc;
	}
	public void setJxbmc(String jxbmc) {
		this.jxbmc = jxbmc;
	}
	public String getKcbh() {
		return kcbh;
	}
	public String getSfhb() {
		return sfhb;
	}
	public void setSfhb(String sfhb) {
		this.sfhb = sfhb;
	}
	public void setKcbh(String kcbh) {
		this.kcbh = kcbh;
	}
	public String getKcmc() {
		return kcmc;
	}
	public void setKcmc(String kcmc) {
		this.kcmc = kcmc;
	}
	public String getZxf() {
		return zxf;
	}
	public void setZxf(String zxf) {
		this.zxf = zxf;
	}
	public String getJgfsx() {
		return jgfsx;
	}
	public void setJgfsx(String jgfsx) {
		this.jgfsx = jgfsx;
	}
	public String getShzt() {
		return shzt;
	}
	public void setShzt(String shzt) {
		this.shzt = shzt;
	}
	public String getRwmc() {
		return rwmc;
	}
	public void setRwmc(String rwmc) {
		this.rwmc = rwmc;
	}
	public String getKcsx() {
		return kcsx;
	}
	public void setKcsx(String kcsx) {
		this.kcsx = kcsx;
	}
	public String getKcxz() {
		return kcxz;
	}
	public void setKcxz(String kcxz) {
		this.kcxz = kcxz;
	}
	public String getZxs() {
		return zxs;
	}
	public void setZxs(String zxs) {
		this.zxs = zxs;
	}
	public String getKcksdw() {
		return kcksdw;
	}
	public void setKcksdw(String kcksdw) {
		this.kcksdw = kcksdw;
	}
	public String getKcksxn() {
		return kcksxn;
	}
	public void setKcksxn(String kcksxn) {
		this.kcksxn = kcksxn;
	}
	public String getKcksxq() {
		return kcksxq;
	}
	public void setKcksxq(String kcksxq) {
		this.kcksxq = kcksxq;
	}
	public String getKhfs() {
		return khfs;
	}
	public void setKhfs(String khfs) {
		this.khfs = khfs;
	}
	public String getRkjsxm() {
		return rkjsxm;
	}
	public void setRkjsxm(String rkjsxm) {
		this.rkjsxm = rkjsxm;
	}
	public String getRkjsjgh() {
		return rkjsjgh;
	}
	public void setRkjsjgh(String rkjsjgh) {
		this.rkjsjgh = rkjsjgh;
	}
	public String getCjjlfs() {
		return cjjlfs;
	}
	public void setCjjlfs(String cjjlfs) {
		this.cjjlfs = cjjlfs;
	}
	public String getCjlljgh() {
		return cjlljgh;
	}
	public void setCjlljgh(String cjlljgh) {
		this.cjlljgh = cjlljgh;
	}
	public String getCjlljjsxm() {
		return cjlljjsxm;
	}
	public void setCjlljjsxm(String cjlljjsxm) {
		this.cjlljjsxm = cjlljjsxm;
	}
	public String getTjrxm() {
		return tjrxm;
	}
	public void setTjrxm(String tjrxm) {
		this.tjrxm = tjrxm;
	}
	public String getTjsj() {
		return tjsj;
	}
	public void setTjsj(String tjsj) {
		this.tjsj = tjsj;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getCjllmm() {
		return cjllmm;
	}
	public void setCjllmm(String cjllmm) {
		this.cjllmm = cjllmm;
	}
	public String getKcxmbh() {
		return kcxmbh;
	}
	public void setKcxmbh(String kcxmbh) {
		this.kcxmbh = kcxmbh;
	}
	public String getKcxmmc() {
		return kcxmmc;
	}
	public void setKcxmmc(String kcxmmc) {
		this.kcxmmc = kcxmmc;
	}
	public String getSfgxk() {
		return sfgxk;
	}
	public void setSfgxk(String sfgxk) {
		this.sfgxk = sfgxk;
	}
	public String getKcksdwmc() {
		return kcksdwmc;
	}
	public void setKcksdwmc(String kcksdwmc) {
		this.kcksdwmc = kcksdwmc;
	}
	public String getKhfsmc() {
		return khfsmc;
	}
	public void setKhfsmc(String khfsmc) {
		this.khfsmc = khfsmc;
	}
	public String getKcxzmc() {
		return kcxzmc;
	}
	public void setKcxzmc(String kcxzmc) {
		this.kcxzmc = kcxzmc;
	}
	public String getKcsxmc() {
		return kcsxmc;
	}
	public void setKcsxmc(String kcsxmc) {
		this.kcsxmc = kcsxmc;
	}
	public String getSkrs() {
		return skrs;
	}
	public void setSkrs(String skrs) {
		this.skrs = skrs;
	}
	public String getYlrs() {
		return ylrs;
	}
	public void setYlrs(String ylrs) {
		this.ylrs = ylrs;
	}
	public String getZyfxmc() {
		return zyfxmc;
	}
	public void setZyfxmc(String zyfxmc) {
		this.zyfxmc = zyfxmc;
	}
	public List<CjglXscjjlBean> getCjjlList() {
		return cjjlList;
	}
	public void setCjjlList(List<CjglXscjjlBean> cjjlList) {
		this.cjjlList = cjjlList;
	}
	public List<CjglLlrwfxBean> getFxList() {
		return fxList;
	}
	public void setFxList(List<CjglLlrwfxBean> fxList) {
		this.fxList = fxList;
	}

}
