package com.cjlr.data;

import org.apache.ibatis.annotations.Param;

import com.cjlr.entity.CjglCjlrsjszBean;

public interface CjlrsjMapper {

	/**
	 * 根据学年学期查询时间设置
	 * @return
	 */
	public CjglCjlrsjszBean selectCjlrsj(@Param("xn") String xn, @Param("xq")String xq);

}
