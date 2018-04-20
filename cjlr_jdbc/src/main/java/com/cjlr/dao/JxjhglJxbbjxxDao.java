package com.cjlr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cjlr.entity.JxjhglJxbbjxxBean;
import com.utils.ConnectionUtil;

/**
 * 教学班班级信息Dao
 * @author lianyi
 *
 */
public class JxjhglJxbbjxxDao {

	/**
	 * 根据教学编号获取行政班
	 * @param jxbh
	 * @return
	 */
	public List<JxjhglJxbbjxxBean> findByJxbh(String jxbh) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<JxjhglJxbbjxxBean> list = new ArrayList<JxjhglJxbbjxxBean>();
		String sql = "SELECT * FROM jxjhgl_jxbbjxx WHERE jxbh = ?";
		try {
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, jxbh);
			rs = stmt.executeQuery();
			while (rs.next()) {
				JxjhglJxbbjxxBean bean = new JxjhglJxbbjxxBean();
				bean.setBjdm(rs.getString("bjdm"));
				bean.setBjmc(rs.getString("bjmc"));
				bean.setJxbh(rs.getString("jxbh"));
				bean.setBz(rs.getString("bz"));
				bean.setHbzt(rs.getString("hbzt"));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(rs, stmt, conn);
		}
		return list;
	}
}
