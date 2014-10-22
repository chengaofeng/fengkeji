var mockOnLoad = false;
var mock = {};
(function () {

    mock.root = '/WEB-INF/';

    mock.path = {};
    mock.path.calculate = function () {
        var pathname = document.location.pathname;
        var root = mock.root;
        var rootIndex = pathname.indexOf(root, 1);
        var htmlPath = pathname.substr(rootIndex);
        var count = htmlPath.split('/').length - 2;
        var relativePath = '';
        for (var i = 0; i < count; i++) {
            relativePath = relativePath.concat('../');
        }
        return relativePath;
    };


    mock.load = {};
    mock.load.js = function (src, check, next) {
        check = new Function('return !!(' + check + ')');
        if (!check()) {
            var script = document.createElement('script');
            script.src = src;
            var head = document.getElementsByTagName('head');
            //document.body.appendChild(script);
            head.item(0).appendChild(script);
            setTimeout(function () {
                if (!check()) {
                    setTimeout(arguments.callee, 100);
                } else {
                    if (next) {
                        next();
                    }
                }
            }, 100);
        } else {
            if (next) {
                next();
            }
        }
    };

    mock.load.css = function (src, title) {
        var css = document.createElement('link');
        css.href = src;
        css.type = 'text/css';
        css.rel = 'stylesheet';
        css.media = 'all';
        if (title) {
            css.title = title;
        }
        var head = document.getElementsByTagName('head');
        head.item(0).appendChild(css);
    };

    mock.load.alternatecss = function (src, title) {
        var css = document.createElement('link');
        css.href = src;
        css.type = 'text/css';
        css.rel = 'alternate stylesheet';
        css.media = 'all';
        css.title = title;
        var head = document.getElementsByTagName('head');
        head.item(0).appendChild(css);

    };

    mock.load.html = function (relativePath) {

        var includeCount = $('div[include]').size();
        var count = 0;
        if (includeCount != 0) {
            $('div[include]').each(function () {
                var that = $(this);
                var value = $(this).attr("th:include");
                var parts = value.substring(0, value.indexOf("::"));
                parts = $.trim(parts);
                var path = relativePath + mock.root + "views/" + parts + ".html";
                var url = path;
                $.ajax({
                    type:"GET",
                    url:url,
                    dataType:"html",
                    success:function (data) {
                        //wxcom.logger.log(data);
                        count = count + 1;
                        var html = data;
                        $("div[fragment]", data).each(function () {
                            var content = $(this).html();
                            that.html(content);
                        });
                        if (includeCount <= count) {
                            mockOnLoad = true;
                        }
                    },
                    error:function (xhr, s, ex) {
                        wxcom.logger.log(xhr);
                        wxcom.logger.log(s);
                        wxcom.logger.log(ex);
                        // TODO
                        mockOnLoad = true;
                    }
                });
            });
        }
        else {
            mockOnLoad = true;
        }
    };

    mock.load.resolveLayout = function (relativePath){

        var body = $("body");
        var layout = body.attr("data-sitemesh-layout");
        if (layout != null){
            return relativePath + mock.root + "views/common/" + layout + ".html";
        }
        else{
            return relativePath + mock.root + "views/common/layout_menu.html";
        }
    };

    mock.load.layout = function (relativePath) {

        mock.load.hideLoadingMessage();

        var url = mock.load.resolveLayout(relativePath);

        var that = $("body");
        var currentHtml = that.html();

        $.ajax({
            type:"GET",
            url:url,
            dataType:"html",
            success:function (data) {
                var content = $(data);
                var layoutBody = $("#body", content);
                that.html(layoutBody);
                var mainContent = $("#data-sitemesh-content", that);
                mainContent.html(currentHtml);
                mock.load.html(relativePath);
            },
            error:function (xhr, s, ex) {
                wxcom.logger.log(xhr);
                wxcom.logger.log(s);
                wxcom.logger.log(ex);
                // TODO
                mockOnLoad = true;
            }
        });
    };

    mock.load.loading = null;
    mock.load.showLoadingMessage = function(){
        var dialog = $("<div/>").attr("id", "mockLoadingMessage").text("loading layout...");
        mock.load.loading = dialog.dialog({modal:true});
    };

    mock.load.hideLoadingMessage = function(){
        mock.load.loading.dialog("close");
        mock.load.loading.dialog("destory");
        mock.load.loading = null;
    };

    mock.load.queue = {};

    mock.load.queue.context = {};

    mock.load.queue.contains = function(context, func){
        var task = context.task;
        for(var i = 0; i < task.length; i++){
            if (task[i].toString() == func.toString()){
                return true;
            }
        }
        return false;
    };

    mock.load.waitonExecute = function(check, expression){
        var ctx = mock.load.queue.context[expression];
        if (ctx.already){
            return;
        }

        var task = ctx.task;
        for(var i = 0; i < task.length; i++){
            task[i]();
        }
        ctx.already = true;
    };

    mock.load.waitonInternal = function(check, expression){

        if (!check()) {
            setTimeout(function () {
                if (!check()) {
                    setTimeout(arguments.callee, 100);
                } else {
                    mock.load.waitonExecute(check, expression);
                }
            }, 100);
        } else {
            mock.load.waitonExecute(check, expression);
        }

    };

    mock.load.waiton = function (func, expression) {

        var ctx = mock.load.queue.context[expression];
        if (ctx == null){
            ctx = {};
            ctx.already = false;
            ctx.task = new Array();
            mock.load.queue.context[expression] = ctx;
        }

        if (!mock.load.queue.contains(ctx, func)){
            ctx.task.push(func);
        }

        var check = new Function('return !!(' + expression + ')');
        if (!check()) {
            mock.load.waitonInternal(check, expression);
        } else {
            mock.load.waitonExecute(check, expression);
        }

    };

})();

