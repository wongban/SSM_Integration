var appName = AdfPage.PAGE._baseResourceUrl;

var intervalID;
var lrrwTable; // 录入任务table
var cjjlTable; // 成绩记录table
var button;    // 按钮

var lrrw = null; // 录入任务，加载成绩记录时获取数据
var cjbz = null; // 成绩备注，在第一次加载成绩记录时获取数据
var cjdj = null; // 成绩等级，在第一次加载成绩记录时获取数据

/**
 * ie8 forEach支持
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

/**
 * 取消事件的默认动作，兼容IE
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
 * 获取ajax对象
 */
function getXmlhttp() {
    if (window.XMLHttpRequest) // code for IE7+, Firefox, Chrome, Opera, Safari
        return new XMLHttpRequest();
    else // code for IE6, IE5
        return new ActiveXObject("Microsoft.XMLHTTP");
}

$( document ).ready(function() {
    documentReady();
});

// 权限编号
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

    // 利用submit form实现h5自带验证功能
    // 由于ADF已经自动生成form标签，所以只能在其form添加submit监听
    // 不能自己生成新的form标签，form不支持嵌套
    var form = document.forms[0];
    form.setAttribute("onsubmit", "save(event)");

    // 学年学期
    var xmlhttp = getXmlhttp();
    xmlhttp.onreadystatechange = function() {
    	if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    		var json = xmlhttp.responseText;
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
	var timestmp = new Date().valueOf();
    xmlhttp.open("GET", appName + "/cjlrservlet?method=queryJsonXnxq&pt="+timestmp, true);
    xmlhttp.send();
}

/**
 * 根据学年学期获取录入任务
 * @returns
 */
function loadLrrw() {
	var select = document.getElementById("xnxqSelect");
	var dqxnxq = select.options[select.selectedIndex].value;

    var xmlhttp = getXmlhttp();
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var json = xmlhttp.responseText;
            var list = JSON.parse(json);

            // 删除现有数据
            lrrw = null;
            lrrwString.innerHTML = null;
            var rows = cjjlTable.rows;
            for (var i = rows.length - 1; i > 0; i--) {
            	cjjlTable.deleteRow(i);
            }
            rows = lrrwTable.rows;
            for (var i = rows.length - 1; i > 0; i--) {
            	lrrwTable.deleteRow(i);
            }

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

                var row = lrrwTable.querySelector("tbody").insertRow(-1);
                if (lrrw.shzt == "9") {
                	row.style.color = "green";
                }

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
	var timestmp = new Date().valueOf();
    xmlhttp.open("GET", appName + "/cjlrservlet?method=queryJsonLrrw&dqxnxq=" + dqxnxq+"&pt="+timestmp, true);
    xmlhttp.send();
}

/**
 * 鼠标点击tr事件
 * @param evt
 * @returns
 */
