<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Add person</title>
</head>
<body>
	Language :
	<a href="?language=en">English</a>|
	<a href="?language=fr">Français</a>|
	<a href="?language=ar">Arabe</a>
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
		<h1>Add person</h1>

 		<form:form method="POST" modelAttribute="personForm"> <%--commandName="person"--%> 

			<form:errors path="*" cssClass="alert alert-danger" element="div" />

			<div class="form-group">
<!-- 				<label for="id">Id :</label> -->
				<form:input class="form-control" path="id" type="hidden" value="1"/>
				<form:errors path="id" cssClass="alert alert-warning" element="div" />
			</div>
			<div class="form-group">
				<label for="firstName">First name :</label>
				<form:input class="form-control" path="firstName" />
				<form:errors path="firstName" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="lastName">Last name :</label>
				<form:input class="form-control" path="lastName" />
				<form:errors path="lastName" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="mail">Email :</label>
				<form:input path="mail" class="form-control" />
				<form:errors path="mail" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="website">Web site :</label>
				<form:input path="website" class="form-control" />
				<form:errors path="website" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="birthday">Birthday :</label>
				<form:input class="form-control" path="birthday" type="date"
					id="birthday" />
				<form:errors path="birthday" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="password">Password :</label>
				<form:input type="password" path="password" class="form-control" />
				<form:errors path="password" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="groupId">Group :</label>
				<form:select path="groupId" multiple="false" class="form-control">
					<form:option value="${groupNames.key }" label="--- Select ---" />
					<form:options items="${groupNames}" />
				</form:select>
				<form:errors path="groupId" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-info">Submit</button>
			</div>
		</form:form>
	</div>
</body>
</html>