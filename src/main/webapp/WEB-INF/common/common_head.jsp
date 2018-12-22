<%@ page language="java" contentType="text/html; charset=utf-8"
  pageEncoding="utf-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    
    <title>DLC 数据商城</title>
    <%@ include file="../common/common_href_css.jsp"%>

</head>
<body>
    <div>
        <div class="header">
            <div class="logo"></div>
            <span class="user">欢迎：
                <span class="userName"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <span onclick="userInit.logOut()">退出</span>
            </span>
        </div>
        <div class="mainContent">
        	<%@ include file="../common/common_sidemenu.jsp"%>