function selectTr(evt) {
    var el = evt.srcElement ? evt.srcElement : evt.target;
    if (el.tagName != "TD") return;
    var tr = el.parentNode;
    var table = tr.parentNode.parentNode;

    // 样式
    var currentTr = table.currentTr;
    if (currentTr != tr) {
    	if (currentTr != null) {
    		currentTr.style.backgroundColor = "";
    	}
    	table.currentTr = tr;
    	table.currentTr.style.backgroundColor = "#D9F3FF";
    }

    if (table.id == "lrrwTable") {
    	document.getElementById("autoSaveString").innerHTML = null;

		// 循环执行保存
		if (intervalID != null) clearInterval(intervalID);
		intervalID = setInterval(function() {
			if (!lrrw) return;
			if (lrrw.shzt == "9") return;
			var table = document.getElementById("cjjlTable");
			if (!table) return;
			var rows = table.rows;
			if (!rows) return;
			var cjjlLength = rows.length;
			if (cjjlLength < 2) return;
	
			// 封装成绩记录数据
			var cjjlArr = new Array(); // 成绩记录数组
			for (var index = 1; index < cjjlLength; index++) {
				var row = rows[index];

				var cjjl = new Object();        // 成绩记录对象
				cjjl["cjfxList"] = new Array(); // 成绩分项数组
	
				var elements = row.getElementsByTagName("input");
				for (var i = 0, j = elements.length; i < j; i++) {
					var input = elements[i];
					if (input.name != "hscj") {
						cjjl[input.name] = input.value;
					} else {
						// 成绩记录分项
						var cjfx = new Object();
						cjfx.zj = input.id;
						cjfx.hscj = input.value;
						
						cjjl["cjfxList"].push(cjfx);
					}
				}
	
				var elements = row.getElementsByTagName("select");
				for (var i = 0, j = elements.length; i < j; i++) {
					var select = elements[i];
					if (select.name == "zpcj") {
						cjjl["zpcj"] = select.options[select.selectedIndex].getAttribute("zdfsx");
						cjjl["cj"] = select.value;
						continue;
					}
					if (select.name == "cjbz") {
						cjjl["cjbz"] = select.value;
						continue;
					}
				}
	
				cjjlArr.push(cjjl);
				lrrw.cjjlList = cjjlArr;
			}
	
			var xmlhttp = getXmlhttp();
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    var json = xmlhttp.responseText;
                    var message = JSON.parse(json);
                    if (message.status) {
                    	document.getElementById("autoSaveString").innerHTML = "最后自动保存时间：" + new Date().toLocaleTimeString();
                    }
                }
            }
			var timestmp = new Date().valueOf();
			xmlhttp.open("POST", appName + "/cjlrservlet?method=save&pt="+timestmp, true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xmlhttp.send("json=" + JSON.stringify(lrrw));
		}, 120 * 1000);

    	var rwbh = lrrwTable.currentTr.querySelector("[name='rwbh']").value;
    	var shzt = lrrwTable.currentTr.querySelector("[name='shzt']").value;

		var button1 = document.getElementById("saveButton");
        var button2 = document.getElementById("submitButton");
        var button3 = document.getElementById("importCjjlButton");
        var button4 = document.getElementById("exportCjjlButton");
        var a = document.getElementById("fileA");
        if (shzt == "9") {
        	button1.disabled = true;
        	button1.style.backgroundColor = "#ccc";
        	button2.disabled = true;
        	button2.style.backgroundColor = "#ccc";
        	button4.disabled = false;
        	button4.style.backgroundColor = "";
			if (button3 != null) {
				button3.disabled = true;
				button3.style.backgroundColor = "#ccc";
			}
			if (a != null){
				a.disabled = true;
				a.style.backgroundColor = "#ccc";
			}
        } else {
        	button1.disabled = false;
        	button1.style.backgroundColor = "";
        	button2.disabled = false;
        	button2.style.backgroundColor = "";
        	button4.disabled = true;
        	button4.style.backgroundColor = "#ccc";
            if (button3 != null) {
				button3.disabled = false;
				button3.style.backgroundColor = "";
			}
			if (a != null) {
				a.disabled = false;
				a.style.backgroundColor = "";
			}
        }
    	loadCjjl(rwbh);
    }
}

/**
 * 根据任务编号获取成绩记录
 * @param tr
 * @returns
 */
