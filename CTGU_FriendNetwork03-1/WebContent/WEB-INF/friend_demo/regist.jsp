<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
	<head>
		<title>regist</title>
		<meta http-equiv="content-type" content="text/html;charset=utf-8"/>
		<link rel="stylesheet" type="text/css" href="../css/style.css" />
	</head>
	<script type="text/javascript" src="../js/jquery.js"></script>
	<script type="text/javascript">
	//注册验证
	var flag1 = false;//用户名
	var flag2 = false;//真实姓名
	var flag3 = false;//验证码
	var flag4 = false;//密码
	var flag5 = false;//年龄
	var flag6 = false;//电话
	$(function() {
		var $username = $("input:[name='username']");
		$username.blur(function() {
			var usernameVal = $username.val();
			if (usernameVal == null || usernameVal == "") {
				//提醒
				flag1 = false;
				$("#usernameErr").html("输入不能为空!").css("color", "red");
				return;
			}
			//alert(usernameVal);
			$.ajax({
				url : "findByName.do",
				data : {"username" : usernameVal},
				dataType : "json",
				success : function(msg) {	
					//alert("msg");
					if(msg!=1){
						flag1=false;
						$("#usernameErr").html("用户名被占用！").css("color","red");
					}else{
						flag1=true;
						$("#usernameErr").html("可以使用！").css("color","green");
					}
				}
			});

		});
	});
	
	$(function() {
		var $name = $("input:[name='name']");
		var regex = /^[\u3400-\u9FFF]+$/;
		$name.blur(function() {
			var nameVal = $name.val();
			if (nameVal == null || nameVal == "") {
				//提醒
				flag2 = false;
				$("#nameErr").html("输入不能为空!").css("color", "red");
				return;
			}else{
				var f = regex.test(nameVal);
				if(f){
					flag2 = true;
					$("#nameErr").html("格式正确!").css("color", "green");
				}
				else{
					flag2 = false;
					$("#nameErr").html("请输入汉字!").css("color", "red");
				}
			}
		});
	});
	
	
	$(function() {
		var $phone = $("input:[name='phone']");
		var regex = /^1[3|4|5|8][0-9]\d{8}$/;
		$phone.blur(function() {
			var phoneVal = $phone.val();
			if (phoneVal == null || phoneVal == "") {
				//提醒
				flag6 = false;
				$("#phoneErr").html("输入不能为空!").css("color", "red");
				return;
			}else{
				var f = regex.test(phoneVal);
				if(f){
					flag6 = true;
					$("#phoneErr").html("格式正确!").css("color", "green");
				}
				else{
					flag6 = false;
					$("#phoneErr").html("格式错误!").css("color", "red");
				}
			}
		});
	});
	$(function() {
		var $age = $("input:[name='age']");
		var regex = /^\d{1,2}$/;
		$age.blur(function() {
			var ageVal = $age.val();
			if (ageVal == null || ageVal == "") {
				//提醒
				flag5 = false;
				$("#ageErr").html("输入不能为空!").css("color", "red");
				return;
			}else{
				var f = regex.test(ageVal);
				if(f){
					flag5 = true;
					$("#ageErr").html("格式正确!").css("color", "green");
				}
				else{
					flag5 = false;
					$("#ageErr").html("请输入0-99!").css("color", "red");
				}
			}
		});
	});
	$(function() {
		var $pwd = $("input:[name='password']");
		var regex = /^[a-zA-Z0-9]{6,15}$/;
		$pwd.blur(function() {
			var pwdVal = $pwd.val();
			if (pwdVal == null || pwdVal == "") {
				//提醒
				flag4 = false;
				$("#pwdErr").html("输入不能为空!").css("color", "red");
				return;
			}else{
				var f = regex.test(pwdVal);
				if(f){
					flag4 = true;
					$("#pwdErr").html("格式正确!").css("color", "green");
				}
				else{
					flag4 = false;
					$("#pwdErr").html("请输入6-15位字符!").css("color", "red");
				}
			}
	
		});
	});
	function changeCode() {
		//获得节点对象
		var img = document.getElementById("num");
		img.src = "verify.do?" + new Date();
		//alert($("input:[name='username']"));
	}
	$(function() {
		//验证码
		var $checkCode=$("input:[name='checkCode']");
		$checkCode.blur(function() {
			var checkCode=$checkCode.val();
			var $checkErr=$("#checkErr");
			if(checkCode==null||checkCode==""){
				$checkErr.html("不能为空").css("color","red");
				flag3=false;
			}else{
				//判定输入是否正确，ajax
				$.ajax({
					url:"checkCode.do",
					data:{"code":checkCode},
					dataType:"json",
					success:function(msg){
						//alert(msg);
						if(msg){
							flag3=true;
							$checkErr.html("输入正确").css("color","green");
						}else{
							flag3=false;
							$checkErr.html("输入不一致").css("color","red");
						}
					}
	
				});
			}
		})
	});
	function  checkSubmit(){
		if(!flag1){
			$("#usernameErr").html("请重新输入!").css("color", "red");
		}
		if(!flag2){
			$("#nameErr").html("请重新输入!").css("color", "red");
		}
		if(!flag3){
			$("#checkErr").html("请重新输入!").css("color", "red");
		}
		if(!flag4){
			$("#pwdErr").html("请重新输入!").css("color", "red");
		}
		if(!flag5){
			$("#ageErr").html("请重新输入!").css("color", "red");
		}
		if(!flag6){
			$("#phoneErr").html("请重新输入!").css("color", "red");
		}
		
		return flag1&&flag2&&flag3&&flag4&&flag5&&flag6;
	}
