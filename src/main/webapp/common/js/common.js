$(function() {
    userInit.getUserInfo();
});

var userInit = {
    //获取用户信息
    getUserInfo: function() {
        $.ajax({
            url: '/dlc_mk/dlc/user/getuserinfo?format=json',
            type: 'GET',
            async: false,
            cache: false,
            dataType: 'json',
//            contentType: 'application/x-www-form-urlencoded;charset=utf-8',
        }).then(
            function success(response) {
                //判断是否登录状态
                notLogin(response.code);

                var data = response.header.data;
                $('.userName').html(data.userName);
                //管理员权限
                $userAdmin = $('.fileUploadMapping,#userManageAdmin');
                if (data.roleName == 'admin') {
                    $userAdmin.css('display', 'block');
                } else {
                    $userAdmin.remove();
                }
            },
            function error(e) {
                layer.msg('获取用户权限异常！', {
                    icon: 2
                });
            });
    },
    logOut: function() {
        $.ajax({
            url: '/dlc_mk/dlc/user/logout/?format=json',
            type: 'GET',
            //async: false,
            dataType: 'json',
            contentType: 'application/x-www-form-urlencoded;charset=utf-8',
        }).then(
            function success(response) {
                //判断是否登录状态
                notLogin(response.code);
                if (response.code == 1003) {
 
                    window.location.replace('/dlc_mk/dlc/user/login');
                }
            },
            function error(e) {
                layer.msg('退出登录异常！', {
                    icon: 2
                });
            });
    }
};

var notLogin = function(code) {
    if (code == 8003) {
        window.location.href = '/dlc_mk/dlc/user/login';
    }
};
