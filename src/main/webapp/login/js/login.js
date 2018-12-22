$(function() {
    button.init(); //button波纹方法
    $('md-input-container input').focus(function(event) {
        $(this).parents('md-input-container').addClass('md-input-focused');
    }).blur(function(event) {
        $(this).parents('md-input-container').removeClass('md-input-focused');
    });
    login.init();
});

var login = {
    // 获取版本号
    init: function() {

        $("body").keydown(function(event) {
            if (event.keyCode == "13") { //keyCode=13是回车键
                $("#login").click();
            }
        });
        $("#login").click(function(event) {
            if (!login.reg_check()) {
                return false;
            }
            login.loginAjax(login.reg_check());
        });
    },
    reg_check: function() { //判断是否为空方法
        if ($('#Name').val().trim() == "") {
            layer.msg('登录名不能为空！', {
                icon: 2
            }); 

            return false;
        } else if ($('#Password').val().trim() == "") {
            layer.msg('密码不能为空！', {
                icon: 2
            });
            return false;
        }
        var data = {
            Name: $('#Name').val(),
            Password: $('#Password').val(),
        };
        return data;
    },
    loginAjax: function(data) { //登陆接口
        $.ajax({
            url: '/dlc_mk/dlc/user/dologin?format=json',
            type: 'POST',
            data: {
                "account": data.Name,
                "password": data.Password
            },
            dataType: 'json',
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
        }).then(
            function success(response) {
                if (response.code == 1000) {
                    window.location.href = "/dlc_mk/dlc/user/home";
                } else if (response.code == 1001) {
                    layer.msg("账号不存在！", {
                        icon: 2
                    });
                } else if (response.code == 1002) {
                    layer.msg("账号或密码错误！", {
                        icon: 2
                    });
                }

            },
            function error(e) {
                console.log(e);
                layer.msg("登录异常！", {
                    icon: 2
                });
            });
    },

};
