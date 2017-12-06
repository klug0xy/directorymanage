<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="p" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
<c:url var="findAllPersons" value="/user/actions/findallpersons" />
<c:url var="findAllGroupPersons" value="/user/actions/findallgrouppersons" />
<c:url var="findOnePerson" value="/user/actions/findoneperson" />
<c:url var="removeAllPersons" value="/user/actions/removeallpersons" />
<c:url var="removeOnePerson" value="/user/actions/removeoneperson" />
<c:url var="personDetails" value="/user/actions/persondetails" />
<c:url var="addPerson" value="/user/actions/addperson" />
<c:url var="editPerson" value="/user/actions/editperson" />
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
		<c:choose>
			<c:when  test="${ personName != null }">
				<h1>Person(s) result for "${personName }"</h1>
			</c:when>
			<c:when test="${ groupName != null && groupId != null}">
				<h1>Persons for '${ groupName}' group with id ${ groupId}</h1>
			</c:when>
		</c:choose>
		<table class="table table-hover">
				<tr>
					<td><b>Last Name</b></td>
					<td><b>First Name</b></td>
					<td><b>Web site</b></td>
					<td><b>Group</b></td>
				</tr>
				<c:forEach items="${persons}" var="person">
					<tr>
						<td><c:out value="${person.lastName}" /></td>
						<td><c:out value="${person.firstName }" /></td>
						<td><c:out value="${person.website }" /></td>
						<td><c:out value="${groupNames.get(person.groupId)}" /></td>
						<td><a class="btn btn-success" href="${editPerson}?personId=${person.id}">Edit</a></td>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<td><a class="btn btn-danger" href="${removeOnePerson}/${person.id}">Remove</a></td>
						</sec:authorize>
					</tr>
				</c:forEach>
		</table>
		<c:choose>
			<c:when test="${groupName != null}">
					<p:paginate max="${maxRows }" offset="${offset}" count="${count}"
   					uri="${findAllGroupPersons }" groupId="${groupId }" groupName="${groupName }" 
   					next="&raquo;" previous="&laquo;" />
			</c:when>
			<c:otherwise>
						<p:paginate max="${maxRows }" offset="${offset}" count="${count}"
   					uri="${findAllPersons }" groupId="${groupId }" 
   					groupName="groupName" next="&raquo;" previous="&laquo;" />
			</c:otherwise>
		</c:choose>
	
	</div>
</body>
</html>