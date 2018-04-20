package com.cjlr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cjlr.entity.CjglXscjhzbBean;
import com.utils.ConnectionUtil;

public class CjglXscjhzbDao {

	public boolean insert(CjglXscjhzbBean bean) {
		Connection conn = null;
		PreparedStatement sm = null;
		String sql = 
			"INSERT INTO cjgl_xscjhzb (" + 
			"	cjhzzj," + 
			"	xn," + 
			"	xq," + 
			"	xh," + 
			"	kcsx," + 
			"	kcxz," + 
			"	kcmc," + 
			"	kcbm," + 
			"	xf," + 
			"	jd," + 
			"	cj," + 
			"	kcxmbh," + 
			"	kcxmmc," + 
			"	bkcj," + 
			"	cxcj," + 
			"	kcxf," + 
			"	cjjgx," + 
			"	sfgxk," + 
			"	jmhzzcj) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, NULL, ?, ?, ?, ?)";

		boolean flag = false;
		try {
			conn = ConnectionUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, bean.getCjhzzj());
			sm.setString(2, bean.getXn());
			sm.setString(3, bean.getXq());
			sm.setString(4, bean.getXh());
			sm.setString(5, bean.getKcsx());
			sm.setString(6, bean.getKcxz());
			sm.setString(7, bean.getKcmc());
			sm.setString(8, bean.getKcbm());
			sm.setString(9, bean.getXf());
			sm.setString(10, bean.getJd());
			sm.setString(11, bean.getCj());
			sm.setString(12, bean.getKcxmbh());
			sm.setString(13, bean.getKcxmmc());
			sm.setString(14, bean.getKcxf());
			sm.setString(15, bean.getCjjgx());
			sm.setString(16, bean.getSfgxk());
			sm.setString(17, bean.getJmhzzcj());
			flag = sm.executeUpdate() > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(null, sm, conn);
		}
		return flag;
	}

	public boolean update(CjglXscjhzbBean bean) {
		Connection conn = null;
		PreparedStatement sm = null;
		String sql = 
			"UPDATE cjgl_xscjhzb" +
			"   SET cj      = ?," +
			"       jd      = ?," +
			"       xf      = ?," +
			"       jmhzzcj = ?" +
			" WHERE kcbm = ?" +
			"   AND xn = ?" +
			"   AND xq = ?" +
			"   AND xh = ?";
		boolean flag = false;
		try {
			conn = ConnectionUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, bean.getCj());
			sm.setString(2, bean.getJd());
			sm.setString(3, bean.getXf());
			sm.setString(4, bean.getJmhzzcj());
			sm.setString(5, bean.getKcbm());
			sm.setString(6, bean.getXn());
			sm.setString(7, bean.getXq());
			sm.setString(8, bean.getXh());
			flag = sm.executeUpdate() > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(null, sm, conn);
		}
		return flag;
	}

	public boolean saveOrUpdate(CjglXscjhzbBean bean) {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		String sql = 
			"SELECT COUNT(*)" + 
			"  FROM cjgl_xscjhzb" + 
			" WHERE (cjhzzj = ? OR" + 
			"       (kcbm || xh = SUBSTR(?, 5, (LENGTH(?) - 5))))" + 
			"   AND cj IS NOT NULL";
		boolean flag = false;
		try {
			conn = ConnectionUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, bean.getCjhzzj());
			sm.setString(2, bean.getCjhzzj());
			sm.setString(3, bean.getCjhzzj());
			rs = sm.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				if (count != 0) {
					return update(bean);
				} else {
					return insert(bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(rs, sm, conn);
		}
		return flag;
	}
}
