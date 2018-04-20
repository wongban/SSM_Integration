package com.cjlr.data;

import java.util.List;

import com.cjlr.entity.CjglXscjjlBean;

public interface XscjjlMapper {

	/**
	 * 根据任务编号查询成绩记录
	 * @param rwbh
	 * @return
	 */
	public List<CjglXscjjlBean> findByRwbh(CjglXscjjlBean bean);

	/**
	 * 更新成绩记录
	 * @return
	 */
	public int updateCjjl(CjglXscjjlBean bean);

}
