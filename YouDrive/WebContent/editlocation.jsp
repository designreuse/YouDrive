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
<title>Edit Rental Location</title>
</head>
<body>
<h3>Edit Rental Location</h3>
	<ol class="nav">
		<li><a href="addvehicle.jsp">Add Vehicle</a></li>
		<li><a href="addvehicletype.jsp">Add Vehicle Type</a></li>
		<li><a href="addlocation.jsp">Add Location</a></li>
		<li><a href="adduser.jsp">Add Admin User</a></li>
		<li><a href="managevehicles.jsp">Manage Vehicles</a></li>
		<li><a href="managevehicletypes.jsp">Manage Vehicle Types</a></li>
		<li><a href="managelocations.jsp">Manage Locations</a></li>
		<li><a href="manageusers.jsp">Manage Users</a></li>
	</ol>
	<div class="body">
		<p class="error">
			<c:out value="${errorMessage }" />
		</p>
		<form method="post" action="LocationManagement" name="editLocation"
			id="editLocation">
			<c:if test="${location != null }">
				<label for="locationName">Location Name:</label>
				<input  required type="text"id="locationName" name="locationName"
					value="<c:out value="${location.name }"/>" />
				<br />
				<label for="locationAddress">Location Address:</label>
				<input  required type="text" id="locationAddress" name="locationAddress"
					value="<c:out value="${location.address }"/>" />
				<br />
				<label for="capacity">Location Capacity:</label>
				<input  required type="text" id="capacity" name="capacity"
					value="<c:out value="${location.capacity }"/>" />
				<br />
				<input type="hidden" name="action" id="action" value="editLocation" />
				<input type="hidden" name="locationID" id="locationID"
					value="<c:out value="${location.id}" />" />
				<input type="submit" value="Update" />
				<input type="button"
					onclick="window.location.replace('managelocations.jsp')"
					value="Cancel" />
			</c:if>
		</form>
	</div>
</body>
</html>