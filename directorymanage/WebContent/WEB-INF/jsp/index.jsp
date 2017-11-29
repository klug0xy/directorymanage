<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE >
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%-- rediriger le contrôleur login --%>
<%-- <c:redirect url="/login.htm"/> --%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title }</title>
</head>
<body>
<h1>${message }</h1>
<a href="${ pageContext.request.contextPath}/user">Users manager</a><br/>
<a href="${ pageContext.request.contextPath}/group">Groups manager</a><br/>
<a href="${ pageContext.request.contextPath}/">Edit your profile <c:out value="${username}"></c:out></a><br/>
<a href="${ pageContext.request.contextPath}/login?logout">Logout</a><br/>
</body>
</html>