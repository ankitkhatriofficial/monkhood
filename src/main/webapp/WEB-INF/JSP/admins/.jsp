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
<title>Verified Rooms</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admins/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/footer">
</head>

<body>

	<div class="container">

		<div class="left-container">
			<ul>
				<li><a href="${pageContext.request.contextPath}/welcome">Show
						Users</a></li>
				<li><a
					href="${pageContext.request.contextPath}/admin/unverified-users">Show
						Pending Users</a></li>
				<li><a href="${pageContext.request.contextPath}/admin/add-user">Add
						user</a></li>
				<li><a
					href="${pageContext.request.contextPath}/admin/unverified-rooms">Show
						Room Requests</a></li>
				<li class="active"><a
					href="${pageContext.request.contextPath}/admin/verified-rooms">Show
						Rooms</a></li>
				<li><a href="${pageContext.request.contextPath}/admin/add-room">Add
						Room</a></li>
				<li><a
					href="${pageContext.request.contextPath}/admin/tenant-requests">Show
						Tenant Request</a></li>
				<li><a
					href="${pageContext.request.contextPath}/admin/tenant-history">Show
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

		<div class="right-container">
			<table>
				<tr>
					<th>RoomID</th>
					<th>Location</th>
					<th>State</th>
					<th>Pincode</th>
					<th>Price</th>
					<th>Room Type</th>
					<th>Mandt. Things</th>
					<th>Facilites</th>
					<th>Owner's Name</th>
					<th>Owner's Contact No</th>
					<th>Action</th>
				</tr>

				<c:forEach var="room" items="${rooms}">
					<tr>
						<td>${room.roomId}</td>
						<td>${room.address}</td>
						<td>${room.state}</td>
						<td>${room.pincode}</td>
						<td>${room.price}</td>
						<td>${room.roomType}</td>
						<td>${room.mandatory}</td>
						<td>${room.facilities}</td>
						<td>${room.owner.firstName}</td>
						<td>${room.owner.mobile}</td>
						<td><a
							href="${pageContext.request.contextPath}/admin/modify-verified-room/${room.roomId}"><button>Modify
									Details</button></a></td>
					</tr>
				</c:forEach>

			</table>
		</div>
	</div>
	<div class="footer">
		<p>copyright @ 2020-09 (Ankit Khatri)</p>
	</div>
</body>

</html>