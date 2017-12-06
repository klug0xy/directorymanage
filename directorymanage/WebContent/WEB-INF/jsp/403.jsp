<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/include-403.jsp"%>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
<c:url var="loginPage" value="/login" />
<c:url var="indexPage" value="/" />
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<!--IE Compatibility modes-->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!--Mobile first-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>403</title>

<meta name="description"
	content="Free Admin Template Based On Twitter Bootstrap 3.x">
<meta name="author" content="">

<meta name="msapplication-TileColor" content="#5bc0de" />
<meta name="msapplication-TileImage"
	content="<c:url value="resources/img/metis-tile.png"/>" />


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body class="error">
	<div class="container">
		<div class="col-lg-8 col-lg-offset-2 text-center">
			<div class="logo">
				<h1>403</h1>
			</div>
			<p class="lead text-muted">Oops, an error has occurred.
				Forbidden!</p>
			<div class="clearfix"></div>
			<div class="col-lg-6 col-lg-offset-3">

			</div>
			<div class="clearfix"></div>
			<div class="sr-only">&nbsp;</div>
			<br>
			<div class="col-lg-6 col-lg-offset-3">
				<div class="btn-group btn-group-justified">
				<sec:authorize access="isAnonymous()">
					<a href=${loginPage } class="btn btn-info">Login here</a>
				</sec:authorize>
					<a href=${indexPage } class="btn btn-warning">Return Website</a>
				</div>
			</div>
		</div>
		<!-- /.col-lg-8 col-offset-2 -->
	</div>
</body>

</html>
