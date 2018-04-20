package com.cjlr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cjlr.entity.CjglCjbzszbBean;
import com.utils.ConnectionUtil;

/**
 * 成绩备注Dao
 * @author lianyi
 *
 */
public class CjglCjbzszbDao {

	/**
	 * 查询成绩备注
	 * @return
	 */
	public List<CjglCjbzszbBean> findAll() {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		List<CjglCjbzszbBean> list = null;
		String sql = "SELECT * FROM cjgl_cjbzszb ORDER BY bzdm";
		try {
			conn = ConnectionUtil.getConnection();
			sm = conn.prepareStatement(sql);
			rs = sm.executeQuery();
			list = new ArrayList<CjglCjbzszbBean>();
			while (rs.next()) {
				CjglCjbzszbBean bean = new CjglCjbzszbBean();
				bean.setBzdm(rs.getString("bzdm"));
				bean.setBzmc(rs.getString("bzmc"));
				bean.setDyfs(rs.getBigDecimal("dyfs"));
				bean.setBz(rs.getString("bz"));
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
