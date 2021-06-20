<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add user Page</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admins/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/footer.css">
</head>

<body>

	<div class="container">

		<div class="upper-container">
			<ul>
				<li><a href="${pageContext.request.contextPath}/welcome">Verified
						Users</a></li>
				<li><a
					href="${pageContext.request.contextPath}/admin/unverified-users">
						Pending Users</a></li>
				<li class="active"><a
					href="${pageContext.request.contextPath}/admin/add-user">Add
						User</a></li>
				<li><a
					href="${pageContext.request.contextPath}/admin/unverified-properties">
						Property Requests</a></li>
				<li><a
					href="${pageContext.request.contextPath}/admin/verified-properties">Verified
						Properties</a></li>
				<li><a
					href="${pageContext.request.contextPath}/admin/add-property">Add
						Property</a></li>
				<li><a
					href="${pageContext.request.contextPath}/admin/tenant-requests">
						Tenant Requests</a></li>
				<li><a
					href="${pageContext.request.contextPath}/admin/tenant-history">
						Tenants Services</a></li>
				<li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
				<li><a
					href="${pageContext.request.contextPath}/change-password">Change
						Password</a></li>
				<li><form:form
						action="${pageContext.request.contextPath}/logout" method="post">
						<input type="submit" value="Logout" />
					</form:form></li>
			</ul>
		</div>

		<div class="bottom-container">
			<form:form action="${pageContext.request.contextPath}/admin/add-user"
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
						<td>Role</td>
						<td><select name="role">
								<option value="1">USER</option>
								<option value="2">EMPLOYEE</option>
								<option value="3">ADMIN</option>
						</select></td>
					</tr>
					<tr>
						<td colspan="2">${addUserMsg}<br /> <input type="submit"
							value="Add User"></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>

	<div class="footer">
		<p>copyright @ 2020-09 (Ankit Khatri)</p>
	</div>
</body>

</html>