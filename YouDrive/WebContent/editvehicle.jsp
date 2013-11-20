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
		
		<%-- Triggers form submission to retrieve comments
		$("#comments").click(function(){
			$("#vehicleCommentsForm").submit();
		});--%>
	});
	function setbg(color) {
		document.getElementById("comment").style.background = color
	}
</script>
<title>Edit Vehicle Details</title>
</head>
<body>
	<h3>Edit Vehicle Details</h3>
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
							<input required type="text" id="lastServiced" name="lastServiced" value="${vehicle.lastServiced}" /><br/>
							<label for="vehicleType">Vehicle Type:</label>
							<select name="vehicleType">
								<option value="${vehicle.vehicleType }"><c:out value="${vehicleMgr.getVehicleType(vehicle.vehicleType)}"/></option>
								<c:forEach items="${vehicleTypeMgr.getAllVehicleTypes()}" var="vehicleType" varStatus="status">
									<c:if test="${vehicleType.id != vehicle.vehicleType }">
										<option value="${vehicleType.id }"><c:out value="${vehicleMgr.getVehicleType(vehicleType.id)}"/></option>
									</c:if>
								</c:forEach>
							</select><br/>
							<label for="vehicleLocation">Location:</label>
							<select name="vehicleLocation">
								<option value="${vehicle.assignedLocation }"><c:out value="${vehicleMgr.getVehicleLocation(vehicle.assignedLocation)}"/></option>
								<c:forEach items="${locationMgr.getAllLocations()}" var="location" varStatus="status">
									<c:if test="${location.id != vehicle.assignedLocation }">
										<option value="${location.id }"><c:out value="${vehicleMgr.getVehicleLocation(location.id)}"/></option>
									</c:if>
								</c:forEach>
							</select><br/>
							<a href="viewcomments.jsp" id="comments">View Comments</a><br/>
							<label for="comment">Enter Comments:</label>
							<textarea name="comment" id="comment" onfocus="this.value=''; setbg('#e5fff3');" onblur="setbg('white')"></textarea><br/>	
							<input type="hidden" id="action" name="action" value="editVehicle"/>
							<input type="hidden" id="vehicleID" name="vehicleID" value="${vehicle.id }"/>
							<input type="submit" value="Update" /> <input type="button"
								onclick="window.location.replace('managevehicles.jsp')"
								value="Cancel" />
						</form>
						<%-- Submit this form when user clicks viewcomments.jsp link 
						<form method="get" action="VehicleManagement" id="vehicleCommentsForm" name="vehicleCommentsForm">
							<input type="hidden" id="action" name="action" value="viewComments" />
							<input type="hidden" id="vehicle_id" name="vehicle_id" value="${ vehicle.id}" />
						</form>--%>
					</c:when>
				</c:choose>
			</c:when>
			<c:otherwise>
				<p class="error">Please <a href="login.jsp">login</a> as an admin to access this page.</p>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>