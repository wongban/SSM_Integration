// 遇到的坑：WIN 7下的IE 11里，table的rows[]不包含thead里的tr，其他浏览器甚至WIN 10的IE 11都包含

var appName = "";     // 工程地址
var intervalID;       // 用于保存setInterval的返回值，用来停止循环
var lrrwTable;        // 录入任务table，页面加载时绑定，方便后续获取内容
var cjjlTable;        // 成绩记录table，页面加载时绑定，方便后续获取内容
var currentLrfsRadio; // 录入方式选中项，读取成绩记录时赋值，切换录入方式时使用

var lrrw = null;      // 录入任务，点击一个录入任务时请求的数据
var cjbz = null;      // 成绩备注，页面加载时请求，为生成成绩记录做准备
var cjdj = null;      // 成绩等级，页面加载时请求，为生成成绩记录做准备

/**
 * 取消事件的默认动作（兼容各浏览器）
 * @param event
 * @returns
 */
function preventDefault(event) {
	if (document.all) {
		window.event.returnValue = false;
	} else {
		event.preventDefault();
	}
}

/**
 * 判断IE版本
 * @returns
 */
function IEVersion() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
    var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
    if(isIE) {
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        if(fIEVersion == 7) {
            return 7;
        } else if(fIEVersion == 8) {
            return 8;
        } else if(fIEVersion == 9) {
            return 9;
        } else if(fIEVersion == 10) {
            return 10;
        } else {
            return 6;//IE版本<=7
        }   
    } else if(isEdge) {
        return 'edge';//edge
    } else if(isIE11) {
        return 11; //IE11  
    }else{
        return -1;//不是ie浏览器
    }
}

/**
 * IE8 forEach支持
 */
if (!Array.prototype.forEach) {
	Array.prototype.forEach = function forEach(callback, thisArg) {
		var T, k;
		if (this == null) {
			throw new TypeError("this is null or not defined");
		}
		var O = Object(this);
		var len = O.length >>> 0;
		if (typeof callback !== "function") {
			throw new TypeError(callback + " is not a function");
		}
		if (arguments.length > 1) {
			T = thisArg;
		}
		k = 0;
		while (k < len) {
			var kValue;
			if (k in O) {
				kValue = O[k];
				callback.call(T, kValue, k, O);
			}
			k++;
		}
	};
}

$(document).ready(function() {

	lrrwTable = document.getElementById("lrrwTable");
	cjjlTable = document.getElementById("cjjlTable");
	// 表格是否支持多选（ctrl+鼠标左键，暂时没用到多选功能）
	lrrwTable.multiSelect = false;
	cjjlTable.multiSelect = true;
	// 用来存放当前的选中行
	lrrwTable.select = new Array();
	cjjlTable.select = new Array();
	// 绑定点击事件
	lrrwTable.onclick = selectTr;
	cjjlTable.onclick = selectTr;
	// 绑定其他事件
	lrrwTable.onmouseover = mouseover;
	lrrwTable.onmouseout = mouseout;
	cjjlTable.onmouseover = mouseover;
	cjjlTable.onmouseout = mouseout;
	cjjlTable.onkeydown = move;

	document.getElementById("saveButton").onclick = operate;
	document.getElementById("submitButton").onclick = operate;
	document.getElementById("exportButton").onclick = operate;
	document.getElementById("queryButton").onclick = operate;
	document.getElementById("resetButton").onclick = operate;
	document.getElementById("xnxqSelect").onchange = changeXnxq;

	// 目前处理自适应的代码在IE9下不正常，如果是IE9则使用百分比高度
	if (IEVersion() == 9) {
        lrrwTable.style.height = "80%";
        cjjlTable.style.height = "80%";
	}

	// 学年学期
	var xmlhttp1 = new XMLHttpRequest();
	xmlhttp1.onreadystatechange = function() {
		if (xmlhttp1.readyState == 4 && xmlhttp1.status == 200) {
			var json = xmlhttp1.responseText;
			var list = JSON.parse(json);

			var select = document.getElementById("xnxqSelect");
			for (var i = 0; i < list.length; i++) {
				var xn = list[i].xndm;
				var xq = list[i].xqdm;
				var xnString = xn + "-" + (parseInt(xn) + 1);
				var xqString = "第" + (xq==1 ? "一" : "二") + "学期";

				var option = document.createElement("option");
				option.text = xnString + " " + xqString;
				option.value = xn + xq;
				if (list[i].sfdqxq == 1) {
					option.selected = true;
				}
				select.add(option);
			}
			// 获取录入任务
			loadLrrw();
		}
	}
	xmlhttp1.open("GET", "cjlrservlet?method=queryJsonXnxq&time=" + new Date().getTime(), true);
	xmlhttp1.send();

	// 成绩备注
	var xmlhttp2 = new XMLHttpRequest();
	xmlhttp2.onreadystatechange = function() {
		if (xmlhttp2.readyState==4 && xmlhttp2.status==200) {
			var json = xmlhttp2.responseText;
			cjbz = JSON.parse(json);
		}
	}
	xmlhttp2.open("GET", "cjlrservlet?method=queryJsonCjbz&time=" + new Date().getTime(), true);
	xmlhttp2.send();

	// 成绩等级
	var xmlhttp3 = new XMLHttpRequest();
	xmlhttp3.onreadystatechange = function() {
		if (xmlhttp3.readyState==4 && xmlhttp3.status==200) {
			var json = xmlhttp3.responseText;
			cjdj = JSON.parse(json);
		}
	}
	xmlhttp3.open("GET", "cjlrservlet?method=queryJsonCjdj&time=" + new Date().getTime(), true);
	xmlhttp3.send();
});

