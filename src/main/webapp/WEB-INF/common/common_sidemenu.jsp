<%@ page language="java" contentType="text/html; charset=utf-8"
  pageEncoding="utf-8"%>
 <!-- 右侧菜单区域 -->
<div class="left-menu">
    <ul>
        <li><a href="<%=request.getContextPath()%>/dlc/user/home"><span class="basicsData"></span>基 础 数 据</a></li>
        <li><a href="javascrpit:void(0)"><span class="dataScene"></span>数据场景</a></li>
        <li><a href="javascrpit:void(0)"><span class="Portrayal"></span>画像报告</a></li>
        <!-- id="userManageAdmin" 权限控制 -->
        <%--<li id="userManageAdmin"><a href="<%=request.getContextPath()%>/dlc/cruduser/usermanagement"><span class="userManage"></span>用户管理</a></li>--%>
        <li><a href="<%=request.getContextPath()%>/dlc/cruduser/usermanagement"><span class="userManage"></span>用户管理</a></li>

    </ul>
    <p class="copyright">MAS Copyright © 2019</p>
</div>
<!-- 右侧主内容区域 -->

