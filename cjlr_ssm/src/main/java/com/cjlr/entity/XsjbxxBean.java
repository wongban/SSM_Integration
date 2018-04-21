package com.cjlr.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XsjbxxBean {
	private String xsid;     // 民航学院在不同的系统有不同的学号，并且目前管理不能统一
	private String xh;       // 此学号为教务处学号
	private String xm;       // 姓名
	private String xmpy;     // 姓名拼音
	private String cwm;      // 曾用名
	private String xbdm;     // 参看性别代码表
	private String xxm;      // 参看XTGL_DMJ_XXM代码集_血型码GG
	private Date csrq;       // 出生日期
	private String sfzjlxm;  // 参考 XTGL_DMJ_SFZJLX
	private String sfzjlxmc; // 证件类型名称
	private String sfzjh;    // 身份证件号
	private String nj;       // 年级
	                         // ZP BLOB 照片
	private String xqdm;     // 参考XTGL_XQJBSJZL
	private String xqmc;     // 校区名称
	private String dwdm;     // 参考XTGL_DWJBSJZL
	private String dwmc;     // 单位名称
	private String zydm;     // 参考XTGL_ZYXXSJZL
	private String zymc;     // 专业方向名称
	private String bjdm;     // 参考XTGL_BJSJZL
	private String bjmc;     // 班级名称
	private Number xz;       // 学制
	private String rxny;     // 入学年月
	private String xznj;     // 现在年级
	private String xslb;     // 参考XJGL_DMJ_XSLBDM
	private String xsdqzt;   // 学生当前状态
	private String pyccdm;   // 参看XTGL_DMJ_CCBM层次表
	private String mzdm;     // 民族代码
	private String gjdqdm;   // 国籍地区码
	private String szly;     // 学生来源
	private String zxzt;     // 在校状态
	private String rxfsdm;   // 参考 XJGL_DMJ_YXFSDM 保送、自主招生、特招
	private String pyfsdm;   // 参考XJGL_DMJ_PYFX
	private String jdfs;     // 参考XJGL_DMJ_JDFSDM
	private String zzmm;     // 参看政治面貌代码
	private String hkszdmc;  // 户口所在地
	private String hkxz;     // 参看户口性质代码表
	private String jkzk;     // 参考 XJGL_DMJ_STJKZTDM身体健康状况代码
	private String hyzk;     // 参考XJGL_DMJ_HYZKDM
	private String gatqwdm;  // 参考XJGL_DMJ_GATQWM
	private String zjxym;    // 参考XJGL_DMJ_XYZJDM
	private String jg;       // 籍贯
	private String jtdz;     // 家庭地址
	private String gddh;     // 固定电话
	private String yddh;     // 移动电话
	private String dzyx;     // 电子邮箱
	private String qq;       // QQ
	private String txdz;     //  通信地址
	private String yzbm;     // 邮政编码
	private String ykt;      // 这个号码以后为扫描枪、读卡器准备
	private String sg;       // 身高(CM)
	private String tz;       // 体重(KG)
	private String yhzh;     // 银行账号
	private String ybkh;     // 医保卡号
	private String ssh;      // 宿舍号
	private String ssdh;     // 宿舍电话
	private String bz;       // 备注
	private String czr;      // 也充当发布人
	private String czsj;     // 操作时间
	private String czrxm;    // 操作人姓名
	private String sfyxj;    // 1 是否有学籍
	private String zczt;    // 注册状态
	private String sfsczj;   // 
	private String xssf;     // 省份
	private String xss;      // 市
	private String xsx;      // 县（区）
	private String kslb;     // 
	private String zylb;
	private String wlklb;
	private String byxx;
	private String bdh;
	private String ksh;
	private String rxqxl;
	private String sfczhkznc;
	private String sfddpy;
	private String xdxtz;
	private Number rxzf;
	private String bz1;
	private String bz2;
	private String bz3;
	private String zyfxmc;
	private String zyfxdm;
	private String zkzh;
	private String gbzydm;

	public String getXsid() {
		return xsid;
	}
	public void setXsid(String xsid) {
		this.xsid = xsid;
	}
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getXmpy() {
		return xmpy;
	}
	public void setXmpy(String xmpy) {
		this.xmpy = xmpy;
	}
	public String getCwm() {
		return cwm;
	}
	public void setCwm(String cwm) {
		this.cwm = cwm;
	}
	public String getXbdm() {
		return xbdm;
	}
	public void setXbdm(String xbdm) {
		this.xbdm = xbdm;
	}
	public String getXxm() {
		return xxm;
	}
	public void setXxm(String xxm) {
		this.xxm = xxm;
	}
	public Date getCsrq() {
		return csrq;
	}
	public void setCsrq(String csrq) {
		try {
			if (csrq == null || "".equals(csrq)) return;
			this.csrq = new SimpleDateFormat("yyyyMMdd").parse(csrq);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public String getSfzjlxm() {
		return sfzjlxm;
	}
	public void setSfzjlxm(String sfzjlxm) {
		this.sfzjlxm = sfzjlxm;
	}
	public String getSfzjlxmc() {
		return sfzjlxmc;
	}
	public void setSfzjlxmc(String sfzjlxmc) {
		this.sfzjlxmc = sfzjlxmc;
	}
	public String getSfzjh() {
		return sfzjh;
	}
	public void setSfzjh(String sfzjh) {
		this.sfzjh = sfzjh;
	}
	public String getNj() {
		return nj;
	}
	public void setNj(String nj) {
		this.nj = nj;
	}
	public String getXqdm() {
		return xqdm;
	}
	public void setXqdm(String xqdm) {
		this.xqdm = xqdm;
	}
	public String getXqmc() {
		return xqmc;
	}
	public void setXqmc(String xqmc) {
		this.xqmc = xqmc;
	}
	public String getDwdm() {
		return dwdm;
	}
	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	public String getZydm() {
		return zydm;
	}
	public void setZydm(String zydm) {
		this.zydm = zydm;
	}
	public String getZymc() {
		return zymc;
	}
	public void setZymc(String zymc) {
		this.zymc = zymc;
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
	public String getRxny() {
		return rxny;
	}
	public void setRxny(String rxny) {
		this.rxny = rxny;
	}
	public String getXznj() {
		return xznj;
	}
	public void setXznj(String xznj) {
		this.xznj = xznj;
	}
	public String getXslb() {
		return xslb;
	}
	public void setXslb(String xslb) {
		this.xslb = xslb;
	}
	public String getXsdqzt() {
		return xsdqzt;
	}
	public void setXsdqzt(String xsdqzt) {
		this.xsdqzt = xsdqzt;
	}
	public String getPyccdm() {
		return pyccdm;
	}
	public void setPyccdm(String pyccdm) {
		this.pyccdm = pyccdm;
	}
	public String getMzdm() {
		return mzdm;
	}
	public void setMzdm(String mzdm) {
		this.mzdm = mzdm;
	}
	public String getGjdqdm() {
		return gjdqdm;
	}
	public void setGjdqdm(String gjdqdm) {
		this.gjdqdm = gjdqdm;
	}
	public String getSzly() {
		return szly;
	}
	public void setSzly(String szly) {
		this.szly = szly;
	}
	public String getZxzt() {
		return zxzt;
	}
	public void setZxzt(String zxzt) {
		this.zxzt = zxzt;
	}
	public String getRxfsdm() {
		return rxfsdm;
	}
	public void setRxfsdm(String rxfsdm) {
		this.rxfsdm = rxfsdm;
	}
	public String getPyfsdm() {
		return pyfsdm;
	}
	public void setPyfsdm(String pyfsdm) {
		this.pyfsdm = pyfsdm;
	}
	public String getJdfs() {
		return jdfs;
	}
	public void setJdfs(String jdfs) {
		this.jdfs = jdfs;
	}
	public String getZzmm() {
		return zzmm;
	}
	public void setZzmm(String zzmm) {
		this.zzmm = zzmm;
	}
	public String getHkszdmc() {
		return hkszdmc;
	}
	public void setHkszdmc(String hkszdmc) {
		this.hkszdmc = hkszdmc;
	}
	public String getHkxz() {
		return hkxz;
	}
	public void setHkxz(String hkxz) {
		this.hkxz = hkxz;
	}
	public String getJkzk() {
		return jkzk;
	}
	public void setJkzk(String jkzk) {
		this.jkzk = jkzk;
	}
	public String getHyzk() {
		return hyzk;
	}
	public void setHyzk(String hyzk) {
		this.hyzk = hyzk;
	}
	public String getGatqwdm() {
		return gatqwdm;
	}
	public void setGatqwdm(String gatqwdm) {
		this.gatqwdm = gatqwdm;
	}
	public String getZjxym() {
		return zjxym;
	}
	public void setZjxym(String zjxym) {
		this.zjxym = zjxym;
	}
	public String getJg() {
		return jg;
	}
	public void setJg(String jg) {
		this.jg = jg;
	}
	public String getJtdz() {
		return jtdz;
	}
	public void setJtdz(String jtdz) {
		this.jtdz = jtdz;
	}
	public String getGddh() {
		return gddh;
	}
	public void setGddh(String gddh) {
		this.gddh = gddh;
	}
	public String getYddh() {
		return yddh;
	}
	public void setYddh(String yddh) {
		this.yddh = yddh;
	}
	public String getDzyx() {
		return dzyx;
	}
	public void setDzyx(String dzyx) {
		this.dzyx = dzyx;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getTxdz() {
		return txdz;
	}
	public void setTxdz(String txdz) {
		this.txdz = txdz;
	}
	public String getYzbm() {
		return yzbm;
	}
	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}
	public String getYkt() {
		return ykt;
	}
	public void setYkt(String ykt) {
		this.ykt = ykt;
	}
	public String getSg() {
		return sg;
	}
	public void setSg(String sg) {
		this.sg = sg;
	}
	public String getTz() {
		return tz;
	}
	public void setTz(String tz) {
		this.tz = tz;
	}
	public String getYhzh() {
		return yhzh;
	}
	public void setYhzh(String yhzh) {
		this.yhzh = yhzh;
	}
	public String getYbkh() {
		return ybkh;
	}
	public void setYbkh(String ybkh) {
		this.ybkh = ybkh;
	}
	public String getSsh() {
		return ssh;
	}
	public void setSsh(String ssh) {
		this.ssh = ssh;
	}
	public String getSsdh() {
		return ssdh;
	}
	public void setSsdh(String ssdh) {
		this.ssdh = ssdh;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getCzsj() {
		return czsj;
	}
	public void setCzsj(String czsj) {
		this.czsj = czsj;
	}
	public String getCzrxm() {
		return czrxm;
	}
	public void setCzrxm(String czrxm) {
		this.czrxm = czrxm;
	}
	public String getSfyxj() {
		return sfyxj;
	}
	public void setSfyxj(String sfyxj) {
		this.sfyxj = sfyxj;
	}
	public String getZczt() {
		return zczt;
	}
	public void setZczt(String zczt) {
		this.zczt = zczt;
	}
	public String getSfsczj() {
		return sfsczj;
	}
	public void setSfsczj(String sfsczj) {
		this.sfsczj = sfsczj;
	}
	public String getXssf() {
		return xssf;
	}
	public void setXssf(String xssf) {
		this.xssf = xssf;
	}
	public String getXss() {
		return xss;
	}
	public void setXss(String xss) {
		this.xss = xss;
	}
	public String getXsx() {
		return xsx;
	}
	public void setXsx(String xsx) {
		this.xsx = xsx;
	}
	public String getKslb() {
		return kslb;
	}
	public void setKslb(String kslb) {
		this.kslb = kslb;
	}
	public String getZylb() {
		return zylb;
	}
	public void setZylb(String zylb) {
		this.zylb = zylb;
	}
	public String getWlklb() {
		return wlklb;
	}
	public void setWlklb(String wlklb) {
		this.wlklb = wlklb;
	}
	public String getByxx() {
		return byxx;
	}
	public void setByxx(String byxx) {
		this.byxx = byxx;
	}
	public String getBdh() {
		return bdh;
	}
	public void setBdh(String bdh) {
		this.bdh = bdh;
	}
	public String getKsh() {
		return ksh;
	}
	public void setKsh(String ksh) {
		this.ksh = ksh;
	}
	public String getRxqxl() {
		return rxqxl;
	}
	public void setRxqxl(String rxqxl) {
		this.rxqxl = rxqxl;
	}
	public String getSfczhkznc() {
		return sfczhkznc;
	}
	public void setSfczhkznc(String sfczhkznc) {
		this.sfczhkznc = sfczhkznc;
	}
	public String getSfddpy() {
		return sfddpy;
	}
	public void setSfddpy(String sfddpy) {
		this.sfddpy = sfddpy;
	}
	public String getXdxtz() {
		return xdxtz;
	}
	public void setXdxtz(String xdxtz) {
		this.xdxtz = xdxtz;
	}
	public Number getXz() {
		return xz;
	}
	public void setXz(Number xz) {
		this.xz = xz;
	}
	public Number getRxzf() {
		return rxzf;
	}
	public void setRxzf(Number rxzf) {
		this.rxzf = rxzf;
	}
	public String getBz1() {
		return bz1;
	}
	public void setBz1(String bz1) {
		this.bz1 = bz1;
	}
	public String getBz2() {
		return bz2;
	}
	public void setBz2(String bz2) {
		this.bz2 = bz2;
	}
	public String getBz3() {
		return bz3;
	}
	public void setBz3(String bz3) {
		this.bz3 = bz3;
	}
	public String getZyfxmc() {
		return zyfxmc;
	}
	public void setZyfxmc(String zyfxmc) {
		this.zyfxmc = zyfxmc;
	}
	public String getZyfxdm() {
		return zyfxdm;
	}
	public void setZyfxdm(String zyfxdm) {
		this.zyfxdm = zyfxdm;
	}
	public String getZkzh() {
		return zkzh;
	}
	public void setZkzh(String zkzh) {
		this.zkzh = zkzh;
	}
	public String getGbzydm() {
		return gbzydm;
	}
	public void setGbzydm(String gbzydm) {
		this.gbzydm = gbzydm;
	}
	
}
