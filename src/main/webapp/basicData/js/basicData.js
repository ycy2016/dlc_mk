$(function() {
    basic.init();
});
//基础
var basic = {
    //基础数据表
    $basicTable: $('#basicTable'),
    //详情数据表
    $detailednessTable: $('#detailednessTable'),
    $goBack: $('.go-back'),
    $thisTableName: $('.thisTableName'),
    init: function() {
        basic.getBasicAjax();
        $('.basicsData').parent().addClass('active');
    },
    //获取基本数据
    getBasicAjax: function() {
        layer.msg('数据加载中......', {
            icon: 16,
            time: false,
            shade: [0.5, '#555555']
        });
        $.ajax({
            url: '/dlc_mk/dlc/interface/getinterfacebyall?format=json',
            type: 'GET',
            dataType: 'json',
//            contentType: 'application/x-www-form-urlencoded;charset=utf-8',
        }).then(
            function success(response) {

                layer.closeAll();
                //判断是否登录状态
                notLogin(response.code);
                basic.basicTableFun(response.header.data);
            },
            function error(e) {
                layer.closeAll();
                layer.msg('获取基本数据异常！', {
                    icon: 2
                });
            });
    },
    //初始化基本数据表格
    basicTableFun: function(data) {
        basic.$basicTable.bootstrapTable('destroy');
        basic.$basicTable.bootstrapTable({
            data: data, //table所展示的数据
            pagination: true, // 设置为 true 会在表格底部显示分页条
            pageSize: 20, //初始化一页显示几条
            paginationPreText: '上一页',
            paginationNextText: '下一页',
            pageList: [10, 20, 50, 100], //选页功能
            search: true, //开始前台搜索功能
            showTitle: true, //设置为true th 显示title
            columns: [{
                field: 'interface_id',
                title: '接口代号',
            }, {
                field: 'interface',
                title: '数据接口'
            }, {
                field: 'req_params',
                title: '请求参数',

            }, {
                field: 'matched_rate',
                title: '匹配率',
            }, {
                field: 'covered_operator',
                title: '覆盖运营商',
            }, {
                field: 'price',
                title: '价格',
            }, {
                field: 'interface_id',
                title: '标签详情',
                searchable: false,
                formatter: function(value, row, x) {
                    return '<span interface="' + row.interface + '" value="' + value + '" class="icon searchDetailedness"></span>';
                }
            }],
        });
        basic.$basicTable.unbind('click');
        //查看详情点击事件
        basic.$basicTable.on('click', '.searchDetailedness', function(event) {

            basic.$goBack.css('display', 'block');
            //隐藏主数据table
            basic.$basicTable.parents('.bootstrap-table').css('display', 'none');
            //展示详情table
            basic.$detailednessTable.parents('.bootstrap-table').css('display', 'block');

            var value = $(this).attr('value'),
                interfacename = $(this).attr('interface');
            basic.$thisTableName.text(interfacename);
            detailedness.getDetailednessAjax(value);

        });
    },
    //返回上一页方法
    goBackFun: function() {
        basic.$goBack.css('display', 'none');
        //隐藏详情table
        basic.$detailednessTable.parents('.bootstrap-table').css('display', 'none');
        //展示主数据table
        basic.$basicTable.parents('.bootstrap-table').css('display', 'block');
        basic.$thisTableName.text('基础数据服务产品简介');
        basic.getBasicAjax();
    }
};
//详情
var detailedness = {
    $detailednessTable: $('#detailednessTable'),
    //查看详情数据
    getDetailednessAjax: function(id) {
        $.ajax({
            url: '/dlc_mk/dlc/interface/gettagbyinterfaceid/?format=json',
            type: 'GET',
            async: false,
            data: {
                'interface_id': id
            },
            dataType: 'json',
//            contentType: 'application/x-www-form-urlencoded;charset=utf-8',
        }).then(
            function success(response) {
                //判断是否登录状态
                notLogin(response.code);
                detailedness.detailednessTableFun(response.header.data);
            },
            function error(e) {
                layer.msg('获取详情数据异常！', {
                    icon: 2
                });
            });
    },
    //查看详情表格初始化
    detailednessTableFun: function(data) {
        detailedness.$detailednessTable.bootstrapTable('destroy');
        detailedness.$detailednessTable.bootstrapTable({
            data: data, //table所展示的数据
            pagination: true, // 设置为 true 会在表格底部显示分页条
            pageSize: 20, //初始化一页显示几条
            paginationPreText: '上一页',
            paginationNextText: '下一页',
            pageList: [10, 20, 50, 100], //选页功能
            search: true, //开始前台搜索功能
            showTitle: true, //设置为true th 显示title
            columns: [{
                field: 'Tag_3rd',
                title: '标签',
            }, {
                field: 'Tag_1st',
                title: '标签分类'
            }, {
                field: 'Sample',
                title: '列表值',
                searchable: false,
                formatter: function(value, row, x) {
                    var html = '<select    data-key="name"  class="selmultipleSelect" >';

                    for (var i = 0; i < value.length; i++) {

                        html += '<option title="' + value[i] + '" value="' + value[i] + '">' + value[i] + '</option>';
                    }
                    html += '</select>';
                    return html;
                }

            }, {
                field: 'tag_rate',
                title: '填充率',
            }, {
                field: 'src_desc',
                title: '标签逻辑',
            }],
            onPageChange: function() {
                detailedness.initSelect();
            }
        });
        detailedness.initSelect();

    },
    //初始化下拉框方法
    initSelect: function() {
        detailedness.$detailednessTable.children('tbody').find('tr').each(function(index, el) {
            var $sel = $(this).children('td').children('.selmultipleSelect');

            $sel.multiselect({
                multiple: false,
            }).multiselectfilter({
                label: '',
            }).multiselectstyle();

            var $nextbtn = $sel.next();
            $nextbtn.unbind('mouseover');
            $nextbtn.mouseover(function(event) {
                var value = $nextbtn.children(':last-child').html();
                $nextbtn.attr('title', value);
            });
        });
    },


};


//上传mapping
var fileMapping = function(event) {
    var $fileName = $('#fileUploadMapping');
    var val = $fileName.val();
    var index = val.lastIndexOf('.');
    var type = val.substring(index + 1);

    if (type != 'xls' && type != 'xlsx') {
        layer.msg('请选择excel表格文件！', {
            icon: 2
        });
        $fileName.val('');
        return false;
    }
    var formData = new FormData($('#uploadMappingId')[0]);
    layer.msg('文件上传中......', {
        icon: 16,
        time: false,
        shade: [0.5, '#555555']
    });
    $.ajax({
        url: "/dlc_mk/dlc/mapping/upload?format=json",
        type: 'PUT',
        data: formData,
        processData: false, // 告诉jQuery不要去处理发送的数据
        contentType: false, //告诉jquery不要修改请求头
        /*  dataType:'json', */
        cache: false,
        isModified: true,
        success: function(response) {
            layer.closeAll();
            if (response.code == 8000) {

                layer.msg(response.msg, {
                    icon: 1
                });
                setTimeout(function() {
                    basic.goBackFun();
                }, 1500);

            } else {
                layer.msg(response.msg, {
                    icon: 2
                });
            }
        },
        error: function(e) {
            layer.closeAll();
            console.log(e);
        }
    });
    $fileName.val('');

};