function loadCjjl(rwbh) {
	if (rwbh == null) return;

    var xmlhttp = getXmlhttp();
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            var json = xmlhttp.responseText;
            var map = JSON.parse(json);
            if (map.status == 0) {//0：获取不到数据1：获取到数据
                alert(map.message);
                return;
            }

            // 成绩备注，在第一次加载成绩记录时获取数据
            if (cjbz == null) {
                var xmlhttp2 = getXmlhttp();
                xmlhttp2.onreadystatechange = function() {
                    if (xmlhttp2.readyState==4 && xmlhttp2.status==200) {
                        var json = xmlhttp2.responseText;
                        cjbz = JSON.parse(json);
                    }
                }
				var timestmp = new Date().valueOf();
                xmlhttp2.open("GET", appName + "/cjlrservlet?method=queryJsonCjbz&pt="+timestmp, false);
                xmlhttp2.send();
            }
            // 成绩等级，在第一次加载成绩记录时获取数据
            if (cjdj == null) {
                var xmlhttp3 = getXmlhttp();
                xmlhttp3.onreadystatechange = function() {
                    if (xmlhttp3.readyState==4 && xmlhttp3.status==200) {
                        var json = xmlhttp3.responseText;
                        cjdj = JSON.parse(json);
                    }
                }
				var timestmp1 = new Date().valueOf();
                xmlhttp3.open("GET", appName + "/cjlrservlet?method=queryJsonCjdj&pt="+timestmp1, false);
                xmlhttp3.send();
            }

            // 填充录入任务信息
            lrrw = map.lrrwBean;
            var lrrwStr = 
            	"教学班名称：#jxbmc " +
            	"课程名称：#kcmc " +
            	"学分：#zxf " +
            	"及格线：#jgfsx " +
            	"录入方式：" + 
                "<input type='radio' id='fs1' name='cjjlfs' value='1' onchange='lrfsChange(this)' #checked1/><label for='fs1'>百分制</label>" +
                "<input type='radio' id='fs2' name='cjjlfs' value='2' onchange='lrfsChange(this)' #checked2/><label for='fs2'>等级制</label>";
            var innerHTML = lrrwStr
            	.replace("#jxbmc", lrrw.jxbmc)
            	.replace("#kcbh", lrrw.kcbh)
            	.replace("#kcmc", lrrw.kcmc)
            	.replace("#kcsxmc", lrrw.kcsxmc)
            	.replace("#zxf", parseFloat(lrrw.zxf))
            	.replace("#jgfsx", lrrw.jgfsx)
            	.replace("#checked1", lrrw.cjjlfs == 1 ? "checked" : "")
            	.replace("#checked2", lrrw.cjjlfs == 2 ? "checked" : "");
            lrrw.fxList.forEach(function(fx, index, array) {
            	innerHTML += " #fxmc：#fxszbl".replace("#fxmc", fx.fxmc).replace("#fxszbl", parseFloat(fx.fxszbl));
            });
            document.getElementById("lrrwString").innerHTML = innerHTML;

            // 填充行
            var cjjlArr = lrrw.cjjlList;
            // 删除现有数据
            // cjjlTable.querySelector("tbody").innerHTML = null;
            var rows = cjjlTable.rows;
            for (var i = rows.length - 1; i > 0; i--) {
            	cjjlTable.deleteRow(i);
            }

            cjjlArr.forEach(function(cjjl, index, array) {

                var row = cjjlTable.querySelector("tbody").insertRow(-1);
                var hiddenText = "<input type='hidden' name='cjbh' value='#cjbh'/><input type='hidden' name='xh' value='#xh'/>"
                    			 .replace("#cjbh", cjjl.cjbh)
                    			 .replace("#xh", cjjl.xh);

                var xhCell = row.insertCell(0);
                var xmCell = row.insertCell(1);
                var xbCell = row.insertCell(2);
                var fxCell = row.insertCell(3);
                var zpCell = row.insertCell(4);
                var xfCell = row.insertCell(5);
                var jdCell = row.insertCell(6);
                var bzCell = row.insertCell(7);

                xhCell.innerHTML = hiddenText + cjjl.xh;
                xmCell.innerHTML = !cjjl.xm ? "" : cjjl.xm;
                xbCell.innerHTML = !cjjl.xbmc ? "" : cjjl.xbmc;
                xfCell.innerHTML = "<input type='number' name='xf' step='0.1' disabled='disabled' value='#xf' />".replace("#xf", cjjl.xf ? parseFloat(cjjl.xf) : "");
                jdCell.innerHTML = "<input type='number' name='jd' step='0.1' disabled='disabled' value='#jd' />".replace("#jd", cjjl.jd ? parseFloat(cjjl.jd) : "");

                // 成绩分项处理
                if (cjjl.cjfxList) {
                    var text = "";
                    cjjl.cjfxList.forEach(function(cjfx, index, array) {
                        text += "#fxmc <input type='number' min='0' max='100' step='0.1' id='#zj' name='hscj' value='#hscj' bl='#bl' onfocus='this.select()' /> "
                                .replace("#fxmc", cjfx.fxmc)
                                .replace("#zj",   cjfx.zj)
                                .replace("#hscj", cjfx.hscj ? parseFloat(cjfx.hscj) : "")
                                .replace("#bl",   cjfx.fxszbl);
                    });
                    fxCell.innerHTML = text;
                }

                // 总评成绩处理
                if (map.lrrwBean.cjjlfs != 2) {
                    zpCell.innerHTML = "<input type='number' min='0' max='100' step='0.1' name='zpcj' value='#zpcj' onfocus='this.select()' />"
                                       .replace("#zpcj", cjjl.zpcj ? parseFloat(cjjl.zpcj) : "");
                } else {
                    var text = "<select name='zpcj' onchange='djChange(event)'><option value=''></option>";
                    cjdj.forEach(function(value, index, array) {
                        text += "<option value='#djdm' zxfsx='#zxfsx' zdfsx='#zdfsx' #selected>#djmc</option>"
                                .replace("#selected", value.djdm == cjjl.cj ? "selected='selected'" : "")
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
                                .replace("#selected", value.bzdm == cjjl.cjbz ? "selected='selected'" : "")
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
    var timestmp = new Date().valueOf();
    xmlhttp.open("GET", appName + "/cjlrservlet?method=queryJsonCjjl&rwbh=" + rwbh+"&pt="+timestmp, true);
    xmlhttp.send();
    return status;
}


function save(evt) {
    // 取消事件的默认动作。只需要用到其表单验证功能
    preventDefault(evt);

    if (!lrrw) {
        alert("请先选择一个班级的课程。");
        return;
    }

    if (lrrw.shzt == "9") {
        alert("此班级课程已经审批通过。");
        return;
    }

    var rows = cjjlTable.rows;
    var cjjlLength = rows.length;
    if (cjjlLength < 2) {
        alert("此班级课程没有成绩记录。");
        return;
    }

    /**
     * 封装成绩记录数据
     */
    var cjjlArr = new Array(); // 成绩记录数组
    for (var index = 1; index < cjjlLength; index++) {
    	var row = rows[index];

        var cjjl = new Object();        // 成绩记录对象
        cjjl["cjfxList"] = new Array(); // 成绩分项数组

        var elements = row.getElementsByTagName("input");
        for (var i = 0, j = elements.length; i < j; i++) {
        	var input = elements[i];
            if (input.name != "hscj") {
                cjjl[input.name] = input.value;
            } else {
            	// 成绩记录分项
            	var cjfx = new Object();
            	cjfx.zj = input.id;
            	cjfx.hscj = input.value;
            	
            	cjjl["cjfxList"].push(cjfx);
            }
        }

        var elements = row.getElementsByTagName("select");
        for (var i = 0, j = elements.length; i < j; i++) {
        	var select = elements[i];
            if (select.name == "zpcj") {
                cjjl["zpcj"] = select.options[select.selectedIndex].getAttribute("zdfsx");
                cjjl["cj"] = select.value;
                continue;
            }
            if (select.name == "cjbz") {
                cjjl["cjbz"] = select.value;
                continue;
            }
        }

        cjjlArr.push(cjjl);
        lrrw.cjjlList = cjjlArr;
    }

    var action = null;
    switch (button.id) {
    case "saveButton":
    	action = "save";
    	break;
    case "submitButton":
    	var r = confirm("提交后无法在录入时间外进行修改，请谨慎操作！");
        if (!r) return;
        action = "submit";
    	break;
    }

    var xmlhttp = getXmlhttp();
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
	var timestmp = new Date().valueOf();
    xmlhttp.open("POST", appName + "/cjlrservlet?method=" + action+"&pt="+timestmp, true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send("json=" + JSON.stringify(lrrw));
}

function exportCjjl() {
	var timestmp = new Date().valueOf();
    window.location = appName + "/cjlrservlet?method=exportCjjl&rwbh=" + lrrw.rwbh+"&pt="+timestmp;
}

function importCjjl() {
	var form = document.forms[0];
	var cache = form.enctype;
	form.enctype = "multipart/form-data";
	$(form).ajaxSubmit({
		url: appName + "/cjlrservlet?method=importCjjl",
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

}

function required(ele) {
    button = ele;
    var arr = document.getElementsByName("zpcj");
    for (var i = 0; i < arr.length; i++) {
        arr[i].required = true;
    }
}

function requiredOff(ele) {
    button = ele;
    var arr = document.getElementsByName("zpcj");
    for (var i = 0; i < arr.length; i++) {
        arr[i].required = false;
    }
}

/**
 * 监听等级成绩
 */
function djChange(evt) {
    var el = evt.srcElement ? evt.srcElement : evt.target;
    var tr = el.parentNode.parentNode;

    var selectZpcj = tr.querySelector("[name='zpcj']");
    var selectCjbz = tr.querySelector("[name='cjbz'");
    var inputXf = tr.querySelector("[name='xf']");
    var inputJd = tr.querySelector("[name='jd']");

    // 成绩备注选中则不计算
    if (selectCjbz.options[selectCjbz.selectedIndex].value) return;
    // 等级成绩选中空的情况
    if (!selectZpcj.options[selectZpcj.selectedIndex].value) {
        inputXf.value = null;
        inputJd.value = null;
        return;
    }

    var zpcj = parseFloat(selectZpcj.options[selectZpcj.selectedIndex].getAttribute("zdfsx"));
    var zxf = lrrw.zxf;
    var jgfsx = lrrw.jgfsx;

    inputXf.value = zpcj < jgfsx ? 0 : zxf;
    inputJd.value = zpcj < 60 ? 0 : ((zpcj - 50) / 10).toFixed(1);
}

/**
 * 监听成绩备注
 */
function bzChange(evt) {
    var el = evt.srcElement ? evt.srcElement : evt.target;
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
                zpcj = parseFloat(optionZpcj.getAttribute("zdfsx"));
            }
        } else {
            inputHscjArr.forEach(function(inputHscj, index, array) {
                if (inputHscj.value != "") {
                    zpcj += inputHscj.value * inputHscj.getAttribute("bl"); 
                }
            });
        }
    }

    if (zpcj != null) zpcj = zpcj.toFixed(1);

    if (inputZpcj) inputZpcj.value = zpcj;
    if (zpcj != null) {
        inputXf.value = zpcj < jgfsx ? 0 : zxf;
        inputJd.value = zpcj < 60 ? 0 : ((zpcj - 50) / 10).toFixed(1);
    } else {
        inputXf.value = null;
        inputJd.value = null;
    }
}

/**
 * 监听按键，计算成绩
 */
function keyup(evt) {
    var key = evt.keyCode;
    if (37 <= key && key <= 40) return;

    var el = evt.srcElement ? evt.srcElement : evt.target;
    var tr = el.parentNode.parentNode;

    var inputZpcj = tr.querySelector("input[name='zpcj']");
    var inputXf = tr.querySelector("[name='xf']");
    var inputJd = tr.querySelector("[name='jd']");
    var selectZpcj = tr.querySelector("select[name='zpcj']");
    var selectCjbz = tr.querySelector("[name='cjbz']");

    var inputHscjArr = tr.querySelectorAll("[name='hscj']");

    var optionCjbz = selectCjbz.options[selectCjbz.selectedIndex];
    if (optionCjbz.value != "" || selectZpcj) {
        return;
    }

    var zpcj = null;
    var zxf = parseFloat(lrrw.zxf);
    var jgfsx = parseFloat(lrrw.jgfsx);

    if (el.name != "zpcj") {
        inputHscjArr.forEach(function (inputHscj, index, array) {
            if (inputHscj.value != "") {
                zpcj += inputHscj.value * inputHscj.getAttribute("bl");
            }
        });
        if (zpcj != null) {
            inputZpcj.value = zpcj.toFixed(1);
            inputXf.value = zpcj < jgfsx ? 0 : zxf;
            inputJd.value = zpcj < 60 ? 0 : ((zpcj - 50) / 10).toFixed(1);
        } else {
            inputZpcj.value = null;
            inputXf.value = null;
            inputJd.value = null;
        }
    } else {
        if (inputZpcj.value != null && inputZpcj.value != "") {
        	zpcj = parseFloat(inputZpcj.value);
            inputXf.value = zpcj < jgfsx ? 0 : zxf;
            inputJd.value = zpcj < 60 ? 0 : ((zpcj - 50) / 10).toFixed(1);
        } else {
            inputXf.value = null;
            inputJd.value = null;
        }
    }
}

/**
 * 监听按键，移动光标
 */
function keydown(evt) {
	var key = evt.keyCode;
	if (key < 37 || 40 < key) return;

	var input = evt.srcElement ? evt.srcElement : evt.target;
    var tr = input.parentNode.parentNode;

    var rowIndex = tr.rowIndex; // 当前行索引
    var inputs = tr.getElementsByTagName("input");
    var inputIndex;

    for (inputIndex = 0; inputIndex < inputs.length; inputIndex++) {
        if (input === inputs[inputIndex]) break;
    }

    var index = null;
    switch (evt.keyCode) {// 左上右下
    case 37:
        break;
    case 38:
    	preventDefault(evt);
    	if (rowIndex > 1) {
            cjjlTable.rows[rowIndex-1].getElementsByTagName("input")[inputIndex].focus();
    	}
        break;
    case 39:
        break;
    case 40:
    	preventDefault(evt);
    	if (rowIndex < cjjlTable.rows.length-1) {
            cjjlTable.rows[rowIndex+1].getElementsByTagName("input")[inputIndex].focus();
    	}
        break;
    }

}

/**
 * 录入方式改变
 * @param obj
 * @returns
 */
function lrfsChange(obj) {
	lrrw.cjjlfs = obj.value;

	var elements = cjjlTable.getElementsByTagName("*");
	for (var i = 0; i < elements.length; i++) {
		var element = elements[i];
		if (element.type == "hidden") continue;
		if (element.tagName != "INPUT" && element.tagName != "SELECT") continue;
		if (element.name == "zpcj") {
			switch (obj.value) {
			case "1":
				var text = "<input type='number' min='0' max='100' step='0.1' name='zpcj' onfocus='this.select()'/>";
				element.parentNode.innerHTML = text;
				break;
			case "2":
				var text = "<select name='zpcj' onchange='djChange(event)'><option value=''></option>";
				cjdj.forEach(function(value) {
					text += "<option value='#djdm' zxfsx='#zxfsx' zdfsx='#zdfsx'>#djmc</option>"
						.replace("#djdm", value.djdm)
						.replace("#zxfsx", value.zxfsx)
						.replace("#zdfsx", value.zdfsx)
						.replace("#djmc", value.djmc);
				});
				text += "</select>";
				element.parentNode.innerHTML = text;
				break;
			}
		} else if (element.tagName == "SELECT") {
			element.selectedIndex = 0;
		} else {
			element.value = "";
		}
	}

}