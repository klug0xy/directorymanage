<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
<%@ include file="/WEB-INF/jsp/include-login.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false"%>
<c:url var="addPerson" value="user/actions/addperson" />
<c:url var="recover" value="/recover" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Directory manage - Login</title>
</head>
<body>
	<div class="top-right-home">
		<a href="<c:url value="/"/>">Home</a>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-login">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-6">
								<a href="#" class="active" id="login-form-link">Login</a>
							</div>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<div class="col-xs-6">
									<a href="${addperson }" id="register-form-link" onclick="changeContent()">Register</a>
								</div>
							</sec:authorize>
						</div>
						<hr>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<c:if test="${not empty error}">
									<div class="error"><p>${error}</p></div>
								</c:if>
								<c:if test="${not empty msg}">
									<div class="msg"><p>${msg}</p></div>
								</c:if>
								<c:url var="loginUrl" value="/login" />
								<c:url var="loginUrlCsrf" value="/login?${_csrf.parameterName}=${_csrf.token}" />
								<form id="login-form" action="${loginUrl }" method="post" role="form"
									style="display: block;">
									<div class="form-group">
										<input type="text" name="mail" id="mail" tabindex="1" 
										class="form-control" placeholder="Email" value=""/>
									</div>
									<div class="form-group">
										<input type="password" name="password" id="password"
											tabindex="2" class="form-control" placeholder="Password"/>
									</div>
									<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
									<div class="form-group text-center">
										<input type="checkbox" tabindex="3" class="" name="remember"
											id="remember"> <label for="remember">
											Remember Me</label>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="login-submit" id="login-submit"
													tabindex="4" class="form-control btn btn-login"
													value="Log In">
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-lg-12">
												<div class="text-center">
													<a href="${recover }" tabindex="5"
														class="forgot-password">Forgot Password?</a>
												</div>
											</div>
										</div>
									</div>
								</form>
								<form id="register-form" action="${addPerson }" method="post"
									role="form" style="display: none;">
									<div class="form-group">
										<input type="text" name="username" id="username" tabindex="1"
											class="form-control" placeholder="Username" value="">
									</div>
									<div class="form-group">
										<input type="email" name="email" id="email" tabindex="1"
											class="form-control" placeholder="Email Address" value="">
									</div>
									<div class="form-group">
										<input type="password" name="password" id="password"
											tabindex="2" class="form-control" placeholder="Password">
									</div>
									<div class="form-group">
										<input type="password" name="confirm-password"
											id="confirm-password" tabindex="2" class="form-control"
											placeholder="Confirm Password">
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="register-submit"
													id="register-submit" tabindex="4"
													class="form-control btn btn-register" value="Register Now">
											</div>
										</div>
									</div>
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