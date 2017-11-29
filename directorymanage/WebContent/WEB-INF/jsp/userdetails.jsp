<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<%@ include file="/WEB-INF/jsp/include.jsp"%>

<c:url var="findAllPersons" value="/user/actions/findallpersons" />
<c:url var="findAllGroupPersons" value="/user/actions/findallgrouppersons" />
<c:url var="findOnePerson" value="/user/actions/findoneperson" />
<c:url var="removeAllPersons" value="/user/actions/removeallpersons" />
<c:url var="removeOnePerson" value="/user/actions/removeoneperson" />
<c:url var="personDetails" value="/user/actions/persondetails" />
<c:url var="addPerson" value="/user/actions/addperson" />
<c:url var="editPerson" value="/user/actions/editperson" />


<html>
<head>
<title>Directory manage - Persons</title>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
</head>
<body>
	<a style="position:fixed;top:5px;right:5px;margin:0;padding:5px 3px;"
	href="<c:url value="/"></c:url>">Home</a>
	<div class="container">
		<h1>Person</h1>

		<table class="table table-hover">
				<tr>
					<td><b>ID</b></td>
					<td><b>Last Name</b></td>
					<td><b>First Name</b></td>
					<td><b>Web site</b></td>
					<td><b>Group</b></td>
				</tr>
				<tr>
					<td><c:out value="${onePerson.id }" /></td>
					<td><c:out value="${onePerson.lastName}" /></td>
					<td><c:out value="${onePerson.firstName }" /></td>
					<td><c:out value="${onePerson.website }" /></td>
					<td><c:out value="${groupNames.get(onePerson.groupId)}" /></td>
					<td><a class="btn btn-success" href="${editPerson}?personId=${onePerson.id}">Edit</a></td>
					<td><a class="btn btn-danger" href="${removeOnePerson}/${onePerson.id}">Remove</a></td>
				</tr>
		</table>
		
	</div>
</body>
</html>