var relativePath = mock.path.calculate();

mock.load.css(relativePath + 'assets/${artifactId}/src/main/webapp/assets/lib/jquery/jquery-ui/css/theme/jquery-ui-1.10.2.css', 'wx');
mock.load.css(relativePath + 'assets/lib/jquery/tablesorter/themes/blue/style.css');
mock.load.css(relativePath + 'assets/lib/jquery/jquery-ui-bootstrap/bootstrap/bootstrap.css');
mock.load.css(relativePath + 'assets/css/common.css');
mock.load.css(relativePath + 'assets/css/decorators.css');


mock.load.js(relativePath + 'assets/lib/styleswitcher/styleswitcher.js', 'window.readCookie', function () {
    mock.load.js(relativePath + 'assets/lib/jquery/jquery-1.7.js', 'window.jQuery', function () {
        mock.load.js(relativePath + 'assets/lib/jquery/jquery-ui/js/jquery-ui-1.10.2.custom.js', 'window.jQuery.ui', function () {
            mock.load.showLoadingMessage();
            mock.load.js(relativePath + 'assets/lib/jquery/jquery-ui/js/i18n/jquery-ui-i18n.js', 'window.jQuery.datepicker.regional', function () {
                mock.load.js(relativePath + 'assets/lib/jquery/tablesorter/jquery.tablesorter.min.js', 'window.jQuery.tablesorter', function () {
                    mock.load.js(relativePath + 'assets/lib/jquery/DataTables/js/jquery.dataTables.min.js', 'window.jQuery.fn.dataTable', function () {
                        mock.load.js(relativePath + 'assets/lib/jquery/highlight/jquery.highlight.js', 'window.jQuery.fn.highlight', function () {
                            mock.load.js(relativePath + 'assets/lib/jquery/password_strength/password_strength_plugin.js', 'window.jQuery.fn.passStrength', function () {
                                mock.load.js(relativePath + 'assets/lib/jquery/jquery-formtips/jquery.formtips.1.2.5.js', 'window.jQuery.fn.formtips', function () {
                                    mock.load.js(relativePath + 'assets/lib/jquery/clearable_text_field/js/jquery.clearableTextField.js', 'window.jQuery.fn.clearableTextField', function () {
                                        mock.load.js(relativePath + 'assets/js/common.js', 'window.wxcom', function () {
                                                mock.load.layout(relativePath);
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
        });
    });
});