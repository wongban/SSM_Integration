package com.cjlr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cjlr.entity.CjglCjdjdzbBean;
import com.utils.ConnectionUtil;

/**
 * 成绩等级对照Dao
 * @author lianyi
 *
 */
public class CjglCjdjdzbDao {

	/**
	 * 查询成绩等级
	 * @return
	 */
	public List<CjglCjdjdzbBean> findAll() {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		List<CjglCjdjdzbBean> list = null;
		String sql = "SELECT * FROM cjgl_cjdjdzb ORDER BY djdm";
		try {
			conn = ConnectionUtil.getConnection();
			sm = conn.prepareStatement(sql);
			rs = sm.executeQuery();
			list = new ArrayList<CjglCjdjdzbBean>();
			while (rs.next()) {
				CjglCjdjdzbBean bean = new CjglCjdjdzbBean();
				bean.setDjdm(rs.getString("djdm"));
				bean.setDjmc(rs.getString("djmc"));
				bean.setZxfsx(rs.getString("zxfsx"));
				bean.setZdfsx(rs.getString("zdfsx"));
				bean.setBz(rs.getString("bz"));
				bean.setKslx(rs.getString("kslx"));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(rs, sm, conn);
		}
		return list;
	}
}
