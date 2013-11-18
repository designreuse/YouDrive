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
<title>Edit User Details</title>
</head>
<body>
	<h3>Edit User Details</h3>
	<ol class="nav">
		<li><a href="addvehicle.jsp">Add Vehicle</a></li>
		<li><a href="addvehicletype.jsp">Add Vehicle Type</a></li>
		<li><a href="addlocation.jsp">Add Location</a></li>
		<li><a href="managevehicles.jsp">Manage Vehicles</a></li>
		<li><a href="managevehicletypes.jsp">Manage Vehicle Types</a></li>
		<li><a href="managelocations.jsp">Manage Locations</a></li>
		<li><a href="manageusers.jsp">Manage Users</a></li>
	</ol>
	<div class="body">
		<p class="error">
			<c:out value="${errorMessage }" />
		</p>
		<c:choose>
			<c:when test="${user != null }">
				<form id="editUser" name="editUser" action="UserManagement"
					method="post">
					<label for="firstName">First Name:</label> 
					<input id="firstName" name="firstName" value="${ user.firstName }"/><br />
					<label for="lastName">Last Name:</label> 
					<input id="lastName" name="lastName" value="${ user.lastName }" /><br /> 
					<label for="email">Email Address:</label> 
					<input id="email" name="email" value="${ user.email }"/><br />
					<label for="username">Username:</label> 
					<input id="username" name="username" value="${ user.username }"/><br /> 
					<label for="password">Password:</label>
					<input id="password" name="password" value="${ user.password }"/><br />
					<!-- Don't display extra stuff if user is admin -->
					<c:if test="${ user.isAdmin() == null}">
						<label for="address">Address:</label>
						<input id="address" name="address" value="${ user.address }"/><br />
						<div class="license">
							<label for="license">License:</label>
							<input id="license" name="license" value="${ user.license }"/><br />
							<label for="state">State:</label>
							<input id="state" name="state" value="${ user.state }"/><br />
						</div>
						<div class="payment">
							<label for="ccType">Credit Card Type:</label>
							<input id="ccType" name="ccType" value="${ user.ccType }"/><br />
							<label for="ccNumber">Credit Card Number:</label>
							<input id="ccNumber" name="ccNumber" value="${ user.ccNumber }"/><br />
							<label for="ccExpirationDate">Expiration Date:</label>
							<input id="ccExpirationDate" name="ccExpirationDate" value="${ user.ccExpirationDate }"/><br />
							<label for="ccSecurityCode">Verification Code:</label>
							<input id="ccSecurityCode" name="ccSecurityCode" value="${ user.ccSecurityCode }"/><br />
						</div>
					</c:if>
					<input type="hidden" id="action" name="action" value="editUser"/>
					<input type="submit" value="Update"/>
					<input type="button" onclick="window.location.replace('manageusers.jsp')" value="Cancel"/>
				</form>
			</c:when>
			<c:otherwise>
				User not found.
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>