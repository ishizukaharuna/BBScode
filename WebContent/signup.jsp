<%@page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@page isELIgnored= "false" %>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix = "fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv = "Content-Type" content = "text/html; charset=UTF-8">
	<title>ユーザー新規登録</title>
	<link href="./css01/bootstrap.min.css" rel="stylesheet" type="text/css">

</head>
<style>
	 body {
 	  background: #ffdab9;
 	  margin-top:50px;
 	  margin-bottom:50px;
 	}
	h2{text-align: center; font-weight:600; }
 	.main{
 		font-size:18px;
 		line-height: 25px;
 		padding: 40px; background: #ffffff;
		width: 700px; color:#6c3524;
		margin: 30px auto; border: 3px solid #ffb9b3;
		text-align: left;
 	}

 	.errorMessages{font-size:18px;color:#ff0000;text-align: center; font-weight:600;}
	.signupBotan{background: #ffffff; width: 554px; color:#6c3524; text-align:center;margin: 5px auto; text-align:right;}
 	.signup{
 		position: relative;
		display: inline-block;
	    padding: 1.0em 2.0em;
	    text-decoration: none;
	    color: #FFF;
	    background: #2fb5eb;/*色*/
	    border-radius: 4px;/*角の丸み*/
	    box-shadow: inset 0 2px 0 rgba(255,255,255,0.2), inset 0 -2px 0 rgba(0, 0, 0, 0.05);
	    font-weight: bold;
	    border: solid 2px #248ab3;/*線色*/
 	}

 	.signup:active {/*押したとき*/
		    box-shadow: 0 0 2px rgba(0, 0, 0, 0.30);
	}

	.link{border: 3px solid #ffb9b3;margin-right: auto;margin-left: 20px;line-height: 25px;
				padding: 20px; text-decoration: underline;background: #fff7e6;width:180px;text-align: center;font-size:18px;}
	a:link { color: #1e90ff;}
	a:visited { color: #9932cc; }
	a:hover { color: #17b2e6; }
	a:active { color: #add8e6; }

</style>


<body>
<h2>ユーザー新規登録</h2>
<div class="link">
	<a href="management">ユーザー管理</a><br/>
	<a href="./">ホーム</a>
</div>

<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul style="list-style-type: none">
			<c:forEach items="${ errorMessages }" var="message">
				<li><c:out value="${ message }"></c:out>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
<div class="main">
<form action="signup" method="post">
	<label for="account">ユーザーID</label><br/>
	<input name="account" value="${ User.account }" id="name"/>

	半角英数6文字以上、10文字以内<br/>

	<label for="password">パスワード　　　</label><br/>
	<input name="password" type="password" id="password"/>
	記号も含む半角文字6文字以上、20文字以内<br/>

	<label for="checkPassword">確認用パスワード </label><br/>
	<input name="checkPassword" type="password" id="checkPassword"/><br/>

	<label for="name">ユーザー名　　　 </label><br/>
	<input name="name" value="${ User.name }" id="name"/>
	10文字以内<br/>

	<label for="branch">支店　　　　　　</label><br/>
	<select name="branch_id">
	<option value=""></option>
		<c:forEach items="${ branches }" var="branch">
		<c:if test="${ User.branch_id == branch.id }">
			<option value="${ branch.id }"selected>${ branch.name }</option>
		</c:if>
		<c:if test="${ User.branch_id != branch.id }">
			<option value="${ branch.id }">${ branch.name }</option>
		</c:if>
		</c:forEach>
	</select><br/>
	<label for="">部署・役職　　　</label><br/>
	<select name="position_id">
	<option value=""></option>

		<c:forEach items="${ positions }" var="position">
		<c:if test="${ User.position_id == position.id }">
			<option value="${ position.id }"selected>${ position.name }</option>
		</c:if>
		<c:if test="${ User.position_id != position.id }">
			<option value="${ position.id }">${ position.name }</option>
		</c:if>
		</c:forEach>
	</select>
<br/><br/>
	<div class="signupBotan">
	<input type="hidden" name="id" value="${ branch.id }">
	<input type="hidden" name="id" value="${ position.id }">
	<input type="submit" class="signup" value="登録"/>
	</div>
</form>
</div>
</body>
</html>