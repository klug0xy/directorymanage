<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
<%@ include file="/WEB-INF/jsp/include-newpassword.jsp"%>
<c:url var="newPassUrl" value="/newpassword" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New Password</title>
</head>
<body>
<div class="top-right-home">
		<a href="<c:url value="/"/>">Home</a>
	</div>
	<div class="top-right-login">
		<sec:authorize access="isAnonymous()">
			<a href="${ pageContext.request.contextPath}/login">Login</a>
		</sec:authorize>
	</div>
	<div class="top-right-logout">
		<sec:authorize access="isAuthenticated()">
			<a href="${ pageContext.request.contextPath}/login?logout">Logout</a>
		</sec:authorize>
	</div>

	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<h1>Change Password</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6 col-sm-offset-3">
				<c:choose>
					<c:when test="${not empty successChangePassMsg}">
						<div class="successChangePassMsg">
							<p>${successChangePassMsg}</p>
						</div>
					</c:when>
					<c:when test="${not empty failChangePassMsg}">
						<div class="failChangePassMsg">
							<p>${failChangePassMsg}</p>
						</div>
					</c:when>
					<c:otherwise>
						<p class="text-center">Use the form below to change your
							password. Your password cannot be the same as your username.</p>
					</c:otherwise>
				</c:choose>
				<form method="post" action="${newPassUrl }?personId=${personId}"
					id="passwordForm">
					<input type="password" class="input-lg form-control"
						name="password1" id="password1" placeholder="New Password"
						autocomplete="off">
					<div class="row">
						<div class="col-sm-6">
							<span id="8char" class="glyphicon glyphicon-remove"
								style="color: #FF0004;"></span> 8 Characters Long<br> <span
								id="ucase" class="glyphicon glyphicon-remove"
								style="color: #FF0004;"></span> One Uppercase Letter
						</div>
						<div class="col-sm-6">
							<span id="lcase" class="glyphicon glyphicon-remove"
								style="color: #FF0004;"></span> One Lowercase Letter<br> <span
								id="num" class="glyphicon glyphicon-remove"
								style="color: #FF0004;"></span> One Number
						</div>
					</div>
					<input type="password" class="input-lg form-control"
						name="password2" id="password2" placeholder="Repeat Password"
						autocomplete="off">
					<div class="row">
						<div class="col-sm-12">
							<span id="pwmatch" class="glyphicon glyphicon-remove"
								style="color: #FF0004;"></span> Passwords Match
						</div>
					</div>
					<input type="submit"
						class="col-xs-12 btn btn-primary btn-load btn-lg"
						data-loading-text="Changing Password..." value="Change Password">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
			<!--/col-sm-6-->
		</div>
		<!--/row-->
	</div>

</body>
</html>