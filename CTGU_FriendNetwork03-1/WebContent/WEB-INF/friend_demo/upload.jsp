<%@ page language="java" contentType="text/html; charse=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<title>regist</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../css/style.css" />
<title>Insert title here</title>
<style type="text/css">
.mydiv {
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -200px 0 0 -200px;
	width: 400px;
	height: 400px;
	text-align: center;
}
</style>
</head>

<body>
	<div id="wrap">
		<div id="top_content">
			<div id="header">
			<div id="topheader">
					<h1 id="title">
						<a href="#">请更改信息</a>
					</h1>
				</div>
				<div id="navigation"></div>
			</div>

			<div calss=mydiv>
				<p id="whereami"></p>
			
				<form action="update.do" method="post">
					<table cellpadding="0" cellspacing="0" border="0"
						class="form_table">
						<tr>
							<td valign="middle" align="right">id:</td>
							<td valign="middle" align="left"><input
								class="inputgri" name="id" disabled="disabled" value="${u.id}"/></td>
						</tr>
						
						<tr>
							<td valign="middle" align="right">用户名:</td>
							<td valign="middle" align="left"><input 
								class="inputgri" name="username" readonly="readonly" value="${u.username}"/>
								<span><font color="red">用户名不可更改</font></span></td>
						</tr>
						<tr>
							<td valign="middle" align="right">密码:</td>
							<td valign="middle" align="left"><input  
								class="inputgri" name="password" value="${u.password }"/></td>
						</tr>
						<tr>
							<td valign="middle" align="right">name:</td>
							<td valign="middle" align="left"><input 
								class="inputgri" name="name" value="${u.name }"/></td>
						</tr>
						<tr>
							<td valign="middle" align="right">年龄:</td>
							<td valign="middle" align="left"><input type="text"
								class="inputgri" name="age" value="${u.age}"/></td>
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
								class="inputgri" name="phone" value="${u.phone}" /></td>
						</tr>
						<tr>
					 <td valign="middle" align="right">更新信息</td>
					 <td valign="middle" align="left"><input type="submit" value="提交" style="magrin-left: 60px;"></input></td>
				</tr>
						
					</table>
					
		</form>
		<div id="footer">
				<div id="footer_bg">
					ABC@126.com
				</div>
			</div>
	</div>
</body>
</html>
