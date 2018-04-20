package com.cjlr.data;

import java.util.List;

import com.cjlr.entity.CjglLlrwfxBean;

public interface LlrwfxMapper {

	/**
	 * 根据任务编号查询分项
	 * @param rwbh
	 * @return
	 */
	public List<CjglLlrwfxBean> selectLlrwfx(String rwbh);

}
