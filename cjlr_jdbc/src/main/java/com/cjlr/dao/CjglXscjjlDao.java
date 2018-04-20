package com.cjlr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cjlr.entity.CjglXscjfxbBean;
import com.cjlr.entity.CjglXscjjlBean;
import com.utils.ConnectionUtil;

public class CjglXscjjlDao {

	/**
	 * 根据任务编号查询成绩记录
	 * @param rwbh
	 * @return
	 */
	public List<CjglXscjjlBean> findByRwbh(String rwbh) {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		List<CjglXscjjlBean> list = null;
		String sql = 
			"SELECT cjjl.*," + 
			"       jbxx.xm," + 
			"       jbxx.bjdm," + 
			"       jbxx.bjmc," + 
			"       xbm.mc AS xbmc," + 
			"       bz.bzmc AS cjbzmc," + 
			"       dj.djmc AS cjdjmc," + 
			"       hksq.tjzt AS hksq," + 
			"       mksq.tjzt AS mksq" + 
			"  FROM cjgl_xscjjl cjjl," + 
			"       xsxxgl_xsjbxx jbxx," + 
			"       xtgl_dmj_xbm xbm," + 
			"       cjgl_cjbzszb bz," + 
			"       cjgl_cjdjdzb dj," + 
			"       cjgl_hkxsxxb hksq," + 
			"       cjgl_mkxsxxb mksq" + 
			" WHERE cjjl.xh = jbxx.xh(+)" + 
			"   AND jbxx.xbdm = xbm.dm(+)" + 
			"   AND cjjl.cjbz = bz.bzdm(+)" + 
			"   AND cjjl.cj = dj.djdm(+)" + 
			"   AND cjjl.xn||cjjl.xq||cjjl.kcbm||cjjl.xh = hksq.zj(+)" + 
			"   AND cjjl.xn||cjjl.xq||cjjl.kcbm||cjjl.xh = mksq.zj(+)" + 
			"   AND cjlrrwh = ?" + 
			" ORDER BY jbxx.dwdm, jbxx.zydm, jbxx.bjdm, jbxx.xh";
		try {
			conn = ConnectionUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, rwbh);
			rs = sm.executeQuery();
			list = new ArrayList<CjglXscjjlBean>();
			while (rs.next()) {
				CjglXscjjlBean bean = new CjglXscjjlBean();
				bean.setCjbh(rs.getString("cjbh"));       // 成绩编号
				bean.setCjlrrwh(rs.getString("cjlrrwh")); // 成绩录入任务号
				bean.setXn(rs.getString("xn"));           // 学年
				bean.setXq(rs.getString("xq"));           // 学期
				bean.setKcsx(rs.getString("kcsx"));       // 课程性质
				bean.setKcxz(rs.getString("kcxz"));       // 课程属性
				bean.setKcmc(rs.getString("kcmc"));       // 课程名称
				bean.setKcbm(rs.getString("kcbm"));       // 课程编码
				bean.setXf(rs.getString("xf"));           // 学分
				bean.setXh(rs.getString("xh"));           // 学号
				bean.setJd(rs.getString("jd"));           // 绩点
				bean.setCj(rs.getString("cj"));           // 成绩
				bean.setZpcj(rs.getString("zpcj"));       // 总评成绩
				bean.setCjbz(rs.getString("cjbz"));       // 成绩备注
				bean.setLrr(rs.getString("lrr"));         // 成绩录入人编号
				bean.setRlsj(rs.getString("rlsj"));       // 录入时间
				bean.setCjjmh(rs.getString("cjjmh"));     // 成绩加密号
				bean.setCjjgx(rs.getString("cjjgx"));     // 成绩及格线
				bean.setSfhdxf(rs.getString("sfhdxf"));   // 是否获得学分(0否，1是)
				bean.setTjzt(rs.getString("tjzt"));       // 提交状态
				bean.setBz(rs.getString("bz"));           // 备注
				bean.setLrrxm(rs.getString("lrrxm"));     // 成绩录入人姓名
				bean.setKcxmbh(rs.getString("kcxmbh"));   // 课程项目编号
				bean.setKcxmmc(rs.getString("kcxmmc"));   // 课程项目名称
				bean.setSfgxk(rs.getString("sfgxk"));
				bean.setJmhzzcj(rs.getString("jmhzzcj")); // 加密成绩

				bean.setXm(rs.getString("xm"));
				bean.setBjdm(rs.getString("bjdm"));
				bean.setBjmc(rs.getString("bjmc"));
				bean.setXbmc(rs.getString("xbmc"));
				bean.setCjbzmc(rs.getString("cjbzmc"));
				bean.setCjdjmc(rs.getString("cjdjmc"));
				bean.setHksq(rs.getString("hksq"));
				bean.setMksq(rs.getString("mksq"));

				bean.setCjfxList(new ArrayList<CjglXscjfxbBean>());

				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(rs, sm, conn);
		}
		return list;
	}

