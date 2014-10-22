// require jquery
// require jquery ui
var wxcom = {};
(function () {

    wxcom.logger = {};

    wxcom.logger.log = function (message) {
        if (window.console) {
            console.log(message);
        }
    };

    wxcom.data = {};
    wxcom.data.attribute = {
        DATE_FORMAT_DISP:"wxcom-dateformat-disp",
        DATE_FORMAT_TEXT:"wxcom-dateformat-text",
        DATE_FORMAT_ILLEGAL_TO_EMPTY:"wxcom-dateformat-illegal-to-empty",
        CALENDAR_NUMBER_OF_MONTHS:"wxcom-calendar-number-of-months",
        GRID_SCROLL_X : "wxcom-grid-scroll-x",
        GRID_SCROLL_Y : "wxcom-grid-scroll-y",
        GRID_SCROLL_Y_MAX : "wxcom-grid-scroll-y-max",
        GRID_SCROLL_Y_MIN : "wxcom-grid-scroll-y-min",
        GRID_PER_ROW : "wxcom-grid-per-row",
        GRID_ADJUSTMENT_Y : "wxcom-grid-adjustment-y",
        GRID_OPTIONS : "wxcom-grid-options",
        GRID_COLUMN_SORTABLE : "wxcom-grid-column-sortable",
        GRID_COLUMN_SORT_TYPE : "wxcom-grid-column-sort-type",
        NUMBER_FRACTION : "wxcom-number-fraction",
        LINK_PARAMS : "wxcom-link-params",
        LINK_PARAMS_MAPPING : "wxcom-link-params-mapping",
        SUBWINDOW_MAPPING : "wxcom-subwindow-mapping",
        SITEMESH_CONTENT:"sitemesh-content"
    };

    wxcom.util = {};

    wxcom.util.contextPath = function () {
        if (typeof appGlobal == "undefined") {
            return "/wxcom";
        }
        else {
        	// @{/}でクッキーにjsessionidが含まれてるケースでは、付与されているので整形する。
        	var path = appGlobal.contextPath;
        	var index = path.indexOf(";jsessionid");
        	if (index != -1){
        		path = path.substring(0, index);
        	}
            return path;
        }
    };

    wxcom.util.parseJSON = function (param) {
        if (typeof param === "string") {
            return $.parseJSON(param);
        }
        else {
            return param;
        }
    };

    wxcom.util.objects = {};
    wxcom.util.objects.isNullOrUndefined = function(o){
        return o === undefined || o == null;
    };

    wxcom.util.strings = {};
    wxcom.util.strings.isEmpty = function(s){
        return s == null || s.length == 0;
    };
    
    wxcom.ui = {};
    wxcom.ui.css = {};
    wxcom.ui.css.cssToNumber = function(s){
    	if (typeof s === 'number'){
    		if (s < 0){
    			return 0;
    		}
    		else{
    			return s;
    		}
    	}
    	else if (typeof s === 'string'){
    		return parseInt(s, 10);
    	}
    	return parseInt(s, 10);
    };

    wxcom.store = {};
    wxcom.store.flashStore = {

        data : {},

        put : function(key , value){
            wxcom.store.flashStore.data[key] = value;
        },

        get : function(key){
            return wxcom.store.flashStore.data[key];
        }
    };

    wxcom.calendar = {
        SUN:0,
        MON:1,
        TUE:2,
        WED:3,
        THU:4,
        FRI:5,
        SAT:6
    };

    wxcom.calendar.image = function () {
        var contextPath = wxcom.util.contextPath();
        return contextPath + "/assets/image/calendar.png";
    };

    wxcom.calendar.datepicker = {
        holidays:[]
    };

    wxcom.calendar.datepicker.setHolidays = function (holidays) {
        this.holidays = holidays;
    };

    wxcom.calendar.datepicker.beforeShow = function (date) {

        var day = date.getDay();

        if (wxcom.calendar.SUN == day) {
            return [false, 'sunday'];
        }
        if (wxcom.calendar.SAT == day) {
            return [false, 'saturday'];
        }

        var yyyymmdd = wxcom.format.date.toText("yymmdd", date);

        if (wxcom.calendar.datepicker.holidays === undefined) {
            return [true, 'weekday'];
        }

        var found = $.inArray(yyyymmdd, wxcom.calendar.datepicker.holidays) != -1;

        if (found) {
            return [false, 'holiday'];
        }
        else {
            return [true, 'weekday'];
        }

    };

    wxcom.calendar.datepicker.onSelect = function(dateText, inst){
        // for formtips.
        if ($(this).hasClass("tipped")){
            $(this).removeClass("tipped");
        }
    };

    wxcom.date = {};
    wxcom.date.datepicker = {};
    wxcom.date.datepicker.getInstance = function(n){
        return $(n).data('datepicker');
    };

    wxcom.date.datepicker.createCurrentDate = function(instance){
        if (instance.currentYear){
            var year = instance.currentYear;
            var month = instance.currentMonth;
            var day = instance.currentDay;
            var date = new Date(year, month, day);
            return date;
        }
        else{
            return null;
        }
    };


    wxcom.animation = {};

    wxcom.animation.toggleColor = function (id, color) {
        var target = $("#" + id);
        target.css("background-color", color);
    };

    wxcom.animation.toggleDisplay = function (id, callback) {
        var target = $("#" + id);
        var style = target.attr("style");
        if (style === undefined || style == null) {
            target.hide();
            if (callback){
            	callback(target, false);
            }
        }
        else {
            var match = style.match(/display: none/);
            if (match != null) {
                target.show();
                if (callback){
                	callback(target, true);
                }
            }
            else {
                target.hide();
                if (callback){
                	callback(target, false);
                }
            }
        }

    };

    wxcom.animation.modal = function (param) {
        var json = wxcom.util.parseJSON(param);
        var options = $.extend({}, {
            text:'modal',
            modal:false
        }, json);
        var dialog = $("<div/>").attr("id", "loadingMessage").text(options.text);
        dialog.dialog({modal:options.modal});
    };

    wxcom.form = {};

    wxcom.form.set = function (id, value) {
        $("#" + id).val(value);
    };

    wxcom.form.setForm = function (formId, id, value) {
        var f = $("#" + formId);
        $("#" + id).val(value);
    };


    wxcom.form.submit = function (formId) {
        $("#" + formId).submit();
    };

    wxcom.form.action = function (formId, id, value) {
        wxcom.form.setForm(formId, id, value);
        wxcom.form.submit(formId);
    };

    wxcom.form.checkboxes = {
        off:true,
        toggleClass : "toggle-checked"
    };
    
    wxcom.form.checkboxes.toggle = function (name) {
        if (wxcom.form.checkboxes.off) {
            $("input[name=" + name + "]").attr({checked:"checked"});
            wxcom.form.checkboxes.off = false;
        }
        else {
            $("input[name=" + name + "]").attr({checked:false});
            wxcom.form.checkboxes.off = true;
        }
    };
    
    wxcom.form.checkboxes.toggle.color = function (name, selector) {
    	
    	var toggleClass = wxcom.form.checkboxes.toggleClass;
    	
        if (wxcom.form.checkboxes.off) {
            $("input[name=" + name + "]").attr({checked:"checked"}).parents("tr").addClass(toggleClass);
            wxcom.form.checkboxes.off = false;
        }
        else {
            $("input[name=" + name + "]").attr({checked:false}).parents("tr").removeClass(toggleClass);
            wxcom.form.checkboxes.off = true;
        }
    };
    
    wxcom.form.checkboxes.toggle.selector = function (selector) {
    	
    	var checkboxSelector = selector || "input[type=checkbox][class*=toggle-checkbox]";
    	
    	var toggleClass = wxcom.form.checkboxes.toggleClass;
    	
        if (wxcom.form.checkboxes.off) {
            $(checkboxSelector).attr({checked:"checked"}).parents("tr").addClass(toggleClass);
            wxcom.form.checkboxes.off = false;
        }
        else {
            $(checkboxSelector).attr({checked:false}).parents("tr").removeClass(toggleClass);
            wxcom.form.checkboxes.off = true;
        }
    };

    wxcom.form.paging = {};

    wxcom.form.paging.calculateOffset = function (limit, nextPageNo) {
        if (nextPageNo == 1) {
            return 0;
        }
        return limit * (nextPageNo - 1);
    };

    wxcom.form.paging.calculateLastPageNo = function (limit, total) {
        return Math.ceil(total / limit);
    };

    wxcom.form.paging.disabled = function (n) {
        return $(n).hasClass("disabled");
    };

    wxcom.form.paging.active = function (n) {
        return $(n).hasClass("active");
    };

    wxcom.form.paging.go = function (formId, startPageNo, nextPageNo, nextOffset) {
        wxcom.form.set("startPageNo", startPageNo);
        wxcom.form.set("currentPageNo", nextPageNo);
        wxcom.form.set("offset", nextOffset);
        wxcom.form.set("action", "paging");
        wxcom.form.submit(formId);
    };

    wxcom.form.paging.first = function (formId, n) {

        if (wxcom.form.paging.disabled(n)) {
            return false;
        }

        var currentPageNo = $("#currentPageNo").val();
        var nextPageNo = 1;
        var startPageNo = nextPageNo;

        var limit = $("#limit").val();
        var nextOffset = wxcom.form.paging.calculateOffset(limit, nextPageNo);

        wxcom.form.paging.go(formId, startPageNo, nextPageNo, nextOffset);
    };

    wxcom.form.paging.prev = function (formId, n) {

        if (wxcom.form.paging.disabled(n)) {
            return false;
        }

        var currentPageNo = $("#currentPageNo").val();
        var nextPageNo = parseInt(currentPageNo, 10) - 1;
        var startPageNo = nextPageNo;

        var limit = $("#limit").val();
        var nextOffset = wxcom.form.paging.calculateOffset(limit, nextPageNo);

        wxcom.form.paging.go(formId, startPageNo, nextPageNo, nextOffset);
    };

    wxcom.form.paging.jump = function (formId, n) {

        if (wxcom.form.paging.active(n)) {
            return false;
        }

        var nextPageNo = n.html();

        var limit = $("#limit").val();
        var nextOffset = wxcom.form.paging.calculateOffset(limit, nextPageNo);
        var startPageNo = nextPageNo;

        wxcom.form.paging.go(formId, startPageNo, nextPageNo, nextOffset);
    };

    wxcom.form.paging.next = function (formId, n) {

        if (wxcom.form.paging.disabled(n)) {
            return false;
        }

        var currentPageNo = $("#currentPageNo").val();
        var nextPageNo = parseInt(currentPageNo, 10) + 1;
        var startPageNo = nextPageNo;

        var limit = $("#limit").val();
        var nextOffset = wxcom.form.paging.calculateOffset(limit, nextPageNo);

        wxcom.form.paging.go(formId, startPageNo, nextPageNo, nextOffset);
    };

    wxcom.form.paging.last = function (formId, n) {

        if (wxcom.form.paging.disabled(n)) {
            return false;
        }

        var limit = $("#limit").val();
        var total = $("#total").val();

        var currentPageNo = $("#currentPageNo").val();
        var nextPageNo = wxcom.form.paging.calculateLastPageNo(limit, total);
        var startPageNo = nextPageNo;

        var nextOffset = wxcom.form.paging.calculateOffset(limit, nextPageNo);

        wxcom.form.paging.go(formId, startPageNo, nextPageNo, nextOffset);
    };

    wxcom.form.paging.inputJump = function (formId, n) {

        var value = $(n).val();
        if ($.trim(value) == "") {
            return false;
        }

        if (!wxcom.format.util.isNumber(value)) {
            return false;
        }

        var nextPageNo = value;
        if (nextPageNo <= 0){
            return false;
        }

        var currentInputPageNo = $("#currentInputPageNo").val();
        if (nextPageNo == currentInputPageNo) {
            return false;
        }

        var limit = $("#limit").val();
        var total = $("#total").val();
        var lastPageNo = wxcom.form.paging.calculateLastPageNo(limit, total);

        if (lastPageNo < nextPageNo) {
            return false;
        }

        var limit = $("#limit").val();
        var nextOffset = wxcom.form.paging.calculateOffset(limit, nextPageNo);
        var startPageNo = nextPageNo;

        wxcom.form.paging.go(formId, startPageNo, nextPageNo, nextOffset);
    };

    wxcom.form.paging.prepare = function () {
        $("form").each(function () {
            var form = $(this);
            var formId = form.attr("id");
            $(".first:not([class*='disabled']) a", form).click(function (e) {
                wxcom.form.paging.first(formId, $(this));
                e.preventDefault();
            });
            $(".prev:not([class*='disabled']) a", form).click(function (e) {
                wxcom.form.paging.prev(formId, $(this));
                e.preventDefault();
            });
            $(".pageLink:not([class*='disabled']) a", form).click(function (e) {
                wxcom.form.paging.jump(formId, $(this));
                e.preventDefault();
            });
            $(".next:not([class*='disabled']) a", form).click(function (e) {
                wxcom.form.paging.next(formId, $(this));
                e.preventDefault();
            });
            $(".last:not([class*='disabled']) a", form).click(function (e) {
                wxcom.form.paging.last(formId, $(this));
                e.preventDefault();
            });
            $(".paging-input:not([class*='disabled'])", form).blur(function (e) {
                wxcom.form.paging.inputJump(formId, $(this));
                e.preventDefault();
            });
        });
    };

    wxcom.form.linkparams = {
    	config : {
    		LINKPARAM_WIN_OPTION : ""
    	}
    };
    
    wxcom.form.linkparams.copy = function(formId, element, attributeName, optionKey){

    	var name = attributeName || wxcom.data.attribute.LINK_PARAMS;
    	var json = $(element).data(name);
    	if (wxcom.util.objects.isNullOrUndefined(json)){
    		json = $(element).parent().data(name);
    		if (wxcom.util.objects.isNullOrUndefined(json)){
    			json = $(element).parents("td").data(name);
    		}
    	}
    	
    	var mapping = $(element).data(wxcom.data.attribute.LINK_PARAMS_MAPPING);
    	if (wxcom.util.objects.isNullOrUndefined(mapping)){
        	var form = $('#' + formId);
        	for (var i in json){
        		var e = form.find('input[name=' + i + ']');
        		e.val(json[i]);
        	}
    	}
    	else{
        	var form = $('#' + formId);
        	for (var i in json){
        		var name = mapping[i] || i;
        		var e = form.find('input[name=' + name + ']');
        		e.val(json[i]);
        	}
    	}
    	
    	var option = wxcom.form.linkparams.config.LINKPARAM_WIN_OPTION;
    	wxcom.window.newwindow.open(formId, option);
    	return false;
    };

    wxcom.window = {};
    wxcom.window.close = function(){
    	(window.open('','_self').opener=window).close();
    };
    
    wxcom.window.newwindow = {};

    wxcom.window.newwindow.open = function(formId, option) {

        var form = $("#" + formId);
        var windowName = form.attr("target");
        if (windowName){
        	var win = window.open("about:blank", windowName, option);        	
        }
    	wxcom.form.submit(formId);
    	return win;
    };

    wxcom.window.newwindow.linkopen = function($node, option) {

    	var url = $node.attr("href")
        var windowName = $node.attr("target");
        var win = window.open(url, windowName, option);        	
    	return win;
    };

    wxcom.window.newwindow.action = function(formId, option, id, value) {
        wxcom.form.setForm(formId, id, value);
    	return wxcom.window.newwindow.open(formId, option);
    };

    wxcom.window.subwindow = {};
    wxcom.window.subwindow.opener = {};
    wxcom.window.subwindow.opener.open = function(subwindowKey, subwindowFormId, option, copyTargetFormId, element){

        var mapping = $(element).data(wxcom.data.attribute.SUBWINDOW_MAPPING);
        wxcom.store.flashStore.put(subwindowKey, {
            "formId" : copyTargetFormId,
            "mapping" : mapping
        });

        var inverseMapping = null;
        if (!wxcom.util.objects.isNullOrUndefined(mapping)){
            inverseMapping = {};
            for (i in mapping){
                inverseMapping[mapping[i]] = i;
            }
        }

        var subwindowForm = $("#" + subwindowFormId);
        var data = $("#" + copyTargetFormId).serializeArray();
        $.each(data, function(){

            if (!wxcom.util.objects.isNullOrUndefined(inverseMapping)){
                var actualName = inverseMapping[this.name];
                if (!wxcom.util.objects.isNullOrUndefined(actualName)){
                    subwindowForm.find("input[name=" + actualName + "]").val(this.value);
                }
            }
            else{
                var name = this.name;
                subwindowForm.find("input[name=" + name + "]").val(this.value);
            }
        });

        wxcom.window.newwindow.open(subwindowFormId, option);
    };

    wxcom.window.subwindow.child = {};
    wxcom.window.subwindow.child.copyOpener = function(subwindowKey, element, attributeName){

        if (!opener){
        	wxcom.window.close();
            return false;
        }

        var meta = opener.wxcom.store.flashStore.get(subwindowKey);
        if (wxcom.util.objects.isNullOrUndefined(meta)){
            // unresolved target form, because opener state changed. e.g window refresh.
        	wxcom.window.close();
            return false;
        }

        var name = attributeName || wxcom.data.attribute.LINK_PARAMS;

       	var json = $(element).data(name);
       	if (wxcom.util.objects.isNullOrUndefined(json)){
       		json = $(element).parent().data(name);
       	}
        var mapping = meta.mapping || {};
       	var form = opener.$('#' + meta.formId);

       	for (var i in json){

            var elementName = i;
            var openerElementName = mapping[i];
            if (!wxcom.util.objects.isNullOrUndefined(openerElementName)){
                elementName = openerElementName;
            }
       		var e = form.find('input[name=' + elementName + ']:not(input[type=hidden])');
            if (!wxcom.util.objects.isNullOrUndefined(e.get(0))){
                e.val(json[i]);
            }
            else{
                e = form.find('span[id=' + elementName + ']');
                e.text(json[i]);
            }
       	}

       	wxcom.window.close();
        return false;
    };

    wxcom.grid = {};

    wxcom.grid.exports = {};

    wxcom.grid.exports.toDataJson = function (tableId, filter) {

        var rows = [];

        var fil = filter;
        if (fil == null) {
            fil = function (propertyName) {
                return true;
            };
        }

        $("#" + tableId + " tbody tr").each(function () {
            var tr = $(this);
            var rowData = [];

            $("td", tr).each(function () {
                var td = $(this);
                var propertyName = td.attr("field");
                if ((propertyName != null) && fil(propertyName)) {
                    var value = td.html();
                    var text = '"' + propertyName + '"' + ':' + '"' + value + '"';
                    rowData.push(text);
                }
            });

            var rowResult = '{' + rowData.join(",") + '}';
            rows.push(rowResult);
        });

        var result = "[" + rows.join(",") + "]";
        return encodeURI(result);
    };

    wxcom.grid.exports.toHeaderJson = function (tableId, filter) {

        var cells = [];

        $("#" + tableId + " th").each(function () {

            var th = $(this);
            var propertyName = th.attr("field");
            var name = th.html();
            // TODO
            var sortPos = 0;
            var unarySortAsc = 0;
            var nestedSortAsc = 0;


            var rowData = [];
            var text1 = '"' + "propertyName" + '"' + ':' + '"' + propertyName + '"';
            var text2 = '"' + "name" + '"' + ':' + '"' + name + '"';
            var text3 = '"' + "sortPos" + '"' + ':' + '"' + sortPos + '"';
            var text4 = '"' + "unarySortAsc" + '"' + ':' + '"' + unarySortAsc + '"';
            var text5 = '"' + "nestedSortAsc" + '"' + ':' + '"' + nestedSortAsc + '"';
            rowData.push(text1);
            rowData.push(text2);
            rowData.push(text3);
            rowData.push(text4);
            rowData.push(text5);

            var rowResult = '{' + rowData.join(",") + '}';
            cells.push(rowResult);

        });

        var result = "[" + cells.join(",") + "]";
        return encodeURI(result);
    };

    wxcom.grid.exports.base = function (formId, tableId, mediaType, filter) {
        var data = wxcom.grid.exports.toDataJson(tableId, filter);
        var header = wxcom.grid.exports.toHeaderJson(tableId, filter);
        $("#dataJson").val(data);
        $("#headerJson").val(header);
        $("#mediaType").val(mediaType);
        wxcom.form.submit(formId);
    };


    wxcom.grid.exports.pdf = function (formId, tableId, filter) {
        wxcom.grid.exports.base(formId, tableId, "pdf", filter);
    };

    wxcom.grid.exports.csv = function (formId, tableId, filter) {
        wxcom.grid.exports.base(formId, tableId, "pdf", filter);
    };

    wxcom.grid.datatable = {

        config : {
        	DEFAULT_SCROLL_X: "100%",
            MIN_SIZE:60,
            PER_ROW_SIZE:30,
            DEFAULT_MAX_SIZE:350,
            DEFAULT_MIN_SIZE:350,
            ADJUSTMENT_Y:0
        },
        
        references : {
        },
        
        adjustSize:function (rowCount) {
            return wxcom.grid.datatable.adjustMinMaxSize(rowCount, wxcom.grid.datatable.config.PER_ROW_SIZE, wxcom.grid.datatable.config.DEFAULT_MIN_SIZE, wxcom.grid.datatable.config.DEFAULT_MAX_SIZE,wxcom.grid.datatable.config.ADJUSTMENT_Y);
        },
        
        adjustMinMaxSize:function (rowCount, perRow, min, max, heightAdjustment) {

            var perRowSize = wxcom.ui.css.cssToNumber(perRow);
            var size = (rowCount * perRow) + heightAdjustment;
            var minSize = wxcom.ui.css.cssToNumber(min);
            var maxSize = wxcom.ui.css.cssToNumber(max);
            size = min + size;

            if (size < minSize) {
                return minSize;
            }
            else if (size < maxSize) {
                return size;
            }
            else {
                return maxSize;
            }

        }

    };

    wxcom.grid.datatable.prepare = function(){
        wxcom.grid.datatable.register();
    };

    wxcom.grid.datatable.register = function(){

        var ext = $.fn.dataTableExt;

        var numericCommna = wxcom.grid.datatable.sort.numericComma();
        ext.oSort[numericCommna.preKey] = numericCommna.preFunction;
        ext.oSort[numericCommna.ascKey] = numericCommna.ascFunction;
        ext.oSort[numericCommna.descKey] = numericCommna.descFunction;

        var blankNumericComma = wxcom.grid.datatable.sort.blankNumericComma();
        ext.oSort[blankNumericComma.preKey] = blankNumericComma.preFunction;
        ext.oSort[blankNumericComma.ascKey] = blankNumericComma.ascFunction;
        ext.oSort[blankNumericComma.descKey] = blankNumericComma.descFunction;

        wxcom.grid.datatable.register.overrides(ext);

    };

    // can override your project.
    wxcom.grid.datatable.register.overrides = function(dataTableExt){
    };

    wxcom.grid.datatable.sort = {};
    wxcom.grid.datatable.sort.numericComma = function(){

        var meta = {
            "key" : "numeric-comma-asc",
            "preKey" : "numeric-comma-pre",
            "preFunction" : function(a){
                var x = (a == "-" || $.trim(a) == "") ? 0 : wxcom.format.number.toNumber(a);
                if (!wxcom.format.util.isNumber(x)){
                    x = 0;
                }
                return x;
            },
            "ascKey" : "numeric-comma-asc",
            "ascFunction" : function(a, b){
                var x = parseFloat( a );
                var y = parseFloat( b );
                return ((x < y) ? -1 : ((x > y) ?  1 : 0));
            },
            "descKey" : "numeric-comma-desc",
            "descFunction" : function(a, b){
                var x = parseFloat( a );
                var y = parseFloat( b );
                return ((x < y) ?  1 : ((x > y) ? -1 : 0));
            }
        };
        return meta;

    };

    wxcom.grid.datatable.sort.blankNumericComma = function(){

        var meta = {
            "key" : "blank-numeric-comma-asc",
            "preKey" : "blank-numeric-comma-pre",
            "preFunction" : function(a){
                var x = (a == "-" || $.trim(a) == "") ? "" : wxcom.format.number.toNumber(a);
                if (!wxcom.format.util.isNumber(x)){
                    x = "";
                }
                return x;
            },
            "ascKey" : "blank-numeric-comma-asc",
            "ascFunction" : function(a, b){
                if(a == ""){
                	return 2;
                }
                if(b == ""){
                	return -1;
                }
                var x = parseFloat( a );
                var y = parseFloat( b );
                return ((x < y) ? -1 : ((x > y) ?  1 : 0));
            },
            "descKey" : "blank-numeric-comma-desc",
            "descFunction" : function(a, b){
                if(a == ""){
                	return -2;
                }
                if(b == ""){
                	return 1;
                }
                var x = parseFloat( a );
                var y = parseFloat( b );
                return ((x < y) ?  1 : ((x > y) ? -1 : 0));
            }
        };
        return meta;

    };

    wxcom.autoformat = {};

    wxcom.autoformat.formatDate = function (n, value) {
        if (value.length == 0) {
            return value;
        }
        var text = $.trim(value);

        var options = wxcom.format.date.options(n);

        var date = wxcom.format.date.toDate(options.textFormat, value);
        var result = wxcom.format.date.toText(options.dispFormat, date);
        if (result != null){
            return result;
        }
        else{
            return value;
        }
    };


    wxcom.autoformat.toHHMMSS = function (value) {
        if (value.length == 0) {
            return value;
        }
        var text = $.trim(value);
        var time = text;
        if (text.length == 4) {
            time = text.substring(0, 2) + ":" + text.substring(2, 4);
        }
        else if (text.length == 6) {
        	time = text.substring(0, 2) + ":" + text.substring(2, 4) + ":" + text.substring(4, 6);
        }
        return time;
    };

    wxcom.autoformat.date = function (className) {
        var selector = (className === undefined) ? '.datepicker' : '.' + className;
        $(selector).each(function () {
            var value = $(this).val();
            var date = wxcom.autoformat.formatDate(this, value);
            $(this).val(date);
        });
    };

    wxcom.autoformat.dateWithIcon = function (className) {
        var selector = (className === undefined) ? '.date-with-icon' : '.' + className;
        $(selector).each(function () {
            var value = $(this).val();
            var date = wxcom.autoformat.formatDate(this, value);
            $(this).val(date);
        });
    };

    wxcom.autoformat.dateWithoutCalendar = function (className) {
        var selector = (className === undefined) ? '.date-without-calendar' : '.' + className;
        $(selector).each(function () {
            var value = $(this).val();
            var date = wxcom.autoformat.formatDate(this, value);
            $(this).val(date);
        });
    };

    wxcom.autoformat.dateFromTo = function (className) {
        var selector = (className === undefined) ? '.date-from-to' : '.' + className;
        $(selector).each(function () {
            var value = $(this).val();
            var date = wxcom.autoformat.formatDate(this, value);
            $(this).val(date);
        });
    };


    wxcom.autoformat.dispdate = function (className) {
        var selector = (className === undefined) ? '.dispdate' : '.' + className;
        $(selector).each(function () {
            var value = $(this).get(0).innerHTML;
            value = $.trim(value);
            if (value == null || value == "") {
                return;
            }
            var date = wxcom.autoformat.formatDate(this, value);
            $(this).get(0).innerHTML = date;
        });
    };

    wxcom.autoformat.hiddendate = function (className) {
        var selector = (className === undefined) ? '.hiddendate' : '.' + className;
        $(selector).each(function () {
            var node = $(this);
            var value = node.val();
            var format = node.data(wxcom.data.attribute.DATE_FORMAT_DISP);
            if (format === undefined) {
                node.data(wxcom.data.attribute.DATE_FORMAT_DISP, 'yymmdd');
            }
            var date = wxcom.autoformat.formatDate(this, value);
            $(this).val(date);
        });
    };

    wxcom.autoformat.time = function (className) {
        var selector = (className === undefined) ? '.timepicker' : '.' + className;
        $(selector).each(function () {
            var value = $(this).val();
            var result = wxcom.format.time.toDisplay($(this).val());
            $(this).val(result);
        });
    };

    wxcom.autoformat.disptime = function (className) {
        var selector = (className === undefined) ? '.disptime' : '.' + className;
        $(selector).each(function () {
            var value = $(this).get(0).innerHTML;
            var time = wxcom.autoformat.toHHMMSS(value);
            $(this).get(0).innerHTML = time;
        });
    };


    wxcom.autoformat.number = function (className) {
        var selector = (className === undefined) ? '.number' : '.' + className;
        $(selector).each(function () {
            var result = wxcom.format.number.toDisplay($(this).val());
            $(this).val(result);
        });
    };

    wxcom.autoformat.decimal = function (className) {
        var selector = (className === undefined) ? '.decimal' : '.' + className;
        $(selector).each(function () {

            var options = wxcom.format.decimal.options(this);
            var result = wxcom.format.decimal.toDisplay(options.numberFraction, $(this).val());
            $(this).val(result);
        });
    };

    wxcom.autoformat.dispnumber = function (className) {
        var selector = (className === undefined) ? '.dispnumber' : '.' + className;
        $(selector).each(function () {
            var result = wxcom.format.number.toDisplay($(this).get(0).innerHTML);
            $(this).get(0).innerHTML = result;
        });
    };

    wxcom.autoformat.dispdecimal = function (className) {
        var selector = (className === undefined) ? '.dispdecimal' : '.' + className;
        $(selector).each(function () {

            var options = wxcom.format.decimal.options(this);
            var result = wxcom.format.decimal.toDisplay(options.numberFraction, $(this).get(0).innerHTML);
            $(this).get(0).innerHTML = result;
        });
    };

    wxcom.autoformat.placeholder = function (className) {
        var selector = (className === undefined) ? '.formtips' : '.' + className;
        $(selector).formtips();
    };

    wxcom.format = {};

    wxcom.format.util = {};

    wxcom.format.util.isNumber = function (value) {
        var result = "";
        try {
        	var n = parseInt(value, 10);
        	if (isNaN(n)){
        		return false;
        	}
            result = n.toString();
        }
        catch (e) {
            return false;
        }
        return true;
    };

    wxcom.format.number = {};
    wxcom.format.number = function (selector) {
        $(selector).each(function () {
            $(this).focus(function () {
                var result = wxcom.format.number.toNumber($(this).val());
                $(this).val(result);
            });
            $(this).blur(function () {
                var result = wxcom.format.number.toDisplay($(this).val());
                $(this).val(result);
            });
        });
    };

    wxcom.format.number.toNumber = function (value) {
        if (value.length == 0) {
            return value;
        }
        var result = $.trim(value).replace(/,/g, "");
        return result;
    };

    wxcom.format.number.toDisplay = function (value) {
        if (value.length == 0) {
            return value;
        }

        value = $.trim(value).replace(/,/g, "");
        if (!value.match(/^[\-\+]?(([0-9][0-9]+|[0-9])|([1-9][0-9]+(\.[0-9]+)|[0-9](\.[0-9]+)))$/)) {
            return value;
        }

        if (!wxcom.format.util.isNumber(value)) {
            return value;
        }

        var result = value;

        var s = "";
        while (result != (s = result.replace(/^([+-]?\d+)(\d\d\d)/, "$1,$2"))) {
            result = s;
        }

        return result;
    };

    wxcom.format.decimal = function (selector) {
        $(selector).each(function () {

            $(this).focus(function () {
                var result = wxcom.format.number.toNumber($(this).val());
                $(this).val(result);
            });
            $(this).blur(function () {
                var options = wxcom.format.decimal.options(this);
                var result = wxcom.format.decimal.toDisplay(options.numberFraction, $(this).val());
                $(this).val(result);
            });
        });
    };

    wxcom.format.decimal.options = function(n){

        var node = $(n);
        var overrides = {
            numberFraction:node.data(wxcom.data.attribute.NUMBER_FRACTION)
        };
        var options = $.extend({}, { numberFraction:'2' }, overrides);
        return options;
    };

    wxcom.format.decimal.toDisplay = function (n, value) {

        if (value.length == 0) {
            return value;
        }
        var baseValue = value;
    	var point = value.indexOf(".");
    	var pointJudge = point;
    	var giveFraction = "";
		var fractionData = "";
        var numberFraction = Number(n);
    	if (pointJudge < 0) {
            point = value.length;
     		for (var f = 0; f < numberFraction; f++) {
    			giveFraction = giveFraction + "0";
    		}
    	}
    	else {
    		fractionData = value.substring(point + 1, value.length);
    		if (fractionData.length == numberFraction) {
                if (!fractionData.match(/^[\-\+]?(([0-9][0-9]+|[0-9]))$/)) {
                    return baseValue;
                }
    		}
    		else if (fractionData.length > numberFraction) {
	            if (!fractionData.match(/^[\-\+]?(([0-9][0-9]+|[0-9]))$/)) {
	                return baseValue;
	            }
     			value = value.substring(0, point + numberFraction + 1);
    			fractionData = value.substring(point + 1, value.length);
    		}
    		else if (fractionData.length < numberFraction && fractionData.length > 0) {
                if (!fractionData.match(/^[\-\+]?(([0-9][0-9]+|[0-9]))$/)) {
                    return baseValue;
                }
         		giveFraction = "";
        		for (var f = 0; f < numberFraction - fractionData.length; f++) {
        			giveFraction = giveFraction + "0";
        		}
    		}
    		else if (fractionData.length == 0) {
    			giveFraction = "";
        		for (var f = 0; f < numberFraction; f++) {
        			giveFraction = giveFraction + "0";
        		}
    		}
    	}
    	value = value.substring(0, point);
    	result = $.trim(value).replace(/,/g, "");
        if (!result.match(/^[\-\+]?(([0-9][0-9]+|[0-9])|([1-9][0-9]+(\.[0-9]+)|[0-9](\.[0-9]+)))$/)) {
            return baseValue;
        }

        if (!wxcom.format.util.isNumber(result)) {
            return baseValue;
        }

        var s = "";
        while (result != (s = result.replace(/^([+-]?\d+)(\d\d\d)/, "$1,$2"))) {
            result = s;
        }

        if (giveFraction != ""){
    		result = result + "." + fractionData + giveFraction;
    	}
        else {
        	result = result + "." + fractionData;
        }
    	return result;
    };


    wxcom.format.date = function (selector) {
        $.datepicker.setDefaults($.extend($.datepicker.regional['ja']));
        $(selector).each(function () {

            var options = wxcom.format.date.options(this);

            $(this).datepicker({
                        dateFormat:options.dispFormat,
                        beforeShowDay:wxcom.calendar.datepicker.beforeShow,
                        onSelect : wxcom.calendar.datepicker.onSelect
            });
            wxcom.format.date.bind(this, options);
        });
    };

    wxcom.format.dateWithIcon = function (selector) {
        $.datepicker.setDefaults($.extend($.datepicker.regional['ja']));
        $(selector).each(function () {

            var options = wxcom.format.date.options(this);

            $(this).datepicker({
                        dateFormat:options.dispFormat,
                        showOn:"button",
                        numberOfMonths:options.numberOfMonths,
                        buttonImage:wxcom.calendar.image(),
                        buttonImageOnly:true,
                        beforeShowDay:wxcom.calendar.datepicker.beforeShow,
                        onSelect : wxcom.calendar.datepicker.onSelect
            });
            wxcom.format.date.bind(this, options);
        });
    };

    wxcom.format.dateWithoutCalendar = function (selector) {

        $(selector).each(function () {
            var options = wxcom.format.date.options(this);
            wxcom.format.date.bind(this, options);
        });
    };

    wxcom.format.dateFromTo = function (selector) {
        $.datepicker.setDefaults($.extend($.datepicker.regional['ja']));

        var pairs = new Array();
        var fromSelector = selector + ":not([id$=To])";

        $(fromSelector).each(function(){

            var fromId = this.id;
            var toId = fromId.replace("From", "To");
            if ($("#" + toId).size() == 1){
                pairs.push({ from:"#"+fromId, to:"#"+toId });
            }

        });

        for (var i =0; i < pairs.length ; i++){
            var pair = pairs[i];

            var fromToSelector = [ pair.from + ":not([type=hidden])", pair.to + ":not([type=hidden])" ].join(",");
            var options = wxcom.format.date.options(pair.from);

            $(fromToSelector).datepicker({
                        dateFormat:options.dispFormat,
                        showOn:"button",
                        numberOfMonths:options.numberOfMonths,
                        buttonImage:wxcom.calendar.image(),
                        buttonImageOnly:true,
                        beforeShowDay:wxcom.calendar.datepicker.beforeShow,
                        onSelect : wxcom.calendar.datepicker.onSelect
            });

            $(fromToSelector).each(function () {
                wxcom.format.date.bind(this, options);
            });
        }
    };

    wxcom.format.date.blur = function(node, options){

        var text = $.trim($(node).val());
        var date = wxcom.format.date.toDate(options.textFormat, text);
        var result = wxcom.format.date.toText(options.dispFormat, date);

        if (!wxcom.util.strings.isEmpty(result)){
            $(node).val(result);
        }
        else{
            date = wxcom.format.date.toDate(options.dispFormat, text);
            result = wxcom.format.date.toText(options.dispFormat, date);
            if (!wxcom.util.strings.isEmpty(result)){
                $(node).val(result);
            }
            // input value is illegal format.
            else{
                if (options.illegalFormatToEmpty){
                    $(node).val("");
                }
            }
        }

    };

    wxcom.format.date.bind = function(node, options){

        $(node).focus(function () {
            var text = $.trim($(node).val());
            var date = wxcom.format.date.toDate(options.dispFormat, text);
            var result = wxcom.format.date.toText(options.textFormat, date);

            if (!wxcom.util.strings.isEmpty(result)){
                $(node).val(result);
            }
        });

        $(node).blur(function () {
            wxcom.format.date.blur(node, options);
        });
        $(node).keydown(function (e) {
            if ((e.which && e.which == $.ui.ENTER)) {
                wxcom.format.date.blur(node, options);
            }
        });
    };

    wxcom.format.date.options = function(n){

        var overrides = wxcom.format.date.overrides();

        var options = $.extend({}, {
            dispFormat:'yy/mm/dd',
            textFormat:'yymmdd',
            numberOfMonths : 3,
            illegalFormatToEmpty : false
        }, overrides);

        var node = $(n);
        var mergedOptions = $.extend({},
            options,
            {
            dispFormat:node.data(wxcom.data.attribute.DATE_FORMAT_DISP),
            textFormat:node.data(wxcom.data.attribute.DATE_FORMAT_TEXT),
            numberOfMonths:node.data(wxcom.data.attribute.CALENDAR_NUMBER_OF_MONTHS),
            illegalFormatToEmpty:node.data(wxcom.data.attribute.DATE_FORMAT_ILLEGAL_TO_EMPTY)
        });

        return mergedOptions;
    };

    wxcom.format.date.overrides = function(){
        var options = {};
        return options;
    };

    wxcom.format.date.toDate = function (format, value, settings) {
        if (value == null){
            return null;
        }
        try{
            return $.datepicker.parseDate(format, value, settings);
        }
        catch(e){
            return null;
        }
    };

    wxcom.format.date.toText = function (format, date, settings) {
        if (date == null){
            return null;
        }
        return $.datepicker.formatDate(format, date, settings);
    };


    wxcom.format.time = function (selector, step) {
        $(selector).each(function () {
            $(this).focus(function () {
                var result = wxcom.format.time.toText($(this).val());
                $(this).val(result);
            });
            $(this).blur(function () {
                var result = wxcom.format.time.toDisplay($(this).val());
                $(this).val(result);
            });
        });
    };

    wxcom.format.time.toText = function (value) {
        if (value.length == 0) {
            return value;
        }
        var result = $.trim(value).replace(/:/g, "");
        if (!wxcom.format.util.isNumber(result)) {
            return value;
        }
        var times = value.split(":");
        if (times.length == 2){
            var hh = times[0];
            var mm = times[1];
            if (hh.length == 1) {
                hh = "0" + hh;
            }
            if (mm.length == 1) {
                mm = "0" + mm;
            }
            return hh + mm;
        }
        else if (times.length == 3){
            var hh = times[0];
            var mm = times[1];
            var ss = times[2];
            if (hh.length == 1) {
                hh = "0" + hh;
            }
            if (mm.length == 1) {
                mm = "0" + mm;
            }
            if (ss.length == 1) {
                ss = "0" + ss;
            }
            return hh + mm + ss;
        }

        return result;
    };

    wxcom.format.time.toDisplay = function (value) {
        if (value.length == 0) {
            return value;
        }
        var text = wxcom.format.time.toText($.trim(value));
        if (!wxcom.format.util.isNumber(text)) {
            return value;
        }

        var canParse = 3 <= text.length && text.length <= 6;
        if (!canParse) {
            return text;
        }

        if (!wxcom.format.util.isNumber(text)) {
            return text;
        }

        if (text.length == 3){
            var hh = text.substring(0, 2);
            var mm = "0" + text.substring(2, 3);
            var result = hh + ":" + mm;
            return result;
        }
        else if (text.length == 4){
            var hh = text.substring(0, 2);
            var mm = text.substring(2, 4);
            var result = hh + ":" + mm;
            return result;
        }
        else if (text.length == 5){
            var hh = text.substring(0, 2);
            var mm = text.substring(2, 4);
            var ss = "0" + text.substring(4, 5);
            var result = hh + ":" + mm + ":" + ss;
            return result;
        }
        else if (text.length == 6){
            var hh = text.substring(0, 2);
            var mm = text.substring(2, 4);
            var ss = text.substring(4, 6);
            var result = hh + ":" + mm + ":" + ss;
            return result;
        }

    };

    wxcom.format.grid = function (selector) {
        // TODO 同一行は1行に表示されてしまう？（行のすべての項目が同じ行が存在すると、2行でなく、1行として表示されるかも）
        if ($(selector).size() != 0) {

            $(selector).each(function () {
                var sScrollY = $(this).data(wxcom.data.attribute.GRID_SCROLL_Y);
                if (wxcom.util.objects.isNullOrUndefined(sScrollY)) {
                    var rowCount = $('tbody tr', this).size();
                    var min = $(this).data(wxcom.data.attribute.GRID_SCROLL_Y_MIN) || wxcom.grid.datatable.config.DEFAULT_MIN_SIZE;
                    var max = $(this).data(wxcom.data.attribute.GRID_SCROLL_Y_MAX) || wxcom.grid.datatable.config.DEFAULT_MAX_SIZE;
                    var perRow = $(this).data(wxcom.data.attribute.GRID_PER_ROW) || wxcom.grid.datatable.config.PER_ROW_SIZE;
                    var heightAdjustment = $(this).data(wxcom.data.attribute.GRID_ADJUSTMENT_Y) || wxcom.grid.datatable.config.ADJUSTMENT_Y;
                    
                    sScrollY = wxcom.grid.datatable.adjustMinMaxSize(rowCount, perRow, min, max, heightAdjustment);
                }
                var sScrollX = $(this).data(wxcom.data.attribute.GRID_SCROLL_X);
                if (wxcom.util.objects.isNullOrUndefined(sScrollX)) {
                	sScrollX =  wxcom.grid.datatable.config.DEFAULT_SCROLL_X;
                }
                // application default
                var overrides = wxcom.format.grid.overrides(this, sScrollX, sScrollY);

                // wxcom default
                var options = wxcom.format.grid.options(this, overrides);

                // view option
                var viewOptions = $(this).data(wxcom.data.attribute.GRID_OPTIONS);
                if (!wxcom.util.objects.isNullOrUndefined(viewOptions)){
                    options = $.extend({}, options, viewOptions);
                }
                if (!wxcom.util.objects.isNullOrUndefined(options["bSort"])){
                    if (wxcom.util.objects.isNullOrUndefined(options["aoColumnDefs"]) && wxcom.util.objects.isNullOrUndefined(options["aoColumns"])){
                        var aoColumnDefs = wxcom.format.grid.column.getColumnDefs(this);
                        options["aoColumnDefs"] = aoColumnDefs;
                    }
                }

                var datatable = $(this).dataTable(options);
                var id = $(this).attr("id");
                if (!wxcom.util.objects.isNullOrUndefined(id)){
                	wxcom.grid.datatable.references[id] = datatable;
                }
            });
        }
    };

    wxcom.format.grid.options = function(n, overrides){
        var options = $.extend({}, {
            "bPaginate":false,
            "bFilter":false,
            "bInfo":false
        }, overrides);
        return options;
    };

    wxcom.format.grid.overrides = function(n, sScrollX, sScrollY){
        var options = {
            "sScrollX": sScrollX,
            "sScrollY": sScrollY
        };
        return options;
    };

    wxcom.format.grid.column = {};
    wxcom.format.grid.column.createColumnDef = function(index, aLayout, tableNode){

        var thNode = $(aLayout.cell);

        var sortType = wxcom.format.grid.column.getSortType(thNode);
        var sortable = wxcom.format.grid.column.getSortable(thNode);
        var aoColumnDef = {
            "aTargets" : [ index ],
            "sType" : sortType,
            "bSortable" : sortable
        };

        aoColumnDef = wxcom.format.grid.column.overrides(index, aoColumnDef, aLayout, tableNode);
        return aoColumnDef;
    };

    wxcom.format.grid.column.getSortType = function(tdNode){
        var sortType = $(tdNode).data(wxcom.data.attribute.GRID_COLUMN_SORT_TYPE);
        if (!wxcom.util.objects.isNullOrUndefined(sortType)){
            return sortType;
        }
        else{
            return null;
        }
    };


    wxcom.format.grid.column.getSortable = function(tdNode){
        var sortable = $(tdNode).data(wxcom.data.attribute.GRID_COLUMN_SORTABLE);
        if (!wxcom.util.objects.isNullOrUndefined(sortable)){
            return sortable;
        }
        else{
            return true;
        }
    };

    // override can your project.
    wxcom.format.grid.column.overrides = function(index, aoColumnDef, aLayout, tableNode){
        return aoColumnDef;
    };

    wxcom.format.grid.column.getColumnDefs = function(tableNode){

        var theadNode = $("thead", tableNode);
        var thead = theadNode.get(0);
        var aoHeader = [];

        var detectHeaderFunction = $.fn.dataTableExt.oApi["_fnDetectHeader"];
        if (wxcom.util.objects.isNullOrUndefined(detectHeaderFunction)){
            // init dataTable
            $().dataTable();
            detectHeaderFunction = $.fn.dataTableExt.oApi["_fnDetectHeader"];
        }
        detectHeaderFunction(aoHeader, thead);

        var aLayout = aoHeader[0];
        var headerSize = aLayout.length;

        var aoColumnDefs = [];

        for (var i =0; i < headerSize ; i++){
            aoColumnDefs[i] = wxcom.format.grid.column.createColumnDef(i, aLayout[i], tableNode);
        }

        return aoColumnDefs;
    };
        
    wxcom.format.gridCheckboxes = function(selector){

    	var toggleClass = wxcom.form.checkboxes.toggleClass;
    	var checlboxSelector = "tr input[type=checkbox][class*=toggle-checkbox]:not([readonly])";
    	var checkboxes = $(selector).find(checlboxSelector);
    	
    	checkboxes.each(function(){
			var c = $(this).attr("checked");
			if (c){
				$(this).parents("tr").addClass(toggleClass);
			}
			else{
				$(this).parents("tr").removeClass(toggleClass);
			}
    	});
    	
    	checkboxes.each(function(){
    		
    		var checkbox = $(this);
    		var tr = checkbox.parents("tr");
    		
    		checkbox.click(function(e, pressShihtKey){
    			        		
        		var last = wxcom.format.gridCheckboxes.meta.last;
        		if(!last) {
            		$(tr).toggleClass(toggleClass);
        			wxcom.format.gridCheckboxes.meta.last = this;
            		e.stopPropagation();
        			return;
        		}
        		
        		if(e.shiftKey || pressShihtKey) {
        			var start = checkboxes.index(this);
        			var end = checkboxes.index(last);
        			var rangeCheckboxes = checkboxes.slice(Math.min(start,end), Math.max(start,end)+ 1);
        			rangeCheckboxes.attr('checked', last.checked);
        			rangeCheckboxes.each(function(){
        				
        				var c = $(this).attr("checked");
        				if (c){
        					$(this).parents("tr").addClass(toggleClass);
        				}
        				else{
        					$(this).parents("tr").removeClass(toggleClass);
        				}
        			});
        			if (pressShihtKey){
            			e.preventDefault();        				
        			}
        		}
        		else{
        			$(tr).toggleClass(toggleClass);
        		}
        		wxcom.format.gridCheckboxes.meta.last = this;
        		e.stopPropagation();
    		});
    		tr.click(function(e){
    			checkbox.trigger("click", [e.shiftKey]);
    		});
    	});
    };
    
    wxcom.format.gridCheckboxes.meta = {
    		last : null
    };

    wxcom.autobind = {};
    wxcom.autobind.number = function (className) {
        var selector = (className === undefined) ? '.number' : '.' + className;
        wxcom.format.number(selector);
    };

    wxcom.autobind.decimal = function (className) {
        var selector = (className === undefined) ? '.decimal' : '.' + className;
        wxcom.format.decimal(selector);
    };

    wxcom.autobind.date = function (className) {
        var selector = (className === undefined) ? '.datepicker' : '.' + className;
        wxcom.format.date(selector);
    };

    wxcom.autobind.dateWithIcon = function (className) {
        var selector = (className === undefined) ? '.date-with-icon' : '.' + className;
        wxcom.format.dateWithIcon(selector);
    };

    wxcom.autobind.dateWithoutCalendar = function (className) {
        var selector = (className === undefined) ? '.date-without-calendar' : '.' + className;
        wxcom.format.dateWithoutCalendar(selector);
    };

    wxcom.autobind.dateFromTo = function (className) {
        var selector = (className === undefined) ? '.date-from-to' : '.' + className;
        wxcom.format.dateFromTo(selector);
    };

    wxcom.autobind.time = function (className) {
        var selector = (className === undefined) ? '.timepicker' : '.' + className;
        wxcom.format.time(selector);
    };

    wxcom.autobind.submitbutton = function (className) {
        var selector = (className === undefined) ? '.button-submit' : '.' + className;
        $(selector).click(function () {
            var oldText = $(this).text();
            var suffix = '中...';
            $(this).text(oldText + suffix);
        });
    };

    wxcom.autobind.grid = function (className) {
        var selector = (className === undefined) ? '.grid' : '.' + className;
        wxcom.format.grid(selector);
    };

    wxcom.autobind.gridCheckboxes = {};
    wxcom.autobind.gridCheckboxes = function (className) {
        var selector = (className === undefined) ? '.grid' : '.' + className;
        wxcom.format.gridCheckboxes(selector);
    };
    
    wxcom.ready = {
    	readyList : new Array()
    };
    
	wxcom.ready.add = function(fn){
		wxcom.ready.readyList.push(fn);
	};
	
	wxcom.ready.fire = function(){
		
		if (wxcom.ready.readyList.length == 0){
			return;
		}
		for (i = 0; i < wxcom.ready.readyList.length; i++) {
			wxcom.ready.readyList[i](document, [jQuery]);
		}
	};

    wxcom.ready.start = function () {

        wxcom.grid.datatable.prepare();

        // jQueryのイベントのバインド
        wxcom.autobind.date();
        wxcom.autobind.dateWithIcon();
        wxcom.autobind.dateWithoutCalendar();
        wxcom.autobind.dateFromTo();
        wxcom.autobind.time();
        wxcom.autobind.number();
        wxcom.autobind.decimal();
        wxcom.autobind.submitbutton();
        wxcom.autobind.grid();
        wxcom.autobind.gridCheckboxes();


        // 初期表示
        wxcom.autoformat.date();
        wxcom.autoformat.dateWithIcon();
        wxcom.autoformat.dateWithoutCalendar();
        wxcom.autoformat.dateFromTo();
        wxcom.autoformat.dispdate();
        wxcom.autoformat.time();
        wxcom.autoformat.disptime();
        wxcom.autoformat.hiddendate();
        wxcom.autoformat.number();
        wxcom.autoformat.dispnumber();
        wxcom.autoformat.decimal();
        wxcom.autoformat.dispdecimal();
        wxcom.autoformat.placeholder();

        wxcom.form.paging.prepare();

        wxcom.ready.fire();
    };
})();
