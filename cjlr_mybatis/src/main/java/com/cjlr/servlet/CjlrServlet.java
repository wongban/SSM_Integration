package com.cjlr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjlr.entity.CjglCjbzszbBean;
import com.cjlr.entity.CjglCjdjdzbBean;
import com.cjlr.entity.CjglCjllrwBean;
import com.cjlr.entity.CjglXscjjlBean;
import com.cjlr.entity.XtglXnxqszbBean;
import com.cjlr.service.CjlrService;
import com.google.gson.Gson;

/**
 * 成绩录入servlet
 * @author jobs
 *
 */
public class CjlrServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private CjlrService cjlrService = new CjlrService();

	private enum Function {
		queryJsonXnxq, 
		queryJsonLrrw, 
		queryJsonCjjl, 
		queryJsonCjbz, 
		queryJsonCjdj, 
		save, 
		submit, 
		exportCjjl, 
		importCjjl
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		Function function = Function.valueOf(method);
		switch (function) {
		case queryJsonXnxq: queryJsonXnxq(request, response); break;
		case queryJsonLrrw: queryJsonLrrw(request, response); break;
		case queryJsonCjjl: queryJsonCjjl(request, response); break;
		case queryJsonCjbz: queryJsonCjbz(request, response); break;
		case queryJsonCjdj: queryJsonCjdj(request, response); break;
		case save: save(request, response); break;
		case submit: submit(request, response); break;
		case exportCjjl: exportCjjl(request, response); break;
		case importCjjl: importCjjl(request, response); break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 查询学年学期json格式
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void queryJsonXnxq(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<XtglXnxqszbBean> list = cjlrService.findAllXnxq();
		response.setContentType("application/json"); 
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(new Gson().toJson(list));
	}

	/**
	 * 查询录入任务json格式
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void queryJsonLrrw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String yhzh = "W00002";// Z00050  H00031
		String dqxnxq = request.getParameter("dqxnxq");
		List<CjglCjllrwBean> list = cjlrService.findByJgh(yhzh, dqxnxq);
		response.setContentType("application/json"); 
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(new Gson().toJson(list));
	}

	/**
	 * 查询成绩记录json格式
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void queryJsonCjjl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rwbh = request.getParameter("rwbh");
		String json = request.getParameter("json");
		CjglXscjjlBean queryBean = new Gson().fromJson(json, CjglXscjjlBean.class);
		if (queryBean == null) queryBean = new CjglXscjjlBean();
		queryBean.setCjlrrwh(rwbh);

		Map<String, Object> map = cjlrService.findByRwbh(queryBean);
		response.setContentType("application/json"); 
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(new Gson().toJson(map));
	}

	/**
	 * 查询成绩备注json格式
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void queryJsonCjbz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CjglCjbzszbBean> list = cjlrService.findAllCjbz();
		response.setContentType("application/json"); 
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(new Gson().toJson(list));
	}

	/**
	 * 查询成绩等级json格式
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void queryJsonCjdj(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CjglCjdjdzbBean> list = cjlrService.findAllCjdj();
		response.setContentType("application/json"); 
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(new Gson().toJson(list));
	}

	/**
	 * 暂存
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> message = null;
		try {
			// List<CjglXscjjlBean> cjjlList = new Gson().fromJson(json, new TypeToken<List<CjglXscjjlBean>>(){}.getType());
			// CjglXscjjlBean[] cjjlArr = new Gson().fromJson(json, CjglXscjjlBean[].class);
			String json = request.getParameter("json");
			CjglCjllrwBean lrrwBean = new Gson().fromJson(json, CjglCjllrwBean.class);
			message = cjlrService.save(lrrwBean);
		} catch (Exception e) {
			e.printStackTrace();
			message = new HashMap<String, Object>();
			message.put("status", false);
			message.put("message", "出错了，请联系管理员！");
		}
		response.setContentType("application/json"); 
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(new Gson().toJson(message));
	}

	/**
	 * 提交
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void submit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> message = null;
		try {
			message = cjlrService.submit(request);
		} catch (Exception e) {
			e.printStackTrace();
			message = new HashMap<String, Object>();
			message.put("status", false);
			message.put("message", "出错了，请联系管理员！");
		}
		response.setContentType("application/json"); 
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(new Gson().toJson(message));
	}

	/**
	 * 导出学生成绩
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void exportCjjl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		cjlrService.exportCjjl(request, response);
	}

	/**
	 * 导入学生成绩
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void importCjjl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> returnData = cjlrService.importCjjl(request, response);
		response.setContentType("text/html"); 
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(new Gson().toJson(returnData));
	}

}
