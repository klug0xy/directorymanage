<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/head-bootstrap.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Edit person profile</title>
<script type="text/javascript">
	$(function() {
		$("#birthday").datepicker({
			dateFormat : "dd/mm/yy"
		});
	})
</script>
</head>

<body>
	<div class="top-left">
		<div class="language">
			Language : <a href="?language=en">English</a>| <a href="?language=fr">Français</a>|
			<a href="?language=ar">Arabe</a>
		</div>
	</div>
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
		<h1>Edit person</h1>

		<form:form method="POST" commandName="person">

			<form:errors path="*" cssClass="alert alert-danger" element="div" />

			<div class="form-group">
				<!-- 				<label for="id">Id :</label> -->
				<form:input class="form-control" type="hidden" path="id"
					value="${person.id}" />
				<form:errors path="id" cssClass="alert alert-warning" element="div" />
			</div>
			<div class="form-group">
				<label for="firstName">First name :</label>
				<form:input class="form-control" path="firstName"
					value="${person.firstName }" />
				<form:errors path="firstName" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="lastName">Last name :</label>
				<form:input class="form-control" path="lastName"
					value="${person.lastName }" />
				<form:errors path="lastName" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="mail">Email :</label>
				<form:input path="mail" class="form-control" value="${person.mail }" />
				<form:errors path="mail" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="website">Web site :</label>
				<form:input path="website" class="form-control"
					value="${person.website }" />
				<form:errors path="website" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="birthday">Birthday :</label>
				<form:input class="form-control" path="birthday" type="date"
					id="birthday" value="${person.birthday }" />
				<form:errors path="birthday" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="password">Password :</label>
				<form:input type="password" path="password" class="form-control"
					value="${person.password }" />
				<form:errors path="password" cssClass="alert alert-warning"
					element="div" />
			</div>
			<div class="form-group">
				<label for="groupId">Group :</label>
				<form:select path="groupId" multiple="false" class="form-control">
					<form:option value="${person.groupId }" label="--- Select ---" />
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