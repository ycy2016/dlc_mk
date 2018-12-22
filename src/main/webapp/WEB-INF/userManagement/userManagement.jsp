<%@ page language="java" contentType="text/html; charset=utf-8"
  pageEncoding="utf-8"%>
 <%@ include file="../common/common_head.jsp"%>
 <!-- 主要内容 -->
<div class="main">
    <div class="titleContent userManagement">
        <div class="title">用户管理

        </div>
        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
           
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
	                <div class="operation">
		                <span class="thisTableName">用户管理</span>
		                <span class="addUser" onclick="userManagement.layerOpen('addUser')">+ 添加用户
		                    
		                </span>
		            </div>
                    <div class="content">
                        <table id="userManagementTable"></table>
                    </div>
                </div>
            </div>
        </div> 
    </div>
</div>
<!-- 弹出框 -->
<div class="windowModel" style="display:none;">
    <div class="userService">
        <div class="title">添加用户</div>
        <div class="content">
            <ul>
                <li>
                    <div class="text">
                       账号： 
                    </div>
                    <div  class="input_internal">
                        <input type="text" maxlength="20" name="account"  placeholder="账号">
                    </div>
                </li>
                <li>
                    <div class="text">
                        用户名：
                    </div> 
                    <div class="input_internal">
                        <input type="text" maxlength="20" name="userName"  placeholder="用户名">
                    </div>
                </li>
                <li>
                    <div class="text">
                        密码：
                    </div>
                    <div class="input_internal"> 
                        <input type="password" maxlength="20" name="password"  placeholder="密码">
                    </div>
                </li>
                <li>
                    <div class="text">
                        角色：
                        
                    </div>
                    <div class="input_internal"> 
                        <div class="radio-input">
                            <input class="" type="radio" checked="checked" name="role" id="role-admin" value="0">
                            <label for="role-admin" class="border"><span class="value">管理员</span></label>
                            <input class="" type="radio" name="role" id="role-user" value="1">
                            <label for="role-user" class="border"><span class="value">普通用户</span></label>
                        </div>
                        
                    </div>
                </li>
                <li>
                    <div class="text">
                        是否删除：
                    </div>
                    <div class="input_internal"> 
                        <div class="radio-input">
                            <input class="" type="radio"  checked="checked" name="status" id="not" value="0">
                            <label for="not" class="border"><span class="value">否</span></label>
                            <input class="" type="radio" disabled="disabled" name="status" id="rev" value="1">
                            <label for="rev" class="border"><span class="value">是</span></label>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div> 
</div>
    </div>
<%@ include file="../common/common_href_js.jsp"%>
<!-- <script type="text/javascript" src = "<%=request.getContextPath()%>/userManagement/js/userManagement.js"></script> -->
<script type="text/javascript" src = "<%=request.getContextPath()%>/userManagement/js/userManagement.js"></script>
<%@ include file="../common/common_buttom.jsp"%>


