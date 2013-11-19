<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="a" uri="/sortVehicle" %>
<c:choose>
	<c:when test="${searchType == null}">
		<c:set var="searchType" value="0"/>
	</c:when>
	<c:otherwise>
		<c:set var="searchType" value="${searchType }"/>
	</c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/homepage.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
	var searchType = 0;
	function changeID(id){
		searchType = id;
		alert(searchType);
	}
	
	$(function() {
		$("#lastServiced").datepicker();
	});
</script>
<title>Manage Vehicles</title>
</head>
<body>
	<h3>Manage Vehicles</h3>
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
			<jsp:useBean id="vehicleMgr" class="com.youdrive.helpers.VehicleDAO" scope="session" />
			<c:set var="allVehicles" value="${vehicleMgr.getAllVehicles() }"/>
				<table border="1">
					<caption>Vehicles</caption>
					<tr>
						<th><a href="#0" onclick="changeID('0')">Make</a></th>
						<th><a href="#1" onclick="changeID('1')">Model</a></th>
						<th><a href="#2" onclick="changeID('2')">Year</a></th>
						<th><a href="#3" onclick="changeID('3')">Tag</a></th>
						<th><a href="#4" onclick="changeID('4')">Mileage</a></th>
						<th><a href="#5" onclick="changeID('5')">Last Serviced</a></th>
						<th>Is Available</th>
						<th><a href="#6" onclick="changeID('6')">Vehicle Type</a></th>
						<th><a href="#7" onclick="changeID('7')">Vehicle Location</a></th>
						<th>Edit</th>
					</tr>
					<c:forEach items="${a:masterSort(allVehicles,searchType)}" var="vehicle" varStatus="status">
						<tr>
							<td><c:out value="${ vehicle.make }" /></td>
							<td><c:out value="${ vehicle.model }" /></td>
							<td><c:out value="${ vehicle.year }" /></td>
							<td><c:out value="${ vehicle.tag }" /></td>
							<td><c:out value="${ vehicle.mileage }" /></td>
							<td><fmt:formatDate type="date" value="${vehicle.lastServiced}" /></td>
							<td><c:out value="${ vehicle.isAvailable() }" /></td>
							<td><c:out value="${ vehicleMgr.getVehicleType(vehicle.vehicleType) }" /></td>
							<td><c:out value="${ vehicleMgr.getVehicleLocation(vehicle.assignedLocation)}" /></td>
							<c:url value="VehicleManagement" var="url">
								<c:param name="vehicleID" value="${vehicle.id}" />
							</c:url>
							<td><a href="<c:out value="${url }" />">Edit</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<p class="error">Please <a href="login.jsp">login</a> as an admin to access this page.</p>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>