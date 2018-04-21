package com.cjlr.data;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cjlr.entity.CjglCjllrwBean;

public interface CjlrrwMapper {

	/**
	 * 根据任务编号查询录入任务
	 * @param rwbh
	 * @return
	 */
	public CjglCjllrwBean findByRwbh(String rwbh);

	/**
	 * 根据教工号查询成绩录入班级
	 * @param yhzh
	 * @param dqxnxq
	 * @return
	 */
	public List<CjglCjllrwBean> findByJgh(@Param("cjlljgh")String yhzh, @Param("dqxnxq")String dqxnxq);

	/**
	 * 更新审核状态
	 * @param bean
	 * @return
	 */
	public int updateShzt(CjglCjllrwBean bean);

	/**
	 * 更新成绩记录方式
	 * @param bean
	 * @return
	 */
	public int updateJlfs(CjglCjllrwBean bean);
}
