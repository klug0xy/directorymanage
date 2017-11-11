<!DOCTYPE html>

<%@ include file="/WEB-INF/jsp/include.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Add person</title>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
</head>

<body>
	Language :
	<a href="?language=en">English</a>|
	<a href="?language=fr">Français</a>|
	<a href="?language=ar">Arabe</a>

	<div class="container">
		<h1>Add person</h1>

		<form:form method="POST" commandName="person">

			<form:errors path="*" cssClass="alert alert-danger" element="div" />

			<div class="form-group">
				<label for="id">Id:</label>
				<form:input class="form-control" path="id" />
				<form:errors path="id" cssClass="alert alert-warning" element="div" />
			</div>
			<div class="form-group">
				<label for="firstName">First name:</label>
				<form:input class="form-control" path="firstName" />
				<form:errors path="firstName" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="lastName">Last name:</label>
				<form:input class="form-control" path="lastName" />
				<form:errors path="lastName" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="birthday">Birthday:</label>
				<form:input class="form-control" path="birthday" type="date"
					id="datetimepicker1" />
				<form:errors path="birthday" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="mail">Email:</label>
				<form:input path="mail" class="form-control" />
				<form:errors path="mail" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="groupId">Group:</label>
				<form:select path="groupId" multiple="false" class="form-control">
					<form:option value="${groupNames.key }" label="--- Select ---" />
					<form:options items="${groupNames}" />
				</form:select>
				<form:errors path="groupId" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-info">Submit</button>
			</div>
		</form:form>
	</div>
</body>
</html>