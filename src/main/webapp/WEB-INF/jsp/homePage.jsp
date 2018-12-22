<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<a>主页面 <%=request.getContextPath()%> </a>
	<p >用户信息:</p><p id="userinfo">.....</p>
	<button id="getuserinfo">获取用户信息</button>
	
	<button id="logout">退出</button>
	
	<button id="upload">同步mapping</button>
	
	<button id="interface">GET请求,获取接口数据</button>
	<button id="test">Post请求,获取接口数据</button>
	<p>
	<button id="forr">转发,获取接口数据</button>
	</p>	
	<p>
	<button id="redirect">重定向到</button>
	</p>
	<p>
	<a href="<%=request.getContextPath()%>/dlc/user/redirectlogin">重定向到百度</a>
	</p>
	<form id= "uploadForm">  
      <p >指定文件名： <input type="text" name="filename" value= ""/></p >  
      <p >上传文件： <input type="file" name="file"/></p>  
      <input type="button" value="上传" onclick="doUpload()" />  
	</form>  
	
</body>
<script src="<%=request.getContextPath()%>/common/js/libs/jquery.min.js"></script>
<!-- <%=request.getContextPath()%>/src/main/webapp/common/js/libs/jquery.min.js -->
<script type="text/javascript">
	

	$.ajax({
		url : "<%=request.getContextPath()%>/dlc/user/getuserinfo",
		type : "get",
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		datatype : "json",
		success : function(res) {
			console.log(res);
			if (res.code == 3000) {
				var res = eval(res);
				$("#userinfo").html("用户信息..."+":"+res);
				
			} 
		},
		error : function() {
		}
	});

	
	
	
	
$("#logout").click(function() {
	$.ajax({
		url : "<%=request.getContextPath()%>/dlc/user/logout",
		type : "get",
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		datatype : "json",
		success : function(data) {
			console.log(data);
			if (data.code == 1003) {
				alert("退出成功");
			} 
		},
		error : function() {
			alert("登录异常");
		}
	});
	
	
	
});
	
	
	
$("#upload").click(function() {
	$.ajax({
		url : "<%=request.getContextPath()%>/dlc/mapping/upload",
		type : "put",
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		datatype : "json",
		success : function(data) {
			console.log(data);
			if (data.code == 8000) {
				alert(data);
			} 
		},
		error : function() {
			alert("upload操作异常");
		}
	});
	
});	
	
$("#interface").click(function() {
	$.ajax({
		url : "<%=request.getContextPath()%>/dlc/interface/getinterfacebyall",
		type : "get",
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		datatype : "json",
		success : function(data) {
			console.log(data);
		},
		error : function() {
			alert("interface操作异常");
		}
	});
	
});	
	
	
$("#redirect").click(function() {
	$.ajax({
		url : "<%=request.getContextPath()%>/dlc/user/redirectlogin",
		type : "get",
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		datatype : "json",
		success : function(data) {
			console.log(data);
		},
		error : function() {
			alert("redirect操作异常");
		}
	});
	
});
	
	
$("#test").click(function() {
	$.ajax({
		url : "<%=request.getContextPath()%>/dlc/test/getData2",
		type : "post",
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		datatype : "json",
		success : function(data) {
			console.log(data);
		},
		error : function() {
			alert("interface操作异常");
		}
	});
	
});
	
	
$("#forr").click(function() {
	$.ajax({
		url : "<%=request.getContextPath()%>/dlc/test/forwardtogetData",
		type : "get",
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		datatype : "json",
		success : function(data) {
			console.log(data);
		},
		error : function() {
			alert("forr操作异常");
		}
	});
	
});
	

function doUpload() {  
    var formData = new FormData($( "#uploadForm" )[0]);  
    
    console.log(formData+'上传文件中...')
    $.ajax({
		url : "<%=request.getContextPath()%>/dlc/mapping/upload",
		data:formData,
         type: "PUT",
         dataType: "json",
         cache: false,//上传文件无需缓存
         processData: false,//用于对data参数进行序列化处理 这里必须false
         contentType: false, //必须
         success: function (result) {
             alert(result.msg);
         },
		
		error : function() {
			alert("file操作异常");
		}
         
	});
    
}  
	
</script >


</html>
