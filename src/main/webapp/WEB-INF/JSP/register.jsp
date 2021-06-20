<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>Resgiter to Monkhood</title>
</head>

<body>

	<div class="formContainer">
		<form:form modelAttribute="user" action="${pageContext.request.contextPath}/register" method="post"
			class="form">
			<div class="row">
				<label for="">FirstName:</label>
				<form:input path="firstName" type="text" />
				<form:errors path="firstName" cssClass="error"></form:errors>
			</div>
			<div class="row">
				<label for="">LastName:</label>
				<form:input path="lastName" type="text" />
				<form:errors path="lastName" cssClass="error"></form:errors>
			</div>
			<div class="row">
				<label for="">Email:</label>
				<form:input path="email" type="text" />
				<form:errors path="email" cssClass="error"></form:errors>
			</div>
			<div class="row">
				<label for="">Mobile:</label>
				<form:input path="mobile" type="text" />
				<form:errors path="mobile" cssClass="error"></form:errors>
			</div>
			<div class="row">
				<label for="">Password:</label>
				<form:input path="password" type="text" />
				<form:errors path="password" cssClass="error"></form:errors>
			</div>
			<div class="row">
				<label for="">Confirm Password:</label>
				<form:input path="confirmPass" type="text" />
			</div>
			<div class="row">
				<span style="color: red">${regMsg}</span> <input type="submit"
					value="Register Yourself" />
			</div>
			<div class="formFooter" style="margin-top: -45px;">
				<a href="${pageContext.request.contextPath}/login">Already have an Account?</a>
			</div>
		</form:form>
	</div>

</body>

</html>