<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/profile/style.css" />

<title>Profile Page</title>

</head>

<body>

	<h3>
		<a href="${pageContext.request.contextPath}/welcome">Back to
			DashBoard</a>
	</h3>

	<div class="container">
		<form:form action="${pageContext.request.contextPath}/profile"
			method="post" modelAttribute="user">
			<form:hidden path="userId" />
			<center>
				<table>
					<tr>
						<th colspan="2" style="background-color: #ea6262; color: white;">Edit
							Profile Details</th>
					</tr>
					<tr>
						<td>User Id:</td>
						<td><input type="text" value="${user.userId}" disabled /></td>
					</tr>
					<tr>
						<td>First Name</td>
						<td><form:input path="firstName" type="text"
								value="${user.firstName}" /> <form:errors path="firstName"
								cssClass="error"></form:errors></td>
					</tr>
					<tr>
						<td>Last Name</td>
						<td><form:input path="lastName" type="text"
								value="${user.lastName}" /> <form:errors path="lastName"
								cssClass="error"></form:errors></td>
					</tr>
					<tr>
						<td>Email</td>
						<td><form:input path="email" type="text"
								value="${user.email}" /> <form:errors path="email"
								cssClass="error"></form:errors></td>
					</tr>
					<tr>
						<td>Mobile</td>
						<td><form:input path="mobile" type="text"
								value="${user.mobile}" /> <form:errors path="mobile"
								cssClass="error"></form:errors></td>
					</tr>
					<tr>
						<td colspan="2">${profileMsg}<br /> <input type="submit"
							value="Save Changes"></td>
					</tr>
				</table>
			</center>
		</form:form>
	</div>
</body>

</html>