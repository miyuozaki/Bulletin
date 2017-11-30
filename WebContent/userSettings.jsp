<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${editUser.name}の設定</title>
	<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
	<a href="http://localhost:8080/Ozaki_miyu/userManagement">ユーザー管理画面</a>
	<a href="logout">ログアウト</a>
<body>
<div class="main-contents">

<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<li><c:out value="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>

<form action="UserSettings" method="post"><br />
	<input type="hidden" name="id" value="${editUser.id}"/>

	<label for="name">名前</label><br />
	<input name="name" value="${editUser.name}" id="name"/>（10文字以下）<br />

	<label for="login_id">ログインID</label><br />
	<input name="login_id" value="${editUser.login_id}" />（半角英数字6文字以上20文字以下）<br />

	<label for="password">パスワード</label><br />
	<input name="password" type="password" id="password"/> (記号を含む全ての半角文字6文字以上20文字以下)<br />

	<label for="passwordConfirm">パスワード（確認）</label><br />
	<input type="password" name="passwordConfirm" id="passwordConfirm"><br>



	<c:if test="${loginUser.id == editUser.id }">

	<label for="branches">支店名</label><br />
	<c:forEach items="${branches}" var="branch">
		<c:if test="${branch.id == editUser.branch_id }">
			<c:out value="${branch.name}"/>
			<input name="branch_id" type="hidden" value="${branch.id}" id="branch_id"/>
		</c:if>
	</c:forEach></br>
	<label for="departments">部署名</label><br />
	<c:forEach items="${departments}" var="department">
		<c:if test="${department.id == editUser.department_id}">
			<c:out value="${department.name}"/>
			<input name="department_id" type="hidden" value="${department.id}" id="department_id"/>
		</c:if>
	</c:forEach>
</c:if>

<c:if test="${loginUser.id != editUser.id }">

	<label for="branches">支店名</label><br />
	<select name="branch_id">
		<c:forEach items="${branches}" var="branch">
			<option value="${branch.id}" <c:if test="${branch.id == editUser.branch_id }"> selected</c:if>/><c:out value="${branch.name}"/></option>
		</c:forEach>
	</select><br />

	<label for="departments">部署名</label><br />
	<select name="department_id">
		<c:forEach items="${departments}" var="department">
			<option value="${department.id}" <c:if test="${department.id == editUser.department_id }"> selected</c:if>/><c:out value="${department.name}"/></option>
		</c:forEach>
	</select>

</c:if><br />


	<input type="submit" value="編集" /><br />

</form>

</div>
</body>
</html>
