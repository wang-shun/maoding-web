/**
 * 登录
 * Created by wrb on 2018/5/10.
 */

;(function ($, window, document, undefined) {

    "use strict";
    var pluginName = "m_website_login",
        defaults = {
            $type : ''
        };

    function Plugin(element, options) {
        this.element = element;

        this.settings = options;
        this._defaults = defaults;
        this._name = pluginName;
        this.init();
    }

    $.extend(Plugin.prototype, {
        init: function () {
            var that = this;
            that.renderPage();
        }
        , renderPage: function () {
            var that = this;
            var html = template('m_website/m_website_login', {});
            $(that.element).html(html);

            that.login_validate();
            that.initLoginForm();
        }
        ,initLoginForm: function () {
            var that = this;

            $('#password').off('keydown.pwd').on('keydown.login',function (e)
            {
                if (event.keyCode == 13)
                    that.login();
            });

            $('#btnLogin').click(function(){
                that.login();
            });
        },
        login: function () {
            if ($("#loginForm").valid()) {
                var option = {};
                option.url = restApi.url_homeLogin;
                option.postData = {};
                option.postData.cellphone = $('#account').val();
                option.postData.password = $('#password').val();
                m_ajax.postJson(option, function (response) {
                    if (response.code == 0) {

                        removeCookiesOnLoginOut();

                        var url = '/iWork/home/workbench/1';
                        window.location.href = window.rootPath + url;
                    }
                    else {
                        S_toastr['error'](response.info);
                    }
                });
            }
            return false;
        },
        login_validate: function () {//点击登录时进行密码验证
            $("#loginForm").validate({
                rules: {
                    account: "required",
                    password: "required"
                },
                messages: {
                    account: "请输入手机号码",
                    password: "请输入账号密码"
                }
            });
        }
    });

    /*
     1.一般初始化（缓存单例）： $('#id').pluginName(initOptions);
     2.强制初始化（无视缓存）： $('#id').pluginName(initOptions,true);
     3.调用方法： $('#id').pluginName('methodName',args);
     */
    $.fn[pluginName] = function (options, args) {
        var instance;
        var funcResult;
        var jqObj = this.each(function () {

            //从缓存获取实例
            instance = $.data(this, "plugin_" + pluginName);

            if (options === undefined || options === null || typeof options === "object") {

                var opts = $.extend(true, {}, defaults, options);

                //options作为初始化参数，若args===true则强制重新初始化，否则根据缓存判断是否需要初始化
                if (args === true) {
                    instance = new Plugin(this, opts);
                } else {
                    if (instance === undefined || instance === null)
                        instance = new Plugin(this, opts);
                }

                //写入缓存
                $.data(this, "plugin_" + pluginName, instance);
            }
            else if (typeof options === "string" && typeof instance[options] === "function") {

                //options作为方法名，args则作为方法要调用的参数
                //如果方法没有返回值，funcReuslt为undefined
                funcResult = instance[options].call(instance, args);
            }
        });

        return funcResult === undefined ? jqObj : funcResult;
    };

})(jQuery, window, document);