<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Redirecting</title>
</head>
<body>

	<h1 style="text-align: center;">${approveMsg}</h1>
	<h3>Redirecting to you Admin Panel..!</h3>

	<script>
		setTimeout(function() {
			window.location.href = '<c:url value = "/admin/tenant-requests"/>';
		}, 3000);
	</script>

</body>
</html>