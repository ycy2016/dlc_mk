$(function() {
    userManagement.init();
});
var userManagement = {
    $userManagementTable: $('#userManagementTable'),
    init: function() {
        $('.userManage').parent().addClass('active');
        userManagement.userManagementAjax();
    },
    //获取用户数据
    userManagementAjax: function() {
        $.ajax({
            url: "/dlc_mk/dlc/cruduser/getuserbyall/?format=json",
            type: 'GET',
            dataType: 'json',
//            contentType: 'application/x-www-form-urlencoded;charset=utf-8',
        }).then(
            function success(response) {
                //判断是否登录状态
                notLogin(response.code);
                userManagement.userManagementTableFun(response.header.data);
            },
            function error(e) {
                layer.msg('获取用户数据异常！', {
                    icon: 2
                });
            });
    },
    //用户数据表格加载
    userManagementTableFun: function(data) {
        userManagement.$userManagementTable.bootstrapTable('destroy');
        userManagement.$userManagementTable.bootstrapTable({
            data: data, //table所展示的数据
            pagination: true, // 设置为 true 会在表格底部显示分页条
            pageSize: 20, //初始化一页显示几条
            paginationPreText: '上一页',
            paginationNextText: '下一页',
            pageList: [10, 20, 50, 100], //选页功能
            search: true, //开始前台搜索功能
            showTitle: true, //设置为true th 显示title
            columns: [{
                field: 'userId',
                title: 'ID',
            }, {
                field: 'account',
                title: '账号'
            }, {
                field: 'userName',
                title: '用户名',

            }, {
                field: 'roleName',
                title: '权限',
            }, {
                field: 'status',
                title: '是否删除',
            }, {
                field: 'userId',
                title: '编辑用户',
                searchable: false,
                formatter: function(value, row, x) {
                    return '<span  value="' + x + '" class="icon eidtUser"></span>';
                }
            }],
        });
        userManagement.$userManagementTable.unbind('click');
        //给编辑添加事件
        userManagement.$userManagementTable.on('click', '.eidtUser', function(e) {
            var idx = $(this).parents('tr').attr('data-index');
            var row = data[idx];
            userManagement.layerOpen('editUser', row);
        });
    },
    //打开弹出框
    layerOpen: function(type, data) {
        layer.open({
            type: 1,
            title: false,
            closeBtn: true,
            area: '500px;',
            shade: 0.8,
            id: 'LAY_layuipro',
            resize: false,
            btn: ['保存', '取消'],
            btnAlign: 'c',
            moveType: 1,
            content: $('.windowModel').html(),
            yes: function(index, layero) {
                //按钮【按钮一】的回调
                var check = userManagement.reg_check(type);
                if (check) {
                    //保存方法
                    if (type == 'addUser') {
                        //添加方法
                        userManagement.addUserAjax(check);
                    } else {
                        //修改方法
                        userManagement.editUserAjax(check);
                    }
                }
            },
            off: function() {
                layer.close();
            },
        });
        var $LAY_layuipro = $('#LAY_layuipro');
        //判断是否是添加方法
        if (type != 'addUser' && data) {
            $LAY_layuipro.find('.title').html('编辑用户');
            $LAY_layuipro.find('input[name=account]').val(data.account).attr('disabled', 'disabled');
            $LAY_layuipro.find('input[name=userName]').val(data.userName);
            $LAY_layuipro.find('input[name=password]').attr('placeholder', '******');

            $LAY_layuipro.find('input[name=status]').removeAttr('disabled');

            if (data.roleName == 'admin') {
                $LAY_layuipro.find('input[name=role]').eq(0).prop('checked', 'checked');
            } else {
                $LAY_layuipro.find('input[name=role]').eq(1).prop('checked', 'checked');
            }
            if (data.status == '是') {
                $LAY_layuipro.find('input[name=status]').eq(1).prop('checked', 'checked');
            } else {
                $LAY_layuipro.find('input[name=status]').eq(0).prop('checked', 'checked');
            }

        } else {
            $LAY_layuipro.find('input[name=status]').attr('disabled', 'disabled');
        }
    },
    //正则表达式校验
    reg_check: function(type) {
        var $userService = $('#LAY_layuipro');
        var reg = /^[-_a-zA-Z0-9]{5,20}$/;
        var data = {
            account: $userService.find('input[name=account]').val().trim(),
            userName: $userService.find('input[name=userName]').val().trim(),
            password: $userService.find('input[name=password]').val().trim(),
            roleId: $userService.find('input[name=role]:checked').val(),
            status: $userService.find('input[name=status]:checked').val()
        };
        if (data.account == '') {
            layer.msg('请输入账号！', {
                icon: 2
            });
            return false;
        } else if (!reg.test(data.account)) {
            layer.msg('账号必须由“[a-zA-Z]”或“_”或“0-9”组成六位以上！', {
                icon: 2
            });
            return false;
        } else if (data.userName == '') {
            layer.msg('请输入用户名！', {
                icon: 2
            });
            return false;
        }
        if (type == "addUser") {
            if (data.password == '') {
                layer.msg('请输入密码！', {
                    icon: 2
                });
                return false;
            } else if (!reg.test(data.password)) {
                layer.msg('密码必须由“[a-zA-Z]”或“_”或“0-9”组成六位以上！', {
                    icon: 2
                });
                return false;
            }
        } else {
            if (data.password != '') {
                if (!reg.test(data.password)) {
                    layer.msg('密码必须由“[a-zA-Z]”或“_”或“0-9”组成六位以上！', {
                        icon: 2
                    });
                    return false;
                }
            } else {
                delete data.password;
            }
        }

        return data;
    },
    //添加用户ajax
    addUserAjax: function(data) {
        $.ajax({
            url: '/dlc_mk/dlc/cruduser/adduser/?format=json',
            type: 'POST',
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: 'application/x-www-form-urlencoded;charset=utf-8',
        }).then(
            function success(response) {
                //判断是否登录状态
                notLogin(response.code);
                if (response.code !== 2003) {
                    layer.closeAll();
                    userManagement.userManagementAjax();
                }

                if (response.code == 2000) {
                    //成功
                    layer.msg(response.msg, {
                        icon: 1
                    });

                } else if (response.code == 2003) {
                    //已存在
                    layer.msg(response.msg, {
                        icon: 2
                    });

                } else if (response.code == 2004) {
                    //失败
                    layer.msg(response.msg, {
                        icon: 2
                    });
                }else {
                	 layer.msg(response.msg, {
                         icon: 2
                     });
                }


            },
            function error(e) {
                layer.closeAll();
                layer.msg('添加用户异常！', {
                    icon: 2
                });
            });
    },
    //修改用户ajax
    editUserAjax: function(data) {
        $.ajax({
            url: '/dlc_mk/dlc/cruduser/updateuser/?format=json',
            type: 'PUT',
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: 'application/x-www-form-urlencoded;charset=utf-8',
        }).then(
            function success(response) {
                //判断是否登录状态
                notLogin(response.code);
                layer.closeAll();
                userManagement.userManagementAjax();
                if (response.code == 2001) {
                    layer.msg(response.msg, {
                        icon: 1
                    });
                } else if (response.code == 2004) {
                    layer.msg(response.msg, {
                        icon: 2
                    });
                }else {
               	 layer.msg(response.msg, {
                     icon: 2
                 });
            }

            },
            function error(e) {
                layer.closeAll();
                layer.msg('编辑用户异常！', {
                    icon: 2
                });
            });
    }
};
