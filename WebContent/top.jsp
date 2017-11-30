<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ホーム</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>

<body>

<div class="title">
<div id="headline">
	<c:if test="${ not empty loginUser }">
		<p><a href="newPosts">新規投稿</a>
		<a href="logout">ログアウト</a>
		<c:if test="${loginUser.department_id == 1}">
			<p><a href="userManagement">ユーザー管理画面</a></p>
		</c:if>
	</c:if>

</div>
<c:if test="${ not empty loginUser }">
	<div class="profile">
		<div class="name"><h2><c:out value="${loginUser.name}" /></h2></div>
		<div class="login_id">
			@<c:out value="${loginUser.login_id}" />
		</div>
		<div class="login_id">
			<c:out value="${loginUser.login_id}" />
		</div>
	</div>
</c:if>
</div>
<div class="main-contents">
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
</div>
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
function enterStop1(){
     var check = confirm("コメントを削除してよろしいですか？");
     return check;
}

</script>
<script type = "text/javascript">
function enterStop2(){
     var check = confirm("投稿を削除してよろしいですか？");
     return check;
}

</script>
<div class="serch">
	<form method="get" action="./">
		<p>日付検索</p>
			<input name="startDate" type="date" value="${startDate}"/>
			<input name="endDate" type="date" value="${endDate}" />
		<p>カテゴリー</p>
		 	<input type="text" name="category" value="${category}" />
			<input type="submit" value="検索" />
	</form><br>
</div>

<div class="posts">
	<c:forEach items="${posts}" var="post">
		<div class="category"><c:out value="${post.category}" /></div>
		<div class="subject"><c:out value="${post.subject}" /></div>
		<div class="text"><pre><c:out value="${post.text}" /></pre></div>
		<div class="name"><c:out value="${post.name}" /></div>
		<div class="created_at"><fmt:formatDate value="${post.created_at}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
		<form action="delete" method="post">
			<input type="hidden" name="id" value="${post.id}"/>
			<input type=submit value=投稿削除 onClick = "return enterStop2()">
		</form>
		<c:forEach items="${comments}" var="comment">
			<c:if test="${comment.post_id == post.id }">
				<div class="comments"><span class="comments"><c:out value="${comment.id}" /></span></div>
				<div class="name"><span class="name"><c:out value="${comment.name}" /></span></div>
				<div class="text"><pre><c:out value="${comment.text}" /></pre></div>
				<div class="post_id"><c:out value="${comment.post_id}" /></div>
				<div class="created_at"><fmt:formatDate value="${comment.created_at}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
				<form action="delete" method="post">
					<input type="hidden" name="id" value="${comment.id}"/>
					<input type=submit value=コメント削除 onClick = "return enterStop1()">
				</form>
			</c:if>
		</c:forEach>
		<form action="newComments" method="post">
			コメント<br />
			<input type="hidden" name="post_id" value="${post.id}"/>
			<textarea name="text" cols="100" rows="5" class="tweet-box"></textarea><br />
			<input type=submit value=コメント投稿>（500文字まで）
		</form>
	</c:forEach>
</div>
</body>
</html>