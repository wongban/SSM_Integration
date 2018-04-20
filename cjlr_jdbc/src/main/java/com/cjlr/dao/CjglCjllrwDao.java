package com.cjlr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cjlr.entity.CjglCjllrwBean;
import com.utils.ConnectionUtil;

/**
 * 成绩录入任务Dao
 * @author lianyi
 *
 */
public class CjglCjllrwDao {

	/**
	 * 根据任务编号查询录入任务
	 * @param rwbh
	 * @return
	 */
	public CjglCjllrwBean findByRwbh(String rwbh) {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		CjglCjllrwBean bean = null;
		String sql = 
		"SELECT jxb.*," +
		"       zyfx.zyfxmc" +
		"  FROM (SELECT cjlrrw.*," +
		"               DECODE(xklx, NULL, (SELECT LISTAGG(bjmc, '，') WITHIN GROUP(ORDER BY bjmc) FROM jxjhgl_jxbbjxx WHERE jxbh=cjlrrw.rwbh), rwmc) AS jxbmc," +
		"               dw.dwmc AS kcksdwmc," +
		"               ksfs.mc AS khfsmc," +
		"               kcsx.mc AS kcsxmc," +
		"               kcxz.mc AS kcxzmc" +
		"          FROM cjgl_cjllrw cjlrrw," +
		"               jxjhgl_jxrwb jxrwb," +
		"               xtgl_dwjbsjzl dw," +
		"               xtgl_jxdmj_ksfs ksfs," +
		"               xtgl_jxdmj_kcsx kcsx," +
		"               xtgl_jxdmj_kcxz kcxz" +
		"         WHERE cjlrrw.rwbh = jxrwb.jxbh(+)" +
		"           AND cjlrrw.kcksdw = dw.dwdm" +
		"           AND cjlrrw.khfs = ksfs.dm" +
		"           AND cjlrrw.kcsx = kcsx.dm" +
		"           AND cjlrrw.kcxz = kcxz.dm" +
		"           AND cjlrrw.rwbh = ?) jxb," +
		"       xtgl_bjsjzl bj," +
		"       xtgl_zyfxxxsjzl zyfx" +
		" WHERE jxb.jxbmc = bj.bjmc(+)" +
		"   AND bj.ssnjzyfxdm = zyfx.zyfxdm(+)";
		try {
			conn = ConnectionUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, rwbh);
			rs = sm.executeQuery();
			if (rs.next()) {
				bean = new CjglCjllrwBean();
				bean.setRwbh(rs.getString("rwbh"));
				bean.setKcbh(rs.getString("kcbh"));
				bean.setKcmc(rs.getString("kcmc"));
				bean.setZxf(rs.getString("zxf"));
				bean.setJgfsx(rs.getString("jgfsx"));
				bean.setShzt(rs.getString("shzt"));
				bean.setRwmc(rs.getString("rwmc"));
				bean.setKcsx(rs.getString("kcsx"));
				bean.setKcxz(rs.getString("kcxz"));
				bean.setZxs(rs.getString("zxs"));
				bean.setKcksdw(rs.getString("kcksdw"));
				bean.setKcksxn(rs.getString("kcksxn"));
				bean.setKcksxq(rs.getString("kcksxq"));
				bean.setKhfs(rs.getString("khfs"));
				bean.setRkjsxm(rs.getString("rkjsxm"));
				bean.setRkjsjgh(rs.getString("rkjsjgh"));
				bean.setCjjlfs(rs.getString("cjjlfs"));
				bean.setCjlljgh(rs.getString("cjlljgh"));
				bean.setCjlljjsxm(rs.getString("cjlljjsxm"));
				bean.setTjrxm(rs.getString("tjrxm"));
				bean.setTjsj(rs.getString("tjsj"));
				bean.setBz(rs.getString("bz"));
				bean.setCjllmm(rs.getString("cjllmm"));
				bean.setKcxmbh(rs.getString("kcxmbh"));
				bean.setKcxmmc(rs.getString("kcxmmc"));
				bean.setSfgxk(rs.getString("sfgxk"));

				bean.setJxbmc(rs.getString("jxbmc"));
				bean.setKcksdwmc(rs.getString("kcksdwmc"));
				bean.setKhfsmc(rs.getString("khfsmc"));
				bean.setKcsxmc(rs.getString("kcsxmc"));
				bean.setKcxzmc(rs.getString("kcxzmc"));
				
				bean.setZyfxmc(rs.getString("zyfxmc"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(rs, sm, conn);
		}
		return bean;
	}

	/**
	 * 根据教工号查询成绩录入班级
	 * @param yhzh
	 * @return
	 */
	public List<CjglCjllrwBean> findByJgh(String yhzh, String dqxnxq) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<CjglCjllrwBean> list = null;
		String sql = 
			"SELECT cjlrrw.*," +
			"       DECODE(xklx, NULL, (SELECT LISTAGG(bjmc, '，') WITHIN GROUP(ORDER BY bjmc) FROM jxjhgl_jxbbjxx WHERE jxbh=ksmdb.jxbh), jxbmc) AS jxbmc," +
			"       DECODE(GLJXBH, NULL, 0, 1) AS sfhb," +
			"       ksmdb.skrs," +
			"       NVL((SELECT COUNT(1) FROM cjgl_xscjjl WHERE cjlrrwh = cjlrrw.rwbh AND (zpcj IS NOT NULL OR cjbz IS NOT NULL) GROUP BY cjlrrwh), 0) AS ylrs" +
			"  FROM cjgl_cjllrw cjlrrw," +
			"       jxjhgl_jxrwb jxrwb," +
			"       (SELECT jxbh, COUNT(1) AS skrs FROM xkgl_xkksmdb GROUP BY jxbh) ksmdb" +
			" WHERE cjlrrw.rwbh = jxrwb.jxbh(+)" +
			"   AND cjlrrw.rwbh = ksmdb.jxbh" +
			"   AND cjlrrw.cjlljgh = ?" +
			"   AND cjlrrw.kcksxn||cjlrrw.kcksxq LIKE ?" +
			" ORDER BY cjlrrw.shzt, " +
			"          NLSSORT(jxbmc, 'NLS_SORT = SCHINESE_PINYIN_M'), " +
			"          NLSSORT(cjlrrw.kcmc, 'NLS_SORT = SCHINESE_PINYIN_M')";
		/*
			"SELECT cjlrrw.*," +
			"       DECODE(xklx, NULL, (SELECT LISTAGG(bjmc, ',') WITHIN GROUP(ORDER BY bjmc) FROM jxjhgl_jxbbjxx WHERE jxbh=cjlrrw.rwbh), rwmc) AS jxbmc" +
			"  FROM cjgl_cjllrw cjlrrw," +
			"       jxjhgl_jxrwb jxrwb" +
			" WHERE cjlrrw.rwbh = jxrwb.jxbh(+)" +
			"   AND cjlrrw.cjlljgh = ?" + 
			"   AND EXISTS (SELECT * FROM xkgl_xkksmdb WHERE jxbh = rwbh AND xn||xq LIKE ?)";
		/*
			SELECT ksmdb.jxbh,
			       DECODE(xklx, NULL, (SELECT LISTAGG(bjmc, ',') WITHIN GROUP(ORDER BY bjmc) FROM jxjhgl_jxbbjxx WHERE jxbh=ksmdb.jxbh), jxbmc) AS jxbmc,
			       kcbh,
			       kcmc
			  FROM (SELECT jxbh FROM xkgl_xkksmdb GROUP BY jxbh) ksmdb,
			       jxjhgl_jxrwb jxrwb
			 WHERE ksmdb.jxbh = jxrwb.jxbh;
		 */
		try {
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, yhzh);
			stmt.setString(2, "%" + dqxnxq + "%");
			rs = stmt.executeQuery();
			list = new ArrayList<CjglCjllrwBean>();
			while (rs.next()) {
				CjglCjllrwBean bean = new CjglCjllrwBean();
				bean.setRwbh(rs.getString("rwbh"));
				bean.setRwmc(rs.getString("rwmc"));
				bean.setKcbh(rs.getString("kcbh"));
				bean.setKcmc(rs.getString("kcmc"));
				bean.setKcsx(rs.getString("kcsx"));
				bean.setKcxz(rs.getString("kcxz"));
				bean.setZxf(rs.getString("zxf"));
				bean.setZxs(rs.getString("zxs"));
				bean.setKcksdw(rs.getString("kcksdw"));
				bean.setKcksxn(rs.getString("kcksxn"));
				bean.setKcksxq(rs.getString("kcksxq"));
				bean.setKhfs(rs.getString("khfs"));
				bean.setRkjsxm(rs.getString("rkjsxm"));
				bean.setRkjsjgh(rs.getString("rkjsjgh"));
				bean.setCjjlfs(rs.getString("cjjlfs"));
				bean.setCjlljgh(rs.getString("cjlljgh"));
				bean.setCjlljjsxm(rs.getString("cjlljjsxm"));
				bean.setJgfsx(rs.getString("jgfsx"));
				bean.setShzt(rs.getString("shzt"));
				bean.setTjrxm(rs.getString("tjrxm"));
				bean.setTjsj(rs.getString("tjsj"));
				bean.setBz(rs.getString("bz"));
				bean.setCjllmm(rs.getString("cjllmm"));
				bean.setKcxmbh(rs.getString("kcxmbh"));
				bean.setKcxmmc(rs.getString("kcxmmc"));
				bean.setSfgxk(rs.getString("sfgxk"));

				bean.setJxbmc(rs.getString("jxbmc"));
				bean.setSfhb(rs.getString("sfhb"));
				bean.setSkrs(rs.getString("skrs"));
				bean.setYlrs(rs.getString("ylrs"));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(rs, stmt, conn);
		}
		return list;
	}

	/**
	 * 更新审核状态
	 * @param conn
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public int updateShzt(Connection conn, CjglCjllrwBean bean) throws SQLException {
		String sql = "UPDATE cjgl_cjllrw SET shzt = ? WHERE rwbh = ?";
		PreparedStatement sm = conn.prepareStatement(sql);
		sm.setString(1, bean.getShzt());
		sm.setString(2, bean.getRwbh());
		int rowCount = sm.executeUpdate();
		if (sm != null) sm.close();
		return rowCount;
	}

	/**
	 * 更新成绩记录方式式
	 * @param bean
	 * @return
	 */
	public int updateJlfs(Connection conn, CjglCjllrwBean bean) throws Exception {
		String sql = "UPDATE cjgl_cjllrw SET cjjlfs = ? WHERE rwbh = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, bean.getCjjlfs());
		stmt.setString(2, bean.getRwbh());
		int rowCount = stmt.executeUpdate();
		if (stmt != null) stmt.close();
		return rowCount;
	}
}
