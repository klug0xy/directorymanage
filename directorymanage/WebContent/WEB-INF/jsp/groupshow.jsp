<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<%@ include file="/WEB-INF/jsp/include.jsp"%>

<c:url var="findAllGroups" value="/group/actions/findallgroups" />
<c:url var="findOneGroup" value="/group/actions/findonegroup" />
<c:url var="removeAllGroups" value="/group/actions/removeallgroups" />
<c:url var="removeOneGroup" value="/group/actions/removeonegroup" />
<c:url var="addGroup" value="/group/actions/addgroup" />
<c:url var="updateGroup" value="/group/actions/updategroup" />

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
							<td><a class="btn btn-info"
							href="${updateGroup}?groupId=${group.key}">Edit</a></td>
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
						<td><a class="btn btn-info"
							href="${updateGroup}?groupId=${oneGroup.id}">Edit</a></td>
						<td><a class="btn btn-danger"
							href="${removeOneGroup}/${oneGroup.id}">Remove</a></td>
					</tr>

				</c:otherwise>
			</c:choose>
		</table>
	</div>
</body>
</html>