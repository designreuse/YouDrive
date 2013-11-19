<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="locationMgr" class="com.youdrive.helpers.LocationDAO"
	scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/homepage.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<title>Manage Locations</title>
</head>
<body>
	<h3>Manage Locations</h3>
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
		<table border="1">
			<caption>Locations</caption>
			<tr>
				<th class="hidden">ID</th>
				<th>Location Name</th>
				<th>Location Address</th>
				<th>Location Capacity</th>
				<th>Edit</th>
			</tr>
			<c:forEach items="${locationMgr.getAllLocations()}" var="location"
				varStatus="status">
				<tr>
					<td class="hidden"><c:out value="${ location.id }" /></td>
					<td><c:out value="${ location.name }" /></td>
					<td><c:out value="${ location.address}" /></td>
					<td><c:out value="${ location.capacity}" /></td>
					<c:url value="LocationManagement" var="url">
						<c:param name="locationID" value="${location.id}" />
					</c:url>
					<td><a href="<c:out value="${url }" />">Edit</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>