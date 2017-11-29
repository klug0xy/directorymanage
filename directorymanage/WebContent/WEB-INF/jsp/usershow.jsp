<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<%@ include file="/WEB-INF/jsp/include.jsp"%>

<c:url var="findAllPersons" value="/user/actions/findallpersons" />
<c:url var="findAllGroupPersons"
	value="/user/actions/findallgrouppersons" />
<c:url var="findOnePerson" value="/user/actions/findoneperson" />
<c:url var="removeAllPersons" value="/user/actions/removeallpersons" />
<c:url var="removeOnePerson" value="/user/actions/removeoneperson" />
<c:url var="personDetails" value="/user/actions/userdetails" />
<c:url var="addPerson" value="/user/actions/addperson" />


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
		<div class="form-group">
			<a class="btn btn-danger" href="${removeAllPersons}">Remove all</a>
		</div>
	</div>
</body>
</html>