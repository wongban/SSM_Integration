// 遇到的坑：WIN 7下的IE 11里，table的rows[]不包含thead里的tr，其他浏览器甚至WIN 10的IE 11都包含

var appName = AdfPage.PAGE._baseResourceUrl;
var intervalID;
var lrrwTable; // 录入任务table
var cjjlTable; // 成绩记录table
var currentLrfsRadio; // 录入方式选中项（读取成绩记录时赋值）

var lrrw = null; // 录入任务，加载成绩记录时获取数据
var cjbz = null; // 成绩备注
var cjdj = null; // 成绩等级

var selectColor = "#D9F3FF"; // 选中行颜色
var failedColor = "#EED5D2"; // 不及格颜色

/**
 * IE 取消事件的默认动作
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
	documentReady();
});

//权限编号
$("#N080203").live('click', function() {
    var int = null;
    int = setInterval(function() {
        if (document.getElementById('cjlrDiv') != null) {
            if (int != null) {
                clearInterval(int);
                documentReady();
            }
        }
    }, 200);
});

function documentReady() {
	lrrwTable = document.getElementById("lrrwTable");
	cjjlTable = document.getElementById("cjjlTable");
	// 是否多选
	lrrwTable.multiSelect = false;
	cjjlTable.multiSelect = true;
	lrrwTable.select = new Array();
	cjjlTable.select = new Array();
	// 绑定点击事件
	lrrwTable.onclick = selectTr;
	cjjlTable.onclick = selectTr;
	// 绑定其他事件
	cjjlTable.onkeydown = move;
	cjjlTable.onchange = calculate;
	cjjlTable.onkeyup = calculate;

	document.getElementById("saveButton").onclick = operate;
	document.getElementById("submitButton").onclick = operate;
	document.getElementById("exportButton").onclick = operate;
	document.getElementById("xnxqSelect").onchange = changeXnxq;

	// IE9自适应问题
	if (IEVersion() == 9) {
        lrrwTable.style.height = "80%";
        cjjlTable.style.height = "80%";	}

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
	xmlhttp1.open("GET", appName + "/cjlrservlet?method=queryJsonXnxq&time=" + new Date().getTime(), true);
	xmlhttp1.send();

	// 成绩备注
	var xmlhttp2 = new XMLHttpRequest();
	xmlhttp2.onreadystatechange = function() {
		if (xmlhttp2.readyState==4 && xmlhttp2.status==200) {
			var json = xmlhttp2.responseText;
			cjbz = JSON.parse(json);
		}
	}
	xmlhttp2.open("GET", appName + "/cjlrservlet?method=queryJsonCjbz&time=" + new Date().getTime(), true);
	xmlhttp2.send();

	// 成绩等级
	var xmlhttp3 = new XMLHttpRequest();
	xmlhttp3.onreadystatechange = function() {
		if (xmlhttp3.readyState==4 && xmlhttp3.status==200) {
			var json = xmlhttp3.responseText;
			cjdj = JSON.parse(json);
		}
	}
	xmlhttp3.open("GET", appName + "/cjlrservlet?method=queryJsonCjdj&time=" + new Date().getTime(), true);
	xmlhttp3.send();
}

/**
 * 功能操作
 * @param obj
 * @returns
 */
