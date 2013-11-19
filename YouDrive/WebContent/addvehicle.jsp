<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

	function setbg(color) {
		document.getElementById("comment").style.background = color
	}
</script>
<title>Add Vehicle</title>
</head>
<body>
	<h3>Add Vehicle</h3>
	<c:if test="${loggedInUser != null }">
		<p class="userInfo">Hello, <c:out value="${loggedInUser.firstName }" /></p>
	</c:if>
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
		<li><a href="logout.jsp">Logout</a></li>
	</ol>
	<div class="body">
		<p class="error">
			<c:out value="${errorMessage }" />
		</p>
		<c:choose>
			<c:when test="${loggedInUser != null && loggedInUser.isAdmin() }">
				<jsp:useBean id="locationDAO" class="com.youdrive.helpers.LocationDAO"	scope="application" />
				<jsp:useBean id="vehicleTypeDAO" class="com.youdrive.helpers.VehicleTypeDAO" scope="application" />
				<form id="addVehicle" name="addVehicle" action="VehicleManagement"	method="post">
					<label for="make">Make:</label> 
					<input required name="make" id="make"type="text" /><br /> 
					<label for="model">Model:</label> 
					<input required name="model" id="model" type="text" /><br /> 
					<label for="year">Year:</label>
					<input required name="year" id="year" type="text" /><br /> 
					<label for="tag">Vehicle Tag:</label> 
					<input required name="tag" id="tag" type="text" /><br /> 
					<label for="mileage">Vehicle Mileage:</label> 
					<input required name="mileage" id="mileage" type="text" /><br /> 
					<label for="lastServiced">Last Serviced Date:</label> 
					<input required name="lastServiced" id="lastServiced" type="text" /><br /> 
					<label for="vehicleType">Vehicle Type:</label> 
					<select required name="vehicleType">
						<c:forEach items="${vehicleTypeDAO.getAllVehicleTypes()}"
							var="vehicleType" varStatus="status">
							<option value="<c:out value="${ vehicleType.id }" />">
								<c:out value="${vehicleType.type }" />
							</option>
						</c:forEach>
					</select><br /> 
					<label for="assignedLocation">Vehicle Location:</label> 
					<select required name="vehicleLocation">
						<c:forEach items="${locationDAO.getAllLocations()}" var="location"
							varStatus="status">
							<option value="<c:out value="${ location.id }" />">
								<c:out value="${location.name }" />
							</option>
						</c:forEach>
					</select><br /> 
					<br /> 
					<input type="hidden" name="action" id="addVehicle" value="addVehicle" /> 
					<input type="submit" value="Submit" /> 
					<input type="reset" value="Reset" />
		
				</form>
			</c:when>
			<c:otherwise>
				<p class="error">Please <a href="login.jsp">login</a> as an admin to access this page.</p>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>