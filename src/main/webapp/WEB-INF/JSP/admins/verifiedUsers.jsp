<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin DashBoard</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admins/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/footer.css">
</head>

<body>
	<%
		int count = 0;
	%>
	<div class="container">
		<div class="upper-container">
			<ul>
				<li class="active"><a
					href="${pageContext.request.contextPath}/welcome">Verified
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
		<div class="bottom-container ">

			<table>
				<tr>
					<th>S.no</th>
					<th>User Id</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Mobile</th>
					<th>Role</th>
					<th>Edit Details</th>
				</tr>

				<c:forEach var="user" items="${users}">
					<c:if
						test="${user.email ne pageContext.request.userPrincipal.name}">
						<tr>
							<td><%=++count%></td>
							<td>${user.userId}</td>
							<td>${user.firstName}</td>
							<td>${user.lastName}</td>
							<td>${user.email}</td>
							<td>${user.mobile}</td>
							<td>${user.roles}</td>
							<td><c:if test="${user.email ne 'test@admin.com'}">
									<a
										href="${pageContext.request.contextPath}/admin/modify-user/${user.userId}"><button>Edit
											Details</button></a>
								</c:if></td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>

		<div class="footer">
			<p>copyright @ 2020-09 (Ankit Khatri)</p>
		</div>
</body>

</html>