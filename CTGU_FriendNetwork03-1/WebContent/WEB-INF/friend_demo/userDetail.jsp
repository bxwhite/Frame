<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html lang="en">
<head>
<title>update Emp</title>
<meta charset="utf-8">
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

			<div id="content">
				<p id="whereami"></p>
				<h1>User Detail:</h1>
				<table class="table">
					<tr>
						<td>昵称</td>
						<td>联系方式</td>
					</tr>
					<tr>
						<td>${myUser.name }</td>
						<td>${myUser.phone }</td>
						${pageContext.request.contextPath}
					</tr>
				</table>
				<c:if test="${loger.id==myUser.id }">
					<h1>Load Photo:</h1>

					<form action="msgUpload.do?id=${myUser.id}" method="post"
						enctype="multipart/form-data">
						Upload File Name: <input type="file" name="file1" /> <input
							type="submit" value="confirm" />
					</form>
				</c:if>
				<h1>view photo:</h1>				
				<table>
					<c:forEach items="${pList}" var="p">
						<tr>
							<td><img
								src="${pageContext.request.contextPath}/upload/${p.picName}"
								width="300" height="200" /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<a href="userList.do">return</a>
		</div>
		<div id="footer">
			<div id="footer_bg">ABC@126.com</div>
		</div>
	</div>
</body>
</html>
