<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="en">
<script>
	/* window.location.href  = "console/unverified"; */
	/* login.jsp在webinf底下, 不能使用 web.xml的welcome-file直接转发 */
	
	window.location.href  = "<%=request.getContextPath()%>/dlc/user/login";

</script>