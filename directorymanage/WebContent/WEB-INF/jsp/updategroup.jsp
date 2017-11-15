<!DOCTYPE html>

<%@ include file="/WEB-INF/jsp/include.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Add group</title>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
</head>

<body>
	Language :
	<a href="?language=en">English</a>|
	<a href="?language=fr">Français</a>|
	<a href="?language=ar">Arabe</a>

	<div class="container">
		<h1>Edit group</h1>

		<form:form method="POST" modelAttribute="groupForm">

			<form:errors path="*" cssClass="alert alert-danger" element="div"/>

			<div class="form-group">
<!-- 				<label for="id">Id :</label> -->
				<form:input class="form-control" path="id"
					placeholder="insert group id here" type="hidden" value="${group.id }"/>
				<form:errors path="id" cssClass="alert alert-warning" 
				element="div" />
			</div>
			<div class="form-group">
				<label for="name">Name :</label>
				<form:input class="form-control" path="name" value="${group.name }"/>
				<form:errors path="name" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-info">Submit</button>
			</div>
		</form:form>
	</div>
</body>
</html>