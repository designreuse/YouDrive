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
<title>Edit Vehicle Type</title>
</head>
<body>
	<h3>Edit Vehicle Type</h3>
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
		<form method="post" action="VehicleTypeManagement"
			name="editVehicleType" id="editVehicleType">
			<c:if test="${vehicleType != null }">
				<label for="vehicleTypeName">Vehicle Type:</label>
				<input required type="text" id="vehicleTypeName" name="vehicleTypeName"
					value="<c:out value="${vehicleType.type }"/>" />
				<br />
				<label for="hourlyPrice">Hourly Price:</label>
				<input required type="text" id="hourlyPrice" name="hourlyPrice"
					value="${vehicleType.hourlyPrice}" />
				<br />
				<label for="dailyPrice">Daily Price:</label>
				<input required type="text" id="dailyPrice" name="dailyPrice"
					value="${vehicleType.dailyPrice}" />
				<br />
				<input type="hidden" name="action" id="action"
					value="editVehicleType" />
				<input type="hidden" name="vehicleTypeID" id="vehicleTypeID"
					value="<c:out value="${vehicleType.id}" />" />
				<input type="submit" value="Update" />
				<input type="button"
					onclick="window.location.replace('managevehicletypes.jsp')"
					value="Cancel" />
			</c:if>
		</form>
	</div>
</body>
</html>