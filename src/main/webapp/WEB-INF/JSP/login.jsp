<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>Login to Monkhood</title>
</head>

<body>

	<div class="formContainer">
		<form:form action="${pageContext.request.contextPath}/login" method="post" class="form">
			<div class="row">
				<label for="">Email:</label> <input type="text" name="email" />
			</div>
			<div class="row">
				<label for="">Password:</label> <input type="text" name="password" />
			</div>
			<c:if test="${param.error != null}">
				<i style="color: red">Invalid Credentials..!</i>
			</c:if>
			<div class="row">
				<label><a href="${pageContext.request.contextPath}/reset-password">Reset Password</a></label> <input type="submit"
					value="Login" />
			</div>
			<div class="formFooter">
				<a href="${pageContext.request.contextPath}/register">New to Monkhood?</a>
			</div>
		</form:form>
	</div>
</body>

</html>