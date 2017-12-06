<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
<c:url var="findAllGroups" value="/group/actions/findallgroups" />
<c:url var="findOneGroup" value="/group/actions/findonegroup" />
<c:url var="removeAllGroups" value="/group/actions/removeallgroups" />
<c:url var="removeOneGroup" value="/group/actions/removeonegroup" />
<c:url var="addGroup" value="/group/actions/addgroup" />
<c:url var="updateGroup" value="/group/actions/updategroup" />
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
		<h1>
			<c:if test="${ groupName != null}">
				Group(s) for "${groupName }"
			</c:if>
			<c:if test="${oneGroup != null }">
				Details for ${oneGroup.name }
			</c:if>
		</h1>

		<table class="table table-hover">
			<tr>
				<td><b>ID</b></td>
				<td><b>Name</b></td>
			</tr>

			<c:choose>
				<c:when test="${limitGroups != null}">
					<c:forEach items="${limitGroups}" var="group">
						<tr>
							<td><c:out value="${group.key }" /></td>
							<td><c:out value="${group.value}" /></td>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<td><a class="btn btn-info"
								href="${updateGroup}?groupId=${group.key}">Edit</a></td>
								<td><a class="btn btn-danger"
								href="${removeOneGroup}/${group.key}">Remove</a></td>
							</sec:authorize>
						</tr>
					</c:forEach>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<div class="form-group">
							<a class="btn btn-danger" href="${removeAllGroups}">Remove all
								groups</a>
						</div>
					</sec:authorize>
				</c:when>
				<c:when test="${groups != null}">
					<c:forEach items="${groups}" var="group">
						<tr>
							<td><c:out value="${group.id}" /></td>
							<td><c:out value="${group.name}" /></td>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<td><a class="btn btn-info"
								href="${updateGroup}?groupId=${group.id}">Edit</a></td>
								<td><a class="btn btn-danger"
								href="${removeOneGroup}/${group.id}">Remove</a></td>
							</sec:authorize>
						</tr>
					</c:forEach>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<div class="form-group">
							<a class="btn btn-danger" href="${removeAllGroups}">Remove all
								groups</a>
						</div>
					</sec:authorize>
				</c:when>
				<c:otherwise>

					<tr>
						<td><c:out value="${oneGroup.id }" /></td>
						<td><c:out value="${oneGroup.name}" /></td>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<td><a class="btn btn-info"
								href="${updateGroup}?groupId=${oneGroup.id}">Edit</a></td>
							<td><a class="btn btn-danger"
								href="${removeOneGroup}/${oneGroup.id}">Remove</a></td>
						</sec:authorize>
					</tr>

				</c:otherwise>
			</c:choose>
		</table>
		<p:paginate max="${maxRows }" offset="${offset}" count="${count}"
   uri="${findAllGroups }" next="&raquo;" previous="&laquo;" />
	</div>
</body>
</html>