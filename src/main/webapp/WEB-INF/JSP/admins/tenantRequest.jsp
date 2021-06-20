<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admins/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/footer.css">
<title>Tenant Request</title>
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
				<li><a
					href="${pageContext.request.contextPath}/admin/verified-properties">Verified
						Properties</a></li>
				<li><a
					href="${pageContext.request.contextPath}/admin/add-property">Add
						Property</a></li>
				<li class="active"><a
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
			<table style="margin-left: 100px">
				<tr>
					<th>Tenant Name</th>
					<th>Mobile</th>
					<th>Alternate Mobile</th>
					<th>Gender</th>
					<th>No of Members</th>
					<th>Want To Live</th>
					<th>Entry Allowed</th>
					<th>College</th>
					<th>Desired Location</th>
					<th>Property Type</th>
					<th>Maximum Budget</th>
					<th>Furnishing Requirement</th>
					<th>Special Request</th>
					<th>Reference</th>
					<th>Requested By</th>
					<th>Booking Amount</th>
					<th>Documents</th>
					<th>Police Verf</th>
					<th>Action</th>
				</tr>
				<c:forEach var="req" items="${tenantReq}">
					<tr>
						<td>${req.fullName}</td>
						<td>${req.mobile}</td>
						<td>${req.alternateMobile}</td>

						<td><c:choose>
								<c:when test="${fn:containsIgnoreCase(req.gender, 'M')}">
								MALE
							</c:when>
								<c:when test="${fn:containsIgnoreCase(req.gender, 'F')}">
								FEMALE
							</c:when>
								<c:otherwise>
								Other
							</c:otherwise>
							</c:choose></td>

						<td>${req.noOfMembers}</td>
						<td style="word-wrap: break-word;"><c:choose>
								<c:when test="${fn:containsIgnoreCase(req.wantToLive, 'AL')}">
								ALONE
							</c:when>
								<c:when test="${fn:containsIgnoreCase(req.wantToLive, 'FR')}">
								With Friends
							</c:when>
								<c:when test="${fn:containsIgnoreCase(req.wantToLive, 'OPP')}">
								With Boyfriend / Girlfriend
							</c:when>
								<c:when test="${fn:containsIgnoreCase(req.wantToLive, 'FAM')}">
								with Family
							</c:when>
							</c:choose></td>

						<td><c:choose>
								<c:when
									test="${fn:containsIgnoreCase(req.entryAllowed, 'SAME')}">
									Only of Same Gender
								</c:when>
								<c:when
									test="${fn:containsIgnoreCase(req.entryAllowed, 'OPPLF')}">
									Along with opposite gender- Less frequently
								</c:when>
								<c:when
									test="${fn:containsIgnoreCase(req.entryAllowed, 'OPPMF')}">
									Along with opposite gender- More frequently
								</c:when>
							</c:choose></td>
						<td>${req.college}</td>
						<td>${req.desiredLocation}</td>
						<td>${req.propertyType eq "[]" ? 'ANY' : req.propertyType}</td>
						<td>${req.budget}</td>
						<td>${req.furnishingReq}</td>
						<td>${req.specialReq}</td>
						<td>${req.refernce}</td>
						<td>${req.requestedBy.firstName} ${req.requestedBy.lastName}</td>
						<td>${empty req.bookingFees ? 'Not Set' : req.bookingFees}
							${req.bookingFeesStatus eq false ? '(Unpaid)' : '(Paid)'}</td>
						<td>link to documents</td>
						<td>${req.policeVerification eq false ? 'No' : 'yes'}</td>
						<td><a
							href="${pageContext.request.contextPath}/admin/modify-tenant-request/${req.id}">Edit</a>
							<a
							href="${pageContext.request.contextPath}/admin/remove-tenant-request/${req.id}"
							onclick="return confirm('Are you sure to delete the Tenant Request?')">Delete</a>
							<a
							href="${pageContext.request.contextPath}/admin/tenant-service-approved/${req.id}"
							onclick="return confirm('Are you sure the tenant got the service?')">Approve</a></td>
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