package com.cjlr.data;

import org.apache.ibatis.annotations.Param;

public interface XscjhzMapper {

	public int margeInto(@Param("zj")String zj, @Param("jgfsx")String jgfsx, @Param("sfgxk")String sfgxk, @Param("zxf")String zxf);

}
