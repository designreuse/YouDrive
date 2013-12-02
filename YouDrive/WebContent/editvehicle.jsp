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
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/signin.css">
	<link rel="stylesheet" href="css/offcanvas.css">
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/offcanvas.js"></script>
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
	    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
	<title>Edit Vehicle Details</title>
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
					<c:if test="${loggedInUser != null && loggedInUser.isAdmin() }">
						<li class="active"><a href="admin.jsp">Home</a></li>
					</c:if>
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
					<c:if test="${errorMessage != null && errorMessage.length() > 0}">
						<div id="errorDisplay" class="alert alert-danger">
							<c:out value="${errorMessage }" />
						</div>
					</c:if>
					<c:choose>
						<c:when test="${loggedInUser != null && loggedInUser.isAdmin() }">
							<c:choose>
								<c:when test="${vehicle != null }">
									<div class="forms">
									<form class="well" id="editVehicle" name="editVehicle" action="VehicleManagement"
										method="post">
										<label for="make">Make:</label>
										<input required type="text" id="make" name="make" value="${vehicle.make }"/><br/>
										<label for="model">Model:</label>
										<input required type="text" id="model" name="model" value="${vehicle.model }" /><br/>
										<label for="year">Year:</label>
										<input required type="text" id="year" name="year" value="${vehicle.year }" /><br/>
										<label for="tag">Tag:</label>
										<input required type="text" id="tag" name="tag" value="${vehicle.tag }" /><br/>
										<label for="mileage">Mileage:</label>
										<input required type="text" id="mileage" name="mileage" value="${vehicle.mileage }" /><br/>
										<label for="lastServiced">Last Serviced:</label>					
										<input required type="text" id="lastServiced" name="lastServiced" value="<fmt:formatDate pattern="MM/dd/yyyy" type="date" value="${vehicle.lastServiced}" />" /><br/>
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
										<label for="comment">Enter Comments:</label><br/>
										<textarea  class="form-control" rows="3" name="comment" id="comment"></textarea><br/>	
										<input type="hidden" id="action" name="action" value="editVehicle"/>
										<input type="hidden" id="vehicleID" name="vehicleID" value="${vehicle.id }"/>							
										<button type="submit" class="btn btn-primary btn-lg btn-block">Update</button>
										<button type="button" onclick="window.location.replace('managevehicles.jsp')" class="btn btn-default btn-lg btn-block">Cancel</button>
									</form>
									</div>
									<%-- Submit this form when user clicks viewcomments.jsp link --%>
									<form method="get" action="VehicleManagement" id="vehicleCommentsForm" name="vehicleCommentsForm">
										<input type="hidden" id="action" name="action" value="viewComments" />
										<input type="hidden" id="vehicle_id" name="vehicle_id" value="${ vehicle.id}" />
									</form>
								</c:when>
							</c:choose>
						</c:when>
						<c:otherwise>
							<p class="error">Please <a href="login.jsp">login</a> as an admin to access this page.</p>
						</c:otherwise>
					</c:choose>
				</div>
				<!--/row-->
			</div>
			<!--/span-->

			<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar"
				role="navigation">
				<div class="list-group">
					<a class="list-group-item">Navigation</a>
		            <a class="list-group-item" href="addvehicle.jsp">Add Vehicle</a>
		            <a class="list-group-item" href="addvehicletype.jsp">Add Vehicle Type</a>
		            <a class="list-group-item" href="addlocation.jsp">Add Location</a>
		            <a class="list-group-item" href="addmembership.jsp">Add Membership</a>
		            <a class="list-group-item" href="adduser.jsp">Add Admin User</a>
		            <a class="list-group-item active" href="managevehicles.jsp">Manage Vehicles</a>
		            <a class="list-group-item" href="managevehicletypes.jsp">Manage Vehicle Types</a>
		            <a class="list-group-item" href="managelocations.jsp">Manage Locations</a>
		            <a class="list-group-item" href="managememberships.jsp">Manage Memberships</a>
		            <a class="list-group-item" href="manageusers.jsp">Manage Admins</a>
		            <a class="list-group-item" href="managecustomers.jsp">Manage Customers</a>
		            <a class="list-group-item" href="logout.jsp">Logout</a>
				</div>
			</div>
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