/**
 * 功能操作（保存、提交、导出）
 */
function operate() {
	var rows = cjjlTable.querySelectorAll("tbody tr");
	switch (this.id) {
	case "saveButton":
		// 校验成绩记录每一行，如果存在不合格的数据给予提示
		for (var index = 0; index < rows.length; index++) {
			var datas = rows[index].querySelectorAll("[name='hscj'], [name='zpcj']");
			for (var i = 0; i < datas.length; i++) {
				var errorMessage = checkRules(datas[i].value);
				// errorMessage不为空则数据不合格
				if (errorMessage != "") {
					// 让不合格的文本框获得焦点
					datas[i].focus();
					// 在不合格的文本框上弹出窗口提示
					$(datas[i]).testRemind(errorMessage);
					return;
				}
			}
		}
		save("save");
		break;
	case "submitButton":
		for (var index = 0; index < rows.length; index++) {
			var cjbzSelect = rows[index].querySelector("[name='cjbz']");
			var datas = rows[index].querySelectorAll("[name='hscj'], [name='zpcj']");
			for (var i = 0; i < datas.length; i++) {

				// 总评成绩不能为空
				if (datas[i].name == "zpcj" && cjbzSelect.value == "") {
					if (datas[i].value == "") {
						datas[0].focus();
						$(datas[0]).testRemind("该学生还没录成绩。", {
							size: 10,
							css: {
								padding: "8px 10px",
								borderColor: "#aaa",
								borderRadius: 8,
								boxShadow: "2px 2px 4px rgba(0,0,0,.2)",
								background: "#fff url(chrome-remind.png) no-repeat 10px 12px",
								backgroundColor: "#fff",
								fontSize: 16,
								textIndent: 20
							}
						});
						return;
					}
				}
				var errorMessage;
				errorMessage = checkRules(datas[i].value);
				if (errorMessage != "") {
					datas[i].focus();
					$(datas[i]).testRemind(errorMessage);
					return;
				}
			}
		}

		save("submit");
		break;
	case "exportButton":
		if (!lrrwTable.select) return;
		var part = 0; // 用来标识用户是否需要对合班的成绩分开导出0：否1：是
		var td = document.getElementById(lrrwTable.select[0]);
		var input = td.querySelector("[name='sfhb']");
		if (input.value != null && input.value == 1) {
			if (confirm("该录入任务是合班的，是否分开导出？")) {
				part = 1;
			}
		}
		var url = "cjlrservlet?method=exportCjjl&rwbh=#rwbh&part=#part&time=#time".
		replace("#rwbh", lrrwTable.select[0]).
		replace("#part", part).
		replace("#time", new Date().getTime());
		window.location = url;
		break;
	case "importButton":
		var form = document.forms[0];
		var cache = form.enctype;
		form.enctype = "multipart/form-data";
		$(form).ajaxSubmit({
			url: "cjlrservlet?method=importCjjl&time=" + new Date().getTime(),
			type: "POST",
			dataType: "json",
			beforeSumit: function(formData, jqForm, options) {
				console.info("beforeSumit");
				console.info(options);
				//formData: 数组对象，提交表单时，Form插件会以Ajax方式自动提交这些数据，格式如：[{name:user,value:val },{name:pwd,value:pwd}]  
				//jqForm:   jQuery对象，封装了表单的元素     
				//options:  options对象 
				//var queryString = $.param(formData);      //name=1&address=2  
				//var formElement = jqForm[0];              //将jqForm转换为DOM对象  
				//var address = formElement.address.value;  //访问jqForm的DOM元素  
				//return true;  //只要不返回false，表单都会提交,在这里可以对表单元素进行验证
			},
			success: function(responseText, statusText) {
				form.enctype = cache;
				// responseText已经是Json对象，不能再转换
				// var returnData = JSON.parse(responseText);
				alert(responseText.message);
				if (responseText.status) {
					loadCjjl(lrrw.rwbh);
				}
				//dataType=xml  
				//var name = $('name', responseXML).text();  
				//var address = $('address', responseXML).text();  
				//$("#xmlout").html(name + "  " + address);  
				//dataType=json  
				//$("#jsonout").html(data.name + "  " + data.address);  
			}
		});
		break;
	case "queryButton":
		var rwbh = lrrw.rwbh;
		var queryBean = new Object();

		var span = document.getElementById("queryInput");
		var arr = span.querySelectorAll("input");
		for (var i = 0; i < arr.length; i++) {
			var input = arr[i];
			
			if (input.value != null && input.value != "") {
				queryBean[input.name] = input.value;
			}
		}
		
		loadCjjl(rwbh, queryBean);
		break;
	case "resetButton":
		var span = document.getElementById("queryInput");
		var arr = span.querySelectorAll("input");
		for (var i = 0; i < arr.length; i++) {
			var input = arr[i];
			input.value = "";
		}
		loadCjjl(lrrw.rwbh);
		break;
	}
}

/**
 * @param action save、submit（保存和提交）
 * @returns
 */
