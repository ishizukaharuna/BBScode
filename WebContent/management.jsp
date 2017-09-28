<%@page language = "java" contentType = "text/html; charset=UTF-8" pageEncoding = "UTF-8" %>
<%@page isELIgnored= "false" %>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix = "fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="./css01/bootstrap.min.css" rel="stylesheet" type="text/css"><link href="./css01/bootstrap.min.css" rel="stylesheet" type="text/css">
	<meta http-equiv = "Content-Type" content = "text/html; charset=UTF-8">
	<title>ユーザー管理</title>
	<script type="text/javascript">

	function check(){

		if(window.confirm('送信してよろしいですか？')){ // 確認ダイアログを表示

			return true; // 「OK」時は送信を実行

		}
		else{ // 「キャンセル」時の処理

			window.alert('キャンセルされました'); // 警告ダイアログを表示
			return false; // 送信を中止
		}
	}
	</script>

	<style>
		a:link { color: #1e90ff;}
		a:visited { color: #9932cc; }
		a:hover { color: #17b2e6; }
		a:active { color: #add8e6; }
		h2{text-align:center;padding: 20px; font-weight:600;}
		.link{border: 3px solid #ffb9b3;margin-right: auto;margin-left: 20px;line-height: 25px;
				padding: 20px; text-decoration: underline;background: #fff7e6;width:180px;text-align: center;font-size:18px;}
		body {
 		   background: #ffdab9;
 		   margin-bottom:50px;
 		 }
 		a{text-decoration: underline;}
		table{margin: 30px auto;background: #ffffff;font-size: 17px;
		border-collapse: collapse;
  		border: solid 2px #ff9999;/*表全体を線で囲う*/
  		color:#6c3524;
		}
 		 table th {/*table内のthに対して*/
		  padding: 15px;/*上下左右10pxずつ*/
		 background: #ffeecc;
		  text-align:center;
		}

		table td {/*table内のtdに対して*/
		  padding: 8px 15px;/*上下3pxで左右10px*/
		  text-align:center;
		}

		.stop{
			position: relative;
		    display: inline-block;
		    padding: 0.2em 0.5em;
		    text-decoration: none;
		    color: #FFF;
		    background: #ff002b;/*色*/
		    border-radius: 4px;/*角の丸み*/
		    box-shadow: inset 0 2px 0 rgba(255,255,255,0.2), inset 0 -2px 0 rgba(0, 0, 0, 0.05);
		    font-weight: bold;
		    border: solid 2px #e6004d;/*線色*/
		}

		.stop:active{/*押したとき*/
		    box-shadow: 0 0 2px rgba(0, 0, 0, 0.30);
		}

		.reborn{
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

		.reborn:active {/*押したとき*/
		    box-shadow: 0 0 2px rgba(0, 0, 0, 0.30);
		}



	</style>

</head>
<body>

<h2>ユーザー管理</h2>
<div class="link">
<a href="signup">ユーザー登録</a><br/>
<a href="./">ホーム</a><br/>
</div>
<div class="main">
	<c:if test="${ not empty errorMessages }">
		<div class="errorMessages">
			<ul>
				<c:forEach items="${ errorMessages }" var="message">
					<li><c:out value="${ message }"/>
				</c:forEach>
			</ul>
		</div>
		<c:remove var="errorMessages" scope="session"/>
	</c:if>
<table border=3>
 <tr><th>ユーザーID</th>
 	 <th>ユーザー名</th>
 	  <th>支店名</th>
 	  <th>部署・役職</th>
 	  <th>編集</th>
 	  <th>復活・停止</th></tr>


		<c:forEach items="${ managements }" var="management">
		<tr>
			<div class="users">
				<div class="user-infomation">
					<td>
						<span class="account">
							<c:out value="${ management.account }"/>
						</span>
					</td>
					<td>
						<span class="name">
							<c:out value="${ management.name }"/>
						</span>
					</td>
					<td>
						<span class="branchId">
							<c:forEach items="${ branches }" var="branch">
								<c:if test="${ management.branch_id == branch.id }">
									<c:out value="${ branch.name }"/>
								</c:if>
							</c:forEach>
						</span>
					</td>
					<td>
						<span class="positionId">
							<c:forEach items="${ positions }" var="position">
								<c:if test="${ management.position_id == position.id }">
									<c:out value="${ position.name }"/>
								</c:if>
							</c:forEach>
						</span>
					</td>
					<td>
						<a href="settings?id=${ management.id }">編集</a>
					</td>
					<td>
						<c:if test="${ loginUser.id == management.id }">
							<c:out value="/"/>
						</c:if>
						<c:if test="${ loginUser.id != management.id }">
							<form action="userStop" method="post" onSubmit="return check()">
								<input type="hidden" name="id" value="${ management.id }">
							<c:if test="${ management.is_working == 0 }">
									<input type="hidden" name="is_working" value="1">
									<input type = "submit"  class="stop" value="停止">
								</c:if>
								<c:if test="${ management.is_working == 1 }">
									<input type="hidden" name="is_working" value="0">
									<input type = "submit" class="reborn" value="復活">
								</c:if>
							</form>
						</c:if>
					</td>
				</tr>
			</div>
		</div>
		</tr>
	</c:forEach>
</table>
</div>
</body>
</html>
