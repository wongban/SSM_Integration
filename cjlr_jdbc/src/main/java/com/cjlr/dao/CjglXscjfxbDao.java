package com.cjlr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cjlr.entity.CjglXscjfxbBean;
import com.utils.ConnectionUtil;

/**
 * 成绩分项Dao
 * @author jobs
 *
 */
public class CjglXscjfxbDao {

	/**
	 * 根据成绩编号查询成绩分项
	 * @param cjbh
	 * @return
	 */
	public List<CjglXscjfxbBean> findByCjbh(String cjbh) {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		List<CjglXscjfxbBean> list = null;
		String sql = "SELECT * FROM cjgl_xscjfxb WHERE cjbh = ? ORDER BY zj";
		try {
			conn = ConnectionUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, cjbh);
			rs = sm.executeQuery();
			list = new ArrayList<CjglXscjfxbBean>();
			while (rs.next()) {
				CjglXscjfxbBean bean = new CjglXscjfxbBean();
				bean.setZj(rs.getString("zj"));
				bean.setCjbh(rs.getString("cjbh"));
				bean.setFxmc(rs.getString("fxmc"));
				bean.setFxdm(rs.getString("fxdm"));
				bean.setFxszbl(rs.getString("fxszbl"));
				bean.setHscj(rs.getString("hscj"));
				bean.setCj(rs.getString("cj"));
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
	 * 根据任务编号查询成绩分项
	 * @param rwbh
	 * @return
	 */
	public List<CjglXscjfxbBean> findByRwbh(String rwbh) {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		List<CjglXscjfxbBean> list = null;
		String sql = "SELECT * FROM cjgl_xscjfxb WHERE cjbh IN (SELECT cjbh FROM cjgl_xscjjl WHERE cjlrrwh = ?) ORDER BY zj";
		try {
			conn = ConnectionUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, rwbh);
			rs = sm.executeQuery();
			list = new ArrayList<CjglXscjfxbBean>();
			while (rs.next()) {
				CjglXscjfxbBean bean = new CjglXscjfxbBean();
				bean.setZj(rs.getString("zj"));
				bean.setCjbh(rs.getString("cjbh"));
				bean.setFxmc(rs.getString("fxmc"));
				bean.setFxdm(rs.getString("fxdm"));
				bean.setFxszbl(rs.getString("fxszbl"));
				bean.setHscj(rs.getString("hscj"));
				bean.setCj(rs.getString("cj"));
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
	 * 批量更新成绩分项
	 * @param cjfxArr
	 * @return
	 */
	public int[] updateBatchCjfx(Connection conn, List<CjglXscjfxbBean> cjfxList) throws Exception {
		String sql = "UPDATE cjgl_xscjfxb SET hscj=?, cj=? WHERE zj=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		for (CjglXscjfxbBean bean : cjfxList) {
			if (bean.getZj() == null) continue;

			stmt.setString(1, bean.getHscj());
			stmt.setString(2, bean.getCj());
			stmt.setString(3, bean.getZj());
			stmt.addBatch();
		}
		int[] rowCount = stmt.executeBatch();
		if (stmt != null) stmt.close();
		return rowCount;
	}

}
