// require jquery
// require jquery ui
// require common.js
// require wx.js

var wxEx = {};
(function(){

    wxEx.window = {};
    wxEx.window.close = function() {
    	wxcom.window.close();
    };
    
    wxEx.window.subwin = {};
    wxEx.window.subwin.close = function(){
    	wxcom.window.close();
    };
    
    wxEx.window.close.ajax = function(gamenIds){
    	var url = "close";
    	var data = {
    			"gamenIds" : gamenIds
    			};
    	$.ajax({
            url:url,
            data:data,
            dataType:"json",
            type:"GET",
            async:false,
            success:function (data, status) {
            },
   	 		error:function(){
   	 			alert("NG");
   	 		}
   	 	});
    };
    
    wxEx.window.openModal = function(url, width, height) {
    	var next = wxcom.util.contextPath() + url;
    	var w = (width == undefined) ? "900px" : width;
    	var h = (height == undefined) ? "768px" : height;
    	var option = "resizable; center;" + " dialogWidth:" + w + "; dialogHeight:" + h;
    	var ret = window.showModalDialog(next, window, option);
    	return ret;
    };

    wxEx.window.tmp = {};
    
    wxEx.form = {};  
    wxEx.form.csvDownload = {};
    wxEx.form.csvDownload = function(formId, url){
    	var message = $("#csvDownMsg").attr("data");
        var mObj = wxEx.form.message.getMessage(message, null);
        wx.form.confirm.showDialog(mObj, function () {
        	wxEx.form.csvDownload.exec(formId, url);
        });
    }
    wxEx.form.csvDownload.exec = function(formId, url){
    	var formId = (formId === undefined) ? "#listForm" : "#" + formId;
    	var nextUrl = (url === undefined) ? "makeCsvFile" : url;
    	var request = {};
    	
		var form = $(formId);
		$(form.serializeArray()).each(function(i, v){
			request[v.name] = v.value;
		});
		$.ajax({
            url:nextUrl,
            data:request,
            dataType:"json",
            type:"POST",
            async:true,
            success:function (data, status) {
            	wx.form.action.closeDialog();
            	var success = data["makeCsvfileSuccess"];
            	var msgs = data["ajaxMessage"];
            	for (var i in msgs) {
            		var msg = msgs[i].split(":");
            		wxEx.form.message.addMessage(msg[0], msg[1]);
            		wxEx.form.message.showMessage();
            	}
            	
            	if (success === true) {
            		var fpath = data["tmpFilepath"];
            		var fname = data["csvFilename"];
            		var dF = $(formId);
            		$("#tmpFilepath").val(fpath);
            		$("#csvFilename").val(fname);
            		var tmpUrl = dF.attr("action");
            		dF.attr("action", "csvDownload");
            		dF.submit();
            		dF.attr("action", tmpUrl);
            	}
            },
   	 		error:function(){
   	 			wx.form.action.closeDialog();
   	 			alert("予期せぬエラーが発生しました。");
   	 		}
   	 	});
    };
    
    wxEx.form.action = function(formId, id, value,e){
    	// 型チェック
    	wxEx.validate.all(formId);
    	// 必須チェック
    	var fid = event.srcElement.id;
    	if (fid) {
    		if ($("#" + fid).hasClass("required")) {
    			$("." + fid).each(function(){
        			wxEx.validate.required($(this));
        		});
    		}
    	}
    	// エラー有無チェック
    	var c = $("#" + formId).find(".wx-client-error");
    	if (c && c.length > 0) {
    		new jQuery.Event(e).preventDefault();
    		return false;
    	}
    	wx.form.action(formId, id, value);
    };
    
    wxEx.form.confirm = function(msgId, args, formId, action, e) {
    	// 型チェック
    	wxEx.validate.all(formId);
    	// 必須チェック
    	var id = event.srcElement.id;
    	if (id) {
    		if ($("#" + id).hasClass("required")) {
    			$("." + id).each(function(){
        			wxEx.validate.required($(this));
        		});
    		}
    	}
    	// エラー有無チェック
    	var c = $("#" + formId).find(".wx-client-error");
    	if (c && c.length > 0) {
    		new jQuery.Event(e).preventDefault();
    		return false;
    	}
    	wxEx.window.saveChildren(wxEx.window.children);
    	wx.form.confirm(msgId, args, formId, action, e);
    }
    
    
    wxEx.form.message = {};
    wxEx.form.message.clientMessage = function(msgId, args){
        var message = "";
        message = $("#" + wxEx.data.attribute.MSG_CONT_AREA).data(msgId);
        
        var mObj = wxEx.form.message.getMessage(message, args);
        wxEx.form.message.addMessage(mObj["msgId"], mObj["message"]);
        wxEx.form.message.showMessage();
    };
    
    wxEx.form.message.addMessage = function(id, message) {
        var type = id.substring(id.length - 1);
        
        var tableE = $('#messageArea table:first tbody:first');
        
        var style = "wx-messageInfo";
        switch (type) {
        case "E":
            style = "wx-messageErr";
            break;
        case "C":
            style = "wx-messageConfirm";
            break;
        case "I":
            style = "wx-messageInfo";
        default:
            break;
        }
        var messagetr = "<tr class='"+style+"'><td class='wx-w100 wx-center'>" 
            + id + "</td><td class= 'wx-w400'>" 
            + message + "</td></tr>";

        tableE.append(messagetr);
    };
    
    wxEx.form.message.showMessage = function(){
        $('#messageArea').dialog({
            resizable:true,
            draggable:true,
            closeText : '',
            height:250,
            width:500,
            title:'メッセージ',
            modal:true,
            closeOnEscape:false,
            position : {
                my : 'center',
                at : 'center',
                of : document
            },
            buttons:{
                "閉じる":{
                    click:function () {
                            $(this).dialog("close");
                        },
                    text:"閉じる",
                    id:"messageDialogClose"
                }
            },
            close:function(){
            	var tableE = $('#messageArea table:first tbody:first');
                tableE.text("");
            },
            open:function () {
                $("#messageDialogClose").focus();
                // メッセージボタンスタイル設定
                $('.ui-dialog-buttonset :button').each(function() { 
                    $(this).addClass('wx-msgDialog');
                    $(this).removeClass(
                                    "ui-widget ui-corner-all ui-button-text-only ui-button ui-state-default");
                    $(this).hover(function(e) {
                        $(this).removeClass("ui-state-hover ui-widget ui-corner-all ui-button-text-only ui-button");
                    });
                    $(this).mousedown(function() {
                        $(this).removeClass('ui-state-active ui-widget ui-corner-all ui-button-text-only ui-button');
                    });
                    $(this).focus(function() {
                        $(this).removeClass('ui-state-focus ui-widget ui-corner-all ui-button-text-only ui-button');
                    });
                });
                
                // ダイアログのディフォルトフォント設定を削除
                $('.ui-dialog').each(function() { 
                    $(this).removeClass("ui-widget");
                });
                
                // メッセージタイトルスタイル設定
                $('.ui-dialog-title').each(function() {
                    $(this).addClass("wx-msgTitlebar");
                });
                $('.ui-dialog-titlebar').each(function() {
                    $(this).addClass("wx-msgDialogTitlebar");	
                });
            }
        });
    };
    
    wxEx.form.message.init = function() {
    	var tableE = $('#messageArea table:first tbody:first');
    	var type = tableE.find(".wx-messageConfirm");
    	if (jQuery.trim(tableE.text()).length != 0) {
    		if (type.length != 0) {
    			wxEx.form.message.confirmMessag(type);
    		} else {
    			wxEx.form.message.showMessage();
    		}
    	}
    }
    
    wxEx.form.message.confirmMessag = function(type){
    	var formId = $("#confrimArea").data("form");
    	var action = $("#confrimArea").data("action");
    	var confirmFlg = $("#confrimArea").data("confirm-flg");
    	var messageId = type.children("td").eq(0).text();
    	confirmFlg = ";" + messageId + ":y";
    	wx.form.confirm.showDialog(null,
    			function(){
    				$("#action").val(action);
    				$("#confirmFlg").val(confirmFlg);
    				wxcom.form.submit(formId);
    			},
    			function(){
    				$("#confirmFlg").val("");
    				return false;
    			});
    };
    
    wxEx.form.message.getMessage = function(message, args){
        var i = 0;
        var repval;
        while(message.indexOf('\{' +  String(i) + "\}") > 0) {
            repval = "";
            if (args) {
                if ($.isArray(args)) {
                    if (i < args.length) {
                        repval = args[i];
                    }
                } else {
                    if (i == 0) {
                        repval = args;
                    }
                }
            }
            message = message.replace('\{' + String(i) + '\}', repval);
        }
        var msgId = message.substring(0, message.indexOf(":"));
        var msg = message.substring(message.indexOf(":") + 1);
        var msgObj = {
        		"msgId" : msgId,
        		"message" : msg
        };
        
        return msgObj;
    };
    
    // バリデーション
    wxEx.validate = {};
    wxEx.validate.all = function(formId){
    	var select = $("#" + formId).find("[validate]");
    	// 項目取得
    	select.each(function(){
    		var type = $(this).attr("validate");
    		switch(type){
    		case "wx-date":
    		case "wx-yymm":
    		case "wx-yyyy":
    			$(this).focus();
    			$(this).blur();
    			break;
    		default :
    			$(this).blur();
    			break;
    		}
    	});
    	
    };
    
    wxEx.validate.addError = function(element) {    	
        if (element.parent()) {
            // クラス属性に、「wx-error」を追加する
            element.parent().addClass("wx-client-error");
            // エラー項目は、エラーメッセージとの関係を立てる
            var errId = element.parent().attr("errid");
            if (errId){
            	if (errId.indexOf(",") != -1) {
            		var ids = errId.split(",");
            		for (var i = 0; i < ids.length;i++) {
            			if (ids[i] === errId) {
            				return;
            			}
            		}
            	} else {
            		if (errId === element.attr("id")) {
            			return;
            		}
            	}
            	errId = errId + "," + element.attr("id");
            	element.parent().attr("errid", errId);
            } else {
                element.parent().attr("errid", element.attr("id"));
            }
        }
    };
    
    wxEx.validate.clearError = function(element) {
        // コントロール、配置されているセル、配置されているセルのエラータイプ属性が存在する場合
        if (element.parent() && element.parent().attr("errid")) {
            // エラー項目は、エラーメッセージとの関係を取得
            var errid = element.parent().attr("errid");

            var erridar = errid.split(",");
            var newErrid;
            if (erridar instanceof Array) {
                // 複数項目の場合
                for ( var i in erridar) {
                    // 当該項目のidだけを削除する
                    if (erridar[i] === element.attr("id")||erridar[i] === "") {
                        continue;
                    }
                    if(newErrid==null) {
                        newErrid=erridar[i];
                    } else {
                         newErrid=newErrid+","+erridar[i];
                    }
                }
                // 他の項目がエラーの場合
                if(newErrid != null) {
                    element.parent().attr("errid", newErrid);
                } else {
                    // セルのクラス属性から「wx-error」を削除する
                    element.parent().removeClass("wx-client-error");
                    element.parent().removeAttr("errid");
                }
            } else { 
                if (element.attr("id") === erridar) {
                     // セルのクラス属性から「wx-error」を削除する
                    element.parent().removeClass("wx-client-error");
                    element.parent().removeAttr("errid");
                }
            }
        }
    };
    
    wxEx.validate.getItemName = function(element){
        // 項目名の取得
    	var name = element.attr('itemName');
    	if (!name || name.length == 0) {
    		name = $("label[for='"+ element.attr("id") +"']").text();
    	}
    	
        return name;
    };

    // バインド
    wxEx.autobind = {};

    //日付
    wxEx.validate.date = {};
    wxEx.validate.date = function(selector) {
        $(selector).each(function(){
            $(this).focus(function(){
            	wxEx.validate.date.getFocus($(this));
            });
            $(this).blur(function(){
                wxEx.validate.date.lostFocus($(this));
            });
            wxEx.validate.date.lostFocus($(this));
        });
    };
    
    wxEx.validate.date.lostFocus = function(element){
        // エラーをクリア
        wxEx.validate.clearError(element);
        var sDate = element.val().trim();
        if (sDate.length == 0) {
        	 $('[ownerid=' + $(element).attr('id') + ']').val(sDate);
            return true;
        }
        // 日付情報取得
        var dateinfo = wxEx.validate.date.getDateinfo(sDate);
        if (dateinfo == null) {
        	 // エラー
            var itemName = wxEx.validate.getItemName(element);
            wxEx.form.message.clientMessage(wxEx.data.attribute.MSG_ID_PATTEN, itemName);
            wxEx.validate.addError(element);
            return false;
        }
        var dateType = wxEx.data.params.DATE_TYPE;
        var val;
        if (dateType == 1) {
            // 西暦モード(YYYY.MM.DD)
            val = dateinfo.sYear + "." + dateinfo.month + "." + dateinfo.day;
        } else {
            // 和暦モード(0)
            var option = element.attr("option");
            if (option == 'number') {
                // 数値和暦(1234)/yy/mm/dd
                val = dateinfo.intGengo + "/" + dateinfo.wYear + "/" + dateinfo.month + "/" + dateinfo.day;
            } else {
                // 和暦記号(MTSH).YY.MM.DD
                val = dateinfo.strGengo + "." + dateinfo.wYear + "." + dateinfo.month + "." + dateinfo.day;
            }
        }
        element.val(val);
        // 送付セット
        var hidden = dateinfo.sYear + "/" + dateinfo.month + "/" + dateinfo.day;
        $('[ownerid=' + $(element).attr('id') + ']').val(hidden);
        return true;
    };
    
    // 入力情報から日付情報を生成
    wxEx.validate.date.getDateinfo = function(sDate) {
        var intGengo = -1; // 元号数字      
        var strGengo = ""; // 元号アルファベット        
        var sYear = ""; // 西暦年       
        var wYear = ""; // 和暦年        
        var month = ""; // 月        
        var day = "";  // 日
        
        // 一番目文字がアルファベットの場合(和暦)
        if (sDate.substring(0, 1).match("[a-z,A-Z]")) {
            // 元号情報を取得
            var gengoInfo = wxEx.util.gengoSearch(sDate.substring(0, 1));
            if (gengoInfo == null) {
                return null;
            }
            intGengo = gengoInfo.intGengo;
            strGengo = gengoInfo.strGengo;
            
            if (sDate.substring(1, 2) == "/") {
            	// (G/YY/MM/DD)
            	var parts = sDate.split("/");
            	if(parts.length != 4) {
            		return null;
            	}
                // 和暦年
                if (parts[1].length > 2) {
                    return null;
                }
                wYear = ("00" + parts[1]).slice(-2);
                // 西暦年
                sYear = String(Number(gengoInfo.frmGengo.substring(0, 4)) + Number(parts[1]) - 1);
                sYear = ("0000" + sYear).slice(-4);
                // 月
                if (parts[2].length > 2) {
                    return null;
                }
                month = ("00" + parts[2]).slice(-2);
                // 日
                if (parts[3].length > 2) {
                    return null;
                }
                day = ("00" + parts[3]).slice(-2);
                
            } else if (sDate.indexOf("/") > -1) {
                // (GYY/MM/DD)
            	var parts = sDate.split("/");
            	if (parts.length != 3) {
            		return null;
            	}
            	if (parts[0].length != 3) {
            		return null;
            	}
                wYear = ("00" + parts[0].substring(1)).slice(-2);
                // 西暦年
				sYear = String(Number(gengoInfo.frmGengo.substring(0, 4)) + Number(wYear) -1);
                sYear = ("0000" + sYear).slice(-4);
                // 月
                if (parts[1].length > 2) {
                	return null;
                }
                month = ("00" + parts[1]).slice(-2);
                // 日
                if (parts[2].length > 2) {
                        return null;
                }
                day = ("00" + parts[2]).slice(-2);
            } else {
            	//GYYMMDD
                if (sDate.length != 7) {
                    return null;
                }
                // 和暦年
                wYear = sDate.substring(1, 3);
                // 西暦年
                sYear = ("0000" + (Number(gengoInfo.frmGengo.substring(0, 4)) + Number(wYear) - 1)).slice(-4);
                // 月
                month = sDate.substring(3, 5);
                // 日
                day = sDate.substring(5, 7);
            }
        } else if (sDate.substring(0, 1).match("[0-9]")) {
        	// １文字目が数字
            // 二番目文字は"/"： 和暦番号(1234)+"/"+和暦年+"/"+月+"/"+日
            if (sDate.substring(1, 2) == "/") {
            	// 和暦数値/YY/MM/DD
                // 元号情報を取得
                var gengoInfo = wxEx.util.gengoSearch(sDate.substring(0, 1));
                if (gengoInfo == null) {
                    return null;
                }
                intGengo = gengoInfo.intGengo;
                strGengo = gengoInfo.strGengo;
                var parts = sDate.split("/");
                if (parts.length != 4) {
                	return null;
                }
                // 和暦年
                if (parts[1].length > 2) {
                    return null;
                }
                wYear = ("00" + parts[1]).slice(-2);
                // 西暦年
                sYear = ("0000" + (Number(gengoInfo.frmGengo.substring(0, 4)) + Number(wYear) - 1)).slice(-4);
                // 月
                if (parts[2].length > 2) {
                    return null;
                }
                month = ("00" + parts[2]).slice(-2);
                // 日
                if (parts[3].length > 2) {
                    return null;
                }
                day = ("00" + parts[3]).slice(-2);

            } else {
            	// 西暦
            	var parts;            	
            	if (sDate.indexOf("/") != -1) {
            		// 区切り文字が"/" 
            		//YYYY/MM/DD or YY/MM/DD
            		parts = sDate.split("/");
            	} else if (sDate.indexOf("-") != -1){
            		// 区切り文字が"-"
            		//YYYY-MM-DD or YY-MM-DD
            		parts = sDate.split("-");
            	} else if (sDate.length == 8) {
            		// YYYYMMDD
            		parts = new Array(3);
            		parts[0] = sDate.substring(0, 4);
            		parts[1] = sDate.substring(4, 6);
            		parts[2] = sDate.substring(6, 8);
            	} else if (sDate.length == 6) {
            		// YYMMDD
            		parts = new Array(3);
            		parts[0] = sDate.substring(0, 2);
            		parts[1] = sDate.substring(2, 4);
            		parts[2] = sDate.substring(4, 6);
            	} else {
            		// 不正文字
            		return null;
            	}
            	if(parts.length != 3) {
            		return null;
            	}
            	
            	// 西暦（年）
            	if (parts[0].length == 4){
            		sYear = parts[0];
            	} else if (parts[0].length == 2) {
            		sYear = "20" + parts[0];
            	} else {
            		return null;
            	}
            	// 月
            	if (parts[1].length > 2) {
            		return null;
            	}
            	month = ("00" + parts[1]).slice(-2);
            	// 日
            	if (parts[2].length > 2) {
            		return null;
            	}
            	day = ("00" + parts[2]).slice(-2);           	
                // 元号情報を取得
                var gengoInfo = wxEx.util.gengoSearch(sYear + month + day);
                if (gengoInfo == null) {
                    return null;
                }
                // 元号数字
                intGengo = gengoInfo.intGengo;
                // 元号アルファベット
                strGengo = gengoInfo.strGengo;
                // 和暦年
                wYear = String(Number(sYear) - Number(gengoInfo.frmGengo.substring(0, 4)) + 1);
                wYear = ("00" + wYear).slice(-2);
            }
        } else {
        	// 上記以外の場合
            return null;
        }
        // 編集結果
        var dateinfo = {
                "intGengo" : intGengo,
                "strGengo" : strGengo,
                "wYear" : wYear,
                "sYear" : sYear,
                "month" : month,
                "day" : day
        };
        // 日付存在性チェック
        if (wxEx.util.dateinfoChk(dateinfo)) {
            return dateinfo;
        }
        return null;
    };
 
    wxEx.validate.date.getFocus = function(element){
    	var val = element.val().trim();
    	while(true) {
    		if (val.indexOf(".") == -1) {
    			break;
    		}
    		val = val.replace(".", "");
    	}
    	element.val(val);
    	element.select();
    };
    
    wxEx.autobind.date = function(className) {
        var selector = (className === undefined) ? 'wx-date' : className;
        selector = '[validate="' + selector + '"]';
        wxEx.autobind.addHidden(selector);
        wxEx.validate.date(selector);
    };
    
    // 年月
    wxEx.validate.yymm = {};
    wxEx.validate.yymm = function(selector){
        $(selector).each(function(){
            $(this).focus(function(){
            	wxEx.validate.date.getFocus($(this));
            });
            $(this).blur(function(){
                wxEx.validate.yymm.lostFocus($(this));
            });
            wxEx.validate.yymm.lostFocus($(this));
        });
        
    };
    
    wxEx.validate.yymm.lostFocus = function(element) {
        // エラーをクリア
        wxEx.validate.clearError(element);
        var sDate = element.val().trim();
        if (sDate.length == 0) {
        	$('[ownerid=' + $(element).attr('id') + ']').val(sDate);
            return true;
        }
        // 年月情報取得
        var dateinfo = wxEx.validate.yymm.getYymminfo(sDate);
        if (dateinfo == null) {
        	 // エラー
        	var itemName = wxEx.validate.getItemName(element);
            wxEx.form.message.clientMessage(wxEx.data.attribute.MSG_ID_PATTEN, itemName);
            wxEx.validate.addError(element);
            return false;
        }
        
        var dateType = wxEx.data.params.DATE_TYPE;
        if (dateType == 1) {
            // 西暦モード（1) YYYY.MM
            element.val(dateinfo.sYear + "." + dateinfo.month);
        } else {
            var option = element.attr("option");
            // 数値和暦
            if (option == 'number') {
                // 和暦番号(1234)+"/"+和暦年+"/"+月２桁
                element.val(dateinfo.intGengo + "/" + dateinfo.wYear + "/" + dateinfo.month);
            } else {
                // 和暦記号(MTSH)+"."+和暦年2桁+"."+月2桁
                element.val(dateinfo.strGengo + "." + dateinfo.wYear + "." + dateinfo.month);
            }
        }
        // 送付セット
        var hidden = dateinfo.sYear + "/" + dateinfo.month;
        $('[ownerid=' + $(element).attr('id') + ']').val(hidden);
        return true;
    };
    
    // 年月情報を取得する
    wxEx.validate.yymm.getYymminfo = function(sDate) {
    	// 編集結果
        var dateinfo = null;
        if (sDate.substring(0, 1).match("[a-z,A-Z]")) {
        	// 一番目文字がアルファベット
            // GYY/MM or G/YY/MM
            if (sDate.indexOf("/") > -1) {
                dateinfo = wxEx.validate.date.getDateinfo(sDate + "/01");
            } else {
            	//GYYMM
                dateinfo = wxEx.validate.date.getDateinfo(sDate + "01");
            }
        } else if (sDate.substring(0, 1).match("[0-9]")) {
            // g/YY/MM or gYY/MM
            if ((sDate.substring(1, 2) == "/") || (sDate.substring(3, 4) == "/")) {
                dateinfo = wxEx.validate.date.getDateinfo(sDate + "/01");
            } else {
                // "/"がある： 西暦年(2桁or4桁)+"/"+月(年2桁の場合は20XX年に変換)
                if (sDate.indexOf("/") != -1) {
                	// YYYY/MM or YY/MM
                    dateinfo = wxEx.validate.date.getDateinfo(sDate + "/01");
                } else if (sDate.indexOf("-") != -1) {
                	// YYYY-MM or YY-MM
                    dateinfo = wxEx.validate.date.getDateinfo(sDate + "-01");
                } else {
                	// YYYYMM or YYMM
                    dateinfo = wxEx.validate.date.getDateinfo(sDate + "01");
                }
            }
        }
        return dateinfo;
    };
    
    wxEx.autobind.yymm = function(className){
        var selector = (className === undefined) ? 'wx-yymm' : className;
        selector = '[validate="' + selector + '"]';
        wxEx.autobind.addHidden(selector);
        wxEx.validate.yymm(selector);
    };
    
    // 年
    wxEx.validate.yyyy = {};
    wxEx.validate.yyyy = function(selector){
        $(selector).each(function(){
            $(this).focus(function(){
            	wxEx.validate.date.getFocus($(this));
            });
            $(this).blur(function(){
                wxEx.validate.yyyy.lostFocus($(this));
            });
            wxEx.validate.yyyy.lostFocus($(this));
        });
    };
    
    // 年
    wxEx.validate.yyyy.lostFocus = function(element) {
        // エラーをクリア
        wxEx.validate.clearError(element);
        var sDate = element.val().trim();
        // 空白チェック
        if (sDate.length == 0) {
        	$('[ownerid=' + $(element).attr('id') + ']').val(sDate);
            return true;
        }
        // 年度情報取得
        sDate = sDate + "/01/01";
        var dateinfo = wxEx.validate.date.getDateinfo(sDate);
        if (dateinfo == null) {
        	// エラー
            var itemName = wxEx.validate.getItemName(element);
            wxEx.form.message.clientMessage(wxEx.data.attribute.MSG_ID_PATTEN, itemName);
            wxEx.validate.addError(element);
        	return false;
        }
        
        var dateType = wxEx.data.params.DATE_TYPE;
        if (dateType == 1) {
            // 西暦モード YYYY
            element.val(dateinfo.sYear);
        } else {
        	// 和暦モード
            var option = element.attr("option");
            // 数値和暦
            if (option == 'number') {
                // g/YY
                element.val(dateinfo.intGengo + "/" + dateinfo.wYear);
            } else {
                // G.YY
                element.val(dateinfo.strGengo + "." + dateinfo.wYear);
            }
        }
        // 送付セット
        var hidden = dateinfo.sYear;
        $('[ownerid=' + $(element).attr('id') + ']').val(hidden);
        return true;
    };
    
    wxEx.autobind.yyyy = function(className) {
        var selector = (className === undefined) ? 'wx-yyyy' : className;
        selector = '[validate="' + selector + '"]';
        wxEx.autobind.addHidden(selector);
        wxEx.validate.yyyy(selector);
    };
    
    wxEx.validate.kngk = function(selector) {
        $(selector).each(function(){
            $(this).focus(function(){
                wxEx.validate.number.getFocus($(this));
                $(this).select();
            });
            $(this).blur(function(){
            	wxEx.validate.number.lostFocus($(this));
            });
            // キー押下イベント設定
            $(this).keypress(function(e) {
                return wxEx.validate.number.keyPress($(this), e);
            });
            // 値変更イベント設定
            $(this).change(function(e) {
            	wxEx.validate.number.change($(this));
            });
            wxEx.validate.number.lostFocus($(this));
        });
    };
    
    wxEx.autobind.kngk = function(className) {
        var selector = (className === undefined) ? 'wx-kngk' : className;
        selector = '[validate="' + selector + '"]';
        wxEx.autobind.addHidden(selector);
        wxEx.validate.kngk(selector);
    };
    
    // 数字
    wxEx.validate.number = {};
    wxEx.validate.number = function(selector) {
        $(selector).each(function(){
            $(this).focus(function(){
            	wxEx.validate.number.getFocus($(this));
                $(this).select();
            });
            $(this).blur(function(){
            	wxEx.validate.number.lostFocus($(this));
            });
            // キー押下イベント設定
            $(this).keypress(function(e) {
            	return wxEx.validate.number.keyPress($(this), e);
            });
            // 値変更イベント設定
            $(this).change(function(e) {
            	wxEx.validate.number.change($(this));
            });
            wxEx.validate.number.lostFocus($(this));
        });
    };
    
    // 数値
    wxEx.validate.number.lostFocus = function(element) {
    	// エラーをクリア
        wxEx.validate.clearError(element);
        // 最小値
        var min = element.attr("min");
        min = (min === undefined) ? 0 : min;
        // 最大値
        var max = element.attr("max");
        max = (max === undefined) ? 0 : max;
        // カンマ有無
        var option = element.attr("option");
        // 桁数
        var fmt = element.attr("fmt");
        var fmtInt;
        var fmtFloat;
        if (fmt && fmt.indexOf(".") != -1) {
        	fmtInt = fmt.substring(0, fmt.indexOf("."));
        	fmtFloat = fmt.substring(fmt.indexOf(".")+1);
        }
        // コントロール値
        var sData = "";
        sData = element.val().trim().replace(/,/g,"");
        if (sData.length == 0) {
        	$('[ownerid=' + $(element).attr('id') + ']').val(sData);
            return true;
        }

        // コントロール値
        sData = wxEx.util.strConv(sData);
        var sDataW = sData;

        // 入力値の冗長な０を削除
        if (sData.startsWith("-")) {
            sData = "-" + sData.substring(1, sData.length).replace(/^0+/, "");
        } else {
            sData = sData.replace(/^0+/, "");
        }

        // 整数部冗長な０を削除
        if (sDataW != null && sDataW != "") {
            if ((sDataW.length > 0 && sData.length == 0)
                    || (sDataW.length > 1 && sData == "-")) {
                sData = "0";
            } else if (sData.startsWith(".")) {
                sData = "0" + sData;
                sDataW = sData;
            }
        }

        // 小数部冗長な０を削除
        if (sDataW.indexOf(".") > -1) {
            sData = sData.replace(/0+$/, "");
            if (sData.endsWith(".")) {
                sData += "0";
            }
        }

        // 整数部
        var sInt = "";
        // 小数部
        var sFloat = "";
        // 入力値をチェックする
        var iError = true;
        while (true) {
            // 整数部・小数部を取り出す
            var iPos = sData.indexOf(".");
            if (iPos > 0) {
                // 小数点複数のチェック
                if (sData.indexOf(".", iPos + 1) > -1) {
                    break;
                }
                sInt = sData.substring(0, iPos);
                sFloat = sData.substring(iPos + 1);
            } else {
                sInt = sData;
            }
            if (!(sInt + sFloat).isNumeric()) {
                break;
            }

            // 桁オーバーをチェックする
            if (fmtInt) {
                if (sInt.length > fmtInt) {
                    break;
                }
            }
            if (fmtFloat) {
                if (sFloat.length > fmtFloat) {
                    break;
                }
            }
            iError = false;
            break;
        }

        // 範囲ﾁｪｯｸを行う
        if (min != 0 || max != 0) {
            var dNum = Number(sData);
            if (dNum < min || max < dNum) {
                iError = true;
            }
        }

        // エラー処理
        if (iError) {
            var itemName = wxEx.validate.getItemName(element);
            wxEx.form.message.clientMessage(wxEx.data.attribute.MSG_ID_PATTEN, itemName);
            wxEx.validate.addError(element);
            return false;
        }
        
        // 少数部を確保する
        if (fmtFloat > 0 && sFloat.length != 0 && sFloat != "0") {
            sFloat = "." + sFloat;
        } else {
            sFloat = "";
        }

        // 送付セット
        var hidden = sInt + sFloat;
        $('[ownerid=' + element.attr('id') + ']').val(hidden);
        // コンマ編集を行う
        if (option == "comma") {
            sInt = wxEx.util.commaFormat(sInt);
        }
        // 表示セット
        element.val(sInt + sFloat);
        return true;
    };
    
    wxEx.validate.number.keyPress = function(element, e) {
        // 入力文字
        var sChr = String.fromCharCode(e.keyCode);

        // 半角に変換
        sChr = wxEx.util.strConv(sChr);
        // 小数部フォーマット
        var fmt = element.attr("fmt");
        var fmtFloat = -1;
        if (fmt) {
        	if (fmt.substring(fmt.indexOf(".")+1).length != 0) {
        		fmtFloat = Number(fmt.substring(fmt.indexOf(".")+1));
        	}
        }

        // [.]、「-」、数字は許可する
        if (sChr == "." && fmtFloat > 0) {
        } else if (sChr == "-") {
        } else if (!sChr.isNumber()) {
            return false;
        }
        // 半角を返す
        e.keyCode = sChr;
        return true;
    };
    
    wxEx.validate.number.change = function(element) {
         // コントロール値
        var sText = "";
        sText = element.val();
        sText = wxEx.util.strConv(sText);
        element.val(sText);
    }
    
    wxEx.validate.number.getFocus = function(element){
        var num = element.val();
        element.val(num);
    };
    
    wxEx.autobind.number = function(className) {
        var selector = (className === undefined) ? 'wx-number' : className;
        selector = '[validate="' + selector + '"]';
        wxEx.autobind.addHidden(selector);
        wxEx.validate.number(selector);
    };

    /**
     * 電話番号のフォーカスアウト処理
     * 
     * @param element コントロール
     */
    wxEx.validate.tel = {};
    // 電話番号設定
    wxEx.validate.tel = function(selector) {
        $(selector).each(function () {
            // フォーカス喪失イベント設定
            $(this).blur(function () {
                wxEx.validate.tel.lostFocus($(this));
            });
             // フォーカス取得イベント設定
            $(this).focus(function(){
            	$(this).select();
            });
            // キー押下イベント設定
            $(this).keypress(function(e) {
                return wxEx.validate.tel.keyPress($(this), e);
            });
            
            // 値変更イベント設定
            $(this).change(function(e) {
                wxEx.validate.hankaku.change($(this));
            });
        });
    };
    wxEx.validate.tel.lostFocus = function(element) {
    	// エラークリア
    	wxEx.validate.clearError(element);
        // トリムされたコントロール値を取得する
        var sData = "";
        sData = element.val().trim();
        // 空白チェック
        if (sData.length == 0) {
            return true;
        }
        // 入力値の全角を半角に変換する
        sData = wxEx.util.strConv(sData);
        // エラーフラグ
        var errorFlag = true;
        var parts;
        // チェック
        while (true) {
            // 全角文字があれば、エラーとする
            if (sData.length != sData.bLen()) {
                break;
            }
            // 「-」以外値に数字以外が含まれている場合、エラーとする
            if (!sData.replace(/-/g, "").isNumber()) {
                break;
            }
            if (sData.indexOf("-") == -1) {
            	break;
            }
            parts = sData.split("-");
            if (parts.length != 3) {
            	break;
            }
            if (parts[0].length > 6 || parts[1].length > 4 || parts[2].length > 4) {
            	// 桁数不正
            	break;
            }
            errorFlag = false;
            break;
        }

        // エラーがあれば
        if (errorFlag) {
            // エラー表示
            wxEx.validate.addError(element);
            // エラーメッセージダイアローグを表示する
            wxEx.form.message.clientMessage(wxEx.data.attribute.MSG_ID_PATTEN, wxEx.validate.getItemName(element));
            return false;
        }
        // コントロール値を属性「value」に設定する
        element.val(sData);
        return true;
    };

    // 電話番号 keyPressイベント
    wxEx.validate.tel.keyPress = function(element, e) {
        // 入力文字
        var sChr = String.fromCharCode(e.keyCode);
        // 半角に変換
        sChr = wxEx.util.strConv(sChr);
        // 「-」、半角数字は許可する
        if (sChr == "-") {
        } else if (!sChr.isNumber()) {
            return false;
        }
        // 半角を返す
        e.keyCode = sChr;
        return true;
    };
    
    
    // 電話番号
    wxEx.autobind.tel = function(className) {
        var selector = (className === undefined) ? 'wx-tel' : className;
        selector = '[validate="' + selector + '"]';
        wxEx.validate.tel(selector);
    };
    
    wxEx.autobind.addHidden = function(selector){
        // 送信用の隠し項目追加
        $(selector).each(function(){
            var sendId = undefined;
            var original = $(this);
            if (original.attr("name")) {
                var ownerid = original.attr("id");
                sendId = 'fw_send_' + ownerid;
                original.after("<input type='hidden' id='" + sendId + "' name='"
                        + original.attr("name") + "' value='" + original.val()
                        + "' ownerid='" + ownerid + "'/>");
                original.attr("oname", original.attr("name"));
                original.attr("sendid", sendId);
                original.removeAttr("name");
            }
        });
    };
    
    wxEx.file = {};
    
    wxEx.file.download = function(element) {
		var url = element.attr("data-downUrl");
		var f = $("form:eq(0)");
		var tmp = f.attr("action");
		f.attr("action", url);
		f.submit();
		f.attr("action", tmp);
	};
	
	wxEx.autobind.download = function(className){
		var selector = (className === undefined) ? '.wx-another' : '.' + className;
		$(selector).each(function(){
			wxEx.file.download($(this));
		});
	};
    
    wxEx.ready = {};
    wxEx.ready.start = function(){
        wxEx.autobind.date();
        wxEx.autobind.yymm();
        wxEx.autobind.yyyy();
        wxEx.autobind.tel();
        wxEx.autobind.kngk();
        wxEx.autobind.number();
        wxEx.autobind.checkbox();
        wxEx.form.message.init();
        
    };
    
    wxEx.ready.subwin = {};
    
    wxEx.ready.subwin.start = function(){

        wxEx.autobind.date();
        wxEx.autobind.yymm();
        wxEx.autobind.yyyy();
        wxEx.autobind.tel();
        wxEx.autobind.kngk();
        wxEx.autobind.number();
        wxEx.autobind.checkbox();
        wxEx.form.message.init();
        wxEx.autobind.download();
    };

    /**
     * 必須チェックを行う
     */
    wxEx.validate.required = function(element) {
    	// エラークリア
    	wxEx.validate.clearError(element);
        var value = element.val();
        if (value == null || value.trim() === "") {
            // エラー表示
            wxEx.validate.addError(element);
            // エラーメッセージダイアローグを表示する
            wxEx.form.message.clientMessage(wxEx.data.attribute.MSG_ID_REQUIRED, wxEx.validate.getItemName(element));
            return false;
        }
        return true;
    };
    
    wxEx.util = {};
    /**
     * 半角・全角変換値
     */
    wxEx.util.CONVERT=[ {"wchar":"ァ","char":"ｧ"},{"wchar":"ア","char":"ｱ"},{"wchar":"ィ","char":"ｨ"},{"wchar":"イ","char":"ｲ"},
                     {"wchar":"ゥ","char":"ｩ"},{"wchar":"ヴ","char":"ｳﾞ"},{"wchar":"ウ","char":"ｳ"},{"wchar":"ェ","char":"ｪ"},
                     {"wchar":"エ","char":"ｴ"},{"wchar":"ォ","char":"ｫ"},{"wchar":"オ","char":"ｵ"},{"wchar":"ガ","char":"ｶﾞ"},
                     {"wchar":"カ","char":"ｶ"},{"wchar":"ギ","char":"ｷﾞ"},{"wchar":"キ","char":"ｷ"},{"wchar":"グ","char":"ｸﾞ"},
                     {"wchar":"ク","char":"ｸ"},{"wchar":"ゲ","char":"ｹﾞ"},{"wchar":"ケ","char":"ｹ"},{"wchar":"ゴ","char":"ｺﾞ"},
                     {"wchar":"コ","char":"ｺ"},{"wchar":"ザ","char":"ｻﾞ"},{"wchar":"サ","char":"ｻ"},{"wchar":"ジ","char":"ｼﾞ"},
                     {"wchar":"シ","char":"ｼ"},{"wchar":"ズ","char":"ｽﾞ"},{"wchar":"ス","char":"ｽ"},{"wchar":"ゼ","char":"ｾﾞ"},
                     {"wchar":"セ","char":"ｾ"},{"wchar":"ゾ","char":"ｿﾞ"},{"wchar":"ソ","char":"ｿ"},{"wchar":"ダ","char":"ﾀﾞ"},
                     {"wchar":"タ","char":"ﾀ"},{"wchar":"ヂ","char":"ﾁﾞ"},{"wchar":"チ","char":"ﾁ"},{"wchar":"ッ","char":"ｯ"},
                     {"wchar":"ヅ","char":"ﾂﾞ"},{"wchar":"ツ","char":"ﾂ"},{"wchar":"デ","char":"ﾃﾞ"},{"wchar":"テ","char":"ﾃ"},
                     {"wchar":"ド","char":"ﾄﾞ"},{"wchar":"ト","char":"ﾄ"},{"wchar":"ナ","char":"ﾅ"},{"wchar":"ニ","char":"ﾆ"},
                     {"wchar":"ヌ","char":"ﾇ"},{"wchar":"ネ","char":"ﾈ"},{"wchar":"ノ","char":"ﾉ"},{"wchar":"バ","char":"ﾊﾞ"},
                     {"wchar":"パ","char":"ﾊﾟ"},{"wchar":"ハ","char":"ﾊ"},{"wchar":"ビ","char":"ﾋﾞ"},{"wchar":"ピ","char":"ﾋﾟ"},
                     {"wchar":"ヒ","char":"ﾋ"},{"wchar":"ブ","char":"ﾌﾞ"},{"wchar":"プ","char":"ﾌﾟ"},{"wchar":"フ","char":"ﾌ"},
                     {"wchar":"ベ","char":"ﾍﾞ"},{"wchar":"ペ","char":"ﾍﾟ"},{"wchar":"ヘ","char":"ﾍ"},{"wchar":"ボ","char":"ﾎﾞ"},
                     {"wchar":"ポ","char":"ﾎﾟ"},{"wchar":"ホ","char":"ﾎ"},{"wchar":"マ","char":"ﾏ"},{"wchar":"ミ","char":"ﾐ"},
                     {"wchar":"ム","char":"ﾑ"},{"wchar":"メ","char":"ﾒ"},{"wchar":"モ","char":"ﾓ"},{"wchar":"ャ","char":"ｬ"},
                     {"wchar":"ヤ","char":"ﾔ"},{"wchar":"ュ","char":"ｭ"},{"wchar":"ユ","char":"ﾕ"},{"wchar":"ョ","char":"ｮ"},
                     {"wchar":"ヨ","char":"ﾖ"},{"wchar":"ラ","char":"ﾗ"},{"wchar":"リ","char":"ﾘ"},{"wchar":"ル","char":"ﾙ"},
                     {"wchar":"レ","char":"ﾚ"},{"wchar":"ロ","char":"ﾛ"},{"wchar":"ワ","char":"ﾜ"},{"wchar":"ヲ","char":"ｦ"},
                     {"wchar":"ン","char":"ﾝ"},{"wchar":"ヱ","char":"ｴ"},{"wchar":"ヰ","char":"ｲ"},{"wchar":"ヵ","char":"ｶ"},
                     {"wchar":"ヶ","char":"ｹ"},{"wchar":"。","char":"｡"},{"wchar":"「","char":"｢"},{"wchar":"」","char":"｣"},
                     {"wchar":"、","char":"､"},{"wchar":"・","char":"･"},{"wchar":"゛","char":"ﾞ"},{"wchar":"゜","char":"ﾟ"},
                     {"wchar":"ぁ","char":"ｧ"},{"wchar":"あ","char":"ｱ"},{"wchar":"ぃ","char":"ｨ"},{"wchar":"い","char":"ｲ"},
                     {"wchar":"ぅ","char":"ｩ"},{"wchar":"う","char":"ｳ"},{"wchar":"ぇ","char":"ｪ"},{"wchar":"え","char":"ｴ"},
                     {"wchar":"ぉ","char":"ｫ"},{"wchar":"お","char":"ｵ"},{"wchar":"が","char":"ｶﾞ"},{"wchar":"か","char":"ｶ"},
                     {"wchar":"ぎ","char":"ｷﾞ"},{"wchar":"き","char":"ｷ"},{"wchar":"ぐ","char":"ｸﾞ"},{"wchar":"く","char":"ｸ"},
                     {"wchar":"げ","char":"ｹﾞ"},{"wchar":"け","char":"ｹ"},{"wchar":"ご","char":"ｺﾞ"},{"wchar":"こ","char":"ｺ"},
                     {"wchar":"ざ","char":"ｻﾞ"},{"wchar":"さ","char":"ｻ"},{"wchar":"じ","char":"ｼﾞ"},{"wchar":"し","char":"ｼ"},
                     {"wchar":"ず","char":"ｽﾞ"},{"wchar":"す","char":"ｽ"},{"wchar":"ぜ","char":"ｾﾞ"},{"wchar":"せ","char":"ｾ"},
                     {"wchar":"ぞ","char":"ｿﾞ"},{"wchar":"そ","char":"ｿ"},{"wchar":"だ","char":"ﾀﾞ"},{"wchar":"た","char":"ﾀ"},
                     {"wchar":"ぢ","char":"ﾁﾞ"},{"wchar":"ち","char":"ﾁ"},{"wchar":"っ","char":"ｯ"},{"wchar":"づ","char":"ﾂﾞ"},
                     {"wchar":"つ","char":"ﾂ"},{"wchar":"で","char":"ﾃﾞ"},{"wchar":"て","char":"ﾃ"},{"wchar":"ど","char":"ﾄﾞ"},
                     {"wchar":"と","char":"ﾄ"},{"wchar":"な","char":"ﾅ"},{"wchar":"に","char":"ﾆ"},{"wchar":"ぬ","char":"ﾇ"},
                     {"wchar":"ね","char":"ﾈ"},{"wchar":"の","char":"ﾉ"},{"wchar":"ば","char":"ﾊﾞ"},{"wchar":"ぱ","char":"ﾊﾟ"},
                     {"wchar":"は","char":"ﾊ"},{"wchar":"び","char":"ﾋﾞ"},{"wchar":"ぴ","char":"ﾋﾟ"},{"wchar":"ひ","char":"ﾋ"},
                     {"wchar":"ぶ","char":"ﾌﾞ"},{"wchar":"ぷ","char":"ﾌﾟ"},{"wchar":"ふ","char":"ﾌ"},{"wchar":"べ","char":"ﾍﾞ"},
                     {"wchar":"ぺ","char":"ﾍﾟ"},{"wchar":"へ","char":"ﾍ"},{"wchar":"ぼ","char":"ﾎﾞ"},{"wchar":"ぽ","char":"ﾎﾟ"},
                     {"wchar":"ほ","char":"ﾎ"},{"wchar":"ま","char":"ﾏ"},{"wchar":"み","char":"ﾐ"},{"wchar":"む","char":"ﾑ"},
                     {"wchar":"め","char":"ﾒ"},{"wchar":"も","char":"ﾓ"},{"wchar":"ゃ","char":"ｬ"},{"wchar":"や","char":"ﾔ"},
                     {"wchar":"ゅ","char":"ｭ"},{"wchar":"ゆ","char":"ﾕ"},{"wchar":"ょ","char":"ｮ"},{"wchar":"よ","char":"ﾖ"},
                     {"wchar":"ら","char":"ﾗ"},{"wchar":"り","char":"ﾘ"},{"wchar":"る","char":"ﾙ"},{"wchar":"れ","char":"ﾚ"},
                     {"wchar":"ろ","char":"ﾛ"},{"wchar":"わ","char":"ﾜ"},{"wchar":"ゎ","char":"ﾜ"},{"wchar":"を","char":"ｦ"},
                     {"wchar":"ん","char":"ﾝ"},{"wchar":"ゑ","char":"ｴ"},{"wchar":"ゐ","char":"ｲ"}];
    
    /**
     * 文字変換（全角→半角)
     */
    wxEx.util.strConv = function(value) {
        var res = "";
        for ( var i = 0; i < value.length; i++) {
            var c = value.charCodeAt(i);
            if (c == 0xFFE5) {
                res += String.fromCharCode(0x5c);
                continue;
            } else if (c == 0x3000) {
                res += String.fromCharCode(0x20);
                continue;
            } else if (0xFF00 <= c && c <= 0xFF5e) {
                res += String.fromCharCode(c - 0xFEE0);
                continue;
            }
            var char = value.charAt(i);
            for ( var j = 0; j < wxEx.util.CONVERT.length; j++) {
                if (!value.substring(i).startsWith(wxEx.util.CONVERT[j].wchar)) {
                    continue;
                }
                i += wxEx.util.CONVERT[j].wchar.length - 1;
                char = wxEx.util.CONVERT[j].char;
            }

            res += char;
        }

        return res;
    };
    
    /**
     * 文字変換（半角→全角)
     */
    wxEx.util.strConvW = function(value) {
        var res = "";
        for ( var i = 0; i < value.length; i++) {
            var c = value.charCodeAt(i);
            if (c == 0x5c) {
                res += '￥';
                continue;
            } else if (c == 0x20) {
                res += String.fromCharCode(0x3000);
                continue;
            } else if (0x20 < c && c <= 0x7e) {
                res += String.fromCharCode(c + 0xFEE0);
                continue;
            }

            var wchar = value.charAt(i);
            for ( var j = 0; j < wxEx.util.CONVERT.length; j++) {
                if (!value.substring(i).startsWith(wxEx.util.CONVERT[j].char)) {
                    continue;
                }
                i += wxEx.util.CONVERT[j].char.length - 1;
                wchar = wxEx.util.CONVERT[j].wchar;
                break;
            }

            res += wchar;
        }

        return res;
    };
    
    /**
     * 文字列が全角文字かチェックする 特記事項 : 長さ0の文字列もTrueを返す
     * 
     * @param sStr String i チェックする文字列
     * @return true:OK,false:NG
     */
    wxEx.util.isZenkaku = function(sStr) {
        var iLen = 0;

        // 全角以外の文字がないかチェックする
        iLen = sStr.length;
        if (sStr.bLen() != (iLen * 2)) {
            return false;
        }
        return true;
    };
    
    /**
     * 文字列が半角カタカナ文字かチェックする 特記事項 : 半角スペースはエラーにしない : 長さ0の文字列もTrueを返す
     * 
     * @param sStr String i チェックする文字列
     * @return true:OK,false:NG
     */
    wxEx.util.isHanKatakana = function(sStr) {
        var iLen = 0;
        var iPos = 0;

        // 半角以外の文字がないかチェックする
        iLen = sStr.length;
        if (sStr.bLen() != iLen) {
            return false;
        }
        // ASC文字コードに変換する
        for (iPos = 0; iPos < iLen; iPos++) {
            var c = sStr.charCodeAt(iPos);

            // ｶﾀｶﾅは許可する,-,ｽﾍﾟｰｽは許可する
            if (!(c == 0x20 || c == 0x2D || (c >= 0xFF66 && c <= 0xFF9F))) {
                return false;
            }
        }
        return true;
    };
    
    // 元号情報を取得
    wxEx.util.gengoSearch = function(param) {
        var gengoInfo = {};
        if (param.length != 1 && param.length != 8) {
            return null;
        }

        if (param.length == 1) {
            if (param.match("[a-z,A-Z]")) {
                // 小文字を大文字に変換
                gengoInfo.strGengo = param.toUpperCase();
                for ( var i = 0; i < wxEx.data.params.GENGO_ALP.length; i++) {
                    if (gengoInfo.strGengo == wxEx.data.params.GENGO_ALP[i]) {
                        gengoInfo.intGengo = i + 1;
                        gengoInfo.frmGengo = wxEx.data.params.GENGO_FRM[i];
                        gengoInfo.toGengo = wxEx.data.params.GENGO_TO[i];
                        return gengoInfo;
                    }
                }
            }
            if (param.match("[0-9]")) {
                gengoInfo.intGengo = param;
                var i = Number(param) - 1;
                if (i < wxEx.data.params.GENGO_ALP.length) {
                    if (wxEx.data.params.GENGO_ALP[i] != "") {
                        gengoInfo.strGengo = wxEx.data.params.GENGO_ALP[i];
                        gengoInfo.frmGengo = wxEx.data.params.GENGO_FRM[i];
                        gengoInfo.toGengo = wxEx.data.params.GENGO_TO[i];
                        return gengoInfo;
                    }
                }
            }
        } else {
            for ( var i = 0; i < wxEx.data.params.GENGO_FRM.length; i++) {
                sStart = wxEx.data.params.GENGO_FRM[i];
                sEnd = wxEx.data.params.GENGO_TO[i];
                if (Number(param) >= Number(sStart) && Number(param) <= Number(sEnd)) {
                    gengoInfo.intGengo = i + 1;
                    if (wxEx.data.params.GENGO_ALP[i] != "") {
                        gengoInfo.strGengo = wxEx.data.params.GENGO_ALP[i];
                        gengoInfo.frmGengo = wxEx.data.params.GENGO_FRM[i];
                        gengoInfo.toGengo = wxEx.data.params.GENGO_TO[i];
                        return gengoInfo;
                    }
                }
            }
        }
        return null;
    };
    
    /**
     * 日付存在チェック
     */
    wxEx.util.dateinfoChk = function(dateinfo) {
    	if (!dateinfo.sYear.isNumber()
    			|| !dateinfo.month.isNumber()
    			|| !dateinfo.day.isNumber()){
    		// 年月日が数値以外
    		return false;
    	}
    	var date = new Date(dateinfo.sYear, Number(dateinfo.month) - 1, dateinfo.day);
    	if (date.getFullYear() != Number(dateinfo.sYear)){
    		return false;
    	}
    	var m = date.getMonth();
    	if (m != (Number(dateinfo.month) - 1)){
    		return false;
    	}
    	if (date.getDate() != Number(dateinfo.day)) {
    		return false;
    	}
        return true;
    };
    
    
    // 数値カンマ編集
    wxEx.util.commaFormat = function(value) {
        var num = new String(value).replace(/,/g, "");
        while (num != (num = num.replace(/^(-?\d+)(\d{3})/, "$1,$2")));
        return num;
    };
    
    // CheckBox選択
    wxEx.autobind.checkbox = function() {
    	var selector = ".checkBoxAll";
    	wxEx.validate.checkBoxAll(selector);
    	
    	var selector = ".checkBoxOne";
    	wxEx.validate.checkBoxOne(selector);

    };
    /**
     * 全選択CheckBox
     * 
     * @param selector クラス名
     */
    wxEx.validate.checkBoxAll = {};
    wxEx.validate.checkBoxAll = function(selector){
        $(selector).each(function () {
            // クリックイベント設定
            $(this).click(function () {
            	wxEx.util.checkBoxAll($(this));
            });
           
        });
    };
    wxEx.util.checkBoxAll = function(element) {
    	var id = event.srcElement.id;
		if ($("#" + id).hasClass("checkBoxAll")) {
			var tempState = event.srcElement.checked;
			$("." + id).each(function() {
				if (this.checked != tempState)
					this.checked = tempState;
			});
		}
    }
    
     /**
      * CheckBoxクリックイベント
      */
    wxEx.validate.checkBoxOne = {};
    wxEx.validate.checkBoxOne = function(selector){
        $(selector).each(function () {
            // クリックイベント設定
            $(this).click(function () {
            	wxEx.util.checkBoxOne($(this));
            });
           
        });
    };
    wxEx.util.checkBoxOne = function(element) {
    	// 項目の取得
		var element = $(event.srcElement);
		// 全選択チェックボクスのIDを取得
		var allId = element.attr("checkallId");
		var cbAll = $("#" + allId)[0];
		var allCheck = true;
		$("." + allId).each(function() {
			if (this.checked === false) {
				// 子CheckBoxが全部選択じゃない場合、全選択CheckBoxは未選択になる
				allCheck = false;
				return false;
			}
		});
		// 子checkBox全部選択した場合、全選択CheckBoxも選択になる
		cbAll.checked = allCheck;
    }
})();
