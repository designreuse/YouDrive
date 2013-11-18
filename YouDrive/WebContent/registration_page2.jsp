<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/homepage.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<title>Complete Registration</title>
</head>
<body>
	<h3>Complete Registration</h3>
	<div class="signup">
		<p class="error">
			<c:out value="${errorMessage }" />
		</p>
		<c:choose>
			<c:when
				test="${registration_page1 != null && registration_page1.size() > 0 }">
				<form id="registerUser" name="registerUser" action="UserManagement" method="post">
					<label for="address">Address:</label>
					<input required type="text" id="address" name="address" /><br /> 
					<label for="license">Driver's License:</label>
					<input required type="text" id="license" name="license" /><br />
					<label for="state">Issuing State:</label> 
					<select id="state" name="state">
						<option value="AL">AL</option>
						<option value="AK">AK</option>
						<option value="AZ">AZ</option>
						<option value="AR">AR</option>
						<option value="CA">CA</option>
						<option value="CO">CO</option>
						<option value="CT">CT</option>
						<option value="DE">DE</option>
						<option value="DC">DC</option>
						<option value="FL">FL</option>
						<option value="GA">GA</option>
						<option value="HI">HI</option>
						<option value="ID">ID</option>
						<option value="IL">IL</option>
						<option value="IN">IN</option>
						<option value="IA">IA</option>
						<option value="KS">KS</option>
						<option value="KY">KY</option>
						<option value="LA">LA</option>
						<option value="ME">ME</option>
						<option value="MD">MD</option>
						<option value="MA">MA</option>
						<option value="MI">MI</option>
						<option value="MN">MN</option>
						<option value="MS">MS</option>
						<option value="MO">MO</option>
						<option value="MT">MT</option>
						<option value="NE">NE</option>
						<option value="NV">NV</option>
						<option value="NH">NH</option>
						<option value="NJ">NJ</option>
						<option value="NM">NM</option>
						<option value="NY">NY</option>
						<option value="NC">NC</option>
						<option value="ND">ND</option>
						<option value="OH">OH</option>
						<option value="OK">OK</option>
						<option value="OR">OR</option>
						<option value="PA">PA</option>
						<option value="RI">RI</option>
						<option value="SC">SC</option>
						<option value="SD">SD</option>
						<option value="TN">TN</option>
						<option value="TX">TX</option>
						<option value="UT">UT</option>
						<option value="VT">VT</option>
						<option value="VA">VA</option>
						<option value="WA">WA</option>
						<option value="WV">WV</option>
						<option value="WI">WI</option>
						<option value="WY">WY</option>
					</select> <br/>
					<label for="ccType">Credit Card Type:</label> 
					<select	name="ccType">
						<option value="Mastercard">MasterCard</option>
						<option value="Visa">Visa</option>
						<option value="Discover">Discover Card</option>
						<option value="Amex">American Express</option>
					</select><br /> 
					<label for="ccNumber">Credit Card Number:</label> 
					<input required type="text" id="ccNumber" name="ccNumber" /><br /> 
					<label	for="ccSecurityCode">CVC/CVV:</label> 
					<input required type="text"	id="ccSecurityCode" name="ccSecurityCode" /><br /> 
					<label	for="ccExpiration">Card Expires:</label> 
					<input required	type="text" id="ccExpiration" name="ccExpiration"
						placeholder="Enter as: MM/YY" /><br /> 
					<input type="hidden" id="action" name="action" value="registerUser2" /> 
					<input	type="submit" value="Next" /> 
					<input type="reset" value="Reset" />
					<input type="hidden" id="firstName" name="firstName"
						value="<c:out value="${ registration_page1.get('firstName')}"/>" />
				</form>
			</c:when>
			<c:when test="${registration_page1 == null }">
				<p>Please start from the first registration page.
			</c:when>
		</c:choose>
	</div>
</body>
</html>