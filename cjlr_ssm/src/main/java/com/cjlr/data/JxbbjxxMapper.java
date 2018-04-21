package com.cjlr.data;

import java.util.List;

import com.cjlr.entity.JxjhglJxbbjxxBean;

public interface JxbbjxxMapper {

	/**
	 * 根据教学编号获取行政班
	 * @return
	 */
	public List<JxjhglJxbbjxxBean> selectJxbbjxx(String jxbh);

}
