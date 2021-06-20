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
<title>Change Password Page</title>

</head>

<body>
	<h3>
		<a href="${pageContext.request.contextPath}/welcome">Back to
			DashBoard</a>
	</h3>
	<div class="container">
		<form:form action="/change-password" method="post">
			<table>
				<tr>
					<th colspan="2" style="background-color: #ea6262; color: white;">Change
						Password</th>
				</tr>
				<tr>
					<td>Old Password:</td>
					<td><input type="text" name="oldpass" /></td>
				</tr>
				<tr>
					<td>New Password:</td>
					<td><input type="text" name="newpass" /></td>
				</tr>
				<tr>
					<td>Confirm Password:</td>
					<td><input type="text" name="confirmpass" /></td>
				</tr>
				<tr>
					<td colspan="2">
						<div>${changePassMsg}</div> <input type="submit"
						value="Change Password" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</body>

</html>