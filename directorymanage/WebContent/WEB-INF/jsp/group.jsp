<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
<c:url var="findAllGroups" value="/group/actions/findallgroups" />
<c:url var="findOneGroup" value="/group/actions/findonegroup" />
<c:url var="removeAllGroups" value="/group/actions/removeallgroups" />
<c:url var="removeOneGroup" value="/group/actions/removeonegroup" />
<c:url var="groupDetails" value="/group/actions/groupdetails" />
<c:url var="addGroup" value="/group/actions/addgroup" />
<c:url var="persons" value="/user" />
<!DOCTYPE html>
<html>
<head>
<title>Directory manage - Groups</title>
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
		<h1>Groups</h1>

		<div class="form-group">
			<a class="btn btn-success" href="${findAllGroups}">Find all
				groups</a>
		</div>
		<form action="${pageContext.request.contextPath }/group/actions/findonegroup"
			method="POST" class="form-inline">

			<div class="form-group">
				<input id="groupName" name="groupName" size="20" class="form-control" />
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
			<div class="form-group">
				<input type="submit" value="Find group"
					class="form-control btn btn-info" />
			</div>
		</form>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<div class="form-group">
				<a class="btn btn-success" href="${addGroup}">Add group</a>
			</div>
		</sec:authorize>
		<div class="form-group">
			<a class="btn btn-success" href="${persons}">Persons directory</a>
		</div>
	</div>
</body>
</html>