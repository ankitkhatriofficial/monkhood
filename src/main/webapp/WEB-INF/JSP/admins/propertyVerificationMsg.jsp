<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Close popup window</title>
</head>
<body>

	<h1 style="text-align: center;">${verifyPropertyMsg}</h1>
	<h3>Redirecting to you Admin Panel..!</h3>

	<script>
		setTimeout(
				function() {
					window.location.href = '<c:url value = "/admin/unverified-properties"/>';
				}, 3000);
	</script>
</body>
</html>