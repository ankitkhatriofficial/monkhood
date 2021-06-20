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
<title>Room Requests</title>
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
				<li><a href="${pageContext.request.contextPath}/admin/add-user">Add
						User</a></li>
				<li><a
					href="${pageContext.request.contextPath}/admin/unverified-properties">
						Property Requests</a></li>
				<li class="active"><a
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
			<table>
				<tr>
					<th>Locality</th>
					<th>Address</th>
					<th>State</th>
					<th>Pincode</th>
					<th>Rent</th>
					<th>Property Type</th>
					<th>Tenant Type</th>
					<th>Tenant Gender</th>
					<th>Floor</th>
					<th>Free Entry</th>
					<th>Couple Allowed</th>
					<th>Security Money</th>
					<th>Amenties Provided</th>
					<th>Rating</th>
					<th>Description</th>
					<th>Owner Name</th>
					<th>Owner mobile</th>
					<th>Images</th>
					<th>Action</th>
				</tr>

				<c:forEach var="property" items="${properties}">
					<tr>
						<td>${property.locality}</td>
						<td>${property.address}</td>
						<td>${property.state}</td>
						<td>${property.pincode}</td>
						<td>${property.rent}</td>
						<td>${property.propertyType}</td>
						<td>${property.tenantType}</td>
						<td>${property.tenantGender eq 'F' ? 'Female' : property.tenantGender eq 'M' ? 'Male' : 'Other'}</td>
						<td>${property.floorNo}</td>
						<td>${property.freeEntry eq 'Y' ? 'Yes' : property.freeEntry eq 'M' ? 'Moderate' : 'No' }</td>
						<td>${property.coupledAllowed eq true ? 'Yes' : 'No'}</td>
						<td>${property.securityMonth}</td>
						<td>${property.amenitiesProvided}</td>
						<td>${property.rating}</td>
						<td>${property.description}</td>
						<td>${property.owner.firstName}${property.owner.lastName}</td>
						<td>${property.owner.mobile}</td>
						<td><a href="">View All Images</a></td>
						<td><a
							href="${pageContext.request.contextPath}/admin/remove-verified-properties/${property.id}"><button>Delete</button>
						</a> <a
							href="${pageContext.request.contextPath}/admin/modify-verified-property/${property.id}"><button>Modify
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