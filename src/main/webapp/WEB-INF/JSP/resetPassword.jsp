<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reset Password</title>
</head>
<body>

	<form:form action="${pageContext.request.contextPath}/reset-password"
		method="post">
		Email : <input type="text" name="email" />
		${resetPassMsg}
		<br />
		<input type="submit" value="Reset Password" />
	</form:form>

</body>
</html>