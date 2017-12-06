<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/include-recover.jsp"%>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Recover password</title>
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
	<div class="form-gap"></div>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="text-center">
							<h3>
								<i class="fa fa-lock fa-4x"></i>
							</h3>
							<h2 class="text-center">Forgot Password?</h2>
							<c:choose>
								<c:when test="${not empty validEmail}">
									<div class="validEmail">
										<p>${validEmail}</p>
									</div>
								</c:when>
								<c:when test="${not empty invalidEmail}">
									<div class="invalidEmail">
										<p>${invalidEmail}</p>
									</div>
								</c:when>
								<c:otherwise>
									<p>You can reset your password here.</p>
								</c:otherwise>
							</c:choose>
							<div class="panel-body">

								<form id="register-form" role="form" autocomplete="off"
									class="form" method="post">

									<div class="form-group">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-envelope color-blue"></i></span> <input
												id="email" name="email" placeholder="email address"
												class="form-control" type="email">
										</div>
									</div>
									<div class="form-group">
										<input name="recover-submit"
											class="btn btn-lg btn-primary btn-block"
											value="Reset Password" type="submit">
									</div>
									<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
								</form>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>