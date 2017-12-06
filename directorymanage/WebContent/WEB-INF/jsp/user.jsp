<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
<c:url var="findAllPersons" value="/user/actions/findallpersons" />
<c:url var="findAllGroupPersons"
	value="/user/actions/findallgrouppersons" />
<c:url var="findOnePerson" value="/user/actions/findoneperson" />
<c:url var="removeAllPersons" value="/user/actions/removeallpersons" />
<c:url var="removeOnePerson" value="/user/actions/removeoneperson" />
<c:url var="personDetails" value="/user/actions/persondetails" />
<c:url var="addPerson" value="/user/actions/addperson" />
<c:url var="group" value="/group" />
<!DOCTYPE html>
<html>
<head>
<title>Directory manage - Persons</title>
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
		<h1>Persons</h1>

		<form
			action="${pageContext.request.contextPath }/user/actions/findallgrouppersons"
			method="POST" class="form-inline">
			<div class="form-group">
				<input id="groupName" name="groupName" size="20" class="form-control" />
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
			<div class="form-group">
				<input type="submit" value="Find all group persons"
					class="form-control btn btn-info" />
			</div>
		</form>
		<form action="${pageContext.request.contextPath }/user/actions/findpersons"
			method="POST" class="form-inline">

			<div class="form-group">
				<input id="personName" name="personName" size="20" class="form-control" />
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
			<div class="form-group">
				<input type="submit" value="Find person"
					class="form-control btn btn-info" />
			</div>
		</form>
		<div class="form-group">
			<a class="btn btn-success" href="${findAllPersons}">Find all
				persons</a>
		</div>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<div class="form-group">
				<a class="btn btn-success" href="${addPerson}">Add person</a>
			</div>
		</sec:authorize>
		<div class="form-group">
			<a class="btn btn-success" href="${group}">Groups directory</a>
		</div>
	</div>
</body>
</html>