<%@ page language="java" contentType="text/html; charse=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var code=5;
	setInterval(function()  {
		code--;
		$("#time").html(code).css("color","red");
		if(code==0){
			window.location.href="login.do";
			return false;
		}
	}, 1000);
});
</script>
<link rel="stylesheet" type="text/css" href="../css/style.css" />
</head>
<body>
<div id="wrap">
		<div id="top_content">
			<div id="header">
				<div id="rightheader">
					<p>
						2016/11/20 <br />
					</p>
				</div>
				<div id="topheader">
					<h1 id="title">
						<a href="#">Main</a>
					</h1>
				</div>
				<div id="navigation"></div>
			</div>
<div>
<p>恭喜您，删除成功</p>
<span id="time" >5</span>秒后自动跳转！
<a href="user/login.do">登录界面</a>
</div>
</body>
</html>