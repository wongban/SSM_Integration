package com.cjlr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.cjlr.entity.XtglRzxxBean;

/**
 * 操作日志dao
 * @author lianyi
 *
 */
public class XtglRzxxDao {

	/**
	 * 增加操作日志
	 * @param conn
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int add(Connection conn, XtglRzxxBean bean) throws Exception {
		String sql = 
			"INSERT INTO xtgl_rzxx" +
			"  (rzzj, czrxm, czrq, ywmc, czbmc, czbzj, czlx, ip)" +
			"VALUES" +
			"  (? || TO_CHAR(CURRENT_TIMESTAMP, 'yyyymmddhh24missff6')," +
			"   NVL(?, ?)," +
			"   ?," +
			"   ?," +
			"   ?," +
			"   ?," +
			"   ?," +
			"   ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, bean.getYhzh());
		stmt.setString(2, bean.getCzrxm());
		stmt.setString(3, bean.getYhzh());
		stmt.setString(4, bean.getCzrq());
		stmt.setString(5, bean.getYwmc());
		stmt.setString(6, bean.getCzbmc());
		stmt.setString(7, bean.getCzbzj());
		stmt.setString(8, bean.getCzlx());
		stmt.setString(9, bean.getIp());

		int rowCount = stmt.executeUpdate();
		if (stmt != null) stmt.close();
		return rowCount;
	}
}
