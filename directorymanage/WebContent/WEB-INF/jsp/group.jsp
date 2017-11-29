<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<%@ include file="/WEB-INF/jsp/include.jsp"%>

<c:url var="findAllGroups" value="/group/actions/findallgroups" />
<c:url var="findOneGroup" value="/group/actions/findonegroup" />
<c:url var="removeAllGroups" value="/group/actions/removeallgroups" />
<c:url var="removeOneGroup" value="/group/actions/removeonegroup" />
<c:url var="groupDetails" value="/group/actions/groupdetails" />
<c:url var="addGroup" value="/group/actions/addgroup" />
<c:url var="persons" value="/user" />

<html>
<head>
<title>Directory manage - Groups</title>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
</head>
<body>
	<div class="container">
		<h1>Groups</h1>

		<div class="form-group">
			<a class="btn btn-success" href="${findAllGroups}">Find all
				groups</a>
		</div>
		<form action="${pageContext.request.contextPath }/group/actions/findonegroup"
			method="POST" class="form-inline">

			<div class="form-group">
				<input id="groupId" name="groupId" size="20" class="form-control" />
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
			<div class="form-group">
				<input type="submit" value="Find group"
					class="form-control btn btn-info" />
			</div>
		</form>
		<div class="form-group">
			<a class="btn btn-success" href="${addGroup}">Add group</a>
		</div>
		<div class="form-group">
			<a class="btn btn-success" href="${persons}">Persons</a>
		</div>
	</div>
	<a href="${ pageContext.request.contextPath}/login?logout">Logout</a>
</body>
</html>