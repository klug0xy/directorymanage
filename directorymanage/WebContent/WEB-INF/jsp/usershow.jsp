<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<%@ include file="/WEB-INF/jsp/include.jsp"%>

<c:url var="findAllPersons" value="/actions/user/findallpersons" />
<c:url var="findAllGroupPersons"
	value="/actions/user/findallgrouppersons" />
<c:url var="findOnePerson" value="/actions/user/findoneperson" />
<c:url var="removeAllPersons" value="/actions/user/removeallpersons" />
<c:url var="removeOnePerson" value="/actions/user/removeoneperson" />
<c:url var="personDetails" value="/actions/user/userdetails" />
<c:url var="addPerson" value="/actions/user/addperson" />


<html>
<head>
<title>Directory manage - Persons</title>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
</head>
<body>
	<div class="container">
		<h1>Persons</h1>

		<table class="table table-hover">
			<c:forEach items="${allPersons}" var="p">
				<tr>
					<td><c:out value="${p.id }" /></td>
					<td><c:out value="${p.firstName}" /></td>
					<td><a class="btn btn-success" href="${personDetails}/${p.id}">Details</a></td>
					<td><a class="btn btn-danger"
						href="${removeOnePerson}/${p.id}">Remove</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>