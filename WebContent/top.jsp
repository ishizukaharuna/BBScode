<%@page import="ishizuka_haruna.beans.Message"%>
<%@page import="ishizuka_haruna.beans.Comment"%>
<%@page import="ishizuka_haruna.service.CommentService"%>
<%@page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@page isELIgnored= "false" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix = "fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="./css01/bootstrap.min.css" rel="stylesheet" type="text/css">
	<meta http-equiv = "Content-Type" content = "text/html; charset=UTF-8">
	<title>ホーム画面</title>
	<script type="text/javascript">

	function check(){

		if(window.confirm('削除してよろしいですか？')){ // 確認ダイアログを表示

			return true; // 「OK」時は送信を実行

		}
		else{ // 「キャンセル」時の処理

			window.alert('キャンセルされました'); // 警告ダイアログを表示
			return false; // 送信を中止

		}
	}
	</script>
	<style>
 		 body {
 		   background: #ffdab9;
 		 }
	</style>

	<style>
		a:link { color: #1e90ff;}
		a:visited { color: #9932cc; }
		a:hover { color: #17b2e6; }
		a:active { color: #add8e6; }
		h2{text-align: center;}
		p{font-size:18px}
		p1{font-size:18px}
		p2{font-size:18px}
		p3{font-size:18px}
		.main-contents{margin-bottom:50px;}
		.header{border: 3px solid #ffb9b3;margin-right: auto;margin-left: 20px; padding: 20px; text-decoration: underline;background: #fff7e6;width:180px;text-align: center;}
		.header:visited {
			 color:#990099
		}
		.select{padding: 20px;text-align: center; background: #ffffff; width: 470px; color:#6c3524; margin: 30px auto; border: 3px solid #ffb9b3;}
		.message{padding: 20px; background: #ffffff; width: 554px; color:#6c3524; margin: 30px auto; border: 3px solid #ffb9b3;}
		.text{text-align:left; font-size: 20px; color: #664229;}
		.title{text-align:left;}
		.category{text-align:right;}
		.user-infomation{text-align:right;}
		.comment{font-size: 18px; padding: 20px; background: #ffffff; width: 450px; color:#6c3524;margin-left: auto;margin-right: 350px;}
		.doComment{background: #ffdab9; width: 554px; color:#6c3524; margin: 30px auto; text-align:right;}
		.commentUser{text-align:right;font-size: 15px;}
		.messageDelet{background:#ffdab9; width: 554px; color:#6c3524; margin: 30px auto; text-align:right;}
		.commentDelet{background:#ffdab9; width: 554px; color:#6c3524; margin: 30px auto; text-align:right;}
		.errorMessages{font-size:18px;color:#ff0000;text-align: center; font-weight:600;}
		.header{font-size:18px;line-height: 25px;}
		.message{font-size:18px;}
		.selectBotanPosition{background:#ffffff; width: 420px; color:#6c3524; margin: 5px auto; text-align:right;}
		.selectCategory{background: #ffffff; width: 400px; color:#6c3524; margin: 10px auto; text-align:left; padding: 12px;}
		.return{padding: 20px; text-decoration: underline; font-size: 18px;}
		.selectBotan{
			position: relative;
		    display: inline-block;
		    padding: 0.25em 0.5em;
		    text-decoration: none;
		    color: #FFF;
		    background: #2fb5eb;/*色*/
		    border-radius: 4px;/*角の丸み*/
		    box-shadow: inset 0 2px 0 rgba(255,255,255,0.2), inset 0 -2px 0 rgba(0, 0, 0, 0.05);
		    font-weight: bold;
		    border: solid 2px #248ab3;/*線色*/
		}

		.selectBotan:active {/*押したとき*/
		    box-shadow: 0 0 2px rgba(0, 0, 0, 0.30);
		}
		.deletBotan {
		    position: relative;
		    display: inline-block;
		    padding: 0.25em 0.5em;
		    text-decoration: none;
		    color: #FFF;
		    background: #ff002b;/*色*/
		    border-radius: 4px;/*角の丸み*/
		    box-shadow: inset 0 2px 0 rgba(255,255,255,0.2), inset 0 -2px 0 rgba(0, 0, 0, 0.05);
		    font-weight: bold;
		    border: solid 2px #e6004d;/*線色*/
		}

		.deletBotan:active{/*押したとき*/
		    box-shadow: 0 0 2px rgba(0, 0, 0, 0.30);
		}

		.doCommentBotan{
			position: relative;
		    display: inline-block;
		    padding: 0.25em 0.5em;
		    text-decoration: none;
		    color: #FFF;
		    background: #2fb5eb;/*色*/
		    border-radius: 4px;/*角の丸み*/
		    box-shadow: inset 0 2px 0 rgba(255,255,255,0.2), inset 0 -2px 0 rgba(0, 0, 0, 0.05);
		    font-weight: bold;
		    border: solid 2px #248ab3;/*線色*/
		}

		.doCommentBotan:active{/*押したとき*/
		    box-shadow: 0 0 2px rgba(0, 0, 0, 0.30);
		}

	</style>

	<style>.user-infomation{font-size:15px,
							color:#cc8552;}</style>
</head>
<body>
<br/><br/>
<h2>ようこそ${ loginUser.name }さん</h2>

	<div class="header">
		<a href="logout">ログアウト</a><br/>
		<c:if test="${ loginUser.position_id == 1 }">
			<a href="management">ユーザー管理</a><br/>
		</c:if>
		<a href="newMessage">新規投稿</a><br/>
	</div>
	<div class="main-contents">
	<c:if test="${ not empty errorMessages }">
		<div class="errorMessages">
			<ul style="list-style-type: none">
				<c:forEach items="${ errorMessages }" var="message">
					<li><c:out value="${ message }"/>
				</c:forEach>
			</ul >
		</div>
		<c:remove var="errorMessages" scope="session"/>
	</c:if>
	<div class="select">
	<p>＊絞込み＊</p><br/><br/>
	日付を選択
		<form action="./" method="get">
			<label for="start"></label>
			<input name="start" value="${ start }" type="date">

			<p1>から</p1>
			<label for="end"></label>
			<input name="end" value="${ end }" type="date">

			<p2>まで</p2>
			<div class="selectCategory">
			<p3>カテゴリーを選択：</p3>
			<select name=category>
				<option value=""></option>
				<c:forEach items="${ categorys }" var="category">

					<c:if test="${ (category.category).equals(selectCategory) }">
						<option value="${ category.category }"selected>
							<c:out value="${ category.category}"></c:out>
						</option>
					</c:if>
					<c:if test="${ !(category.category).equals(selectCategory) }">
						<option value="${ category.category }">
							<c:out value="${ category.category}"></c:out>
						</option>
					</c:if>
				</c:forEach>
			</select>
			</div>
			<div class="selectBotanPosition">
			<span style="margin-right: 8em;"></span>
			<input type="submit" class="selectBotan" value="絞り込む"/>
			</div>
		</form>
	</div>



		<c:forEach items="${ messages }" var="message">	<br/>
		<c:if test="${ category.category == message.category }}">
			<c:out value="${ message.text }"/>
		</c:if>
		<div class="message">
			<div class="title">件名：
				<c:out value="${ message.title }"/>
			</div><br/>
			<div class="text">
			<c:forEach var="s" items="${fn:split(message.text, '
			')}">
			    <div><c:out value="${s}"/></div>
			</c:forEach>
			</div><br/>
			<div class="category">カテゴリー：<c:out value="${ message.category }"/></div><br/>
		<div class="user-infomation">
			<span class="name">ユーザー：<c:out value="${ message.name }"/></span><br/>
			<c:forEach items="${ branches }" var="branch">
				<c:if test="${ message.branch_id == branch.id }">
					支店名：<c:out value="${ branch.name }"/>
				</c:if>
			</c:forEach>
			<br/>
			<c:forEach items="${ positions }" var="position">
				<c:if test="${ message.position_id == position.id }">
						役職名：<c:out value="${ position.name }"/>
				</c:if>
			</c:forEach>
			<br/>
			<fmt:formatDate value = "${ message.insert_date }" pattern = "yyyy/MM/dd HH:mm:ss" />
		</div>
		</div>
		<div class=messageDelet>
		<form action="deleteMessage" method="post" onSubmit="return check()">
			<c:choose>
				<c:when test="${ loginUser.id == message.user_id }">
					<input type="hidden" name="id" value="${ message.id }">
					<input type="submit" class="deletBotan" value="投稿削除"><br/>
				</c:when>

				<c:when test="${ loginUser.position_id == 2 }">
					<input type="hidden" name="id" value="${ message.id }">
					<input type="submit" class="deletBotan" value="投稿削除"><br/>
				</c:when>

				<c:when test="${ loginUser.position_id == 3 && loginUser.branch_id == message.branch_id }">
					<input type="hidden" name="id" value="${ message.id }">
					<input type="submit" class="deletBotan" value="投稿削除"><br/>
				</c:when>
			</c:choose>
		</form>
		</div>
		<div class="comments">
			<c:forEach items="${ comments }" var="comment">
				<c:if test= "${message.id == comment.message_id}">
					<div class="comment">
						<c:forEach var="s" items="${fn:split(comment.text, '
								')}">
								<div><c:out value="${s}"/></div>
							</c:forEach>
						<div class="commentUser">
						ユーザー：<c:out value="${ comment.name }"/><br/>
						<c:forEach items="${ branches }" var="branch">
							<c:if test="${ comment.branch_id == branch.id }">
								支店名：<c:out value="${ branch.name }"/>
							</c:if>
						</c:forEach><br/>
						<c:forEach items="${ positions }" var="position">
							<c:if test="${ message.position_id == position.id }">
								役職名：<c:out value="${ position.name }"/><br/>
							</c:if>
						</c:forEach>
							<fmt:formatDate value = "${ comment.insert_date }" pattern = "yyyy/MM/dd HH:mm:ss" />
						</div>
						</div>
						<div class=commentDelet>
						<form action="deleteComment" method="post" onSubmit="return check()">
							<c:choose>
								<c:when test="${ loginUser.id == comment.user_id }">
									<input type="hidden" name="id" value="${ comment.id }">
									<input type="submit" class="deletBotan"" value="コメント削除"><br/>
								</c:when>
								<c:when test="${ loginUser.position_id == 2 }">
									<input type="hidden" name="id" value="${ comment.id }">
									<input type="submit" class="deletBotan" value="コメント削除"><br/>
								</c:when>

								<c:when test="${ loginUser.position_id == 3 && loginUser.branch_id == comment.branch_id }">
									<input type="hidden" name="id" value="${ comment.id }">
									<input type="submit" class="deletBotan" value="コメント削除"><br/>
								</c:when>
							</c:choose>
						</form>
						</div>
				</c:if>
			</c:forEach>
			<div class="doComment">
			<form action="comment" method="post">
				<label for="text"></label>
				<textarea name = "text" cols = "50" rows = "4" class = "comment-box"></textarea><br/>500文字以内<br/>
				<input type="hidden" name="id" value="${ message.id }">
				<input type = "submit" class="doCommentBotan" value = "コメントする">
			</form>
			</div>
		</div>
		</c:forEach>
		<a href="index.jsp" class="return">このページのトップへ</a><br/>
	</div>
</body>
</html>