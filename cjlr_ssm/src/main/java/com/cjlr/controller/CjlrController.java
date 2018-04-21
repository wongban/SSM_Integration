package com.cjlr.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjlr.entity.CjglCjbzszbBean;
import com.cjlr.entity.CjglCjdjdzbBean;
import com.cjlr.entity.CjglCjllrwBean;
import com.cjlr.entity.CjglXscjjlBean;
import com.cjlr.entity.XtglXnxqszbBean;
import com.cjlr.service.CjlrService;

@Controller
@RequestMapping("cjlr")
public class CjlrController {

	@Autowired
	private CjlrService cjlrService;

	/**
	 * 查询学年学期json格式
	 * @return
	 */
	@RequestMapping(value="/queryJsonXnxq")
	@ResponseBody
	public List<XtglXnxqszbBean> queryJsonXnxq() {
		return cjlrService.findAllXnxq();
	}

	/**
	 * 查询录入任务json格式
	 * @param dqxnxq
	 * @return
	 */
	@RequestMapping("/queryJsonLrrw")
	@ResponseBody
	public List<CjglCjllrwBean> queryJsonLrrw(String dqxnxq) {
		String yhzh = "W00002";// Z00050  H00031
		return cjlrService.findByJgh(yhzh, dqxnxq);
	}

	/**
	 * 查询成绩记录json格式
	 * @param xscjjlBean
	 * @return
	 */
	@RequestMapping("/queryJsonCjjl")
	@ResponseBody
	public Map<String, Object> queryJsonCjjl(CjglXscjjlBean xscjjlBean) {
		return cjlrService.findByRwbh(xscjjlBean);
	}

	/**
	 * 查询成绩备注json格式
	 * @return
	 */
	@RequestMapping("/queryJsonCjbz")
	@ResponseBody
	public List<CjglCjbzszbBean> queryJsonCjbz() {
		return cjlrService.findAllCjbz();
	}

	/**
	 * 查询成绩等级json格式
	 * @return
	 */
	@RequestMapping("/queryJsonCjdj")
	@ResponseBody
	public List<CjglCjdjdzbBean> queryJsonCjdj() {
		return cjlrService.findAllCjdj();
	}

	/**
	 * 保存
	 * @param lrrwBean
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(@RequestBody CjglCjllrwBean lrrwBean) {
		return cjlrService.save(lrrwBean);
	}

	/**
	 * 提交
	 * @param request
	 * @return
	 */
	@RequestMapping("/submit")
	@ResponseBody
	public Map<String, Object> submit(@RequestBody CjglCjllrwBean lrrwBean) {
		return cjlrService.submit(lrrwBean);
	}

	/**
	 * 导出学生成绩
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportCjjl")
	public void exportCjjl(HttpServletRequest request, HttpServletResponse response) {
		cjlrService.exportCjjl(request, response);
	}

	/**
	 * 导入学生成绩
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/importCjjl")
	public Map<String, Object> importCjjl(HttpServletRequest request, HttpServletResponse response) {
		return cjlrService.importCjjl(request, response);
	}
	
}
