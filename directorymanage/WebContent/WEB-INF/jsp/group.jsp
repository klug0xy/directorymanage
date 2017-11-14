<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<%@ include file="/WEB-INF/jsp/include.jsp"%>

<c:url var="findAllGroups" value="/actions/group/findallgroups" />
<c:url var="findOneGroup" value="/actions/group/findonegroup" />
<c:url var="removeAllGroups" value="/actions/group/removeallgroups" />
<c:url var="removeOneGroup" value="/actions/group/removeonegroup" />
<c:url var="groupDetails" value="/actions/group/groupdetails" />
<c:url var="addGroup" value="/actions/group/addgroup" />
<c:url var="persons" value="/actions/user" />

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
		<form action="${pageContext.request.contextPath }/actions/group/findonegroup"
			method="POST" class="form-inline">

			<div class="form-group">
				<input id="groupId" name="groupId" size="20" class="form-control" />
			</div>
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
</body>
</html>