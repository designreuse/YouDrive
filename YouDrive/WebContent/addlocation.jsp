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
<title>Add Rental Location</title>
</head>
<body>
	<h3>Add Rental Location</h3>
	<ol class="nav">
		<li><a href="addvehicle.jsp">Add Vehicle</a></li>
		<li><a href="addvehicletype.jsp">Add Vehicle Type</a></li>
		<li><a href="addlocation.jsp">Add Location</a></li>
		<li><a href="addmembership.jsp">Add Membership</a></li>
		<li><a href="adduser.jsp">Add Admin User</a></li>
		<li><a href="managevehicles.jsp">Manage Vehicles</a></li>
		<li><a href="managevehicletypes.jsp">Manage Vehicle Types</a></li>
		<li><a href="managelocations.jsp">Manage Locations</a></li>
		<li><a href="managememberships.jsp">Manage Memberships</a></li>
		<li><a href="manageusers.jsp">Manage Users</a></li>
	</ol>
	<div class="body">
		<p class="error">
			<c:out value="${errorMessage }" />
		</p>
		<form id="addLocation" name="addLocation" action="LocationManagement"
			method="post">
			<label for="locationName">Location Name:</label> <input
				id="locationName" name="locationName" type="text" /><br /> <label
				for="locationAddress">Address:</label> <input id="locationAddress"
				name="locationAddress" type="text" /><br /> <label for="capacity">Capacity:</label>
			<input id="capacity" name="capacity" type="text" /><br /> <input
				type="hidden" id="action" name="action" value="addLocation" /> <input
				type="submit" value="Submit" /> <input type="reset" value="Reset" />
		</form>
	</div>
</body>
</html>