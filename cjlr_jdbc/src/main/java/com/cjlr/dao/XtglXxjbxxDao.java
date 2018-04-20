package com.cjlr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cjlr.entity.XtglXxjbxxBean;
import com.utils.ConnectionUtil;

public class XtglXxjbxxDao {

	/**
	 * 获取学校基本信息
	 * @param rwbh
	 * @return
	 */
	public XtglXxjbxxBean find() {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		XtglXxjbxxBean bean = null;
		String sql = "SELECT * FROM xtgl_xxjbxx";
		try {
			conn = ConnectionUtil.getConnection();
			sm = conn.prepareStatement(sql);
			rs = sm.executeQuery();
			if (rs.next()) {
				bean = new XtglXxjbxxBean();
				bean.setXxdm(rs.getString("xxdm"));
				bean.setXxmc(rs.getString("xxmc"));
				bean.setXxywmc(rs.getString("xxywmc"));
				bean.setXxdz(rs.getString("xxdz"));
				bean.setZzyzbm(rs.getString("zzyzbm"));
				bean.setXzqhm(rs.getString("xzqhm"));
				bean.setJzny(rs.getString("jzny"));
				bean.setZzbxlxm(rs.getString("zzbxlxm"));
				bean.setXxjbzm(rs.getString("xxjbzm"));
				bean.setXxzgbmm(rs.getString("xxzgbmm"));
				bean.setFddbrhd(rs.getString("fddbrhd"));
				bean.setFrzsh(rs.getString("frzsh"));
				bean.setXzgh(rs.getString("xzgh"));
				bean.setXzxm(rs.getString("xzxm"));
				bean.setDwfzrh(rs.getString("dwfzrh"));
				bean.setLxdh(rs.getString("lxdh"));
				bean.setCzdh(rs.getString("czdh"));
				bean.setDzxx(rs.getString("dzxx"));
				bean.setZydz(rs.getString("zydz"));
				bean.setZz211gczk(rs.getString("zz211gczk"));
				bean.setGcyxzk985(rs.getString("gcyxzk985"));
				bean.setZdxxzk(rs.getString("zdxxzk"));
				bean.setYjsyzk(rs.getString("yjsyzk"));
				bean.setJbwljyzk(rs.getString("jbwljyzk"));
				bean.setJbcrjyzk(rs.getString("jbcrjyzk"));
				bean.setXkmls(rs.getString("xkmls"));
				bean.setGjsfxgzyxzk(rs.getString("gjsfxgzyxzk"));
				bean.setSfzcxtz(rs.getString("sfzcxtz"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(rs, sm, conn);
		}
		return bean;
	}
}
