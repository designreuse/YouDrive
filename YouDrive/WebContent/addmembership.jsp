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
<title>Add Membership</title>
</head>
<body>	
	<h3>Add Membership</h3>
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
		<form method="post" action="MembershipManagement" name="editMembership" id="editMembership">
			<label for="membershipLevel">Membership Level:</label>
			<input  required type="text"id="membershipLevel" name="membershipLevel"/><br />
			<label for="price">Price:</label>
			<input  required type="text" id="price" name="price" /><br />
			<label for="duration">Duration (months):</label>
			<input  required type="text" id="duration" name="duration" /><br />
			<input type="hidden" name="action" id="action" value="addMembership" />
			<input type="submit" value="Add" />
			<input type="button" onclick="window.location.replace('managememberships.jsp')" value="Cancel" />		
		</form>
	</div>
</body>
</html>