function operate() {
	var rows = cjjlTable.querySelectorAll("tbody tr");
	switch (obj.id) {
	case "saveButton":
		for (var index = 0; index < rows.length; index++) {
			var datas = rows[index].querySelectorAll("input[name='hscj'], input[name='zpcj'], select[name='zpcj']");
			for (var i = 0; i < datas.length; i++) {
				var errorMessage = checkRules(datas[i].value);
				if (errorMessage != "") {
					datas[i].focus();
					$(datas[i]).testRemind(errorMessage);
					return;
				}
			}
		}
		save("save");
		break;
	case "submitButton":
		for (var index = 0; index < rows.length; index++) {
			var cjbzSelect = rows[index].querySelector("select[name='cjbz']");
			var datas = rows[index].querySelectorAll("input[name='hscj'], input[name='zpcj'], select[name='zpcj']");
			for (var i = 0; i < datas.length; i++) {
				var errorMessage;
				if (datas[i].name == "zpcj") {
					errorMessage = checkRules(datas[i].value, true);
				} else {
					errorMessage = checkRules(datas[i].value);
				}
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
		if (!lrrwTable.currentTrId) return;
		window.location = appName + "/cjlrservlet?method=exportCjjl&rwbh=" + lrrwTable.currentTrId + "&time=" + new Date().getTime();
		break;
	case "importButton":
		var form = document.forms[0];
		var cache = form.enctype;
		form.enctype = "multipart/form-data";
		$(form).ajaxSubmit({
			url: appName + "/cjlrservlet?method=importCjjl&time=" + new Date().getTime(),
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
	default:
	}
}

/**
 * 数据提交
 * @param action
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

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
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
	xmlhttp.open("POST", appName + "/cjlrservlet?method=" + action + "&time=" + new Date().getTime(), true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send("json=" + JSON.stringify(lrrw));	
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
 * @param evt
 * @returns
 */
function selectTr() {
	var el = event.srcElement ? event.srcElement : event.target;
	if (el.tagName != "TD") return;
	var tr = el.parentNode;
	var table = tr.parentNode.parentNode;

	/**
	 * 选中项
	 */
	if (table.multiSelect && event.ctrlKey) {
		var exist = false;
		for (var i in table.select) {
			if (table.select[i] == tr.id) {
				table.select.splice(i, 1);
				exist = true;
				break;
			}
		}
		if (!exist) {
			table.select.push(tr.id);
		}
	} else {
		table.select.splice(0, table.select.length);
		table.select.push(tr.id);
	}

	/** 
	 * 颜色处理
	 */
	// 初始化颜色
	var rows = table.querySelectorAll("tbody tr");
	for (var i = 0; i < rows.length; i++) {
		rows[i].style.backgroundColor = "";
	}
	// 不及格颜色
	if (table.id == "cjjlTable" && lrrw && lrrw.jgfsx) {
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];

			var zpcj = null;
			var element = row.querySelector("[name='zpcj']");
			if (element) {
				if (element.tagName == "INPUT")
					zpcj = element.value;
				if (element.tagName == "SELECT")
					zpcj = element.options[element.selectedIndex].getAttribute("zxfsx");
				if (zpcj && parseFloat(zpcj) < lrrw.jgfsx)
					row.style.backgroundColor = failedColor;
			}
		}
	}
	// 选中项颜色
	for (var i in table.select) {
		var tr = document.getElementById(table.select[i]);
		if (tr)
			document.getElementById(table.select[i]).style.backgroundColor = selectColor;
	}

	if (table.id == "lrrwTable") {
		var rwbh = lrrwTable.currentTrId;
		loadCjjl(rwbh);
	}
}

/**
 * 根据学年学期获取录入任务
 * @returns
 */
function loadLrrw() {
	var select = document.getElementById("xnxqSelect");
	var dqxnxq = select.options[select.selectedIndex].value;

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var json = xmlhttp.responseText;
			var list = JSON.parse(json);

			$("#lrrwTable tbody tr").remove();

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
				"<input type='hidden' name='shzt' value='#shzt'>";

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
				.replace("#shzt", lrrw.shzt);

				var row = lrrwTable.querySelector("tbody").insertRow();
				row.id = lrrw.rwbh; // <tr>的ID，用于定位

				if (lrrw.shzt == "9")
					row.style.color = "green";

				if (lrrwTable.currentTrId == lrrw.rwbh)
					row.style.backgroundColor = selectColor;

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
				default:
				}
				jxbCell.innerHTML = text + lrrw.jxbmc;
				kcCell.innerHTML = lrrw.kcmc;
				skrsCell.innerHTML = lrrw.skrs;
				ylrsCell.innerHTML = lrrw.ylrs;
			});

		}
	}
	xmlhttp.open("GET", appName + "/cjlrservlet?method=queryJsonLrrw&dqxnxq=" + dqxnxq + "&time=" + new Date().getTime(), true);
	xmlhttp.send();
}

