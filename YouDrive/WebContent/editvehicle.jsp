<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="vehicleMgr" class="com.youdrive.helpers.VehicleDAO" scope="application" />
<jsp:useBean id="vehicleTypeMgr" class="com.youdrive.helpers.VehicleTypeDAO" scope="application" />
<jsp:useBean id="locationMgr" class="com.youdrive.helpers.LocationDAO" scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/homepage.css">
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript">
	$(function() {
		$("#lastServiced").datepicker();
	});
</script>
<title>Edit Vehicle Details</title>
</head>
<body>
<h3>Edit Vehicle Details</h3>
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
			<c:when test="${vehicle != null }">
				<form id="editVehicle" name="editVehicle" action="VehicleManagement"
					method="post">
					<label for="make">Make:</label>
					<input required type="text" id="make" name="make" value="${vehicle.make }" /><br/>
					<label for="model">Model:</label>
					<input required type="text" id="model" name="model" value="${vehicle.model }" /><br/>
					<label for="year">Year:</label>
					<input required type="text" id="year" name="year" value="${vehicle.year }" /><br/>
					<label for="tag">Tag:</label>
					<input required type="text" id="tag" name="tag" value="${vehicle.tag }" /><br/>
					<label for="mileage">Mileage:</label>
					<input required type="text" id="mileage" name="mileage" value="${vehicle.mileage }" /><br/>
					<label for="lastServiced">Last Serviced:</label>					
					<input required type="text" id="lastServiced" name="lastServiced" value="<fmt:formatDate type="date" value="${vehicle.lastServiced}" />" /><br/>
					<label for="vehicleType">Vehicle Type:</label>
					<select name="vehicleType">
						<option value="${vehicle.vehicleType }"><c:out value="${vehicleMgr.getVehicleType(vehicle.vehicleType)}"/></option>
						<c:forEach items="${vehicleTypeMgr.getAllVehicleTypes()}" var="vehicleType" varStatus="status">
							<c:if test="${vehicleType.id != vehicle.vehicleType }">
								<option value="${vehicleType.id }"><c:out value="${vehicleMgr.getVehicleLocation(vehicleType.id)}"/></option>
							</c:if>
						</c:forEach>
					</select><br/>
					<label for="assignedLocation">Location:</label>
					<select name="assignedLocation">
						<option value="${vehicle.assignedLocation }"><c:out value="${vehicleMgr.getVehicleLocation(vehicle.assignedLocation)}"/></option>
						<c:forEach items="${locationMgr.getAllLocations()}" var="location" varStatus="status">
							<c:if test="${location.id != vehicle.assignedLocation }">
								<option value="${location.id }"><c:out value="${vehicleMgr.getVehicleLocation(location.id)}"/></option>
							</c:if>
						</c:forEach>
					</select><br/>
					<input type="hidden" id="action" name="action" value="editVehicle"/>
					<input type="hidden" id="vehicleID" name="vehicleID" value="${vehicle.id }"/>
					<input type="submit" value="Update" /> <input type="button"
						onclick="window.location.replace('managevehicles.jsp')"
						value="Cancel" />
				</form>
			</c:when>
		</c:choose>
	</div>
</body>
</html>