function save(action) {
	if (!action) {
		return;
	}
	if (!lrrw) {
		alert("请先选择一个录入任务。");
		return;
	}
	var rows = cjjlTable.querySelectorAll("tbody tr");
	var cjjlLength = rows.length;
	if (cjjlLength == 0) {
		alert("该录入任务没有成绩记录。");
		return;
	}
	if (action == "submit") {
		var r = confirm("提交后无法在录入时间外进行修改，请谨慎操作！");
		if (!r) return;
	}

	// 封装成绩记录数据
	lrrw.cjjlList = packageCjjl();

	console.info(lrrw);

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		// 操作成功后，弹窗提示、重新读取录入任务、成绩记录
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var json = xmlhttp.responseText;
			var message = JSON.parse(json);
			alert(message.message);
			if (message.status) {
				loadLrrw();
				loadCjjl(lrrw.rwbh);
			}
		}
	}
	xmlhttp.open("POST", "cjlrservlet?method=" + action + "&time=" + new Date().getTime(), true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send("json=" + JSON.stringify(lrrw)); // 将成绩记录数据JSON化，并以POST的形式提交
}

/**
 * 自动保存
 * @returns
 */
function autoSave() {
	intervalID = setInterval(function() {
		if (!lrrw) return;

		var rows = cjjlTable.querySelectorAll("tbody tr");
		if (!rows) return;

		// 封装成绩记录数据
		lrrw.cjjlList = packageCjjl();

		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var json = xmlhttp.responseText;
				var message = JSON.parse(json);
				if (message.status) {
					// 自动保存成功后，前台提示、重新读取录入任务（刷新保存人数）
					document.getElementById("autoSaveString").innerHTML = "最后自动保存时间：" + new Date().toLocaleTimeString();
					loadLrrw();
				}
			}
		}
		xmlhttp.open("POST", "cjlrservlet?method=save&time=" + new Date().getTime(), true);
		xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xmlhttp.send("json=" + JSON.stringify(lrrw));
	}, 2 * 60 * 1000);
}

/**
 * 更改学年学期
 * @returns
 */
function changeXnxq() {
	// 初始化数据
	lrrw = null;
	document.getElementById("lrrwString").innerHTML = "";
	document.getElementById("autoSaveString").innerHTML = "";

	// 上个录入任务自动保存关闭
	if (intervalID) clearInterval(intervalID);

	// 读取录入任务
	loadLrrw();
}

/**
 * 鼠标点击tr事件
 * @returns
 */
function selectTr() {
	// 获取事件对象
	var event = document.all ? window.event : arguments[0] ? arguments[0] : event;
	// 根据事件对象获取事件的元素
    var el = event.srcElement ? event.srcElement : event.target;
	if (el.tagName != "TD") return;
	var tr = el.parentNode;
	var table = tr.parentNode.parentNode;

	var same = false; // 用来判断鼠标点击的行是不是上一次点击的（重复点击相同行）

	/**
	 * 更新table.select[]的值，里面保存着当前选中的行的id
	 */
	if (table.multiSelect && event.ctrlKey) {
		/* 多选的情况 */
		// 如果已经存在就删除，不存在就添加，同时更改class属性，用于css样式
		var exist = false;
		for (var i in table.select) {
			if (table.select[i] == tr.id) {
                table.select.splice(i, 1);
                $(tr).removeClass("select");
				exist = true;
				break;
			}
		}
		if (!exist) {
			table.select.push(tr.id);
			$(tr).addClass("select");
		}
	} else {
		/* 单选的情况 */
		for (var i=table.select.length; i>=0; i--) {
			var id = table.select[i];
			if (id == tr.id) {
				same = true;
			}
			table.select.splice(i, 1);
			$("#" + id).removeClass("select");
		}
		table.select.push(tr.id);
		$(tr).addClass("select");
	}

	/*
	 * 切换录入任务时，判断是否真的切换（选中不一样的行），然后对当前成绩保存并刷新
	 */
	if (table.id == "lrrwTable" && !same) {
		/**
		 * 保存当前录入任务数据
		 */
		var rows = cjjlTable.querySelectorAll("tbody tr");
		if (lrrw && rows) {
			// 封装成绩记录数据
			lrrw.cjjlList = packageCjjl();

			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					var json = xmlhttp.responseText;
					var message = JSON.parse(json);
					if (message.status) {
						loadLrrw(); // 刷新保存人数
					}
				}
			}
			xmlhttp.open("POST", "cjlrservlet?method=save&time=" + new Date().getTime(), true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xmlhttp.send("json=" + JSON.stringify(lrrw));
		}

		// 删除成绩记录表的选中行
        cjjlTable.select = new Array();
		// 刷新成绩记录
		var rwbh = lrrwTable.select[0];
		//查询栏清空
		var span = document.getElementById("queryInput");
		var arr = span.querySelectorAll("input");
		for (var i = 0; i < arr.length; i++) {
			var input = arr[i];
			input.value = "";
		}
		
		loadCjjl(rwbh);
	}
}

/**
 * 鼠标指针位于tr上方（改变当前行的颜色）
 */
function mouseover() {
	var event = document.all ? window.event : arguments[0] ? arguments[0] : event;
	var el = event.srcElement ? event.srcElement : event.target;
	if (el.tagName != "TD") return;
	var tr = el.parentNode;
	// 对当前tr添加class属性，并在css里控制样式（颜色）
	$(tr).addClass("mouseover");
}

