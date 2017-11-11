<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<%@ include file="/WEB-INF/jsp/include.jsp"%>

<c:url var="findAllPersons" value="/actions/user/findallpersons" />
<c:url var="findAllGroupPersons"
	value="/actions/user/findallgrouppersons" />
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
		<h1>Persons</h1>

		<form
			action="${pageContext.request.contextPath }/actions/user/findallgrouppersons"
			method="POST" class="form-inline">
			<div class="form-group">
				<input id="groupId" name="groupId" size="20" class="form-control" />
			</div>
			<div class="form-group">
				<input type="submit" value="Find all group persons"
					class="form-control btn btn-info" />
			</div>
		</form>
		<form action="${pageContext.request.contextPath }/actions/user/findoneperson"
			method="POST" class="form-inline">

			<div class="form-group">
				<input id="personId" name="personId" size="20" class="form-control" />
			</div>
			<div class="form-group">
				<input type="submit" value="Find person"
					class="form-control btn btn-info" />
			</div>
		</form>
		<div class="form-group">
			<a class="btn btn-success" href="${findAllPersons}">Find all
				persons</a>
		</div>
		<div class="form-group">
			<a class="btn btn-success" href="${addPerson}">Add person</a>
		</div>
		<div class="form-group">
			<a class="btn btn-danger" href="${removeAllPersons}">Remove all</a>
		</div>

	</div>
</body>
</html>