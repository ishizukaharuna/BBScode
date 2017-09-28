<%@page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@page isELIgnored= "false" %>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix = "fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv = "Content-Type" content = "text/html; charset=UTF-8">
	<title>新規投稿</title>
	<link href="./css01/bootstrap.min.css" rel="stylesheet" type="text/css">
</head>
<style>


	 body {
 	  background: #ffdab9;
 	  margin-top:50px;
 	  margin-bottom:50px;
 	}
 	a:link { color: #1e90ff;}
	a:visited { color: #9932cc; }
	a:hover { color: #17b2e6; }
	a:active { color: #add8e6; }
 	.main-contents{
		padding: 20px; background: #ffffff;
		width: 700px; color:#6c3524;
		margin: 30px auto; border: 3px solid #ffb9b3;
		text-align: center;
		}
	h2{text-align: center; font-weight:600;}
	.title{font-size:18px;font-weight:200;text-align:left;}
	.text{font-size:20px;font-weight:200;text-align:left;color: #664229;}
	p{font-size:18px;background: #ffffff;width: 650px;text-align:right;}
	.category{text-align:left; font-size: 18px; color: #664229;font-weight:200;}

	.errorMessages{font-size:18px;color:#ff0000;text-align: center; font-weight:600;}
	.contributionPosition{background:#ffffff; width: 584px; color:#6c3524; margin: 30px auto; text-align:right;}
	.return{border: 3px solid #ffb9b3;margin-right: auto;margin-left: 20px;
			padding: 20px; text-decoration: underline;background: #fff7e6;width:180px;text-align: center;font-size:18px;}

	.contribution{
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

	.contribution:active {/*押したとき*/
	    box-shadow: 0 0 2px rgba(0, 0, 0, 0.30);
	}


</style>

<body>
<h2>新規投稿</h2>
<a href="./" class="return">ホーム</a>


	<div class="errorMessages">
		<ul style="list-style-type: none">
			<c:forEach items="${ errorMessages }" var="message">
				<li><c:out value="${ message }"></c:out>
			</c:forEach>
		</ul>
	</div>
<div class="main-contents">
<form action="newMessage" method="post">
	<div class="title">
	件名<br/>
	<input name="title" size="50" value="${ message.title }" id="title"/>
	30文字以内<br/><br/>
	</div>
	<div class="category">
	カテゴリー
	<label for="category"></label><br/>
	<input name="category" value="${ message.category }" id="category"/>
	10文字以内<br/><br/>
	</div>
	<div class="text">
	本文<br/>
	<textarea name="text" cols="63" rows="8" class="comment-box"><c:out value="${ message.text }"/></textarea><br/>
	</div>
	<p>1000文字以内</p>
	<br/>
	<div class="contributionPosition">
	<input type="submit" class="contribution" value="投稿する">
	</div>
</form>
</div>
<br/>
</body>
</html>