/**
 * 当鼠标从tr上移开（改变当前行的颜色）
 */
function mouseout() {
	var event = document.all ? window.event : arguments[0] ? arguments[0] : event;
	var el = event.srcElement ? event.srcElement : event.target;
	if (el.tagName != "TD") return;
	var tr = el.parentNode;
	
	$(tr).removeClass("mouseover");
}

/**
 * 根据学年学期获取录入任务
 * @returns
 */
function loadLrrw() {
	//　获取学年学期select标签的当前选项对应的value
	var select = document.getElementById("xnxqSelect");
	var dqxnxq = select.options[select.selectedIndex].value;

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var json = xmlhttp.responseText;

			// 将请求数据从json格式转换成javascript对象
			var list = JSON.parse(json);

			// 清空当前的录入任务表格
			$("#lrrwTable tbody tr").remove();

			// 根据请求的数据重新填充录入任务表格
			var hiddenContent = 
				"<input type='hidden' name='rwbh' value='#rwbh'>" +
				"<input type='hidden' name='zxf' value='#zxf'>" +
				"<input type='hidden' name='jgfsx' value='#jgfsx'>" +
				"<input type='hidden' name='kcksxn' value='#kcksxn'>" +
				"<input type='hidden' name='kcksxq' value='#kcksxq'>" +
				"<input type='hidden' name='kcsx' value='#kcsx'>" +
				"<input type='hidden' name='kcxz' value='#kcxz'>" +
				"<input type='hidden' name='kcbh' value='#kcbh'>" +
				"<input type='hidden' name='kcmc' value='#kcmc'>" +
				"<input type='hidden' name='kcxmbh' value='#kcxmbh'>" +
				"<input type='hidden' name='kcxmmc' value='#kcxmmc'>" +
				"<input type='hidden' name='sfgxk' value='#sfgxk'>" +
				"<input type='hidden' name='shzt' value='#shzt'>" +
				"<input type='hidden' name='sfhb' value='#sfhb'>";

			list.forEach(function(lrrw, index, array) {
				var text = hiddenContent
				.replace("#rwbh", lrrw.rwbh)
				.replace("#zxf", lrrw.zxf)
				.replace("#jgfsx", lrrw.jgfsx)
				.replace("#kcksxn", lrrw.kcksxn)
				.replace("#kcksxq", lrrw.kcksxq)
				.replace("#kcsx", lrrw.kcsx)
				.replace("#kcxz", lrrw.kcxz)
				.replace("#kcbh", lrrw.kcbh)
				.replace("#kcmc", lrrw.kcmc)
				.replace("#kcxmbh", lrrw.kcxmbh)
				.replace("#kcxmmc", lrrw.kcxmmc)
				.replace("#sfgxk", lrrw.sfgxk)
				.replace("#shzt", lrrw.shzt)
				.replace("#sfhb", lrrw.sfhb);

				var row = lrrwTable.querySelector("tbody").insertRow();
				row.id = lrrw.rwbh; // <tr>的ID，用于定位

				// 录入任务的审核状态为通过时，设置改行字体为绿色
				if (lrrw.shzt == "9")
					row.style.color = "green";

				// 如果是选中项，添加class属性，控制颜色
				if (lrrwTable.select[0] == lrrw.rwbh)
					$(row).addClass("select");

				var shztCell = row.insertCell(0);
				var jxbCell = row.insertCell(1);
				var kcCell = row.insertCell(2);
				var skrsCell = row.insertCell(3);
				var ylrsCell = row.insertCell(4);

				switch (lrrw.shzt) {
				case '0': shztCell.innerHTML = "保存";break;
				case '1': shztCell.innerHTML = "已提交审批";break;
				case '8': shztCell.innerHTML = "审批不通过";break;
				case '9': shztCell.innerHTML = "审批通过";break;
				}
				jxbCell.innerHTML = text + lrrw.jxbmc;
				kcCell.innerHTML = lrrw.kcmc;
				skrsCell.innerHTML = lrrw.skrs;
				ylrsCell.innerHTML = lrrw.ylrs;
			});

		}
	}
	xmlhttp.open("GET", "cjlrservlet?method=queryJsonLrrw&dqxnxq=" + dqxnxq + "&time=" + new Date().getTime(), true);
	xmlhttp.send();
}

/**
 * 根据任务编号获取成绩记录
 * @param rwbh
 * @returns
 */
