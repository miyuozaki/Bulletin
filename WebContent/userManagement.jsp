<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー管理画面</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-contents">

<div class="header">

	<c:if test="${ not empty loginUser }">
		<a href="./">ホーム</a>
		<a href="signup">ユーザー登録</a>
		<a href="logout">ログアウト</a>
	</c:if>
</div>
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="messages">
				<li><c:out value="${messages}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
<script type = "text/javascript" src = "js/jquery.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
    $(function() {
        $("#page-top a").click(function() {
            $('html,body').animate({
                scrollTop: 0
            }, 'fast');
            return false;
        });
    });
</script>
<script type = "text/javascript">
function enterStop(){
     var check = confirm("アカウントを停止させてよろしいですか？");
     return check;
}
function enterRevival(){
    var check = confirm("アカウントを復活させてよろしいですか？");
	return check;
}
</script>

<div class="users">
<table border=1>
<tr>
<th>名前</th>
<th>支店</th>
<th>部署</th>
<th>権限</th>
<th>編集</th>
</tr>

	<c:forEach items="${users}" var="user">
		<tr><td><c:out value="${user.name}" /></td>
			<c:forEach items="${branches}" var="branch">
				<c:if test="${user.branch_id == branch.id}">
					<td><c:out value="${branch.name}" /></td>
				</c:if>
			</c:forEach>
			<c:forEach items="${departments}" var="department">
				<c:if test="${user.department_id == department.id}">
					<td><c:out value="${department.name}" /></td>
				</c:if>
			</c:forEach>
			<form action="stop" method="post">
				<c:if test="${user.id == loginUser.id}">
				<td><label>ログイン中</label></td>
				</c:if>
				<c:if test="${user.id != loginUser.id}">
					<input type="hidden" name="id" value="${user.id}"/>
				</c:if>
				<c:if test="${user.id != loginUser.id}">
				 	<c:if test="${user.is_stopped == 0}">
				 		<input type="hidden" name="is_stopped" value="1"/>
				 		<td><input type=submit value=停止 onClick = "return enterStop()"></td>
				 	</c:if>
				 	<c:if test="${user.is_stopped == 1}">
				 		<input type="hidden" name="is_stopped" value="0"/>
				 		<td><input type=submit value=復活 onClick = "return enterRevival()"></td>
				 	</c:if>
				 </c:if>
			</form>

			<td><a href="UserSettings?userId=${user.id}">編集</a></td></tr>

		</div>
	</c:forEach>
</table>
</div>
</div>
</body>
</html>
