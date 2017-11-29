<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<%@ include file="/WEB-INF/jsp/include.jsp"%>

<c:url var="findAllPersons" value="/user/actions/findallpersons" />
<c:url var="findAllGroupPersons"
	value="/user/actions/findallgrouppersons" />
<c:url var="findOnePerson" value="/user/actions/findoneperson" />
<c:url var="removeAllPersons" value="/user/actions/removeallpersons" />
<c:url var="removeOnePerson" value="/user/actions/removeoneperson" />
<c:url var="personDetails" value="/user/actions/persondetails" />
<c:url var="addPerson" value="/user/actions/addperson" />
<c:url var="group" value="/group" />


<html>
<head>
<title>Directory manage - Persons</title>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
</head>
<body>
	<div class="container">
		<h1>Persons</h1>

		<form
			action="${pageContext.request.contextPath }/user/actions/findallgrouppersons"
			method="POST" class="form-inline">
			<div class="form-group">
				<input id="groupId" name="groupId" size="20" class="form-control" />
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
			<div class="form-group">
				<input type="submit" value="Find all group persons"
					class="form-control btn btn-info" />
			</div>
		</form>
		<form action="${pageContext.request.contextPath }/user/actions/findoneperson"
			method="POST" class="form-inline">

			<div class="form-group">
				<input id="personId" name="personId" size="20" class="form-control" />
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
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
			<a class="btn btn-success" href="${group}">Groups</a>
		</div>
	</div>
	<a href="${ pageContext.request.contextPath}/login?logout">Logout</a>
</body>
</html>