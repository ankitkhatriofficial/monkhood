<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Edit user Page</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admins/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/footer.css">
</head>

<body>
	<%-- recognizing & Setting the user Role  --%>
	<c:choose>
		<c:when test="${user.roles eq '[ROLE_USER]'}">
			<c:set var="userS" scope="request" value="selected" />
		</c:when>
		<c:when test="${user.roles eq '[ROLE_EMP]'}">
			<c:set var="empS" scope="request" value="selected" />
		</c:when>
		<c:when test="${user.roles eq '[ROLE_ADMIN]'}">
			<c:set var="adminS" scope="request" value="selected" />
		</c:when>
	</c:choose>

	<div class="container">
		<div class="upper-container">
			<ul>
				<li><a href="${pageContext.request.contextPath}/welcome">Verified
						Users</a></li>
				<li><a
					href="${pageContext.request.contextPath}/admin/unverified-users">
						Pending Users</a></li>
				<li><a href="${pageContext.request.contextPath}/admin/add-user">Add
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
			<form:form
				action="${pageContext.request.contextPath}/admin/modify-user/"
				method="post" modelAttribute="user">
				<table>
					<tr>
						<th colspan="2">Edit User Details</th>
					</tr>
					<tr>
						<td>User id</td>
						<td><input type="text" value="${user.userId}" disabled /></td>
						<form:hidden path="userId" />
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
						<td>User Status:</td>
						<td>Active: <form:radiobutton path="status" value="true" />
							&nbsp; &nbsp;Non-Active: <form:radiobutton path="status"
								value="false" /> <form:errors path="status" cssClass="error"></form:errors>
						</td>
					</tr>
					<tr>
						<td>Role</td>
						<td><select name="role">
								<option value="1" ${userS}>USER</option>
								<option value="2" ${empS}>EMPLOYEE</option>
								<option value="3" ${adminS}>ADMIN</option>
						</select></td>
					</tr>
					<tr>
						<td colspan="2">${editUserMsg}<br /> <input type="submit"
							value="Modify User Details"></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
	<%-- Removing the user Role --%>
	<c:remove var="userS" />
	<c:remove var="empS" />
	<c:remove var="adminS" />

	<div class="footer">
		<p>copyright @ 2020-09 (Ankit Khatri)</p>
	</div>
</body>

</html>