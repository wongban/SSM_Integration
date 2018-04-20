<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>成绩录入</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/cjlr.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-html5Validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cjlr.js"></script>
</head>
<body>
    <div id="lrrwDiv">
      <div class="nav">
        <button type="button" id="saveButton">暂存</button>&nbsp;
        <button type="button" id="submitButton">提交</button>&nbsp;
        <button type="button" id="exportButton">导出</button><br/>
        <span><select id="xnxqSelect"><option></option></select></span>
      </div>
      <table id="lrrwTable">
        <thead>
          <tr>
            <th>审核状态</th>
            <th>教学班名称</th>
            <th>课程名称</th>
            <th>上课人数</th>
            <th>已录人数</th>
          </tr>
        </thead>
        <tbody>
        </tbody>
      </table>
    </div>
    <div id="cjjlDiv">
      <div class="nav">
        <span id="lrrwString"></span><br/>
        <span id="autoSaveString"></span><br/>
        <span id="queryInput">班级名称：<input name="bjmc">学号：<input name="xh">姓名：<input name="xm">
          <button id="queryButton">查询</button>
          <button id="resetButton">重置</button>
        </span>
      </div>
      <table id="cjjlTable">
        <thead>
          <tr>
            <th>班级</th>
            <th>学号</th>
            <th>姓名</th>
            <th>性别</th>
            <th>成绩分项</th>
            <th>总评成绩</th>
            <th>学分</th>
            <th>绩点</th>
            <th>备注</th>
          </tr>
        </thead>
        <tbody>
        </tbody>
      </table>
    </div>
</body>
</html>