function loadCjjl(rwbh, queryBean) {
	if (!rwbh) return;

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var json = xmlhttp.responseText;
			var map = JSON.parse(json);

			// 关闭上一个自动保存方法
			if (intervalID) clearInterval(intervalID);
			
			/**
			 * 初始化数据
			 */
			lrrw = map.lrrwBean;
			// 操作栏
			var button4 = document.getElementById("exportButton");
			// 按钮禁用（保存状态的录入任务不给予导出功能）
			if (lrrw && lrrw.shzt != "0") {
				if (button4) button4.disabled = false;
				if (button4) button4.style.backgroundColor = "";
			} else {
                if (button4) button4.disabled = true;
				if (button4) button4.style.backgroundColor = "#ccc";
			}
			// 录入任务信息栏
			document.getElementById("lrrwString").innerHTML = "";
			// 自动保存信息栏
			document.getElementById("autoSaveString").innerHTML = "";
			// 当前录入方式
			currentLrfsRadio = null;
			// 清空当前成绩记录
			$("#cjjlTable tbody tr").remove();

			//status为后台记录的请求状态 0：获取不到数据1：获取到数据			
			if (map.status == 0) {
				alert(map.message);
				return;
			}

			// 开启新的自动保存
			autoSave();

			/**
			 * 填充新的成绩记录
			 */
			// 录入任务状态栏
			var lrrwStr = 
				"教学班名称：#jxbmc 课程名称：#kcmc 学分：#zxf 及格线：#jgfsx 录入方式：" + 
				"<input type='radio' id='fs1' name='cjjlfs' value='1' onclick='changeLrfs(this)' #checked1/><label for='fs1'>百分制</label>" +
				"<input type='radio' id='fs2' name='cjjlfs' value='2' onclick='changeLrfs(this)' #checked2/><label for='fs2'>等级制</label>";
			var innerHTML = lrrwStr
				.replace("#jxbmc", lrrw.jxbmc)
				.replace("#kcbh", lrrw.kcbh)
				.replace("#kcmc", lrrw.kcmc)
				.replace("#kcsxmc", lrrw.kcsxmc)
				.replace("#zxf", parseFloat(lrrw.zxf))
				.replace("#jgfsx", lrrw.jgfsx)
				.replace("#checked1", lrrw.cjjlfs == 1 || !lrrw.cjjlfs ? "checked" : "")
				.replace("#checked2", lrrw.cjjlfs == 2 ? "checked" : "");
			lrrw.fxList.forEach(function(fx, index, array) {
				innerHTML += " #fxmc：#fxszbl".replace("#fxmc", fx.fxmc).replace("#fxszbl", parseFloat(fx.fxszbl));
			});

			document.getElementById("lrrwString").innerHTML = innerHTML;

			//　录入方式
			var radios = document.getElementsByName("cjjlfs");
			for (var i = 0; i < radios.length; i++) {
				if (radios[i].checked) currentLrfsRadio = radios[i];
			}

			// 填充行
			var cjjlArr = lrrw.cjjlList;
			cjjlArr.forEach(function(cjjl, index, array) {

				var row = cjjlTable.querySelector("tbody").insertRow();
				
				// 不及格标红
				if (cjjl.zpcj && cjjl.zpcj < lrrw.jgfsx) {
					$(row).addClass("failed");
				}
				
				row.id = cjjl.cjbh; // <tr>的ID，用于定位
				var hiddenText = 
					"<input type='hidden' name='cjbh' value='#cjbh'/><input type='hidden' name='xh' value='#xh'/><input type='hidden' name='xm' value='#xm'/>"
					.replace("#cjbh", cjjl.cjbh)
					.replace("#xh", cjjl.xh)
					.replace("#xm", cjjl.xm);

				var bjCell = row.insertCell(0);
				var xhCell = row.insertCell(1);
				var xmCell = row.insertCell(2);
				var xbCell = row.insertCell(3);
				var fxCell = row.insertCell(4);
				var zpCell = row.insertCell(5);
				var xfCell = row.insertCell(6);
				var jdCell = row.insertCell(7);
				var bzCell = row.insertCell(8);

				bjCell.innerHTML = !cjjl.bjmc ? "" : cjjl.bjmc;
				xhCell.innerHTML = hiddenText + cjjl.xh;
				xmCell.innerHTML = !cjjl.xm ? "" : cjjl.xm;
				xbCell.innerHTML = !cjjl.xbmc ? "" : cjjl.xbmc;
				xfCell.innerHTML = "<input type='text' name='xf' disabled value='#xf' />".replace("#xf", cjjl.xf ? parseFloat(cjjl.xf) : "");
				jdCell.innerHTML = "<input type='text' name='jd' disabled value='#jd' />".replace("#jd", cjjl.jd ? parseFloat(cjjl.jd) : "");

				// 百分制
				if (map.lrrwBean.cjjlfs != 2) {

					// 成绩分项
					if (cjjl.cjfxList) {
						var text = "";
						cjjl.cjfxList.forEach(function(cjfx, index, array) {
							// onfocus：input框获得焦点时，选中所有内容
							// onlur：input框失去焦点时，校验内容和级联操作（成绩分项、总评成绩、学分、绩点、成绩备注）
							text += "#fxmc <input type='text' id='#zj' name='hscj' value='#hscj' bl='#bl' onkeyup='cascade(this)' onfocus='this.select()' onblur='checkAndCascade(this)'/> "
									.replace("#fxmc", cjfx.fxmc)
									.replace("#zj",   cjfx.zj)
									.replace("#hscj", cjfx.hscj ? parseFloat(cjfx.hscj) : "")
									.replace("#bl",   cjfx.fxszbl);
							// 每两项为一行
							if (index % 2 == 1) {
								text += "<br>";
							}
						});
						fxCell.innerHTML = text;
					}

					// 总评成绩
					zpCell.innerHTML = "<input type='text' name='zpcj' disabled value='#zpcj' />"
									   .replace("#zpcj", cjjl.zpcj ? parseFloat(cjjl.zpcj) : "");
				// 等级制
				} else {

					// 成绩分项
					if (cjjl.cjfxList) {
						var text = "";
						cjjl.cjfxList.forEach(function(cjfx, index, array) {
							text += "#fxmc <select id='#zj' name='hscj' bl='#bl' style='width:35px' onchange='cascade(this)'><option value=''></option>"
								.replace("#zj", cjfx.zj)
								.replace("#fxmc", cjfx.fxmc)
								.replace("#bl",   cjfx.fxszbl);
							
							cjdj.forEach(function(value, i, array) {
								text += "<option value='#djdm' zxfsx='#zxfsx' zdfsx='#zdfsx' #selected>#djmc</option>"
										.replace("#selected", value.djdm == cjfx.cj ? "selected" : "")
										.replace("#djdm",     value.djdm)
										.replace("#zxfsx",    value.zxfsx)
										.replace("#zdfsx",    value.zdfsx)
										.replace("#djmc",     value.djmc);
							});
							text += "</select>";
							fxCell.innerHTML = text;

							// 每两项为一行
							if (index % 2 == 1) {
								text += "<br>";
							}

						});
						fxCell.innerHTML = text;
					}

					// 总评成绩
					var text = "<select name='zpcj' disabled ><option value=''></option>";
					cjdj.forEach(function(value, index, array) {
						text += "<option value='#djdm' zxfsx='#zxfsx' zdfsx='#zdfsx' #selected>#djmc</option>"
								.replace("#selected", value.djdm == cjjl.cj ? "selected" : "")
								.replace("#djdm",     value.djdm)
								.replace("#zxfsx",    value.zxfsx)
								.replace("#zdfsx",    value.zdfsx)
								.replace("#djmc",     value.djmc);
					});
					text += "</select>";
					zpCell.innerHTML = text;
				}

				// 成绩备注处理
				if (cjbz) {
					var text = "<select name='cjbz' onchange='cascade(this)' #disabled><option value=''></option>"
								.replace("#disabled", cjjl.hksq==9||cjjl.mksq==9 ? "disabled" : "");
					cjbz.forEach(function(value, index, array) {
						text += "<option value='#bzdm' dyfs='#dyfs' #selected>#bzmc</option>"
								.replace("#selected", value.bzdm == cjjl.cjbz ? "selected" : "")
								.replace("#bzdm",     value.bzdm)
								.replace("#dyfs",     value.dyfs == 0 || value.dyfs ? value.dyfs : "")
								.replace("#bzmc",     value.bzmc);
					});
					text += "</select>";
					bzCell.innerHTML = text;
				}

			});
		}
	}
	xmlhttp.open("POST", "cjlrservlet?method=queryJsonCjjl&rwbh=" + rwbh + "&time=" + new Date().getTime(), true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	if (queryBean != null) {
		xmlhttp.send("json=" + JSON.stringify(queryBean));
	} else {
		xmlhttp.send();
	} 
	return status;
}

