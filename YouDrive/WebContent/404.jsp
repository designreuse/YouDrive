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
	<link rel="stylesheet" href="css/offcanvas.css">
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/offcanvas.js"></script>
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
	<title>YouDrive 404 Page</title>
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
				<a class="navbar-brand" href="index.jsp">YouDrive</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<c:choose>
						<c:when test="${loggedInUser != null && loggedInUser.isAdmin() }">
							<li class="active"><a href="admin.jsp">Home</a></li>
						</c:when>
						<c:otherwise>
							<li class="active"><a href="user.jsp">Home</a></li>
						</c:otherwise>
					</c:choose>
					<li><a href="#about" data-toggle="modal" data-target="#aboutModal">About</a></li>
				</ul>
				<c:if test="${loggedInUser != null }">
					<p class="navbar-right userInfo">Hello, <c:out value="${loggedInUser.firstName }" /></p>
				</c:if>
			</div>
			<!-- /.nav-collapse -->
		</div>
		<!-- /.container -->
	</div>
	<!-- /.navbar -->

	<div class="container">

		<div class="row row-offcanvas row-offcanvas-right">

			<div class="col-xs-12 col-sm-9">
				<p class="pull-right visible-xs">
					<button type="button" class="btn btn-primary btn-xs"
						data-toggle="offcanvas">Toggle nav</button>
				</p>
				<div class="row">
					<div class="alert alert-info">
						<h3>404</h3>
					</div>
					<ol>
						<li><a href="login.jsp" title="Login To YouDrive">Login</a></li>
						<li><a href="index.jsp" title="YouDrive Home">YouDrive</a></li>
						<li><a href="registration_page1" title="Register">Register</a></li>
					</ol>
				</div>
				<!--/row-->
			</div>
			<!--/span-->

			<c:choose>
				<%-- making sure only logged in users can view vehicle comments --%>
				<c:when test="${loggedInUser != null }">
					<c:choose>
						<%-- making sure right navigation menu is displayed to admin versus regular users --%>
						<c:when test="${loggedInUser.isAdmin()}">
							<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar"
								role="navigation">
								<div class="list-group">
									<a class="list-group-item">Navigation</a>
						            <a class="list-group-item" href="addvehicle.jsp">Add Vehicle</a>
						            <a class="list-group-item" href="addvehicletype.jsp">Add Vehicle Type</a>
						            <a class="list-group-item" href="addlocation.jsp">Add Location</a>
						            <a class="list-group-item" href="addmembership.jsp">Add Membership</a>
						            <a class="list-group-item" href="adduser.jsp">Add Admin User</a>
						            <a class="list-group-item" href="managevehicles.jsp">Manage Vehicles</a>
						            <a class="list-group-item" href="managevehicletypes.jsp">Manage Vehicle Types</a>
						            <a class="list-group-item active" href="managelocations.jsp">Manage Locations</a>
						            <a class="list-group-item" href="managememberships.jsp">Manage Memberships</a>
		            				<a class="list-group-item" href="manageusers.jsp">Manage Admins</a>
		            				<a class="list-group-item" href="managecustomers.jsp">Manage Customers</a>
						            <a class="list-group-item" href="logout.jsp">Logout</a>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<%-- Display navigation menu for user --%>
							<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
								<div class="list-group">
									<a class="list-group-item"><strong>Navigation</strong></a>
						            <a class="list-group-item" href="browselocations.jsp">Browse Locations</a>
						            <a class="list-group-item" href="browsevehicles.jsp">Browse Vehicles</a>
		            				<a class="list-group-item" href="reservevehicle.jsp">Reserve Vehicle</a>
						            <a class="list-group-item" href="returnvehicle.jsp">Return Vehicle</a>
						            <a class="list-group-item" href="usermembership.jsp">My Membership</a>
						            <c:url value="UserManagement" var="url">
										<c:param name="customerID" value="${loggedInUser.id}" />
									</c:url>
						            <a class="list-group-item active" href="<c:out value="${url }" />">My Details</a>       
						            <a class="list-group-item" href="logout.jsp">Logout</a>
								</div>
							</div>
						</c:otherwise>
					</c:choose>						
				</c:when>
			</c:choose>
			<!--/span-->
		</div>
		<!--/row-->

		<hr>

		<footer>
			<p>&copy; YouDrive 2013</p>
		</footer>

	</div>
	<!--/.container-->
	
	
	<!-- Modal -->
	<div class="modal fade" id="aboutModal" tabindex="-1" role="dialog"
		aria-labelledby="aboutModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="aboutModalLabel">About YouDrive</h4>
			</div>
			<div class="modal-body">Group Project by Jane Ullah, James
				Vaughan, Rod Rashidi and Trevor Wilson.</div>
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