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
<title>Create a Customer/Admin</title>
</head>
<body>
	<h3>Admin - Add User</h3>
	<ol class="nav">
		<li><a href="addvehicle.jsp">Add Vehicle</a></li>
		<li><a href="addvehicletype.jsp">Add Vehicle Type</a></li>
		<li><a href="addlocation.jsp">Add Location</a></li>
		<li><a href="adduser.jsp">Add Admin User</a></li>
		<li><a href="managevehicles.jsp">Manage Vehicles</a></li>
		<li><a href="managevehicletypes.jsp">Manage Vehicle Types</a></li>
		<li><a href="managelocations.jsp">Manage Locations</a></li>
		<li><a href="manageusers.jsp">Manage Users</a></li>
	</ol>
	<div class="body">
	
		<p class="error">
			<c:out value="${errorMessage }" />
		</p>
		<form id="addUser" name="addUser" action="UserManagement"
			method="post">
			<label for="firstName">First Name:</label> 
			<input required	type="text" id="firstName" name="firstName" /><br /> 
			<label for="lastName">Last Name:</label> 
			<input required type="text"	id="lastName" name="lastName" /><br /> 
			<label for="email">Email Address:</label> 
			<input required type="email" id="email" name="email" /><br />
			<label for="username">Username:</label> 
			<input required type="text"	id="username" name="username" /><br /> 
			<label for="password">Password:</label>
			<input required type="password" id="password" name="password" /><br />
			<input type="hidden" id="action" name="action" value="addAdmin" /> 
			<input type="submit" value="Submit" /> <input type="reset" value="Reset" />
		</form>
	</div>
</body>
</html>