/**
 * 封装成绩记录数据（用于保存、提交）
 */
function packageCjjl() {
	var rows = cjjlTable.querySelectorAll("tbody tr");

	var cjjlList = new Array(); // 成绩记录数组
	for (var i = 0; i < rows.length; i++) {
		var cjjl = new Object();     // 成绩记录对象
		cjjl.cjfxList = new Array(); // 成绩分项数组

		var elements = rows[i].querySelectorAll("input, select");
		for (var j = 0; j < elements.length; j++) {
			var element = elements[j];
			// 成绩分项
			if (element.name == "hscj") {
				switch(element.tagName) {
				case "INPUT":
					var cjfx = {
						zj: element.id, 
						hscj: element.value
					};
					cjjl.cjfxList.push(cjfx);
					break;
				case "SELECT":
					var cjfx = {
						zj: element.id,
						cj: element.value,
						hscj: element.options[element.selectedIndex].getAttribute("zxfsx")
					}
					cjjl.cjfxList.push(cjfx);
					break;
				}
				continue;
			}
			// 总评成绩（等级制），暂时使用最小分数线
			if (element.tagName == "SELECT" && element.name == "zpcj") {
				var zxfsx = element.options[element.selectedIndex].getAttribute("zxfsx");
				cjjl.zpcj = zxfsx != null ? zxfsx : '';
				cjjl.cj = element.value;
				continue;
			}
			// 成绩备注
			if (element.tagName == "SELECT" && element.name == "cjbz") {
				cjjl.cjbz = element.value;
				continue;
			}
			cjjl[element.name] = element.value;
		}
		cjjlList.push(cjjl);
	}
	return cjjlList;
}

/**
 * 级联操作（成绩分项、总评成绩、学分、绩点、成绩备注）
 */