</script>
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

			<div id="content">
				<p id="whereami"></p>
				<h1>注册</h1>
				<form action="regist.do" method="post" onsubmit="return checkSubmit()">
					<table cellpadding="0" cellspacing="0" border="0"
						class="form_table">
						<tr>
							<td valign="middle" align="right">用户名:</td>
							<td valign="middle" align="left"><input type="text"
								class="inputgri" name="username" /><span id="usernameErr"></span></td>
						</tr>
						<tr>
							<td valign="middle" align="right">真实姓名:</td>
							<td valign="middle" align="left"><input type="text"
								class="inputgri" name="name" /><span id="nameErr"></span></td>
						</tr>
						<tr>
							<td valign="middle" align="right">密码:</td>
							<td valign="middle" align="left"><input type="password"
								class="inputgri" name="password" /><span id="pwdErr"></span></td>
						</tr>
						<tr>
							<td valign="middle" align="right">年龄:</td>
							<td valign="middle" align="left"><input type="text"
								class="inputgri" name="age" /><span id="ageErr"></span></td>
						</tr>
						<tr>
							<td valign="middle" align="right">性别:</td>
							<td valign="middle" align="left">男 <input type="radio"
								class="inputgri" name="gendar" value="1" checked="checked" /> 女
								<input type="radio" class="inputgri" name="gendar" value="0" />
							</td>
						</tr>
						<tr>
							<td valign="middle" align="right">电话:</td>
							<td valign="middle" align="left"><input type="text"
								class="inputgri" name="phone" /><span id="phoneErr"></span></td>
						</tr>
						<tr>
							<td valign="middle" align="right">验证码: <img id="num"
								src="verify.do" /> <a href="javascript:;" onclick="changeCode()">换一张</a>
							</td>
							<td valign="middle" align="left"><input type="text"
								class="inputgri" name="checkCode" /> <span id="checkErr"></span></td>
						</tr>
					</table>
					<p>
						<input type="submit" class="button" value="Submit &raquo;" />
					</p>
					<p>
					
					<a href="login.do">直接登录</a>
					
					</p>
				</form>
			</div>
		</div>
		<div id="footer">
			<div id="footer_bg">ABC@126.com</div>
		</div>
	</div>
</body>
</html>