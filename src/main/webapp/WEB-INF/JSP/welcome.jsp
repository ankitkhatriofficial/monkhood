<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome Page</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/index.css">
</head>

<body>
	<h1>
		Welcome
		<security:authentication property="principal.username" />
	</h1>
	<h2>
		<a href="${pageContext.request.contextPath}/property/req">Submit
			Property Details</a> <br /> <br />

		<security:authorize access="hasRole('ROLE_USER')">
			<a href="${pageContext.request.contextPath}/u/tenant-form">Find
				Your desired Room/flat/pg</a>
			<br />
			<br />
			<a href="${pageContext.request.contextPath}/u/booking-history">Show
				Booking History</a>
			<br />
			<br />
		</security:authorize>

		<security:authorize access="hasRole('ROLE_EMP')">
			<a href="${pageContext.request.contextPath}/emp/search-users">Search
				For User</a>
			<br />
			<br />
			<a href="${pageContext.request.contextPath}/emp/add-user">Add
				User</a>
			<br />
			<br />
			<a href="${pageContext.request.contextPath}/emp/search-properties">Search
				for Property</a>
			<br />
			<br />
		</security:authorize>

		<a href="${pageContext.request.contextPath}/profile">Profile</a> <br />
		<br /> <a href="${pageContext.request.contextPath}/change-password">Change
			Password</a> <br /> <br />

		<form:form action="/logout" method="post">
			<input type="submit" value="Logout" />
		</form:form>
	</h2>
</body>

</html>