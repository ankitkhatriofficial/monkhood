<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Searched Users</title>
<link rel="stylesheet"
	href="${pageCoontext.request.contextPath}/css/emp/style.css" />
</head>
<body>

	<h3>
		<a href="${pageContext.request.contextPath}/welcome">Back to
			DashBoard</a>
	</h3>

	<form:form action="${pageContext.request.contextPath}/emp/search-users"
		method="post">
		<select name="searchBy">
			<option value="userId">userId</option>
			<option value="email">Email</option>
			<option value="mobile">Mobile</option>
			<option value="name">Name</option>
		</select>
		<input type="text" name="userDetails" />
		<span style="color: red">${searchUserMsg}</span>
		<input type="submit" value="search" />
	</form:form>

	<table>
		<tr>
			<th>User Id</th>
			<th>Full Name</th>
			<th>Email</th>
			<th>Mobile</th>
			<th>Status</th>
		</tr>
		<c:forEach var="user" items="${users}">
			<tr>
				<td>${user.userId}</td>
				<td>${user.firstName}${user.lastName}</td>
				<td>${user.email}</td>
				<td>${user.mobile}</td>
				<td>${user.status eq false ? 'Non Active' : 'Active'}</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>