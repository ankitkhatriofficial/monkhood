<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Tenant Info Form</title>
<link rel="preconnect" href="https://fonts.gstatic.com" />
<link
	href="https://fonts.googleapis.com/css2?family=KoHo:ital,wght@1,600&display=swap"
	rel="stylesheet" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/tenant.css" />
</head>

<body>

	<h3>
		<a href="${pageContext.request.contextPath}/welcome">Back to
			DashBoard</a>
	</h3>

	<div class="top">
		<div class="common">
			<h2>Tenant Info:</h2>
			<p>Kindly fill this form as accurate as possible, so that we can
				serve you better.</p>
			<p class="red">* Required</p>
		</div>
	</div>
	<form:form modelAttribute="tenantInfo" method="post"
		action="${pageContext.request.contextPath}/u/tenant-form">
		<div class="whole">
			<div class="container">
				<div class="common">
					Full Name:
					<p></p>
					<form:input path="fullName" class="input" type="text"
						placeholder="Name" />
					<form:errors path="fullName" cssClass="error"></form:errors>
					<span class="border"></span>
				</div>
			</div>
			<div class="container">
				<div class="common">
					Contact Number: <span class="red">*</span>
					<p></p>
					<form:input path="mobile" type="text" class="input"
						title="Enter the 10 digit number" pattern="[1-9]{1}[0-9]{9}" />
					<form:errors path="mobile" cssClass="error"></form:errors>
					<span class="border"></span>
				</div>
			</div>
			<div class="container">
				<div class="common">
					Alternate Number:
					<p></p>
					<form:input path="alternateMobile" class="input" type="Number"
						placeholder="Alternate Number" />
					<form:errors path="alternateMobile" cssClass="error"></form:errors>
					<span class="border"></span>
				</div>
			</div>
			<div class="container">
				<div class="common">
					<label for="Gender">Gender:</label>
					<form:select name="Gender" id="Gender" path="gender">
						<form:options items="${tenantInfo.genderOptions}" />
					</form:select>
					<form:errors path="gender" cssClass="error"></form:errors>
				</div>
			</div>
			<div class="container">
				<div class="common">
					No. of member:
					<p></p>
					<form:input path="noOfMembers" class="input" type="Number"
						placeholder="Your answer" />
					<form:errors path="noOfMembers" cssClass="error"></form:errors>
					<span class="border"></span>
				</div>
			</div>
			<div class="container">
				<div class="common">
					<label for="with">Want to Live:</label>
					<p></p>
					<form:select path="wantToLive" name="With" id="with">
						<form:options items="${tenantInfo.wantToLiveOptions}" />
					</form:select>
					<form:errors path="wantToLive" cssClass="error"></form:errors>
				</div>
			</div>
			<div class="container">
				<div class="common">
					<label for="entry">Entry allowed:(Be specific)</label>
					<p></p>
					<form:select path="entryAllowed" name="With" id="entry">
						<form:options items="${tenantInfo.entryAllowedOptions}" />
					</form:select>
					<form:errors path="entryAllowed" cssClass="error"></form:errors>
				</div>
			</div>
			<div class="container">
				<div class="common">
					College / Institute:
					<p></p>
					<form:input path="college" class="input" type="text"
						placeholder="Your answer" />
					<form:errors path="college" cssClass="error"></form:errors>
					<span class="border"></span>
				</div>
			</div>
			<div class="container">
				<div class="common">
					All possible desired localities:
					<p></p>
					<form:input path="desiredLocation" class="input" type="text"
						placeholder="Your answer" />
					<form:errors path="desiredLocation" cssClass="error"></form:errors>
					<span class="border"></span>
				</div>
			</div>
			<div class="container">
				<div class="common round">
					Type:
					<p></p>
					<form:checkboxes items="${tenantInfo.propertyTypeOptions}"
						path="propertyType" />
					<form:errors path="propertyType" cssClass="error"></form:errors>
				</div>
			</div>
			<div class="container">
				<div class="common">
					Budget: (Kindly specify your minimum as well as maximum budget)
					<p></p>
					<form:input path="budget" class="input" type="text"
						placeholder="Your answer" />
					<form:errors path="budget" cssClass="error"></form:errors>
					<span class="border"></span>
				</div>
			</div>
			<div class="container">
				<div class="common">
					Furnishing requirements:
					<p></p>
					<form:input path="furnishingReq" class="input" type="text"
						placeholder="Your answer" />
					<form:errors path="furnishingReq" cssClass="error"></form:errors>
					<span class="border"></span>
				</div>
			</div>
			<div class="container">
				<div class="common">
					Any special request:
					<p></p>
					<form:input path="specialReq" class="input" type="text"
						placeholder="Your answer" />
					<form:errors path="specialReq" cssClass="error"></form:errors>
					<span class="border"></span>
				</div>
			</div>
			<div class="container">
				<div class="common">
					Reference:
					<p></p>
					<form:input path="refernce" class="input" type="text"
						placeholder="Your answer" />
					<form:errors path="refernce" cssClass="error"></form:errors>
					<span class="border"></span>
				</div>
			</div>
		</div>
		<div>${tenantMsg}</div>
		<button type="submit" class="button top">Submit</button>
	</form:form>
</body>

</html>