package com.cjlr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cjlr.entity.XtglXnxqszbBean;
import com.utils.ConnectionUtil;

public class XtglXnxqszbDao {

	public List<XtglXnxqszbBean> findAll() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<XtglXnxqszbBean> list = null;
		String sql = "SELECT * FROM xtgl_xnxqszb ORDER BY xndm DESC, xqdm DESC";
		try {
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			list = new ArrayList<XtglXnxqszbBean>();
			while (rs.next()) {
				XtglXnxqszbBean bean = new XtglXnxqszbBean();
				bean.setXndm(rs.getString("xndm"));
				bean.setXqdm(rs.getString("xqdm"));
				bean.setSfklr(rs.getString("sfklr"));
				bean.setSfkks(rs.getString("sfkks"));
				bean.setSfkpk(rs.getString("sfkpk"));
				bean.setSfkkxq(rs.getString("sfkkxq"));
				bean.setKsrq(rs.getString("ksrq"));
				bean.setJsrq(rs.getString("jsrq"));
				bean.setCzr(rs.getString("czr"));
				bean.setCzrxm(rs.getString("czrxm"));
				bean.setCzsj(rs.getString("czsj"));
				bean.setBz(rs.getString("bz"));
				bean.setSfdqxkxq(rs.getString("sfdqxkxq"));
				bean.setSfdqxq(rs.getString("sfdqxq"));
				bean.setSfzcxq(rs.getString("sfzcxq"));
				bean.setSfkpj(rs.getString("sfkpj"));
				bean.setSfgbkbck(rs.getString("sfgbkbck"));
				bean.setSfkbk(rs.getString("sfkbk"));
				bean.setSfkdjc(rs.getString("sfkdjc"));
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