	/**
	 * 根据任务编号查询成绩记录
	 * @param rwbh
	 * @return
	 */
	public List<CjglXscjjlBean> findByRwbh(String rwbh, CjglXscjjlBean queryBean) {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		List<CjglXscjjlBean> list = null;
		StringBuffer sql = new StringBuffer(
			"SELECT cjjl.*," + 
			"       jbxx.xm," + 
			"       jbxx.bjdm," + 
			"       jbxx.bjmc," + 
			"       xbm.mc AS xbmc," + 
			"       bz.bzmc AS cjbzmc," + 
			"       dj.djmc AS cjdjmc," + 
			"       hksq.tjzt AS hksq," + 
			"       mksq.tjzt AS mksq" + 
			"  FROM cjgl_xscjjl cjjl," + 
			"       xsxxgl_xsjbxx jbxx," + 
			"       xtgl_dmj_xbm xbm," + 
			"       cjgl_cjbzszb bz," + 
			"       cjgl_cjdjdzb dj," + 
			"       cjgl_hkxsxxb hksq," + 
			"       cjgl_mkxsxxb mksq" + 
			" WHERE cjjl.xh = jbxx.xh(+)" + 
			"   AND jbxx.xbdm = xbm.dm(+)" + 
			"   AND cjjl.cjbz = bz.bzdm(+)" + 
			"   AND cjjl.cj = dj.djdm(+)" + 
			"   AND cjjl.xn||cjjl.xq||cjjl.kcbm||cjjl.xh = hksq.zj(+)" + 
			"   AND cjjl.xn||cjjl.xq||cjjl.kcbm||cjjl.xh = mksq.zj(+)" + 
			"   AND cjlrrwh = ?");

		if (queryBean.getBjmc() != null && !"".equals(queryBean.getBjmc())) {
			sql.append(" AND jbxx.bjmc LIKE '%" + queryBean.getBjmc() + "%'");
		}
		if (queryBean.getXm() != null && !"".equals(queryBean.getXm())) {
			sql.append(" AND jbxx.xm LIKE '%" + queryBean.getXm() + "%'");			
		}
		if (queryBean.getXh() != null && !"".equals(queryBean.getXh())) {
			sql.append(" AND cjjl.xh LIKE '%" + queryBean.getXh() + "%'");	
		}
		sql.append(" ORDER BY jbxx.dwdm, jbxx.zydm, jbxx.bjdm, jbxx.xh");
		try {
			conn = ConnectionUtil.getConnection();
			sm = conn.prepareStatement(sql.toString());
			sm.setString(1, rwbh);
			rs = sm.executeQuery();
			list = new ArrayList<CjglXscjjlBean>();
			while (rs.next()) {
				CjglXscjjlBean bean = new CjglXscjjlBean();
				bean.setCjbh(rs.getString("cjbh"));       // 成绩编号
				bean.setCjlrrwh(rs.getString("cjlrrwh")); // 成绩录入任务号
				bean.setXn(rs.getString("xn"));           // 学年
				bean.setXq(rs.getString("xq"));           // 学期
				bean.setKcsx(rs.getString("kcsx"));       // 课程性质
				bean.setKcxz(rs.getString("kcxz"));       // 课程属性
				bean.setKcmc(rs.getString("kcmc"));       // 课程名称
				bean.setKcbm(rs.getString("kcbm"));       // 课程编码
				bean.setXf(rs.getString("xf"));           // 学分
				bean.setXh(rs.getString("xh"));           // 学号
				bean.setJd(rs.getString("jd"));           // 绩点
				bean.setCj(rs.getString("cj"));           // 成绩
				bean.setZpcj(rs.getString("zpcj"));       // 总评成绩
				bean.setCjbz(rs.getString("cjbz"));       // 成绩备注
				bean.setLrr(rs.getString("lrr"));         // 成绩录入人编号
				bean.setRlsj(rs.getString("rlsj"));       // 录入时间
				bean.setCjjmh(rs.getString("cjjmh"));     // 成绩加密号
				bean.setCjjgx(rs.getString("cjjgx"));     // 成绩及格线
				bean.setSfhdxf(rs.getString("sfhdxf"));   // 是否获得学分(0否，1是)
				bean.setTjzt(rs.getString("tjzt"));       // 提交状态
				bean.setBz(rs.getString("bz"));           // 备注
				bean.setLrrxm(rs.getString("lrrxm"));     // 成绩录入人姓名
				bean.setKcxmbh(rs.getString("kcxmbh"));   // 课程项目编号
				bean.setKcxmmc(rs.getString("kcxmmc"));   // 课程项目名称
				bean.setSfgxk(rs.getString("sfgxk"));
				bean.setJmhzzcj(rs.getString("jmhzzcj")); // 加密成绩

				bean.setXm(rs.getString("xm"));
				bean.setBjdm(rs.getString("bjdm"));
				bean.setBjmc(rs.getString("bjmc"));
				bean.setXbmc(rs.getString("xbmc"));
				bean.setCjbzmc(rs.getString("cjbzmc"));
				bean.setCjdjmc(rs.getString("cjdjmc"));
				bean.setHksq(rs.getString("hksq"));
				bean.setMksq(rs.getString("mksq"));

				bean.setCjfxList(new ArrayList<CjglXscjfxbBean>());

				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(rs, sm, conn);
		}
		return list;
	}

	/**
	 * 批量更新成绩记录
	 * @param conn
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int[] updateBatchCjjl(Connection conn, List<CjglXscjjlBean> list) throws Exception {
		Statement stmt = conn.createStatement();
		for (CjglXscjjlBean bean : list) {
			if (bean.getCjbh() == null) continue;

			StringBuilder sql = new StringBuilder("UPDATE cjgl_xscjjl SET ");
			if (bean.getXf() != null) sql.append("xf = '").append(bean.getXf()).append("',");
			if (bean.getJd() != null) sql.append("jd = '").append(bean.getJd()).append("',");
			if (bean.getCj() != null) sql.append("cj = '").append(bean.getCj()).append("',");
			if (bean.getZpcj() != null) sql.append("zpcj = '").append(bean.getZpcj()).append("',");
			if (bean.getCjbz() != null) sql.append("cjbz = '").append(bean.getCjbz()).append("',");
			if (bean.getSfhdxf() != null) sql.append("sfhdxf = ").append(bean.getSfhdxf()).append(",");
			if (bean.getTjzt() != null) sql.append("tjzt = ").append(bean.getTjzt()).append(",");
			if (bean.getJmhzzcj() != null) sql.append("jmhzzcj = '").append(bean.getJmhzzcj()).append("',");

			sql.deleteCharAt(sql.length() - 1);
			sql.append(" WHERE cjbh = '").append(bean.getCjbh()).append("'");

			// 2018-03-17新增：忽略缓考、免考申请通过的数据
			sql.append(" AND xn||xq||kcbm||xh NOT IN (SELECT zj FROM cjgl_hkxsxxb WHERE tjzt = 9)");
			sql.append(" AND xn||xq||kcbm||xh NOT IN (SELECT zj FROM cjgl_mkxsxxb WHERE tjzt = 9)");

			stmt.addBatch(sql.toString());
		}
		int[] rowCount = stmt.executeBatch();
		if (stmt != null) stmt.close();
		return rowCount;
	}
}
