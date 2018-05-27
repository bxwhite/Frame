<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
	<head>
		<title>login</title>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="../css/style.css" />
	</head>
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript">

$(function() {
	var $pwd = $("input:[name='password']");
	var regex = /^[a-zA-Z0-9]{6,15}$/;
	$pwd.blur(function() {
		var pwdVal = $pwd.val();
		if (pwdVal == null || pwdVal == "") {
			//提醒
			$("#pwdErr").html("输入不能为空!").css("color", "red");
			return;
		}else{
			var f = regex.test(pwdVal);
			if(f){
				$("#pwdErr").html("格式正确!").css("color", "green");
			}
			else{
				$("#pwdErr").html("请输入6-15位字符!").css("color", "red");
			}
		}
	});
});
$(function() {
	var $username = $("input:[name='username']");
	$username.blur(function() {
		var usernameVal = $username.val();
		if (usernameVal == null || usernameVal == "") {
			//提醒
			$("#usernameErr").html("输入不能为空!").css("color", "red");
			return;
		}
		//alert(usernameVal);
		$.ajax({
			url : "findByName.do",
			data : {"username" : usernameVal},
			dataType : "json",
			success : function(msg) {					
				if(msg){
					$("#usernameErr").html("用户名不存在!").css("color","red");
				}
				else{
					$("#usernameErr").html("输入正确!").css("color","green");
				}
			}
		});

	});
});

$(function(){
	$button=$("input:[name='mybutton']");

	$button.click(function() {
		//ajax 为空，不跳转，提示错误
		//正确，调用form标签的submit方法
		var username=$("input:[name='username']").val();
		var pwd=$("input:[name='password']").val();
		$.ajax({
			url:"checkpassword.do",
			data : {"username" : username,"password" : pwd},
			dataType : "json",
			success : function(msg) {
				//alert(msg)
				if(!msg){
					$("#loginErr").html("用户名或密码错误！").css("color","red");
				}else{
					//跳转主界面
					$("#myform").submit();
				}
			}
		});
	});
}); 
</script>
	<body>
		<div id="wrap">
			<div id="top_content">
				<div id="header">
					<div id="rightheader">
						<p>
							2016/11/20
							<br />
						</p>
					</div>
					<div id="topheader">
						<h1 id="title">
							<a href="#">Main</a>
						</h1>
					</div>
					<div id="navigation">
					</div>
				</div>
				<div id="content">
					<p id="whereami">
					</p>
					<h1>
						login
					</h1>
					<form action="<%=request.getContextPath() %>/user/toUserList.do" method="post" id="myform">
						<table cellpadding="0" cellspacing="0" border="0"
							class="form_table">
							<tr>
								<td valign="middle" align="right">
									username:
								</td>
								<td valign="middle" align="left">
									<input type="text" class="inputgri" name="username" />
									<span id="usernameErr"></span>
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									password:
								</td>
								<td valign="middle" align="left">
									<input type="password" class="inputgri" name="password" />
									<span id="pwdErr"></span>
								</td>
							</tr>
						</table>
						<p>
							<input type="button" name='mybutton' class='button'value="Submit &raquo;" />
						</p>
						<span id="loginErr"></span>
					</form>
				</div>
			</div>
			<div id="footer">
				<div id="footer_bg">
					ABC@126.com
				</div>
			</div>
		</div>
	</body>
</html>
