<%@page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@page isELIgnored= "false" %>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix = "fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv = "Content-Type" content = "text/html; charset=UTF-8">
	<title>ログイン</title>
	<link href="./css01/bootstrap.min.css" rel="stylesheet" type="text/css">
</head>
<style>
	 body {
 	  background:#ffffff;
 	  margin-top:50px;
 	  margin-bottom:50px;
 	}

 	h2{text-align: center;}

 	.loginform{
 		font-size: 18px;
 		padding: 40px; background: #ffdab9;
		width: 500px; color:#6c3524;
		margin: 30px auto; border: 3px solid #ffb9b3;
		text-align: left;

 	}
	.errorMessages{font-size:18px;color:#ff0000;text-align: center; font-weight:600;}
	.loginBotan{background: #ffdab9; width: 400px;
	color:#6c3524; text-align:center;text-align:right;}
	.login{
		position: relative;
		display: inline-block;
	     padding: 0.5em 1.0em;
	    text-decoration: none;
	    color: #FFF;
	    background: #2fb5eb;/*色*/
	    border-radius: 4px;/*角の丸み*/
	    box-shadow: inset 0 2px 0 rgba(255,255,255,0.2), inset 0 -2px 0 rgba(0, 0, 0, 0.05);
	    font-weight: bold;
	    border: solid 2px #248ab3;/*線色*/
	}

	.loginactive {/*押したとき*/
		    box-shadow: 0 0 2px rgba(0, 0, 0, 0.30);
	}

</style>


<body>
<h2>ログイン</h2><br/>
<div class="main-contents">
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul style="list-style-type: none">
			<c:forEach items="${ errorMessages }" var="message">
				<li><c:out value="${ message }"/>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
<div class="loginform">
<form action="login" method="post">
	<label for="account">ユーザーID</label>
	<input name="account" value="${ loginAccount }" id="account"/><br/><br/>

	<label for="password">パスワード</label>
	<input name="password" type="password" id="password"/><br/><br/>
	<div class="loginBotan">
	<input type="submit" class="login" value="ログイン">
	</div>
<c:remove var="loginAccount" scope="session"/>
</form>
</div>
</div>
</body>
</html>