<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All tenant History</title>
<style>
table tr th, td {
	border: 2px solid black;
}
</style>
</head>
<body>

	<h3>
		<a href="${pageContxet.request.contextPath}/welcome">Back to
			DashBoard</a>
	</h3>

	<table>
		<tr>
			<th>Full Name</th>
			<th>Mobile</th>
			<th>Alternate Mobile</th>
			<th>Requested Date</th>
			<th>Gender</th>
			<th>No of Members</th>
			<th>Want to Live</th>
			<th>Entry Allowed</th>
			<th>College</th>
			<th>Desired Location</th>
			<th>Property Type</th>
			<th>Max Budget</th>
			<th>Furnishing Req</th>
			<th>Special Req</th>
			<th>Refernce</th>
		</tr>

		<c:forEach var="booking" items="${tenantBookings}">
			<tr>
				<td>${booking.fullName}</td>
				<td>${booking.mobile}</td>
				<td>${booking.alternateMobile}</td>
				<td>${booking.tenantReqDate}</td>
				<td><c:choose>
						<c:when test="${fn:containsIgnoreCase(booking.gender, 'M')}">
							MALE
						</c:when>
						<c:when test="${fn:containsIgnoreCase(booking.gender, 'F')}">
							FEMALE
						</c:when>
						<c:otherwise>
							Other
						</c:otherwise>
					</c:choose></td>
				<td>${booking.noOfMembers}</td>
				<td><c:choose>
						<c:when test="${fn:containsIgnoreCase(booking.wantToLive, 'AL')}">
							ALONE
						</c:when>
						<c:when test="${fn:containsIgnoreCase(booking.wantToLive, 'FR')}">
							With Friends
						</c:when>
						<c:when test="${fn:containsIgnoreCase(booking.wantToLive, 'OPP')}">
							With Boyfriend/Girlfriend
						</c:when>
						<c:when test="${fn:containsIgnoreCase(booking.wantToLive, 'FAM')}">
							with Family
						</c:when>
					</c:choose></td>
				<td><c:choose>
						<c:when
							test="${fn:containsIgnoreCase(booking.entryAllowed, 'SAME')}">
								Only of Same Gender
							</c:when>
						<c:when
							test="${fn:containsIgnoreCase(booking.entryAllowed, 'OPPLF')}">
								Along with opposite gender- Less frequently
							</c:when>
						<c:when
							test="${fn:containsIgnoreCase(booking.entryAllowed, 'OPPMF')}">
								Along with opposite gender- More frequently
							</c:when>
					</c:choose></td>
				<td>${booking.college}</td>
				<td>${booking.desiredLocation}</td>
				<td>${booking.propertyType}</td>
				<td>${booking.budget}</td>
				<td>${booking.furnishingReq}</td>
				<td>${booking.specialReq}</td>
				<td>${booking.refernce}</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>