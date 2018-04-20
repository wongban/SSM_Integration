package com.cjlr.data;

import java.util.List;

import com.cjlr.entity.CjglCjbzszbBean;

public interface CjbzMapper {

	/**
	 * 查询成绩备注
	 * @return
	 */
	public List<CjglCjbzszbBean> selectCjbzAll();

}
