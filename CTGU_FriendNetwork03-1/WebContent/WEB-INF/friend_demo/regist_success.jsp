<%@ page language="java" 
	contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<p>恭喜 ${username},注册成功,请先登录</p>
	
<a href="${pageContext.request.contextPath}/user/login.do">
	登录界面
	</a>
</body>
</html>
