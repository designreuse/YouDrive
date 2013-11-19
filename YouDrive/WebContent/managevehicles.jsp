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
	//Equivalent to $( document ).ready(function(){});
	$(function() {
		$('.navSort').click(function(){
			//Get href value
			//Set hidden input field
			//Submit form which reloads the page
			searchValue = $(this).attr('href').substring(1);
			document.getElementById("searchType").value = searchValue;
			$('#sortVehicleForm').submit();
		});
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
			<c:set var="allVehicles" value="${vehicleMgr.getAllVehicles() }" scope="session"/>
				<table border="1">
					<caption>Vehicles</caption>
					<tr>
						<th><a href="#0" class="navSort">Make</a></th>
						<th><a href="#1" class="navSort">Model</a></th>
						<th><a href="#2" class="navSort">Year</a></th>
						<th><a href="#3" class="navSort">Tag</a></th>
						<th><a href="#4" class="navSort">Mileage</a></th>
						<th><a href="#5" class="navSort">Last Serviced</a></th>
						<th>Is Available</th>
						<th><a href="#6" class="navSort">Vehicle Type</a></th>
						<th><a href="#7" class="navSort">Vehicle Location</a></th>
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
				<form id="sortVehicleForm" name="sortVehicleForm" method="get" action="VehicleManagement">
					<input type="hidden" id="action" name="action" value="sortVehicle"/>
					<input type="hidden" id="searchType" name="searchType" value="" />
				</form>
			</c:when>
			<c:otherwise>
				<p class="error">Please <a href="login.jsp">login</a> as an admin to access this page.</p>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>