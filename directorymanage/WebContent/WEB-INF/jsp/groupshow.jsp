<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<%@ include file="/WEB-INF/jsp/include.jsp"%>

<c:url var="findAllGroups" value="/actions/group/findallgroups" />
<c:url var="findOneGroup" value="/actions/group/findonegroup" />
<c:url var="removeAllGroups" value="/actions/group/removeallgroups" />
<c:url var="removeOneGroup" value="/actions/group/removeonegroup" />
<c:url var="groupDetails" value="/actions/group/groupdetails" />
<c:url var="addGroup" value="/actions/group/addgroup" />

<html>
<head>
<title>Directory manage - Groups</title>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
</head>
<body>
	<div class="container">
		<h1>Groups</h1>

		<table class="table table-hover">
			<tr>
				<td><b>ID</b></td>
				<td><b>Name</b></td>
			</tr>

			<c:choose>
				<c:when test="${groupNames != null}">
					<c:forEach items="${groupNames}" var="group">
						<tr>
							<td><c:out value="${group.key }" /></td>
							<td><c:out value="${group.value}" /></td>
							<td><a class="btn btn-danger"
								href="${removeOneGroup}/${group.key}">Remove</a></td>
						</tr>
					</c:forEach>
					<div class="form-group">
						<a class="btn btn-danger" href="${removeAllGroups}">Remove all
							groups</a>
					</div>
				</c:when>
				<c:otherwise>

					<tr>
						<td><c:out value="${oneGroup.id }" /></td>
						<td><c:out value="${oneGroup.name}" /></td>
						<td><a class="btn btn-danger"
							href="${removeOneGroup}/${oneGroup.id}">Remove</a></td>
					</tr>

				</c:otherwise>
			</c:choose>
		</table>
	</div>
</body>
</html>