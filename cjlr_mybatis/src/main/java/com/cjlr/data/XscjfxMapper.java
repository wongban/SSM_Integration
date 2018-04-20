package com.cjlr.data;

import java.util.List;

import com.cjlr.entity.CjglXscjfxbBean;

public interface XscjfxMapper {

	/**
	 * 根据成绩编号查询成绩分项
	 * @param cjbh
	 * @return
	 */
	public List<CjglXscjfxbBean> findByCjbh(String cjbh);

	/**
	 * 根据任务编号查询成绩分项
	 * @param rwbh
	 * @return
	 */
	public List<CjglXscjfxbBean> findByRwbh(String cjlrrwh);

	/**
	 * 更新成绩分项
	 * @param bean
	 * @return
	 */
	public int updateCjfx(CjglXscjfxbBean bean);
}
