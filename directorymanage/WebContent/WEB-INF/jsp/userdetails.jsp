<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<%@ include file="/WEB-INF/jsp/include.jsp"%>

<c:url var="findAllPersons" value="/actions/user/findallpersons" />
<c:url var="findAllGroupPersons" value="/actions/user/findallgrouppersons" />
<c:url var="findOnePerson" value="/actions/user/findoneperson" />
<c:url var="removeAllPersons" value="/actions/user/removeallpersons" />
<c:url var="removeOnePerson" value="/actions/user/removeoneperson" />
<c:url var="personDetails" value="/actions/user/persondetails" />
<c:url var="addPerson" value="/actions/user/addperson" />


<html>
<head>
<title>Directory manage - Persons</title>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
</head>
<body>
	<div class="container">
		<h1>Person</h1>

		<table class="table table-hover">
				<tr>
					<td><b>ID</b></td>
					<td><b>Last Name</b></td>
					<td><b>First Name</b></td>
					<td><b>Birthday</b></td>
					<td><b>Email</b></td>
					<td><b>Group</b></td>
				</tr>
				<tr>
					<td><c:out value="${onePerson.id }" /></td>
					<td><c:out value="${onePerson.lastName}" /></td>
					<td><c:out value="${onePerson.firstName }" /></td>
					<td><c:out value="${onePerson.birthday}" /></td>
					<td><c:out value="${onePerson.mail }" /></td>
					<td><c:out value="${onePerson.groupId}" /></td>
					<td><a class="btn btn-success" href="${addPerson}/${onePerson.id}">Edit</a></td>
					<td><a class="btn btn-danger" href="${removeOnePerson}/${onePerson.id}">Remove</a></td>
				</tr>
		</table>
		
	</div>
</body>
</html>