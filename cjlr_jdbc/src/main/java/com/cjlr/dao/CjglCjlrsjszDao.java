package com.cjlr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cjlr.entity.CjglCjlrsjszBean;
import com.utils.ConnectionUtil;

public class CjglCjlrsjszDao {

	/**
	 * 根据学年学期查询时间设置
	 * @param xn
	 * @param xq
	 * @return
	 */
	public CjglCjlrsjszBean findByXnXq(String xn, String xq) {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		CjglCjlrsjszBean bean = null;
		String sql = "SELECT * FROM cjgl_cjlrsjsz WHERE xn = ? AND xq = ? AND kslx = 0";
		try {
			conn = ConnectionUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, xn);
			sm.setString(2, xq);
			rs = sm.executeQuery();
			if (rs.next()) {
				bean = new CjglCjlrsjszBean();
				bean.setXn(rs.getString("xn"));
				bean.setXq(rs.getString("xq"));
				bean.setKslx(rs.getString("kslx"));
				bean.setKssj(rs.getString("kssj"));
				bean.setJssj(rs.getString("jssj"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(rs, sm, conn);
		}
		return bean;
	}
}
