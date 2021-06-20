<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Property</title>
<link rel="stylesheet"
	href="${pageCoontext.request.contextPath}/css/emp/style.css" />
<body>

	<h3>
		<a href="${pageContext.request.contextPath}/welcome">Back to
			DashBoard</a>
	</h3>

	<form:form>
		<select name="searchBy">
			<option value="locality">Locality</option>
			<option value="pincode">Pincode</option>
			<option value="state">State</option>
			<option value="rating">Rating</option>
		</select>
		<input type="text" name="propertyDetails" />
		<span style="color: red">${searchPropertyMsg}</span>
		<input type="submit" value="Search" />
	</form:form>


	<table>
		<tr>
			<th>Room Id</th>
			<th>Locality</th>
			<th>Address</th>
			<th>State</th>
			<th>Pincode</th>
			<th>Rent</th>
			<th>Free Entry</th>
			<th>Couple Allowed</th>
			<th>Property Type</th>
			<th>Tenant Type</th>
			<th>Tenant Gender</th>
			<th>Security</th>
			<th>Amenities</th>
			<th>House Rating</th>
			<th>Floor</th>
			<th>Description</th>
			<th>Images</th>
		</tr>
		<c:forEach var="property" items="${properties}">
			<tr>
				<td>${property.id}</td>
				<td>${property.locality}</td>
				<td>${property.address}</td>
				<td>${property.state}</td>
				<td>${property.pincode}</td>
				<td>${property.rent}</td>
				<td>${property.freeEntry eq 'Y' ? 'Yes' : property.freeEntry eq 'M' ? 'Moderate' : 'No' }</td>
				<td>${property.coupledAllowed eq true ? 'Yes' : 'No'}</td>
				<td>${property.propertyType}</td>
				<td>${property.tenantType}</td>
				<td>${property.tenantGender eq 'F' ? 'Female' : property.tenantGender eq 'M' ? 'Male' : 'Other'}</td>
				<td>${property.securityMonth}</td>
				<td>${property.amenitiesProvided}</td>
				<td>${property.rating}</td>
				<td>${property.floorNo}</td>
				<td>${property.description}</td>
				<td>Click here</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>