/**
 * 根据任务编号获取成绩记录
 * @param rwbh
 * @returns
 */
function loadCjjl(rwbh) {
	if (!rwbh) return;

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var json = xmlhttp.responseText;
			var map = JSON.parse(json);

			// 上个自动保存关闭
			if (intervalID) clearInterval(intervalID);

			/**
			 * 初始化数据
			 */
			lrrw = map.lrrwBean;
			// 操作栏
			var button4 = document.getElementById("exportButton");
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
			// 成绩记录
			$("#cjjlTable tbody tr").remove();

			if (map.status == 0) {//0：获取不到数据1：获取到数据
				alert(map.message);
				return;
			}

			// 开启新的自动保存
			autoSave();

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
					row.style.background = failedColor;
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

				// 成绩分项处理
				if (cjjl.cjfxList) {
					var text = "";
					cjjl.cjfxList.forEach(function(cjfx, index, array) {
						text += "#fxmc <input type='text' id='#zj' name='hscj' value='#hscj' bl='#bl' onfocus='this.select()' onblur='validate(event)'/> "
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

				// 总评成绩处理
				if (map.lrrwBean.cjjlfs != 2) {
					zpCell.innerHTML = "<input type='text' name='zpcj' disabled value='#zpcj' onfocus='this.select()' onblur='validate(event)'/>"
									   .replace("#zpcj", cjjl.zpcj ? parseFloat(cjjl.zpcj) : "");
				} else {
					var text = "<select name='zpcj' onchange='djChange(event)'><option value=''></option>";
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
					var text = "<select name='cjbz' onchange='bzChange(event);'><option value=''></option>";
					cjbz.forEach(function(value, index, array) {
						text += "<option value='#bzdm' dyfs='#dyfs' #selected>#bzmc</option>"
								.replace("#selected", value.bzdm == cjjl.cjbz ? "selected" : "")
								.replace("#bzdm",     value.bzdm)
								.replace("#dyfs",     value.dyfs)
								.replace("#bzmc",     value.bzmc);
					});
					text += "</select>";
					bzCell.innerHTML = text;
				}

			});
		}
	}
	xmlhttp.open("GET", appName + "/cjlrservlet?method=queryJsonCjjl&rwbh=" + rwbh + "&time=" + new Date().getTime(), true);
	xmlhttp.send();
	return status;
}

/**
 * 封装成绩记录数据
 * @returns
 */
