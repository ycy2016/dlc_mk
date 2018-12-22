<%@ page language="java" contentType="text/html; charset=utf-8"
  pageEncoding="utf-8"%>
<%@ include file="../common/common_head.jsp"%>

<!-- 主要内容 -->
<div class="main">
    <div class="titleContent">
        <div class="title">基础数据

        </div>
        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
            <ul class="layui-tab-title stair">
                <li class="layui-this">
                    产品看板
                </li>
                <li>
                    价格清单
                </li>
                <li>
                    调用申请
                </li>
            </ul>
           
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <div class="operation">
                        <button onclick="basic.goBackFun()" class="go-back"  type="button">返回主页</button>
                        <span class="thisTableName">基础数据服务产品简介</span>
                        <span class="fileUploadMapping">上传Mapping表
                            <form id="uploadMappingId" action="" method=""
                                enctype="multipart/form-data"> 
                                <input id="fileUploadMapping" type="file" onchange="fileMapping(this)" name="file">
                            </form>
                        </span>
                    </div>
                    <div class="content">
                        <table id="basicTable"></table>
                        
                        <table id="detailednessTable"></table>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <div class="content">
                        暂未开发
                    </div>
                </div>
                <div class="layui-tab-item">
                    <div class="content">
                        暂未开发
                    </div>
                </div>
            </div>
        </div> 
    </div>
</div>
</div>
    </div>
<%@ include file="../common/common_href_js.jsp"%>
<!-- <script type="text/javascript" src = "../../basicData/js/basicData.js"></script> -->
<script type="text/javascript" src = "<%=request.getContextPath()%>/basicData/js/basicData.min.js"></script>
<%@ include file="../common/common_buttom.jsp"%>


