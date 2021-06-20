<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Submit Property Details</title>

<script type="text/javascript">
	function disableSecurityDropdown(val) {
		var eleToDisable = document.getElementById('security');
		var eleToEnable = document.getElementById("securityInput");
		if (val == 'Other') {
			eleToEnable.style.display = 'block';
			eleToEnable.disabled = false;
			eleToDisable.disabled = true;
		}
	}
</script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/ownersInfo.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admins/style.css">
</head>

<body>

	<form:form modelAttribute="property"
		action="${pageContext.request.contextPath}/admin/add-property"
		method="post" enctype="multipart/form-data">

		<div class="container">
			<div class="upper-container">
				<ul style="font-size: 14px;">
					<li><a href="${pageContext.request.contextPath}/welcome">Verified
							Users</a></li>
					<li><a
						href="${pageContext.request.contextPath}/admin/unverified-users">
							Pending Users</a></li>
					<li class="active"><a
						href="${pageContext.request.contextPath}/admin/add-user">Add
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
							<input type="submit" value="Logout"
								style="width: 130px; font-size: 16px;" />
						</form:form></li>
				</ul>
			</div>

			<div class="top">
				<div class="common">
					<h2>Carefully Submit the Property Details:</h2>
					<p class="red">* Required</p>
				</div>
			</div>

			<div class="bottom-container">
				<div class="whole">
					<div class="wrapper">
						<div class="label">Locality:</div>
						<form:select path="locality">
							<option value="">Choose</option>
							<option value="Mukharjee Nagar">Mukharjee Nagar</option>
							<option value="Indira vikas colony">Indira vikas colony</option>
							<option value="Bhai parmanand">Bhai parmanand</option>
							<option value="Nirankari colony">Nirankari colony</option>
							<option value="Dhaka village">Dhaka village</option>
							<option value="Outrame line">Outrame line</option>
							<option value="Indira vihar">Indira vihar</option>
							<option value="Nehru viha">Nehru vihar</option>
							<option value="Gandhi vihar">Gandhi vihar</option>
							<option value="Wazirabad">Wazirabad</option>
							<option value="Sant nagar">Sant nagar</option>
							<option value="GTB nagar">GTB nagar</option>
							<option value="Rohini Sector 22">Rohini Sector 22</option>
						</form:select>
						<form:errors path="locality" cssClass="error"></form:errors>
					</div>

					<div class="wrapper">
						<div class="label">Address:</div>
						<form:input path="address" class="inputText" type="text" />
						<form:errors path="address" cssClass="error"></form:errors>
					</div>

					<div class="wrapper">
						<div class="label ">State:</div>
						<form:input path="state" class="inputText" type="text" />
						<form:errors path="state" cssClass="error"></form:errors>
					</div>

					<div class="wrapper">
						<div class="label">Pincode:</div>
						<form:input path="pincode" class="inputText" type="text" />
						<form:errors path="pincode" cssClass="error"></form:errors>
					</div>

					<div class="wrapper">
						<div class="label">Rent:</div>
						<form:input path="rent" class="inputText" type="text" />
						<form:errors path="rent" cssClass="error"></form:errors>
					</div>

					<div class="wrapper">
						<div class="label">Property Type:</div>
						<form:select path="propertyType">
							<form:options items="${property.propertyTypeOptions}" />
						</form:select>
						<form:errors path="propertyType" cssClass="error"></form:errors>
					</div>

					<div class="wrapper">
						<div class="label">Tenant Type:</div>
						<div class="box">
							<form:checkboxes items="${property.tenantOptions}"
								path="tenantType" />
							<form:errors path="tenantType" cssClass="error"></form:errors>
						</div>
					</div>

					<div class="wrapper">
						<div class="label">Tenant Gender:</div>
						<form:select path="tenantGender">
							<form:options items="${property.tenantGenderOptions}" />
						</form:select>
						<form:errors path="tenantGender" cssClass="error"></form:errors>
					</div>

					<div class="wrapper">
						<div class="label">Amenities Provided:</div>
						<div class="box">
							<form:checkboxes items="${property.amenitiesOptions}"
								path="amenitiesProvided" />
							<form:errors path="amenitiesProvided" cssClass="error"></form:errors>
						</div>
					</div>

					<div class="wrapper">
						<div class="label">Floor Number:</div>
						<form:select path="floorNo" id="floor">
							<option value="Choose">Choose</option>
							<form:options items="${property.floorNoOptions}" />
						</form:select>
						<form:errors path="floorNo" cssClass="error"></form:errors>
					</div>

					<div class="wrapper">
						<div class="label">Security (Month count only):</div>
						<form:select path="securityMonth" id="security"
							onchange='disableSecurityDropdown(this.value)'>
							<option value="">Choose</option>
							<option value="0">0 Month</option>
							<option value="1">1 Month</option>
							<option value="2">2 Month</option>
							<option value="3">3 Month</option>
							<option value="4">4 Month</option>
							<option value="Other">Other</option>
						</form:select>
						<form:input type="number" path="securityMonth" id="securityInput"
							style="display: none;" disabled="true" />
						<form:errors path="securityMonth" cssClass="error"></form:errors>
					</div>

					<div class="wrapper">
						<div class="label">House Condition (rating):</div>
						<form:select path="rating">
							<option value="">Choose Rating</option>
							<form:options items="${property.ratingOptions}" />
						</form:select>
						<form:errors path="rating" cssClass="error"></form:errors>
					</div>

					<div class="wrapper">
						<div class="label">Free Entry:</div>
						<form:select path="freeEntry">
							<form:options items="${property.freeEntryOptions}" />
						</form:select>
						<form:errors path="freeEntry" cssClass="error"></form:errors>
					</div>

					<div class="wrapper">
						<div class="label">Couples:</div>
						<form:select path="coupledAllowed">
							<form:options items="${property.coupledAllowedOptions}" />
						</form:select>
						<form:errors path="coupledAllowed" cssClass="error"></form:errors>
					</div>

					<div class="wrapper">
						<div class="label">Description:</div>
						<form:input path="description" class="inputText" type="text" />
						<form:errors path="description" cssClass="error"></form:errors>
					</div>

					<div class="wrapper">
						<div class="label">Property Status:</div>
						<form:select path="availability">
							<form:options items="${property.availabilityOptions}" />
						</form:select>
						<form:errors path="availability" cssClass="error"></form:errors>
					</div>

					<div class="wrapper">
						<div class="label">Owner Id: (Optional)</div>
						<form:input path="ownerId" class="inputText" type="text"
							style="width: 400px;" />
						<form:errors path="ownerId" cssClass="error"></form:errors>
					</div>

					<div class="wrapper">
						<div class="label">Add Property Image:</div>
						<input type="file" name="images" multiple="multiple" />
					</div>

					<div class="propImages"
						style="display: block; left: 0; width: 150px;">
						<div>${propertyMsg}</div>
						<input type="submit" value="Add Property"
							style="padding: 10px; width: 150px;" />
					</div>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>
