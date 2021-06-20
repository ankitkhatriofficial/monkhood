<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add User</title>
<link rel="stylesheet"
	href="${pageCoontext.request.contextPath}/css/emp/style.css" />
</head>
<body>

	<h3>
		<a href="${pageContext.request.contextPath}/welcome">Back to
			DashBoard</a>
	</h3>

	<form:form action="${pageContext.request.contextPath}/emp/add-user"
		method="post" modelAttribute="user">
		<table>
			<tr>
				<th colspan="2">Add User Form</th>
			</tr>
			<tr>
				<td>First Name</td>
				<td><form:input path="firstName" type="text" /> <form:errors
						path="firstName" cssClass="error"></form:errors></td>
			</tr>
			<tr>
				<td>Last Name</td>
				<td><form:input path="lastName" type="text" /> <form:errors
						path="lastName" cssClass="error"></form:errors></td>
			</tr>
			<tr>
				<td>Email</td>
				<td><form:input path="email" type="text" /> <form:errors
						path="email" cssClass="error"></form:errors></td>
			</tr>
			<tr>
				<td>Mobile</td>
				<td><form:input path="mobile" type="text" /> <form:errors
						path="mobile" cssClass="error"></form:errors></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><form:input path="password" type="text" /> <form:errors
						path="password" cssClass="error"></form:errors></td>
			</tr>
			<tr>
				<td>Confirm Password</td>
				<td><form:input path="confirmPass" type="text" /> <form:errors
						path="confirmPass" cssClass="error"></form:errors></td>
			</tr>
			<tr>
				<td colspan="2">${addUserMsg}<br /> <input type="submit"
					value="Add User"></td>
			</tr>
		</table>
	</form:form>


</body>
</html>