function packageCjjl() {
	var rows = cjjlTable.querySelectorAll("tbody tr");

	var cjjlList = new Array(); // 成绩记录数组
	for (var i = 0; i < rows.length; i++) {
		var cjjl = new Object();     // 成绩记录对象
		cjjl.cjfxList = new Array(); // 成绩分项数组

		var elements = rows[i].querySelectorAll("input, select[name='zpcj'], select[name='cjbz']");
		for (var j = 0; j < elements.length; j++) {
			var element = elements[j];
			// 成绩分项
			if (element.name == "hscj") {
				var cjfx = {zj: element.id, hscj: element.value};
				cjjl.cjfxList.push(cjfx);
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
 * 自动保存
 * @returns
 */
function autoSave() {
	intervalID = setInterval(function() {
		if (!lrrw) return;

		var rows = cjjlTable.querySelectorAll("tbody tr");
		if (!rows) return;

        // 用户离开成绩录入页面，关闭自动保存
        if (!document.getElementById('cjlrDiv')) {
            if (intervalID) {
                clearInterval(intervalID);
                return;
            }
        }

		// 封装成绩记录数据
		lrrw.cjjlList = packageCjjl();

		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var json = xmlhttp.responseText;
				var message = JSON.parse(json);
				if (message.status) {
					document.getElementById("autoSaveString").innerHTML = "最后自动保存时间：" + new Date().toLocaleTimeString();
					loadLrrw(); // 刷新保存人数
				}
			}
		}
		xmlhttp.open("POST", appName + "/cjlrservlet?method=save&time=" + new Date().getTime(), true);
		xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xmlhttp.send("json=" + JSON.stringify(lrrw));
	}, 120 * 1000);
}

/**
 * 监听等级成绩
 */
function djChange() {
	var el = event.srcElement ? event.srcElement : event.target;
	var tr = el.parentNode.parentNode;

	var selectZpcj = tr.querySelector("[name='zpcj']");
	var selectCjbz = tr.querySelector("[name='cjbz']");
	var inputXf = tr.querySelector("[name='xf']");
	var inputJd = tr.querySelector("[name='jd']");

	// 成绩备注选中则不计算
	if (selectCjbz.options[selectCjbz.selectedIndex].value) return;
	// 等级成绩选中空的情况
	if (!selectZpcj.options[selectZpcj.selectedIndex].value) {
		inputXf.value = "";
		inputJd.value = "";
		return;
	}

	var zpcj = parseFloat(selectZpcj.options[selectZpcj.selectedIndex].getAttribute("zxfsx"));
	var zxf = lrrw.zxf;
	var jgfsx = lrrw.jgfsx;

	inputXf.value = zpcj < jgfsx ? 0 : zxf;
	inputJd.value = zpcj < 60 ? 0 : ((zpcj - 50) / 10).toFixed(1);

	// 不及格标红
	if (zpcj < jgfsx)
		tr.style.background = failedColor;
	else
		tr.style.background = "";
}

/**
 * 监听成绩备注
 */
function bzChange() {
	var el = event.srcElement ? event.srcElement : event.target;
	var tr = el.parentNode.parentNode;

	var selectZpcj = tr.querySelector("select[name='zpcj']");
	var selectCjbz = tr.querySelector("[name='cjbz']");
	var inputZpcj = tr.querySelector("input[name='zpcj']");
	var inputXf = tr.querySelector("[name='xf']");
	var inputJd = tr.querySelector("[name='jd']");

	var inputHscjArr = tr.querySelectorAll("[name='hscj']");

	var zpcj = null;
	var zxf = lrrw.zxf;
	var jgfsx = lrrw.jgfsx;

	var optionCjbz = selectCjbz.options[selectCjbz.selectedIndex];
	if (optionCjbz.value != "") {
		zpcj = parseFloat(optionCjbz.getAttribute("dyfs"));
	} else {
		// 等级成绩or总评成绩
		if (selectZpcj) {
			var optionZpcj = selectZpcj.options[selectZpcj.selectedIndex];
			if (optionZpcj.value != "") {
				zpcj = parseFloat(optionZpcj.getAttribute("zxfsx"));
			}
		} else {
			for (var i = 0; i < inputHscjArr.length; i++) {
				var inputHscj = inputHscjArr[i];
				if (inputHscj.value != "") {
					zpcj += inputHscj.value * inputHscj.getAttribute("bl"); 
				}
			}
		}
	}

	if (zpcj != null) zpcj = zpcj.toFixed(1);

	if (inputZpcj) inputZpcj.value = zpcj;
	if (zpcj != null) {
		inputXf.value = zpcj < jgfsx ? 0 : zxf;
		inputJd.value = zpcj < 60 ? 0 : ((zpcj - 50) / 10).toFixed(1);

		// 不及格标红
		if (zpcj < jgfsx)
			tr.style.background = failedColor;
		else
			tr.style.background = "";
	} else {
		inputXf.value = "";
		inputJd.value = "";
	}
}

/**
 * 监听按键，计算成绩
 */
function calculate() {
	var key = event.keyCode;
	if (37 <= key && key <= 40) return;

	var el = event.srcElement ? event.srcElement : event.target;
	var tr = el.parentNode.parentNode;

	var inputZpcj = tr.querySelector("input[name='zpcj']");
	var inputXf = tr.querySelector("[name='xf']");
	var inputJd = tr.querySelector("[name='jd']");
	var selectZpcj = tr.querySelector("select[name='zpcj']");
	var selectCjbz = tr.querySelector("[name='cjbz']");

	var inputHscjArr = tr.querySelectorAll("[name='hscj']");

    // 成绩备注有选中，则退出不计算
	var optionCjbz = selectCjbz.options[selectCjbz.selectedIndex];
	if (optionCjbz.value != "") return;

	var zpcj = null;
	var zxf = parseFloat(lrrw.zxf);
	var jgfsx = parseFloat(lrrw.jgfsx);

	for (var i = 0; i < inputHscjArr.length; i++) {
		var inputHscj = inputHscjArr[i];
		
		// 验证不通过，则退出不计算
		var errorMessage = checkRules(inputHscj.value);
		if (errorMessage != "") return;

		if (inputHscj.value != "") {
			zpcj += inputHscj.value * inputHscj.getAttribute("bl"); 
		}
	}

	// 不及格标红
	if (zpcj && zpcj < jgfsx)
		tr.style.background = failedColor;
	else
		tr.style.background = "";

	// 等级制的情况
	if (selectZpcj) {
		if (zpcj != null) {
			for (var i = 0; i < selectZpcj.options.length; i++) {
				var option = selectZpcj.options[i];
				var zxfsx = option.getAttribute("zxfsx");
				var zdfsx = option.getAttribute("zdfsx");

				// 跳过空项
				if (!zxfsx && !zdfsx)
					continue;

				// 小于最小分数线
				if (zxfsx && parseFloat(zxfsx) > zpcj.toFixed(1)) {
					continue; 
				}
				// 大于等于最大分数线
				if (zdfsx && zpcj.toFixed(1) >= parseFloat(zdfsx)) {
					continue;
				}
                
                selectZpcj.selectedIndex = i;
                
                inputXf.value = zpcj < jgfsx ? 0 : zxf;
                inputJd.value = zpcj < 60 ? 0 : ((zpcj - 50) / 10).toFixed(1);
                break;
			}
		} else {
			selectZpcj.selectedIndex = 0;
			inputXf.value = "";
			inputJd.value = "";
		}
	// 百分制的情况
	} else {
		if (zpcj != null) {
			inputZpcj.value = zpcj.toFixed(1);
			inputXf.value = zpcj < jgfsx ? 0 : zxf;
			inputJd.value = zpcj < 60 ? 0 : ((zpcj - 50) / 10).toFixed(1);
		} else {
			inputZpcj.value = "";
			inputXf.value = "";
			inputJd.value = "";
		}	
	}

}

/**
 * 监听按键，移动光标
 */
function move() {
	var key = event.keyCode;
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
		preventDefault(event);
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
 * 校验
 * @param obj
 * @returns
 */
function validate(event) {
	var obj = event.srcElement ? event.srcElement : event.target;
	var errorMessage = checkRules(obj.value);
	if (errorMessage != "") {
		obj.style.borderColor = "red";
		setTimeout(function() {obj.focus();}, 0);
		setTimeout(function() {$(obj).testRemind(errorMessage);}, 0);
	} else {
		if (obj.style.borderColor) obj.style.borderColor = "";
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
	var elements = cjjlTable.querySelectorAll("input[type='text'], select");
	for (var i = 0; i < elements.length; i++) {
		var element = elements[i];
		// 总评成绩根据录入方式处理
		if (element.name == "zpcj") {
			switch (obj.value) {
			case "1":
				element.parentNode.innerHTML = "<input type='text' name='zpcj' onfocus='this.select()' onblur='validate(event)'/>";
				break;
			case "2":
				var text = "<select name='zpcj' onchange='djChange(event)'><option value='' selected></option>";
				for (var j = 0; j < cjdj.length; j++) {
					var value = cjdj[j];
					text += "<option value='#djdm' zxfsx='#zxfsx' zdfsx='#zdfsx'>#djmc</option>"
						.replace("#djdm", value.djdm)
						.replace("#zxfsx", value.zxfsx)
						.replace("#zdfsx", value.zdfsx)
						.replace("#djmc", value.djmc);
				}
				text += "</select>";
				element.parentNode.innerHTML = text;
				break;
			default:
			}
			continue;
		}

		if (element.tagName == "SELECT") {
			element.selectedIndex = 0;
		} else {
			element.value = "";
		}
	}
}