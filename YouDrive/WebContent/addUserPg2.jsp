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
<title>Enter Payment Information</title>
</head>
<body>
	<div class="body">
		<p class="error">
			<c:out value="${errorMessage }" />
		</p>
		<form id="addUser" name="addUser" action="UserManagement"
			method="post">
			<label for="address">Address:</label> <input required type="text"
				id="address" name="address" /><br /> <label for="license">License:</label>
			<input required type="text" id="license" name="license" /><br /> <label
				for="state">State Issue:</label> <input required type="text"
				id="state" name="state" /><br /> <label for="ccType">Credit
				Card Type:</label> <select name="ccType">
				<option value="MasterCard">MasterCard</option>
				<option value="Visa">Visa</option>
				<option value="Discover">Discover Card</option>
				<option value="Amex">American Express</option>
			</select> <label for="ccNumber">Credit Card Number:</label> <input required
				type="text" id="username" name="username" /><br /> <label
				for="ccSecurityCode">CVC/CVV:</label> <input required type="text"
				id="ccSecurityCode" name="ccSecurityCode" /><br /> <label
				for="ccSecurityCode">CVC/CVV:</label> <input required type="text"
				id="ccSecurityCode" name="ccSecurityCode" /><br /> <label
				for="ccExpiration">Card Expires:</label> <input required type="text"
				id="ccExpiration" name="ccExpiration" placeholder="Enter as: MM/YY" /><br />
			<input type="hidden" id="action" name="action" value="addUser2" /> <input
				type="submit" value="Next" /> <input type="reset" value="Reset" />
		</form>
	</div>
</body>
</html>