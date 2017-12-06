<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
<%@ include file="/WEB-INF/jsp/include-index.jsp"%>
<c:url var="editPerson" value="user/actions/editperson" />
<!DOCTYPE html>
<html lang="fr">
<head>
<title>${title }</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<div class="container-fluid bg-1 text-center">
		<h1>${message }</h1>
		<h3>JEE 1 project - Directory manager</h3>
		<div class="img1">
			<img src="<c:url value="resources/img/amu.jpeg"/>" class="img-responsive img-rounded"
				style="display: inline" alt="Amu">
		</div>
		<a href="${ pageContext.request.contextPath}/user">Users manager</a><br />
		<a href="${ pageContext.request.contextPath}/group">Groups manager</a><br />
		<sec:authorize access="isAuthenticated()">
			<a href="<c:url value="${editPerson }?personMail=${username }"/>">
			Edit your profile <c:out value="${username}"></c:out></a><br />
			<a href="${ pageContext.request.contextPath}/login?logout">Logout</a><br />
		</sec:authorize>
		<sec:authorize access="isAnonymous()">
			<a href="${ pageContext.request.contextPath}/login">Login here</a>
		</sec:authorize>
	</div>
	<footer class="container-fluid bg-4 text-center">
		<p>
			Project made by : <a href="mjidhoussem@gmail.com">Houssem MJID</a><br />
			<a href="">Mohamad ABDELNABI</a>
		</p>
	</footer>
</body>
</html>