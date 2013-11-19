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
<title>View Vehicle Comments</title>
</head>
<body>
	<h3>View Vehicle Comments</h3>

	<c:if test="${loggedInUser != null }">
		<p class="userInfo">Hello, <c:out value="${loggedInUser.firstName }" /></p>
	</c:if>
	<c:if test="${ loggedInUser != null && loggedInUser.isAdmin()}">
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
	</c:if>
	<div class="body">	
		<p class="error">
			<c:out value="${errorMessage }" />
		</p>
	</div>
</body>
</html>