function cascade(el) {
	var tr = el.parentNode.parentNode;
    var el_hscjArr = tr.querySelectorAll("[name='hscj']");
	var el_zpcj = tr.querySelector("[name='zpcj']");
	var el_xf = tr.querySelector("[name='xf']");
	var el_jd = tr.querySelector("[name='jd']");
	var el_cjbz = tr.querySelector("[name='cjbz']");

	var zpcj = null;
	var zxf = parseFloat(lrrw.zxf);
	var jgfsx = parseFloat(lrrw.jgfsx);

	/**
	 * 成绩备注
	 */
	var option_cjbz = el_cjbz.options[el_cjbz.selectedIndex];
	var dyfs = option_cjbz.getAttribute("dyfs");
	if (option_cjbz.value) {
		if (dyfs) {
			zpcj = parseFloat(dyfs);
			switch (el_zpcj.tagName) {
				case "INPUT": el_zpcj.value = zpcj; break;
				case "SELECT":
					for (var i=0; i<el_zpcj.options.length; i++) {
						var option = el_zpcj.options[i];
						var zxfsx = option.getAttribute("zxfsx");
						var zdfsx = option.getAttribute("zdfsx");

						// 跳过空项
						if (!zxfsx && !zdfsx) continue;
						// 小于最小分数线
						if (zxfsx && parseFloat(zxfsx) > zpcj.toFixed(1)) continue;
						// 大于等于最大分数线
						if (zdfsx && zpcj.toFixed(1) >= parseFloat(zdfsx)) continue;

						el_zpcj.selectedIndex = i;
					}
					break;
			}
			el_xf.value = zpcj < jgfsx ? 0 : zxf;
			el_jd.value = zpcj < 60 ? 0 : ((zpcj - 50) / 10).toFixed(1);
		} else {
			switch (el_zpcj.tagName) {
				case "INPUT": el_zpcj.value = ""; break;
				case "SELECT": el_zpcj.selectedIndex = 0; break;
			}
			el_xf.value = "";
			el_jd.value = "";
		}
		// 不及格标红
		if (zpcj != null && zpcj < jgfsx)
			$(tr).addClass("failed");
		else
			$(tr).removeClass("failed");
		return;
	}

	/**
	 * 总评成绩
	 */
	if (el.name == "zpcj") {
		switch (el_zpcj.tagName) {
		case "INPUT": break;
		case "SELECT":
			var zxfsx = el_zpcj.options[el_zpcj.selectedIndex].getAttribute("zxfsx");
			if (zxfsx != null) {
				zpcj = parseFloat(zxfsx);
				console.info(zxfsx);
				el_xf.value = zpcj < jgfsx ? 0 : zxf;
				el_jd.value = zpcj < 60 ? 0 : ((zpcj - 50) / 10).toFixed(1);
			} else {
				el_xf.value = "";
				el_jd.value = "";
			}
			break;
		}
		
		// 不及格标红
		if (zpcj != null && zpcj < jgfsx)
			$(tr).addClass("failed");
		else
			$(tr).removeClass("failed");
		return;
	}

	/**
	 * 成绩分项
	 */
	for (var i = 0; i < el_hscjArr.length; i++) {
		var el_hscj = el_hscjArr[i];

		var value = null;
		
		// 根据录入方式获取成绩分项
		switch (el_hscj.tagName) {
		case "INPUT": value = el_hscj.value;break;
		case "SELECT": value = el_hscj.options[el_hscj.selectedIndex].getAttribute("zxfsx");break;
		}
		if (value) {
			// 验证不通过，则退出不计算
			var errorMessage = checkRules(value);
			if (errorMessage) return;

			zpcj += value * el_hscj.getAttribute("bl");
		}
	}

	if (zpcj == null) {
		switch (el_zpcj.tagName) {
			case "INPUT": el_zpcj.value = ""; break;
			case "SELECT": el_zpcj.selectedIndex = 0; break;
		}
		el_xf.value = "";
		el_jd.value = "";
	} else {
		switch (el_zpcj.tagName) {
		case "INPUT": el_zpcj.value = zpcj.toFixed(1); break;
		case "SELECT": 
			for (var i=0; i<el_zpcj.options.length; i++) {
				var option = el_zpcj.options[i];
				var zxfsx = option.getAttribute("zxfsx");
				var zdfsx = option.getAttribute("zdfsx");

				// 跳过空项
				if (!zxfsx && !zdfsx) continue;
				// 小于最小分数线
				if (zxfsx && parseFloat(zxfsx) > zpcj.toFixed(1)) continue;
				// 大于等于最大分数线
				if (zdfsx && zpcj.toFixed(1) >= parseFloat(zdfsx)) continue;

				el_zpcj.selectedIndex = i;
			}
			break;
		}
		el_xf.value = zpcj < jgfsx ? 0 : zxf;
		el_jd.value = zpcj < 60 ? 0 : ((zpcj - 50) / 10).toFixed(1);
	}
	// 不及格标红
	if (zpcj != null && zpcj < jgfsx)
		$(tr).addClass("failed");
	else
		$(tr).removeClass("failed");
}

/**
 * 监听按键，移动光标
 */
function move() {
	// 根据事件获取按键的key值
    var event = document.all ? window.event : arguments[0] ? arguments[0] : event;
	var key = event.keyCode;
	//　如果按键不是方向键则退出
	if (key < 37 || 40 < key) return;
	
	var input = event.srcElement ? event.srcElement : event.target;
	var tr = input.parentNode.parentNode;

	var rowIndex = tr.rowIndex; // 当前行索引
	var inputs = tr.getElementsByTagName("input");
	var inputIndex;

	for (inputIndex = 0; inputIndex < inputs.length; inputIndex++) {
		if (input === inputs[inputIndex]) break;
	}

	switch (event.keyCode) {// 左上右下
	case 37:
	preventDefault(event); // 取消事件默认动作
	if (inputIndex > 1) cjjlTable.rows[rowIndex].getElementsByTagName("input")[inputIndex-1].focus();
		break;
	case 38:
		preventDefault(event);
		if (cjjlTable.rows[rowIndex-1] && cjjlTable.rows[rowIndex-1].id)
			cjjlTable.rows[rowIndex-1].getElementsByTagName("input")[inputIndex].focus();
		break;
	case 39:
		preventDefault(event);
		if (inputIndex < inputs.length-1) cjjlTable.rows[rowIndex].getElementsByTagName("input")[inputIndex+1].focus();
		break;
	case 40:
		preventDefault(event);
		if (rowIndex < cjjlTable.rows.length-1) cjjlTable.rows[rowIndex+1].getElementsByTagName("input")[inputIndex].focus();
		break;
	default:
	}

}

