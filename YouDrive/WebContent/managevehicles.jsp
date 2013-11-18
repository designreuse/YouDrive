<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="vehicleMgr" class="com.youdrive.helpers.VehicleDAO"
	scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/homepage.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<title>Manage Vehicles</title>
</head>
<body>
	<h3>Manage Vehicles</h3>
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
		<table border="1">

			<caption>Vehicles</caption>
			<tr>
				<th>Make</th>
				<th>Model</th>
				<th>Year</th>
				<th>Tag</th>
				<th>Mileage</th>
				<th>Last Serviced</th>
				<th>Is Available</th>
				<th>Vehicle Type</th>
				<th>Vehicle Location</th>
				<th>Edit</th>
			</tr>
			<c:forEach items="${vehicleMgr.getAllVehicles()}" var="vehicle"
				varStatus="status">
				<tr>
					<td><c:out value="${ vehicle.make }" /></td>
					<td><c:out value="${ vehicle.model }" /></td>
					<td><c:out value="${ vehicle.year }" /></td>
					<td><c:out value="${ vehicle.tag }" /></td>
					<td><c:out value="${ vehicle.mileage }" /></td>
					<td><fmt:formatDate type="date" value="${vehicle.lastServiced}" /></td>
					<td><c:out value="${ vehicle.isAvailable() }" /></td>
					<td><c:out
							value="${ vehicleMgr.getVehicleType(vehicle.vehicleType) }" /></td>
					<td><c:out
							value="${ vehicleMgr.getVehicleLocation(vehicle.assignedLocation)}" /></td>
					<c:url value="VehicleManagement" var="url">
						<c:param name="vehicleID" value="${vehicle.id}" />
					</c:url>
					<td><a href="<c:out value="${url }" />">Edit</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>