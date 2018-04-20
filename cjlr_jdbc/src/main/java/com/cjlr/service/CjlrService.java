package com.cjlr.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import com.cjlr.dao.CjglCjbzszbDao;
import com.cjlr.dao.CjglCjdjdzbDao;
import com.cjlr.dao.CjglCjllrwDao;
import com.cjlr.dao.CjglCjlrsjszDao;
import com.cjlr.dao.CjglLlrwfxDao;
import com.cjlr.dao.CjglXscjfxbDao;
import com.cjlr.dao.CjglXscjjlDao;
import com.cjlr.dao.JxjhglJxbbjxxDao;
import com.cjlr.dao.XtglRzxxDao;
import com.cjlr.dao.XtglXnxqszbDao;
import com.cjlr.dao.XtglXxjbxxDao;
import com.cjlr.entity.CjglCjbzszbBean;
import com.cjlr.entity.CjglCjdjdzbBean;
import com.cjlr.entity.CjglCjllrwBean;
import com.cjlr.entity.CjglCjlrsjszBean;
import com.cjlr.entity.CjglLlrwfxBean;
import com.cjlr.entity.CjglXscjfxbBean;
import com.cjlr.entity.CjglXscjjlBean;
import com.cjlr.entity.JxjhglJxbbjxxBean;
import com.cjlr.entity.XtglRzxxBean;
import com.cjlr.entity.XtglXnxqszbBean;
import com.cjlr.entity.XtglXxjbxxBean;
import com.google.gson.Gson;
import com.utils.ConnectionUtil;
import com.utils.XscjMD5Utils;

public class CjlrService {

	private CjglCjllrwDao lrrwDao = new CjglCjllrwDao();
	private CjglLlrwfxDao fxDao = new CjglLlrwfxDao();
	private CjglXscjjlDao cjjlDao = new CjglXscjjlDao();
	private CjglXscjfxbDao cjfxDao = new CjglXscjfxbDao();
	private CjglCjbzszbDao cjbzDao = new CjglCjbzszbDao();
	private CjglCjdjdzbDao cjdjDao = new CjglCjdjdzbDao();
	private CjglCjlrsjszDao sjszDao = new CjglCjlrsjszDao();
	private XtglXnxqszbDao xnxqDao = new XtglXnxqszbDao();
	private XtglXxjbxxDao xxjbxxDao = new XtglXxjbxxDao();
	private XtglRzxxDao rzDao = new XtglRzxxDao();
	private JxjhglJxbbjxxDao jxbbjxxDao = new JxjhglJxbbjxxDao();

	/**
	 * 获取学年学期设置表数据
	 * @return
	 */
	public List<XtglXnxqszbBean> findAllXnxq() {
		return xnxqDao.findAll();
	}

	/**
	 * 查询成绩备注
	 * @return
	 */
	public List<CjglCjbzszbBean> findAllCjbz() {
		return cjbzDao.findAll();
	}

	/**
	 * 查询成绩等级
	 * @return
	 */
	public List<CjglCjdjdzbBean> findAllCjdj() {
		return cjdjDao.findAll();
	}

	/**
	 * 根据教工号查询成绩录入班级
	 * @param yhzh
	 * @return
	 */
	public List<CjglCjllrwBean> findByJgh(String yhzh, String dqxnxq) {
		return lrrwDao.findByJgh(yhzh, dqxnxq);
	}

