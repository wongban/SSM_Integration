package com.cjlr.entity;

import java.util.List;

/**
 * 学生成绩记录bean
 * @author jobs
 *
 */
public class CjglXscjjlBean {
	private String cjbh;      // 成绩编号
	private String cjlrrwh;   // 成绩录入任务号
	private String xn;        // 学年
	private String xq;        // 学期
	private String kcsx;      // 课程性质
	private String kcxz;      // 课程属性
	private String kcmc;      // 课程名称
	private String kcbm;      // 课程编码
	private String xf;        // 学分
	private String xh;        // 学号
	private String jd;        // 绩点
	private String cj;        // 成绩
	private String zpcj;      // 总评成绩
	private String cjbz;      // 成绩备注
	private String lrr;       // 成绩录入人编号
	private String rlsj;      // 录入时间
	private String cjjmh;     // 成绩加密号
	private String cjjgx;     // 成绩及格线
	private String sfhdxf;    // 是否获得学分(0否，1是)
	private String tjzt;      // 提交状态
	private String bz;        // 备注
	private String lrrxm;     // 成绩录入人姓名
	private String kcxmbh;    // 课程项目编号
	private String kcxmmc;    // 课程项目名称
	private String sfgxk;
	private String jmhzzcj;   // 加密成绩

	// 关联查询
	private String xm;         // 姓名
	private String bjdm;       // 班级代码
	private String bjmc;       // 班级名称
	private String xbmc;       // 性别名称
	private String cjbzmc;     // 成绩备注名称
	private String cjdjmc;     // 成绩等级名称
	private String hksq;       // 缓考申请状态
	private String mksq;       // 免考申请状态

	private List<CjglXscjfxbBean> cjfxList; // 成绩分项

	public String getCjbh() {
		return cjbh;
	}
	public void setCjbh(String cjbh) {
		this.cjbh = cjbh;
	}
	public String getCjlrrwh() {
		return cjlrrwh;
	}
	public void setCjlrrwh(String cjlrrwh) {
		this.cjlrrwh = cjlrrwh;
	}
	public String getXn() {
		return xn;
	}
	public void setXn(String xn) {
		this.xn = xn;
	}
	public String getXq() {
		return xq;
	}
	public void setXq(String xq) {
		this.xq = xq;
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
	public String getKcmc() {
		return kcmc;
	}
	public void setKcmc(String kcmc) {
		this.kcmc = kcmc;
	}
	public String getKcbm() {
		return kcbm;
	}
	public void setKcbm(String kcbm) {
		this.kcbm = kcbm;
	}
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getCj() {
		return cj;
	}
	public void setCj(String cj) {
		this.cj = cj;
	}
	public String getCjbz() {
		return cjbz;
	}
	public void setCjbz(String cjbz) {
		this.cjbz = cjbz;
	}
	public String getLrr() {
		return lrr;
	}
	public void setLrr(String lrr) {
		this.lrr = lrr;
	}
	public String getRlsj() {
		return rlsj;
	}
	public void setRlsj(String rlsj) {
		this.rlsj = rlsj;
	}
	public String getCjjmh() {
		return cjjmh;
	}
	public void setCjjmh(String cjjmh) {
		this.cjjmh = cjjmh;
	}
	public String getXf() {
		return xf;
	}
	public void setXf(String xf) {
		this.xf = xf;
	}
	public String getJd() {
		return jd;
	}
	public void setJd(String jd) {
		this.jd = jd;
	}
	public String getZpcj() {
		return zpcj;
	}
	public void setZpcj(String zpcj) {
		this.zpcj = zpcj;
	}
	public String getCjjgx() {
		return cjjgx;
	}
	public void setCjjgx(String cjjgx) {
		this.cjjgx = cjjgx;
	}
	public String getSfhdxf() {
		return sfhdxf;
	}
	public void setSfhdxf(String sfhdxf) {
		this.sfhdxf = sfhdxf;
	}
	public String getTjzt() {
		return tjzt;
	}
	public void setTjzt(String tjzt) {
		this.tjzt = tjzt;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getLrrxm() {
		return lrrxm;
	}
	public void setLrrxm(String lrrxm) {
		this.lrrxm = lrrxm;
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
	public String getJmhzzcj() {
		return jmhzzcj;
	}
	public void setJmhzzcj(String jmhzzcj) {
		this.jmhzzcj = jmhzzcj;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
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
	public String getXbmc() {
		return xbmc;
	}
	public void setXbmc(String xbmc) {
		this.xbmc = xbmc;
	}
	public String getCjbzmc() {
		return cjbzmc;
	}
	public void setCjbzmc(String cjbzmc) {
		this.cjbzmc = cjbzmc;
	}
	public List<CjglXscjfxbBean> getCjfxList() {
		return cjfxList;
	}
	public void setCjfxList(List<CjglXscjfxbBean> cjfxList) {
		this.cjfxList = cjfxList;
	}
	public String getCjdjmc() {
		return cjdjmc;
	}
	public void setCjdjmc(String cjdjmc) {
		this.cjdjmc = cjdjmc;
	}
	public String getHksq() {
		return hksq;
	}
	public void setHksq(String hksq) {
		this.hksq = hksq;
	}
	public String getMksq() {
		return mksq;
	}
	public void setMksq(String mksq) {
		this.mksq = mksq;
	}
	@Override
	public String toString() {
		return "CjglXscjjlBean [xf=" + xf + ", xh=" + xh + ", bjdm=" + bjdm + ", bjmc=" + bjmc + "]";
	}

}
