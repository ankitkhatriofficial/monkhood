<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Submit Property Details</title>

<script type="text/javascript">
	function disablefloorNoSelect(val) {
		var eleToDisable = document.getElementById('floor');
		var eleToEnable = document.getElementById("floorInput");
		if (val == 'Other') {
			eleToEnable.style.display = 'block';
			eleToEnable.disabled = false;
			eleToDisable.disabled = true;
		}
	}
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
</head>
<body>
	<h3>
		<a href="${pageContext.request.contextPath}/welcome">Back to
			DashBoard</a>
	</h3>
	<div class="top">
		<div class="common">
			<h2>Carefully Submit the Property Details:</h2>
			<p class="red">* Required</p>
		</div>
	</div>

	<form:form modelAttribute="property"
		action="${pageContext.request.contextPath}/property/req" method="post"
		enctype="multipart/form-data">

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
					<option value="M">Male</option>
					<option value="F">Female</option>
					<option value="O">Other</option>
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
				<form:select path="floorNo"
					onchange="disablefloorNoSelect(this.value)" id="floor">
					<option value="Choose">Choose</option>
					<option value="0">0</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="Other">Other</option>
				</form:select>
				<form:input type="number" path="floorNo" id="floorInput"
					style="display : none;" disabled="true" />
				<form:errors path="floorNo" cssClass="error"></form:errors>
			</div>

			<div class="wrapper">
				<div class="label">Security (Month count only):</div>
				<form:select path="securityMonth" id="security"
					onchange="disableSecurityDropdown(this.value)">
					<option value="">Choose</option>
					<option value="0">0 Month</option>
					<option value="1">1 Month</option>
					<option value="2">2 Month</option>
					<option value="3">3 Month</option>
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
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
				</form:select>
				<form:errors path="rating" cssClass="error"></form:errors>
			</div>

			<div class="wrapper">
				<div class="label">Free Entry:</div>
				<form:select path="freeEntry">
					<option value="N">No</option>
					<option value="Y">Yes</option>
					<option value="M">Moderate</option>
				</form:select>
				<form:errors path="freeEntry" cssClass="error"></form:errors>
			</div>

			<div class="wrapper">
				<div class="label">Couples:</div>
				<form:select path="coupledAllowed">
					<option value="false">No</option>
					<option value="true">Yes</option>
				</form:select>
				<form:errors path="coupledAllowed" cssClass="error"></form:errors>
			</div>

			<div class="wrapper">
				<div class="label">Description:</div>
				<form:input path="description" class="inputText" type="text" />
				<form:errors path="description" cssClass="error"></form:errors>
			</div>

			<security:authorize access="hasRole('ROLE_EMP')">
				<div class="wrapper">
					<div class="label">Owner Id: (Optional)</div>
					<form:input path="ownerId" class="inputText" type="text" />
					<form:errors path="ownerId" cssClass="error"></form:errors>
				</div>
			</security:authorize>
			<div class="wrapper">
				<div class="label">Add Property Image:</div>
				<input type="file" name="images" multiple="multiple" />
			</div>

			<div class="propImages" style="display: block;">
				<div>${propertyReqMsg}</div>
				<input type="submit" value="Add Property" />
			</div>
		</div>
	</form:form>
</body>
</html>
