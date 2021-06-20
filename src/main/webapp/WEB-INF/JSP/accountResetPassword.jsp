<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reset Your Password</title>
</head>
<body>

	<form:form
		action="${pageContext.request.contextPath}/account/reset-password"
		method="post">
		<input type="text" value="${passvkey}" name="passvkey" hidden="true" />
		New Password : <input type="text" name="npass" />
		<br />
		Confirm Password : <input type="text" name="ncpass" />
		<br />
		${accountResetPassMsg}
		<input type="submit" value="Change Password" />
	</form:form>

	<h3>
		<a href="${pageContext.request.contextPath}/login">Login Here</a>
	</h3>

</body>
</html>