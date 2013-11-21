<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/homepage.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/signin.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<title>Create a Customer/Admin</title>
</head>
<body>
	<div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="http://localhost:8080/YouDrive">YouDrive</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="http://localhost:8080/YouDrive">Home</a></li>
					<li><a href="#about" data-toggle="modal" data-target="#aboutModal">About</a></li>
				</ul>
			</div>
			<!-- /.nav-collapse -->
		</div>
		<!-- /.container -->
	</div>
	
	
	<h3 class="page_title">Admin - Add User</h3>
	<c:if test="${loggedInUser != null }">
		<p class="userInfo">Hello, <c:out value="${loggedInUser.firstName }" /></p>
	</c:if>
	<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
		<div class="list-group">
            <a class="list-group-item active" href="addvehicle.jsp">Add Vehicle</a>
            <a class="list-group-item" href="addvehicletype.jsp">Add Vehicle Type</a>
            <a class="list-group-item" href="addlocation.jsp">Add Location</a>
            <a class="list-group-item" href="addmembership.jsp">Add Membership</a>
            <a class="list-group-item" href="adduser.jsp">Add Admin User</a>
            <a class="list-group-item" href="managevehicles.jsp">Manage Vehicles</a>
            <a class="list-group-item" href="managevehicletypes.jsp">Manage Vehicle Types</a>
            <a class="list-group-item" href="managelocations.jsp">Manage Locations</a>
            <a class="list-group-item" href="managememberships.jsp">Manage Memberships</a>
            <a class="list-group-item" href="manageusers.jsp">Manage Users</a>
            <a class="list-group-item" href="logout.jsp">Logout</a>
          </div>
    </div>
    <div class="container">
	
		<p class="error">
			<c:out value="${errorMessage }" />
		</p>
		<c:choose>
			<c:when test="${loggedInUser != null && loggedInUser.isAdmin() }">
			<div class="forms">
				<form class="form-horizontal" id="addUser" name="addUser" action="UserManagement" method="post">
					<label for="firstName">First Name:</label> 
					<input required	type="text" id="firstName" name="firstName" /><br /> 
					<label for="lastName" >Last Name:</label> 
					<input required type="text" id="lastName" name="lastName" /><br /> 
					<label for="email">Email Address:</label> 
					<input required type="email" id="email" name="email" /><br />
					<label for="username">Username:</label> 
					<input required type="text"	id="username" name="username" /><br /> 
					<label for="password">Password:</label>
					<input required type="password" id="password" name="password" /><br />
					<input type="hidden" id="action" name="action" value="addAdmin" /> 
					<button type="submit" class="btn btn-primary btn-lg btn-block">Submit</button>
					<button type="reset" class="btn btn-default btn-lg btn-block">Reset</button>
				</form>
			</div>
			</c:when>
			<c:otherwise>
			<p class="error">
				<p class="error">Please <a href="login.jsp">login</a> as an admin to access this page.</p>
			</p>
			</c:otherwise>
		</c:choose>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="aboutModal" tabindex="-1" role="dialog" aria-labelledby="aboutModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="aboutModalLabel">About YouDrive</h4>
				</div>
				<div class="modal-body">Group Project by Jane Ullah, James Vaughan, Rod Rashidi and Trevor Wilson.</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<!-- <button type="button" class="btn btn-primary">Save changes</button> -->
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</body>
</html>