package com.cjlr.data;

import java.util.List;

import com.cjlr.entity.CjglCjdjdzbBean;

public interface CjdjMapper {

	/**
	 * 查询成绩等级
	 * @return
	 */
	public List<CjglCjdjdzbBean> selectCjdjAll();

}