/**
 * 焦点离开输入框触发（方向键、鼠标均可）
 * 1、校验输入项，不通过不允许离开
 * 2、校验通过，触发级联
 * @param obj
 * @returns
 */
function checkAndCascade(obj) {
	var errorMessage = checkRules(obj.value);
	if (errorMessage != "") {
		obj.style.borderColor = "red";
		setTimeout(function() {obj.focus();}, 0);
		setTimeout(function() {$(obj).testRemind(errorMessage);}, 0);
	} else {
		if (obj.style.borderColor) obj.style.borderColor = "";
		cascade(obj);
	}
}

/**
 * 校验规则
 * @param value      校验数据
 * @param required   必填
 * @returns
 */
function checkRules(value, required) {

	if (required && value == "")
		return "不能为空";

	if (!value)
		return "";

	if (!/^\-?\d+(\.\d+)?$/.test(value))
		return "请输入数字";

	if (value.split(".")[1] && value.split(".")[1].length > 1)
		return "只能输入1位小数";

	if (parseFloat(value) < 0 || parseFloat(value) > 100)
		return "范围在0-100之间";

	return "";
}

/**
 * 录入方式改变
 * @param obj
 * @returns
 */
function changeLrfs(obj) {
	if (currentLrfsRadio == obj) return;

	var r = confirm("更改录入方式会清空所有成绩，并可能被自动保存。确定更改？");
	if (!r) {
		obj.checked = false;
		if (currentLrfsRadio) currentLrfsRadio.checked = true;
		return;
	}
	currentLrfsRadio = obj;

	// 上个录入任务自动保存关闭
	if (intervalID) clearInterval(intervalID);
	// 开启新的自动保存
	if (lrrw.shzt != "9") autoSave();

	lrrw.cjjlfs = obj.value;

	var rows = cjjlTable.querySelectorAll("tbody tr");
	for (var i = 0; i < rows.length; i++) {
		var row = rows[i];
		var els_hscj = row.querySelectorAll("[name='hscj']");
		var el_zpcj = row.querySelector("[name='zpcj']");
		var el_cjbz = row.querySelector("[name='cjbz']");

		// 成绩分项
		for (var index = 0; index < els_hscj.length; index++) {
			var element = els_hscj[index];
			var id = element.id;
			var bl = element.getAttribute("bl");
			
			switch (obj.value) {
			case "1":
				// 创建元素
				var elementNew = document.createElement("input");
				elementNew.id = id;
				elementNew.name = "hscj";
				elementNew.type = "text";
				elementNew.setAttribute("bl", bl);
				elementNew.setAttribute("onfocus", "this.select()");
				elementNew.setAttribute("onblur", "checkAndCascade(this)");
				elementNew.setAttribute("onkeyup", "cascade(this)");
				
				// 替换元素
				element.parentNode.replaceChild(elementNew, element);
				break;
			case "2":
				var elementNew = document.createElement("select");
				elementNew.id = id;
				elementNew.name = "hscj";
				elementNew.setAttribute("bl", bl);
				elementNew.setAttribute("style", "width: 35px");
				elementNew.setAttribute("onchange", "cascade(this)");
				
				var innerHTML = "<option value=''></option>"
					cjdj.forEach(function(value, i, array) {
						innerHTML += "<option value='#djdm' zxfsx='#zxfsx' zdfsx='#zdfsx'>#djmc</option>"
							.replace("#djdm",     value.djdm)
							.replace("#zxfsx",    value.zxfsx)
							.replace("#zdfsx",    value.zdfsx)
							.replace("#djmc",     value.djmc);
					});
				elementNew.innerHTML = innerHTML;
				
				element.parentNode.replaceChild(elementNew, element);
				break;
			default:
				break;
			}
		}

		// 总评成绩根据录入方式处理
		switch (obj.value) {
		case "1":
			el_zpcj.parentNode.innerHTML = "<input type='text' name='zpcj' onblur='checkAndCascade(this)' disabled />";
			break;
		case "2":
			var elementNew = document.createElement("select");
			elementNew.name = "zpcj";
			elementNew.setAttribute("disabled", "disabled");

			var option = document.createElement('option');
			elementNew.add(option);

			for (var j = 0; j < cjdj.length; j++) {
				var value = cjdj[j];
				
				var option = document.createElement('option');
				option.text = value.djmc;
				option.value = value.djdm;
				option.setAttribute("zxfsx", value.zxfsx);
				option.setAttribute("zdfsx", value.zdfsx);
				
				elementNew.add(option);
			}
			el_zpcj.parentNode.replaceChild(elementNew, el_zpcj);

		}

		cascade(el_cjbz);
	}
}