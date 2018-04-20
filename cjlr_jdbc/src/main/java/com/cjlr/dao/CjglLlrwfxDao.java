package com.cjlr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cjlr.entity.CjglLlrwfxBean;
import com.utils.ConnectionUtil;

public class CjglLlrwfxDao {

	/**
	 * 根据任务编号查询分项
	 * @param rwbh
	 * @return
	 */
	public List<CjglLlrwfxBean> findByRwbh(String rwbh) {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		List<CjglLlrwfxBean> list = null;
		String sql = "SELECT * FROM cjgl_llrwfx WHERE rwbh = ? ORDER BY fxdm";
		try {
			conn = ConnectionUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, rwbh);
			rs = sm.executeQuery();
			list = new ArrayList<CjglLlrwfxBean>();
			while (rs.next()) {
				CjglLlrwfxBean bean = new CjglLlrwfxBean();
				bean.setZj(rs.getString("zj"));
				bean.setRwbh(rs.getString("rwbh"));
				bean.setFxmc(rs.getString("fxmc"));
				bean.setFxdm(rs.getString("fxdm"));
				bean.setFxszbl(rs.getString("fxszbl"));
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
