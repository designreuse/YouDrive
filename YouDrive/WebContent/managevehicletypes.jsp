<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="vehicleTypeMgr"
	class="com.youdrive.helpers.VehicleTypeDAO" scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/homepage.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<title>Manage Vehicle Types</title>
</head>
<body>
	<h3>Manage Vehicle Types</h3>
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
				<table border="1">		
					<caption>Vehicle Types</caption>
					<tr>
						<th>Vehicle Type</th>
						<th>Hourly Price</th>
						<th>Daily Price</th>
						<th>Edit</th>
					</tr>
					<c:forEach items="${vehicleTypeMgr.getAllVehicleTypes()}"
						var="vehicleType" varStatus="status">
						<tr>
							<td><c:out value="${ vehicleType.type }" /></td>
							<td><fmt:formatNumber value="${ vehicleType.hourlyPrice}" type="currency" /></td>
							<td><fmt:formatNumber value="${ vehicleType.dailyPrice}" type="currency" /></td>
							<c:url value="VehicleTypeManagement" var="url">
								<c:param name="vehicleTypeID" value="${vehicleType.id}" />
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