	/**
	 * 根据任务编号查询成绩记录
	 * @param rwbh
	 * @return
	 */
	public Map<String, Object> findByRwbh(CjglXscjjlBean queryBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String rwbh = queryBean.getCjlrrwh();
			CjglCjllrwBean lrrwBean = lrrwDao.findByRwbh(rwbh);
			CjglCjlrsjszBean sjszBean = sjszDao.findByXnXq(lrrwBean.getKcksxn(), lrrwBean.getKcksxq());
			
			if (sjszBean == null || sjszBean.getKssj() == null || sjszBean.getJssj() == null) {
				map.put("status", 0);
				map.put("lrrwBean", lrrwBean);
				map.put("message", "当前开课学年学期未设置成绩录入时间，请核对！");
				return map;
			}

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			long kssj = format.parse(sjszBean.getKssj()).getTime();

			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(format.parse(sjszBean.getJssj()));
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			long jssj = rightNow.getTimeInMillis();
			long dqsj = System.currentTimeMillis();

			if (dqsj < kssj || jssj < dqsj) {
				map.put("status", 0);
				map.put("lrrwBean", lrrwBean);
				map.put("message", "请在" + sjszBean.getKssj() + "到" + sjszBean.getJssj() + "时间范围录成绩。");
				return map;
			}

			List<CjglLlrwfxBean> fxList = fxDao.findByRwbh(rwbh);
			lrrwBean.setFxList(fxList);
			List<CjglXscjjlBean> cjjlList = cjjlDao.findByRwbh(rwbh, queryBean);
			lrrwBean.setCjjlList(cjjlList);

			// 一次性取出所有该录入任务的成绩分项，再写逻辑放入对应的成绩记录（如果按成绩记录每次去取会增加查询次数降低效率）
			// 利用Map支持字符串索引，hash表搜索提升效率
			List<CjglXscjfxbBean> cjfxList = cjfxDao.findByRwbh(rwbh);
			Map<String, CjglXscjjlBean> cjjlMap = new HashMap<String, CjglXscjjlBean>();
			for (CjglXscjjlBean bean : cjjlList) {
				cjjlMap.put(bean.getCjbh(), bean);
			}
			for (CjglXscjfxbBean bean : cjfxList) {
				CjglXscjjlBean cjjlBean = cjjlMap.get(bean.getCjbh());
				if (cjjlBean != null) cjjlBean.getCjfxList().add(bean);
			}

			map.put("status", 1);
			map.put("lrrwBean", lrrwBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 保存成绩
	 * @param lrrwBean
	 * @return
	 */
	public Map<String, Object> save(CjglCjllrwBean lrrwBean) throws Exception {
		Map<String, Object> returnData = new HashMap<String, Object>();
		if (lrrwBean == null || lrrwBean.getRwbh() == null || "".equals(lrrwBean.getRwbh())) {
			returnData.put("status", true);
			returnData.put("message", "请先选择录入任务！");
			return returnData;
		}
		String validateString = validate(lrrwBean, true);
		if (validateString != null) {
			returnData.put("status", false);
			returnData.put("message", validateString);
			return returnData;
		}
		Connection conn = null;
		try {
			// 禁用自动提交，事务控制
			conn = ConnectionUtil.getConnection();
			conn.setAutoCommit(false);

			List<CjglXscjfxbBean> cjfxList = new ArrayList<CjglXscjfxbBean>();
			for (CjglXscjjlBean cjjlBean : lrrwBean.getCjjlList()) {
				cjfxList.addAll(cjjlBean.getCjfxList());
			}

			if (lrrwDao.updateJlfs(conn, lrrwBean) != 1) {
				returnData.put("status", false);
				returnData.put("message", "录入任务更新异常，请联系管理员！");
				return returnData;
			}
			cjjlDao.updateBatchCjjl(conn, lrrwBean.getCjjlList());
			cjfxDao.updateBatchCjfx(conn, cjfxList);
			conn.commit();
			returnData.put("status", true);
			returnData.put("message", "保存成功！");
		} catch (Exception e) {
			e.printStackTrace();

			conn.rollback();
			returnData.put("status", false);
			returnData.put("message", "出错了，请联系管理员！\n" + e.toString());			
		} finally {
			conn.setAutoCommit(true);
			if (conn != null) conn.close();
		}
		return returnData;
	}

	/**
	 * 提交成绩录入
	 * @param lrrwBean
	 * @return
	 */
	public Map<String, Object> submit(HttpServletRequest request) throws Exception {
		String json = request.getParameter("json");
		CjglCjllrwBean lrrwBean = new Gson().fromJson(json, CjglCjllrwBean.class);

		Map<String, Object> returnData = new HashMap<String, Object>();
		if (lrrwBean == null || lrrwBean.getRwbh() == null || "".equals(lrrwBean.getRwbh())) {
			returnData.put("status", true);
			returnData.put("message", "请先选择录入任务！");
			return returnData;
		}
		String validateString = validate(lrrwBean, false);
		if (validateString != null) {
			returnData.put("status", false);
			returnData.put("message", validateString);
			return returnData;
		}
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			// 禁用自动提交，事务控制
			conn = ConnectionUtil.getConnection();
			conn.setAutoCommit(false);

			String xn = lrrwBean.getKcksxn();
			String xq = lrrwBean.getKcksxq();
			String kcbh = lrrwBean.getKcbh();
			String jgfsx = lrrwBean.getJgfsx();
			List<CjglXscjjlBean> cjjlList = lrrwBean.getCjjlList();
			List<CjglXscjfxbBean> cjfxList = new ArrayList<CjglXscjfxbBean>();
			for (CjglXscjjlBean cjjlBean : cjjlList) {
				cjfxList.addAll(cjjlBean.getCjfxList());
			}
			StringBuilder zjArr = new StringBuilder("");// 拼接成绩记录主键

			for (CjglXscjjlBean bean : cjjlList) {
				String zpcj = bean.getZpcj();
				String cjMd5Str = XscjMD5Utils.cjMD5(zpcj); // 成绩加密
				bean.setSfhdxf(new BigDecimal(zpcj).compareTo(new BigDecimal(jgfsx)) >= 0 ? "1" : "0");
				bean.setJmhzzcj(cjMd5Str);
				bean.setTjzt("9");

				zjArr.append("'").append(xn).append(xq).append(kcbh).append(bean.getXh()).append("',");
			}
			zjArr.deleteCharAt(zjArr.length() - 1);

			// 成绩记录、成绩分项
			cjjlDao.updateBatchCjjl(conn, cjjlList);
			cjfxDao.updateBatchCjfx(conn, cjfxList);

			// 汇总表
			String hzSQL = 
				"MERGE INTO cjgl_xscjhzb a " + 
				"USING (SELECT * FROM cjgl_xscjjl WHERE xn || xq || kcbm || xh IN (" + zjArr + ")) b " + 
				"ON (a.kcbm || a.xh = b.kcbm || b.xh) " + 
				"WHEN MATCHED THEN " + 
				"  UPDATE SET a.cj = b.zpcj, a.jd = b.jd, a.xf = b.xf, a.jmhzzcj = b.jmhzzcj " + 
				"WHEN NOT MATCHED THEN " + 
				"  INSERT (cjhzzj, xn, xq, xh, kcsx, kcxz, kcmc, kcbm, xf, jd, cj, kcxmbh, kcxmmc, bkcj, cxcj, jmhzzcj, cjjgx, sfgxk, kcxf, bybkcjtemp, bkcjbz) " + 
				"  VALUES (b.xn||b.xq||b.kcbm||b.xh, b.xn, b.xq, b.xh, b.kcsx, b.kcxz, b.kcmc, b.kcbm, b.xf, b.jd, b.zpcj, b.kcxmbh, b.kcxmmc, NULL, NULL, b.jmhzzcj, ?, ?, ?, NULL, NULL)";

			stmt = conn.prepareStatement(hzSQL);
			stmt.setString(1, lrrwBean.getJgfsx());
			stmt.setString(2, lrrwBean.getSfgxk());
			stmt.setString(3, lrrwBean.getZxf());
			int rowCount = stmt.executeUpdate();
			if (rowCount != cjjlList.size()) {
				returnData.put("status", false);
				returnData.put("message", "汇总表更新异常，总共：" + cjjlList.size() + "条数据，实际更新：" + rowCount + "条数据，联系管理员！");
				conn.rollback();
				return returnData;
			}

			// 录入任务审核状态
			if (lrrwDao.updateShzt(conn, lrrwBean) != 1) {
				returnData.put("status", false);
				returnData.put("message", "录入任务审核状态更新异常，联系管理员！");
				conn.rollback();
				return returnData;
			}

			// 操作日志
			HttpSession session = request.getSession();
			String yhzh = (String) session.getAttribute("yhzh");
			yhzh = "妹夫";
			String yhxm = (String) session.getAttribute("yhxm");
			if (yhxm == null || "".equals(yhxm)) {
				yhxm = yhzh;
			}
			String ywmc = new StringBuilder(yhzh).append("提交了第").
						append(lrrwBean.getKcksxn()).append("学年 第").
						append(lrrwBean.getKcksxq()).append("学期的").
						append(lrrwBean.getRwbh()).append("缓考成绩录入任务").toString();
			XtglRzxxBean rzBean = new XtglRzxxBean();
			rzBean.setYhzh(yhzh);
			rzBean.setCzrxm(yhxm);
			rzBean.setCzrq(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			rzBean.setYwmc(ywmc);
			rzBean.setCzbmc("CJGL_CJLLRW");
			rzBean.setCzbzj("RWBH");
			rzBean.setCzlx("1");
			rzBean.setIp(getIpAddr(request));
			if (rzDao.add(conn, rzBean) != 1) {
				returnData.put("status", false);
				returnData.put("message", "日志插入失败，请联系管理员！");
				conn.rollback();
				return returnData;
			}

			
			conn.commit();
			returnData.put("status", true);
			returnData.put("message", "提交成功！");
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();

			returnData.put("status", false);
			returnData.put("message", "出错了，请联系管理员！\n" + e.toString());			
		} finally {
			conn.setAutoCommit(true);

			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
		return returnData;
	}

	/**
	 * 导出成绩记录
	 * @param request
	 * @param response
	 */
	public void exportCjjl(HttpServletRequest request, HttpServletResponse response) {
		OutputStream stream = null;
		try {
			String rwbh = request.getParameter("rwbh");
			String part = request.getParameter("part");

			// 学校基本信息
			XtglXxjbxxBean xxjbxxbean = xxjbxxDao.find();
			// 录入任务
			CjglCjllrwBean lrrwBean = lrrwDao.findByRwbh(rwbh);
			// 录入任务分项
			List<CjglLlrwfxBean> fxList = fxDao.findByRwbh(rwbh);
			// 对应的行政班
			List<JxjhglJxbbjxxBean> jxbbjxxList = jxbbjxxDao.findByJxbh(rwbh);
			// 成绩记录
			List<CjglXscjjlBean> cjjlList = cjjlDao.findByRwbh(rwbh);
			// 成绩分项
			List<CjglXscjfxbBean> cjfxList = cjfxDao.findByRwbh(rwbh);

			/*
			 * 成绩分项和成绩记录绑定
			 */
			Map<String, CjglXscjjlBean> cjjlMap = new HashMap<String, CjglXscjjlBean>();
			for (CjglXscjjlBean bean : cjjlList) {
				cjjlMap.put(bean.getCjbh(), bean);
			}
			for (CjglXscjfxbBean bean : cjfxList) {
				CjglXscjjlBean cjjlBean = cjjlMap.get(bean.getCjbh());
				if (cjjlBean != null) cjjlBean.getCjfxList().add(bean);
			}

			Map<String, JxjhglJxbbjxxBean> map = new HashMap<String, JxjhglJxbbjxxBean>();
			/*
			 * part 1：合班并且分开打印 ，非1：只打印一份
			 */
			if ("1".equals(part)) {
				/*
				 * 成绩记录按行政班分开
				 */
				for (JxjhglJxbbjxxBean bean : jxbbjxxList) {
					bean.setCjjlList(new ArrayList<CjglXscjjlBean>());
					map.put(bean.getBjdm(), bean);
				}
				for (CjglXscjjlBean bean : cjjlList) {
					map.get(bean.getBjdm()).getCjjlList().add(bean);
				}
			} else {
				JxjhglJxbbjxxBean bean = new JxjhglJxbbjxxBean();
				bean.setBjmc(lrrwBean.getJxbmc());
				bean.setCjjlList(cjjlList);
				map.put(null, bean);
			}

			
			String kcksxn = lrrwBean.getKcksxn();
			String kcksxq = lrrwBean.getKcksxq();
			if (kcksxn != null && !"".equals(kcksxn))
				kcksxn += "-" + (Integer.valueOf(kcksxn) + 1);

			stream = response.getOutputStream();
			// 合班/非合班（reposne，stream处理）
			String fileName = kcksxn + "学年第" + lrrwBean.getKcksxq() + "学期-" + lrrwBean.getRkjsxm() + "-" + lrrwBean.getJxbmc();
			if (map.size() == 1) {
				fileName = fileName + ".xls";
				response.setContentType("application/x-xls"); // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
			} else {
				fileName = fileName + ".zip";
				response.setContentType("application/zip");
				stream = new ZipOutputStream(response.getOutputStream());
			}
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
			
			/*
			 * 按行政班打印
			 */
			for (JxjhglJxbbjxxBean bjxxBean : map.values()) {
				if (stream instanceof ZipOutputStream)
					((ZipOutputStream) stream).putNextEntry(new ZipEntry(kcksxn + "学年第" + lrrwBean.getKcksxq() + "学期-" + lrrwBean.getRkjsxm() + "-" + bjxxBean.getBjmc() + ".xls"));

				HSSFWorkbook wb = new HSSFWorkbook();
				Sheet sheet1 = wb.createSheet("期末成绩表");

				cjjlList = bjxxBean.getCjjlList();
				// 内容行最大值
				int maxRow = 45; 
				if (cjjlList.size() > 90)
					maxRow = cjjlList.size() - cjjlList.size() / 2; 				
				
				int[] qmcjArr = new int[10]; // 期末各分数段
				double qmzf = 0;             // 期末总分
				double qmzgf = 0;            // 期末最高分
				double qmzdf = 100;          // 期末最低分
				int[] zpcjArr = new int[10]; // 总评各分数段
				double zpzf = 0;             // 总评总分
				double zpzgf = 0;            // 总评最高分
				double zpzdf = 100;          // 总评最低分

				for (CjglXscjjlBean cjjlBean : cjjlList) {
					
					if (cjjlBean.getZpcj() != null) {
						Double zpcj = Double.valueOf(cjjlBean.getZpcj());
						int range = zpcj.intValue() / 10; 
						zpcjArr[range == 10 ? 9 : range] ++;
						
						zpzf = zpzf + zpcj;
						if (zpcj > zpzgf) zpzgf = zpcj;
						if (zpcj < zpzdf) zpzdf = zpcj;
					}
					
					cjfxList = cjjlBean.getCjfxList();
					for (CjglXscjfxbBean bean : cjfxList) {
						if ("期末成绩".equals(bean.getFxmc()) && bean.getHscj() != null) {
							Double qmcj = Double.valueOf(bean.getHscj());
							int range = qmcj.intValue() / 10;
							qmcjArr[range == 10 ? 9 : range] ++;
							
							qmzf = qmzf + qmcj;
							if (qmcj > qmzgf) qmzgf = qmcj;
							if (qmcj < qmzdf) qmzdf = qmcj;
						}
					}
				}
				
				// 及格率、优秀率、平均分
				NumberFormat percent = NumberFormat.getPercentInstance();
				DecimalFormat df = new DecimalFormat("#.0");
				String qmjgl = percent.format((qmcjArr[9] + qmcjArr[8] + qmcjArr[7] + qmcjArr[6]) / (double)cjjlList.size());
				String qmyxl = percent.format(qmcjArr[9] / (double)cjjlList.size());
				String qmpjf = df.format(qmzf / cjjlList.size());
				
				String zpjgl = percent.format((zpcjArr[9] + zpcjArr[8] + zpcjArr[7] + zpcjArr[6]) / (double)cjjlList.size());
				String zpyxl = percent.format(zpcjArr[9] / (double)cjjlList.size());
				String zppjf = df.format(zpzf / cjjlList.size());
				
				// 字段描 述、属性、单元格宽度
				List<String[]> arr = new ArrayList<String[]>();
				arr.add(new String[]{"学号", "xh", "12"});
				arr.add(new String[]{"姓名", "xm", "6"});
				for (CjglLlrwfxBean bean : fxList) {
					arr.add(new String[]{bean.getFxmc(), "hscj", "8", "cjfxList"});
				}
				arr.add(new String[]{"总评", lrrwBean.getCjjlfs() == null || "1".equals(lrrwBean.getCjjlfs()) ? "zpcj" : "cjdjmc", "5"});
				arr.add(new String[]{"备注", "cjbzmc", "5"});
				
				String[][][] titleArr = new String[][][] {{
					{"教学班号：", lrrwBean.getRwbh()},
					{"开课部门：", lrrwBean.getKcksdwmc()},
					{"班级：", bjxxBean.getBjmc()},
					{"学分：", Double.valueOf(lrrwBean.getZxf()).toString()}
				}, {
					{"课程名称：", lrrwBean.getKcmc()},
					{"课程性质：", lrrwBean.getKcxzmc()},
					{"考核方式：", lrrwBean.getKhfsmc()},
					{"填表日期：", new SimpleDateFormat("yyyy-MM-dd").format(new Date())}}
				};
				
				// 字体
				Font titleFont = wb.createFont();
				titleFont.setFontName("宋体");
				titleFont.setFontHeightInPoints((short) 12);
				titleFont.setBold(true);
				
				Font font1 = wb.createFont();
				font1.setFontName("宋体");
				font1.setFontHeightInPoints((short) 10);
				
				// 单元格样式
				CellStyle titleStyle = wb.createCellStyle();
				titleStyle.setAlignment(HorizontalAlignment.CENTER);
				titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				titleStyle.setFont(titleFont);
				
				CellStyle subtitleStyle = wb.createCellStyle();
				subtitleStyle.setAlignment(HorizontalAlignment.CENTER);
				subtitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				subtitleStyle.setFont(font1);
				
				CellStyle style1 = wb.createCellStyle();
				style1.setVerticalAlignment(VerticalAlignment.CENTER);
				style1.setFont(font1);
				
				CellStyle style2 = wb.createCellStyle();
				style2.setVerticalAlignment(VerticalAlignment.CENTER);
				style2.setBorderTop(BorderStyle.THIN);
				style2.setBorderRight(BorderStyle.THIN);
				style2.setBorderBottom(BorderStyle.THIN);
				style2.setBorderLeft(BorderStyle.THIN);
				style2.setFont(font1);
				
				Row titleRow = sheet1.createRow(0);
				Cell titleCell = titleRow.createCell(0);
				titleCell.setCellValue(xxjbxxbean.getXxmc() + "期末成绩登记表");
				titleCell.setCellStyle(titleStyle);
				sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, arr.size()*2-1));
				
				Row subtitleRow = sheet1.createRow(1);
				Cell subtitleCell = subtitleRow.createCell(0);
				subtitleCell.setCellValue(kcksxn + "学年第" + kcksxq + "学期");
				subtitleCell.setCellStyle(subtitleStyle);
				sheet1.addMergedRegion(new CellRangeAddress(1, 1, 0, arr.size()*2-1));
				
				int titleRowNum = sheet1.getPhysicalNumberOfRows();
				for (int i = 0; i < titleArr.length; i++) {
					Row currentRow = sheet1.createRow(titleRowNum + i);
					for (int j = 0; j < titleArr[i].length; j++) {
						Cell cell1 = currentRow.createCell(j * 3);
						cell1.setCellValue(titleArr[i][j][0]);
						cell1.setCellStyle(style1);
						Cell cell2 = currentRow.createCell(j * 3 + 1);
						cell2.setCellValue(titleArr[i][j][1]);
						cell2.setCellStyle(style1);
					}
				}
				
				titleRowNum = sheet1.getPhysicalNumberOfRows();
				// 标题行
				Row headRow = sheet1.createRow(titleRowNum);
				for (int i = 0; i < 2; i++)
					for (int index = 0; index < arr.size(); index++) {
						// 单元格宽度
						Integer width = Integer.valueOf(arr.get(index)[2]);
						sheet1.setColumnWidth(index + arr.size() * i, width * 256);
						// 单元格内容
						Cell cell = headRow.createCell(index + arr.size() * i);
						cell.setCellValue(arr.get(index)[0]);
					}
				
				// 内容行
				for (int row = 0; row < cjjlList.size(); row++) {
					Row bodyRow;
					if (row + 1 <= maxRow)
						bodyRow = sheet1.createRow(row + titleRowNum + 1);
					else
						bodyRow = sheet1.getRow(row % maxRow + titleRowNum + 1);
					
					int index = 0;
					for (int column = 0; column < arr.size(); column++) {
						// 获取变量值
						String var = arr.get(column)[1];
						CjglXscjjlBean bean = cjjlList.get(row);
						
						if (arr.get(column).length == 3) {
							// 填充
							Field field = bean.getClass().getDeclaredField(var);
							field.setAccessible(true);
							Cell cell = bodyRow.createCell(row / maxRow * arr.size() + column);
							cell.setCellValue((String) field.get(bean));
						} else {
							Field field = bean.getClass().getDeclaredField(arr.get(column)[3]);
							field.setAccessible(true);
							List<?> list = (List<?>) field.get(bean);
							Object subObject = list.get(index);
							field = subObject.getClass().getDeclaredField(var);
							field.setAccessible(true);
							// 填充
							Cell cell = bodyRow.createCell(row / maxRow * arr.size() + column);
							cell.setCellValue((String) field.get(subObject));
							index ++;
						}
					}
				}
				// 标题行内容行加单元格样式（主要是针对空单元格的边框）
				int start = headRow.getRowNum();
				for (int i = 0; i < 2; i++)
					for (int rowI = 0; rowI <= maxRow; rowI++) {
						for (int cellI = 0; cellI < arr.size(); cellI++) {
							Row row = sheet1.getRow(start + rowI);
							if (row == null)
								row = sheet1.createRow(start + rowI);
							Cell cell = row.getCell(cellI + arr.size() * i);
							if (cell == null)
								cell = row.createCell(cellI + arr.size() * i);
							cell.setCellStyle(style2);
						}
					}
				// 签字行
				titleRowNum = sheet1.getPhysicalNumberOfRows();
				Row endRow = sheet1.createRow(titleRowNum + 1);
				Cell endCell = endRow.createCell(0);
				endCell.setCellValue("教师：__________签字             开课部门盖章：_____________");
				endCell.setCellStyle(style1);

				String[][][] titleFxArr = new String[][][] {{
					{"开课部门：", lrrwBean.getKcksdwmc()},
					{"班级：", bjxxBean.getBjmc()},
				}, {
					{"课程：", lrrwBean.getKcmc()},
					{"任课教师：", lrrwBean.getRkjsxm()},
				}, {
					{"考试（查）、总评综合统计", null},
					{"填表日期：", new SimpleDateFormat("yyyy-MM-dd").format(new Date())}}
				};
				
				List<List<Object>> tableList = new ArrayList<List<Object>>();
				Object[] row1 = {null, "统计数", "90-100", "80-89", "70-79", "60-69", "50-59", "40-49", "30-39", "20-29", "10-19", "0-9", "及格率", "优秀率", "最高分", "最低分", "平均分"};
				Object[] row2 = {"考试", cjjlList.size(), qmcjArr[9], qmcjArr[8], qmcjArr[7], qmcjArr[6], qmcjArr[5], qmcjArr[4], qmcjArr[3], qmcjArr[2], qmcjArr[1], qmcjArr[0], qmjgl, qmyxl, qmzgf, qmzdf, qmpjf};
				Object[] row3 = {"总评", cjjlList.size(), zpcjArr[9], zpcjArr[8], zpcjArr[7], zpcjArr[6], zpcjArr[5], zpcjArr[4], zpcjArr[3], zpcjArr[2], zpcjArr[1], zpcjArr[0], zpjgl, zpyxl, zpzgf, zpzdf, zppjf};
				tableList.add(Arrays.asList(row1));
				tableList.add(Arrays.asList(row2));
				tableList.add(Arrays.asList(row3));
				tableList.add(Arrays.asList(new Object[tableList.get(0).size()]));
				
				Sheet sheet2 = wb.createSheet("期末成绩分析表");
				
				for (int i = 0; i < tableList.get(0).size(); i++) {
					sheet2.setColumnWidth(i, 5 * 256);
				}
				
				// 字体
				Font titleFxFont = wb.createFont();
				titleFxFont.setFontName("宋体");
				titleFxFont.setFontHeightInPoints((short) 20);
				titleFxFont.setBold(true);
				
				Font font2 = wb.createFont();
				font2.setFontName("宋体");
				font2.setFontHeightInPoints((short) 10);
				font2.setBold(true);
				
				Font tableFont = wb.createFont();
				tableFont.setFontName("宋体");
				tableFont.setFontHeightInPoints((short) 9);
				
				// 单元格样式
				CellStyle titleFxStyle = wb.createCellStyle();
				titleFxStyle.setAlignment(HorizontalAlignment.CENTER);
				titleFxStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				titleFxStyle.setFont(titleFxFont);
				
				CellStyle subtitleFxStyle = wb.createCellStyle();
				subtitleFxStyle.setAlignment(HorizontalAlignment.CENTER);
				subtitleFxStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				subtitleFxStyle.setFont(font2);
				
				CellStyle tableStyle = wb.createCellStyle();
				tableStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				tableStyle.setBorderTop(BorderStyle.THIN);
				tableStyle.setBorderRight(BorderStyle.THIN);
				tableStyle.setBorderBottom(BorderStyle.THIN);
				tableStyle.setBorderLeft(BorderStyle.THIN);
				tableStyle.setFont(tableFont);
				
				Row titleFxRow = sheet2.createRow(0);
				Cell titleFxCell = titleFxRow.createCell(0);
				titleFxCell.setCellValue(xxjbxxbean.getXxmc() + "期末成绩分析表");
				titleFxCell.setCellStyle(titleFxStyle);
				sheet2.addMergedRegion(new CellRangeAddress(0, 0, 0, tableList.get(0).size() - 1));
				
				Row subtitleFxRow = sheet2.createRow(1);
				Cell subtitleFxCell = subtitleFxRow.createCell(0);
				subtitleFxCell.setCellValue(kcksxn + "学年第" + kcksxq + "学期");
				subtitleFxCell.setCellStyle(subtitleStyle);
				sheet2.addMergedRegion(new CellRangeAddress(1, 1, 0, tableList.get(0).size() - 1));
				
				Row subtitle2FxRow = sheet2.createRow(2);
				Cell subtitle2FxCell = subtitle2FxRow.createCell(0);
				subtitle2FxCell.setCellValue("本表由任课教师填写");
				subtitle2FxCell.setCellStyle(subtitleFxStyle);
				sheet2.addMergedRegion(new CellRangeAddress(2, 2, 0, tableList.get(0).size() - 1));
				
				int currentRowNum = sheet2.getPhysicalNumberOfRows();
				
				for (int i = 0; i < titleFxArr.length; i++) {
					Row currentRow = sheet2.createRow(currentRowNum + i);
					currentRow.setHeightInPoints((short) 20);
					for (int j = 0; j < titleFxArr[i].length; j++) {
						Cell cell1 = currentRow.createCell(j * 8);
						cell1.setCellValue(titleFxArr[i][j][0]);
						cell1.setCellStyle(style1);
						Cell cell2 = currentRow.createCell(j * 8 + 2);
						if (titleFxArr[i][j][1] == null) continue;
						cell2.setCellValue(titleFxArr[i][j][1]);
						cell2.setCellStyle(style1);
					}
				}
				
				currentRowNum = sheet2.getPhysicalNumberOfRows();
				for (int i = 0; i < tableList.size(); i++) {
					Row currentRow = sheet2.createRow(currentRowNum + i);
					currentRow.setHeightInPoints((short) 20);
					for (int j = 0; j < tableList.get(i).size(); j++) {
						Cell cell = currentRow.createCell(j);
						Object cellValue = tableList.get(i).get(j);
						if (cellValue != null)
							cell.setCellValue(cellValue.toString());
						cell.setCellStyle(tableStyle);
					}
				}
				
				// 评价内容
				currentRowNum = sheet2.getPhysicalNumberOfRows();
				
				Row pjRow = sheet2.createRow(currentRowNum);
				pjRow.setHeightInPoints((short) 150);
				
				Font pjFont = wb.createFont();
				pjFont.setFontName("宋体");
				pjFont.setFontHeightInPoints((short) 12);
				
				Cell pjCell = pjRow.createCell(0);
				pjCell.setCellValue("后\r\n记");
				CellStyle pjStyle = wb.createCellStyle();
				pjStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				pjStyle.setAlignment(HorizontalAlignment.CENTER);
				pjStyle.setWrapText(true);
				pjStyle.setFont(pjFont);
				pjCell.setCellStyle(pjStyle);
				sheet2.addMergedRegion(new CellRangeAddress(currentRowNum, currentRowNum + 1, 0, 0));
				
				String rig = 
						"一.试题分析(符合教学目的程度、总量、难易度、覆盖面、结构等）:\r\n\r\n\r\n" + 
								"二.成绩综合分析及理由:\r\n\r\n\r\n" + 
								"三.教学中存在问题及改进意见:\r\n\r\n";
				
				CellStyle pj2Style = wb.createCellStyle();
				pj2Style.setVerticalAlignment(VerticalAlignment.TOP);
				pj2Style.setWrapText(true);
				pj2Style.setFont(pjFont);
				
				Cell pj2Cell = pjRow.createCell(1);
				pj2Cell.setCellValue(rig);
				pj2Cell.setCellStyle(pj2Style);
				
				sheet2.addMergedRegion(new CellRangeAddress(currentRowNum, currentRowNum, 1, tableList.get(0).size() - 1));
				
				// 图
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				dataset.addValue(zpcjArr[9], "a", "90-100");
				dataset.addValue(zpcjArr[8], "b", "80-89");
				dataset.addValue(zpcjArr[7], "c", "70-79");
				dataset.addValue(zpcjArr[6], "d", "60-69");
				dataset.addValue(zpcjArr[5], "e", "50-59");
				dataset.addValue(zpcjArr[4], "f", "40-49");
				dataset.addValue(zpcjArr[3], "g", "30-39");
				dataset.addValue(zpcjArr[2], "h", "20-29");
				dataset.addValue(zpcjArr[1], "i", "10-19");
				dataset.addValue(zpcjArr[0], "j", "0-9");
				// 创建主题样式
				StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
				// 设置标题字体
				standardChartTheme.setExtraLargeFont(new java.awt.Font("宋体", java.awt.Font.BOLD, 12));
				// 设置图例的字体
				standardChartTheme.setRegularFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 10));
				// 设置轴向的字体
				standardChartTheme.setLargeFont(new java.awt.Font("宋体", java.awt.Font.PLAIN, 10));
				// 应用主题样式
				ChartFactory.setChartTheme(standardChartTheme);
				JFreeChart jf = ChartFactory.createBarChart("考试(考查)成绩分布图", "分数", "人数", dataset,
						PlotOrientation.VERTICAL, false, false, false);
				
				// 格式化 图片
				CategoryPlot plot = jf.getCategoryPlot();
				BarRenderer renderer = new BarRenderer();
				// 柱状图与边框的左右距离
				CategoryAxis domainAxis = plot.getDomainAxis();
				domainAxis.setLowerMargin(0.01);
				domainAxis.setUpperMargin(0.01);
				// 柱状图与边框的上下距离
				ValueAxis rAxis = plot.getRangeAxis();
				rAxis.setUpperMargin(0.15);
				rAxis.setLowerMargin(0.15);
				renderer.setMaximumBarWidth(2);
				// 设置柱子边框颜色
				renderer.setBaseOutlinePaint(Color.green);
				plot.setBackgroundPaint(new Color(255, 255, 204));
				plot.setRenderer(renderer);
				// ChartFrame frame = new ChartFrame("水果产量图 ", jf, true);
				// frame.pack();
				// frame.setVisible(true);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ChartUtilities.writeChartAsPNG(bos, jf, 500, 280);
				Drawing<?> patriarch = sheet2.createDrawingPatriarch();
				HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 1, (short) currentRowNum + 1, (short) (tableList.get(0).size()), (short) currentRowNum + 2);
				// anchor.setAnchorType(AnchorType.);
				patriarch.createPicture(anchor, wb.addPicture(bos.toByteArray(), Workbook.PICTURE_TYPE_JPEG));
				
				Row pj2Row = sheet2.createRow(currentRowNum + 1);
				pj2Row.setHeightInPoints((short) 220);
				sheet2.addMergedRegion(new CellRangeAddress(currentRowNum + 1, currentRowNum + 1, 1, tableList.get(0).size() - 1));
				
				// 落款
				CellStyle styleLk = wb.createCellStyle();
				styleLk.setAlignment(HorizontalAlignment.LEFT);
				styleLk.setWrapText(true);
				styleLk.setFont(font1);
				
				Row lkRow = sheet2.createRow(currentRowNum + 2);
				sheet2.addMergedRegion(new CellRangeAddress(currentRowNum + 2, currentRowNum + 2, 1, tableList.get(0).size() - 1));
				Cell lkCell1 = lkRow.createCell(1);
				lkCell1.setCellStyle(styleLk);
				lkCell1.setCellValue("教研室主任：___________签章             系主任：_____________签章");
				
				Row lkRow2 = sheet2.createRow(currentRowNum + 3);
				sheet2.addMergedRegion(new CellRangeAddress(currentRowNum + 3, currentRowNum + 3, 1, tableList.get(0).size() - 1));
				
				Cell fxbLkCell3 = lkRow2.createCell(0);
				fxbLkCell3.setCellStyle(styleLk);
				fxbLkCell3.setCellValue("说明：");
				
				Cell fxbLkCell4 = lkRow2.createCell(1);
				fxbLkCell4.setCellStyle(styleLk);
				fxbLkCell4.setCellValue("①本表一式两份，本课程考完后三天之内填好，自留一份，送一份给学院办公室。\r\n②考试后记主要是对考试试题和考试结果的分析意见。");

				wb.write(stream);
				wb.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stream.flush();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 导出成绩记录
	 * @param request
	 * @param response
	 */
	public void exportCjjlOld(HttpServletRequest request, HttpServletResponse response) {

		// 字段描述、属性、单元格宽度
		String[][] arr = new String[][] {
			{"成绩录入任务号", "cjlrrwh", "15"},
			{"学号", "xh", "12"},
			{"姓名", "xm", "10"},
			{"班级", "bjmc", "30"},
			{"总评成绩", "zpcj", "10"},
			{"学分", "xf", "10"},
			{"绩点", "jd", "10"}
		};

		OutputStream stream = null;
		HSSFWorkbook wb = null;
		try {
			String rwbh = request.getParameter("rwbh");
			List<CjglXscjjlBean> cjjlList = cjjlDao.findByRwbh(rwbh);

			response.setContentType("application/x-xls"); // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String("学生成绩.xls".getBytes("utf-8"), "ISO-8859-1"));
			stream = response.getOutputStream();
			wb = new HSSFWorkbook();
			Sheet sheet1 = wb.createSheet("Sheet1");
			
			// 字体
			short fontHeight = 10;
			Font font1 = wb.createFont();
			font1.setFontName("宋体");
			font1.setFontHeightInPoints(fontHeight);
			font1.setBold(true);
			
			Font font2 = wb.createFont();
			font2.setFontName("宋体");
			font2.setFontHeightInPoints(fontHeight);
			
			// 单元格样式
			CellStyle style1 = wb.createCellStyle();
			style1.setAlignment(HorizontalAlignment.CENTER);
			style1.setVerticalAlignment(VerticalAlignment.CENTER);
			style1.setFont(font1);
			
			CellStyle style2 = wb.createCellStyle();
			style2.setVerticalAlignment(VerticalAlignment.CENTER);
			style2.setFont(font2);

			// 标题行
			Row headRow = sheet1.createRow(0);
			for (int i = 0; i < arr.length; i++) {
				// 单元格宽度
				Integer width = Integer.valueOf(arr[i][2]);
				sheet1.setColumnWidth(i, width * 256);
				// 单元格内容
				Cell cell = headRow.createCell(i);
				cell.setCellStyle(style1);
				cell.setCellValue(arr[i][0]);
			}
			
			// 内容
			for (int i = 0; i < cjjlList.size(); i++) {
				Row bodyRow = sheet1.createRow(i + 1);
				
				for (int j = 0; j < arr.length; j++) {
					// 获取变量值
					String var = arr[j][1];
					CjglXscjjlBean bean = cjjlList.get(i);
					Field field = bean.getClass().getDeclaredField(var);//getField
					field.setAccessible(true);
					// 填充
					Cell cell = bodyRow.createCell(j);
					cell.setCellStyle(style2);
					cell.setCellValue((String) field.get(bean));
				}
			}

			wb.write(stream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stream.flush();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 导入成绩记录
	 * You'd use HSSF if you needed to read or write an Excel file using Java (XLS). 
	 * You'd use XSSF if you need to read or write an OOXML Excel file using Java (XLSX). 
	 * Additionally there is a specialized SXSSF implementation which allows to write very large Excel (XLSX) files in a memory optimized way.
	 * @param request
	 * @param response
	 * @return
	 */
	public Map<String, Object> importCjjl(HttpServletRequest request, HttpServletResponse response) {

		// 字段描述、属性、必填
		String[][] heads = new String[][] {
			{"成绩录入任务号", "cjlrrwh", "true"}, 
			{"学号", "xh", "true"}, 
			{"姓名", "xm", "false"}, 
			{"班级", "bjmc", "false"}, 
			{"总评成绩", "zpcj", "true"}, 
			{"学分", "xf", "true"}, 
			{"绩点", "jd", "true"}
		};

		Map<String, Object> returnData = new HashMap<String, Object>();
		Workbook wb = null;
		InputStream file = null;
		Connection conn = null;
		try {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart == false) {
				returnData.put("status", false);
				returnData.put("message", "数据提交方式出错，联系管理员！");
				return returnData;
			}

			ServletFileUpload upload = new ServletFileUpload();      // 文件上传处理程序
			upload.setFileSizeMax(1024 * 1024);
			FileItemIterator iter = upload.getItemIterator(request); // 解析请求
			if (!iter.hasNext()) {
				returnData.put("status", false);
				returnData.put("message", "请确定选择了文件！");	
				return returnData;
			}
			
			FileItemStream item = iter.next();
			

			String fileName = item.getName();
			if (fileName == null || "".equals(fileName)) {
				returnData.put("status", false);
				returnData.put("message", "找不到文件名称，请确定选择了文件！");
				return returnData;
			}

			String suffix = fileName.lastIndexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".")) : null;
			if (!".xlsx".equals(suffix) && !".xls".equals(suffix)) {
				returnData.put("status", false);
				returnData.put("message", "文件格式不正确，请选择Excel文件！");
				return returnData;
			}

			if (item.isFormField()) {
				returnData.put("status", false);
				returnData.put("message", "不是文件，联系管理员！");
				return returnData;
			}
			
			file = item.openStream();
			if (file.available() == 0) {
				returnData.put("status", false);
				returnData.put("message", "文件为空！");
				return returnData;
			}

			if (".xlsx".equals(suffix)) {
				wb = new XSSFWorkbook(file);
			} else {
				wb = new HSSFWorkbook(file);
			}
			Sheet sheet = wb.getSheetAt(0);
			if (sheet == null) {
				returnData.put("status", false);
				returnData.put("message", "在文件中找不到工作表！");
				return returnData;
			}

			// 判断文件首行字段是否符合要求
			boolean flag = true;
			Row firstRow = sheet.getRow(0);
			for (int i = 0; i < heads.length; i++) {
				Cell cell = firstRow.getCell(i);
				if (cell.getCellTypeEnum() != CellType.STRING || 
				   !cell.getStringCellValue().trim().equals(heads[i][0])) {
					flag = false;
					break;
				}
			}

			// 首行字段不匹配提示
			if (!flag) {
				StringBuilder msg = new StringBuilder("请确认首行的格式：\n");
				for (int i = 0; i < heads.length; i++) {
					msg.append(heads[i][0]).append("、");
				}
				returnData.put("status", false);
				returnData.put("message", msg.substring(0, msg.length() - 1));
				return returnData;
			}

			// 读取数据
			// 逻辑行iterator，物理行rowIterator
			StringBuilder message = null;
			List<CjglXscjjlBean> list = new ArrayList<CjglXscjjlBean>();
			Iterator<Row> it = sheet.iterator();
			while (it.hasNext()) {
				Row row = it.next();
				if (row == firstRow) continue;

				StringBuilder rowMessage = null;
				CjglXscjjlBean bean = new CjglXscjjlBean();
				for (int i = 0; i < heads.length; i++) {
					Cell cell = row.getCell(i);
					String cellText = null;

					switch (cell.getCellTypeEnum()) {
					case STRING:
						cellText = cell.getStringCellValue().trim();
						break;
					case NUMERIC:
						cellText = String.valueOf(cell.getNumericCellValue());
						break;
					default:
					}
					
					if (cellText == null || "".equals(cellText)) {
						// 必填判断与提示语句拼接
						if (Boolean.valueOf(heads[i][2])) {
							if (rowMessage == null) {
								rowMessage = new StringBuilder("第").append(row.getRowNum() + 1).append("行：");
								rowMessage.append(heads[i][0]);
							} else {
								rowMessage.append("、").append(heads[i][0]);
							}
						}
						continue;
					}

					Field field = bean.getClass().getDeclaredField(heads[i][1]);//getField
					field.setAccessible(true);
					field.set(bean, cellText);
				}
				list.add(bean);
				if (rowMessage != null) {
					rowMessage.append(" 不能为空\n");
					if (message == null) message = new StringBuilder();
					message.append(rowMessage);
				}
			}

			if (message != null) {
				returnData.put("status", false);
				returnData.put("message", message.toString());
				return returnData;
			}

			// 保存成绩记录
			conn = ConnectionUtil.getConnection();
			conn.setAutoCommit(false);
			cjjlDao.updateBatchCjjl(conn, list);
			conn.commit();
			returnData.put("status", true);
			returnData.put("message", "成绩导入成功！");
			return returnData;
		} catch (Exception e) {
			e.printStackTrace();
			returnData.put("status", false);
			returnData.put("message", "出现异常，请联系管理员！");
		} finally {
			try {
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
				if (wb != null) wb.close();
				if (file != null) file.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnData;
	}

	/**
	 * 数据验证
	 * @param lrrwBean
	 * @return
	 */
	private String validate(CjglCjllrwBean lrrwBean, boolean zpcjNullable) {
		Pattern pattern = Pattern.compile("^\\-?\\d+(\\.\\d+)?$");
		String noNum = "${name}的数据有误，${attr}非数字";
		String outOfRange = "${name}的数据有误，${attr}范围在0-100之间";
		String zpcjNull = "${name}的数据有误，总评成绩不能为空";

		for (CjglXscjjlBean cjjlBean : lrrwBean.getCjjlList()) {

			for (CjglXscjfxbBean fxBean : cjjlBean.getCjfxList()) {
				String hscjStr = fxBean.getHscj();
				if (hscjStr != null && !"".equals(hscjStr)) {
					Matcher isNum = pattern.matcher(hscjStr);
					if (!isNum.matches()) return noNum.replace("${name}", cjjlBean.getXm()).replace("${attr}", "成绩分项存在");

					double hscj = Double.valueOf(hscjStr);
					if (hscj < 0 || 100 < hscj) return outOfRange.replace("${name}", cjjlBean.getXm()).replace("${attr}", "成绩分项存在");
				}
			}

			String zpcjStr = cjjlBean.getZpcj();
			String xfStr = cjjlBean.getXf();
			String jdStr = cjjlBean.getJd();

			if (!zpcjNullable && (zpcjStr == null || "".equals(zpcjStr))) {
				return zpcjNull.replace("${name}", cjjlBean.getXm());
			}
			if (zpcjStr != null && !"".equals(zpcjStr)) {
				Matcher isNum = pattern.matcher(zpcjStr);
				if (!isNum.matches()) return noNum.replace("${name}", cjjlBean.getXm()).replace("${attr}", "总评成绩");
				
				double zpcj = Double.valueOf(zpcjStr);
				if (zpcj < 0 || 100 < zpcj) return outOfRange.replace("${name}", cjjlBean.getXm()).replace("${attr}", "总评成绩");
			}
			if (xfStr != null && !"".equals(xfStr)) {
				Matcher isNum = pattern.matcher(xfStr);
				if (!isNum.matches()) return noNum.replace("${name}", cjjlBean.getXm()).replace("${attr}", "学分");
			}
			if (jdStr != null && !"".equals(jdStr)) {
				Matcher isNum = pattern.matcher(jdStr);
				if (!isNum.matches()) return noNum.replace("${name}", cjjlBean.getXm()).replace("${attr}", "绩点");
			}

		}
		return null;
	}

    /**
     *获取登录地址
     * @param request
     * @return
     */
	private String getIpAddr(HttpServletRequest request) {
		try {
			String ip = request.getHeader("X-Forwarded-For");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			return ip;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
