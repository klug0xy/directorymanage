<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="p" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
<c:url var="findAllPersons" value="/user/actions/findallpersons" />
<c:url var="findAllGroupPersons"
	value="/user/actions/findallgrouppersons" />
<c:url var="findOnePerson" value="/user/actions/findoneperson" />
<c:url var="removeAllPersons" value="/user/actions/removeallpersons" />
<c:url var="removeOnePerson" value="/user/actions/removeoneperson" />
<c:url var="personDetails" value="/user/actions/userdetails" />
<c:url var="addPerson" value="/user/actions/addperson" />
<!DOCTYPE html>
<html>
<head>
<title>Directory manage - Persons</title>
</head>
<body>
	<a style="position:fixed;top:5px;right:5px;margin:0;padding:5px 3px;"
	href="<c:url value="/"></c:url>">Home</a>
	<sec:authorize access="hasRole('ROLE_USER')">
		<a style="position:fixed;top:20px;right:20px;margin:0;padding:5px 3px;"
		href="${ pageContext.request.contextPath}/login?logout">Logout</a>
	</sec:authorize>
	<div class="container">
		<c:choose>
			<c:when test = "${ groupNames != null}">
				<h1>${ groupNames.get(groupId)}</h1>
			</c:when>
			<c:otherwise>
				<h1>All Persons</h1>
			</c:otherwise>
		</c:choose>
		<table class="table table-hover">
			<c:forEach items="${limitPersons}" var="p">
				<tr>
					<td><c:out value="${p.id }" /></td>
					<td><c:out value="${p.firstName}" /></td>
					<td><a class="btn btn-success" href="${personDetails}/${p.id}">Details</a></td>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<td><a class="btn btn-danger"
							href="${removeOnePerson}/${p.id}">Remove</a></td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</table>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<div class="form-group">
				<a class="btn btn-danger" href="${removeAllPersons}">Remove all</a>
			</div>
		</sec:authorize>
		<p:paginate max="${maxRows }" offset="${offset}" count="${count}"
   					uri="${findallpersons }" groupId="${groupId }" 
   					groupName="groupName" next="&raquo;" previous="&laquo;" />
	</div>
</body>
</html>