// require jquery
// require jquery ui
// require common.js
var wx = {};

(function () {

    wx.data = {};
    wx.data.attribute = {
        DIALOG_MESSAGE:"wx-dialog-message",
        GRID_ADJUST_SELECTOR:"wx-grid-adjust-selector",
        GRID_ADJUST_HEIGHT:"wx-grid-adjust-height",
        AUTOCOMPLETE_PARAMS:"wx-autocomplete-params",
        AUTOCOMPLETE_CACHEABLE:"wx-autocomplete-cacheable",
        AUTOCOMPLETE_MINLENGTH:"wx-autocomplete-minlength",
        AUTOCOMPLETE_MAPPING:"wx-autocomplete-mapping",
        ACCEPT_MENU_VERSION:"wx-accept-menu-version"
    };


    wx.form = {};
    wx.form.action = function (formId, id, value) {
        if (!wxcom.util.objects.isNullOrUndefined(id)) {
            wxcom.form.setForm(formId, id, value);
        }
        wx.form.action.showDialog();
        wxcom.form.submit(formId);

        if (event) {
            new jQuery.Event(event).preventDefault();
        }
        return false;
    };

    wx.form.nodialogAction = function (formId, id, value) {
        if (!wxcom.util.objects.isNullOrUndefined(id)) {
            wxcom.form.setForm(formId, id, value);
        }
        wxcom.form.submit(formId);
        return false;
    };

    // 処理中ダイアログ
    wx.form.action.showDialog = function () {
        $("#executeDialog").dialog({
            resizable:false,
            draggable:false,
            height:150,
            width:150,
            modal:true,
            closeOnEscape:false,
            position : [$(window).width()*0.5-50, $(window).height()*0.3],
            open:function () {
                $('.ui-dialog').each(function() { 
                    $(this).removeClass("ui-widget-content");
                });
            	$('.ui-dialog-titlebar').hide();
                //$(".ui-dialog-titlebar-close").hide();
            }
        });
    };
    
    wx.form.action.closeDialog = function() {
    	 $("#executeDialog").dialog("destroy");
    };

    // 確認ダイアログ
    wx.form.confirm = function (msgId, args, formId, action, e) {
        var message = $("#" + msgId).attr("data");
        var mObj = wxEx.form.message.getMessage(message, args);
        wx.form.confirm.showDialog(mObj, function () {
        	$("#action").val(action);
            wxcom.form.submit(formId);
        });
        new jQuery.Event(e).preventDefault();
    };

    wx.form.confirm.showDialog = function (mObj, yesCallback, noCallback) {

        // 確認ダイアログ
        var _dlg = $("#" + wxEx.data.attribute.MSG_DIALOG_AREA);
        if (mObj != null) {
        	wxEx.form.message.addMessage(mObj["msgId"], mObj["message"]);
        }

        _dlg.dialog({
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
                "OK":{
                    click:function () {
                        $(this).dialog("destroy");
                        wx.form.action.showDialog();
                        $('#messageArea table:first tbody:first').text("");
                        yesCallback();
                    },
                    text:"はい",
                    id:"confirmDialogOk"
                },
                "キャンセル":{
                    click:function () {
                        $(this).dialog("destroy");
                        $('#messageArea table:first tbody:first').text("");
                        if (noCallback) {
                        	noCallback();
                        }
                        return false;
                    },
                    text:"いいえ",
                    id:"confirmDialogCancel"
                }
            },
            close:function(){
            	$('#messageArea table:first tbody:first').text("");
            },
            open:function () {
                $('#confirmDialogCancel').focus();
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


    
    wx.autobind = {};
    wx.ready = {};
    wx.ui = {};
    wx.ready.start = function () {

        $(".button").button();
        
        wx.autobind.oritatami();
    };

    wx.autobind.oritatami = function () { 
        var selector = "#oritatami";
        // 一つの場合、
        if ($(selector).length > 0) { 
            wx.ui.setToggle('oritatami', 'jyoken'); 
        }
        
        // 二つ以上の場合、
        for(i=1;;i++) {
            if ($(selector+i).length > 0) { 
                wx.ui.setToggle('oritatami'+i, 'jyoken'+i); 
            } else {
            	break;
            }
        }
    };

    /**
     * 折りたたみ機能を提供する.
     * @param btnId 折りたたみボタン（DIV)のエレメントID 
     * @param areaId 折りたたむ範囲(DIV)のスタイルクラス
     */
    wx.ui.setToggle = function setToggle(btnId, areaClass) {
        var btn = $("<input type='button' value='▼'>");
        btn.click(function(){
            $('.' + areaClass).toggle("blind");
            if ($(this).val() == "▼") {
                $(this).val("▲");
            } else {
                $(this).val("▼");
            }
        });
        $('#' + btnId).append(btn);
    };
})();
