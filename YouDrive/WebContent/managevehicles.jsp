<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="a" uri="/sortItems" %>
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
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/signin.css">
	<link rel="stylesheet" href="css/offcanvas.css">
	<!-- Stylesheet for fancy pants alert box :) -->
	<link rel="stylesheet" href="css/alertify.core.css" />
	<link rel="stylesheet" href="css/alertify.bootstrap.css" />
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/offcanvas.js"></script>
	<script src="js/alertify.min.js"></script>
	    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	    <!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
	    <![endif]-->
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
		
		/* submit the hidden form to delete the vehicle but need to confirm with user first*/
		function getVehicleID(vehicleID, vehicleTag){
			// confirm dialog
			alertify.confirm("You are about to delete vehicle with tag #" + vehicleTag + ". To continue, press \"OK\"; otherwise, hit \"Cancel\"", function (e) {
			    if (e) {
			    	console.log("OK clicked.");
					document.getElementById("vehicleID").value = vehicleID;
					$('#deleteVehicleForm').submit();
			    } else {
			        console.log("Cancel clicked.");
			    }
			});
			console.log(vehicleID);
		}	
	</script>
<title>Manage Vehicles</title>
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
							<c:if test="${vehicleMgr == null }">
								<jsp:useBean id="vehicleMgr" class="com.youdrive.helpers.VehicleDAO" scope="session" />
							</c:if>
							<c:set var="allVehicles" value="${vehicleMgr.getAllVehicles() }" scope="session"/>
							<div class="table-responsive">
								<table class="table table-condensed table-hover">
									<tr>
										<th><a href="#0" class="navSort">Make</a></th>
										<th><a href="#1" class="navSort">Model</a></th>
										<th><a href="#2" class="navSort">Year</a></th>
										<th><a href="#3" class="navSort">Tag</a></th>
										<th><a href="#4" class="navSort">Mileage</a></th>
										<th><a href="#5" class="navSort">Last Serviced</a></th>
										<th><a href="#6" class="navSort">Vehicle Type</a></th>
										<th><a href="#7" class="navSort">Vehicle Location</a></th>
										<th>Edit</th>
										<th>Notes</th> 
										<th>Delete</th>
									</tr>
									<%-- Created a custom EL function to sort the list on demand --%>
									<c:forEach items="${a:vehicleSort(allVehicles,searchType)}" var="vehicle" varStatus="status">
										<tr id="${vehicle.id }">
											<td><c:out value="${ vehicle.make }" /></td>
											<td><c:out value="${ vehicle.model }" /></td>
											<td><c:out value="${ vehicle.year }" /></td>
											<td><c:out value="${ vehicle.tag }" /></td>
											<td><c:out value="${ vehicle.mileage }" /></td>
											<td><fmt:formatDate pattern="MM/dd/yyyy" type="date" value="${vehicle.lastServiced}" /></td>
											<td><c:out value="${ vehicleMgr.getVehicleType(vehicle.vehicleType) }" /></td>
											<td><c:out value="${ vehicleMgr.getVehicleLocation(vehicle.assignedLocation)}" /></td>
											<c:url value="VehicleManagement" var="url">
												<c:param name="vehicleID" value="${vehicle.id}" />
											</c:url>
											<td><a title="Click to edit vehicle tag : ${vehicle.tag }" href="<c:out value="${url }" />"><span class="glyphicon glyphicon-edit"></span></a></td>
											<c:url value="VehicleManagement" var="url">
												<c:param name="viewComments" value="${vehicle.id}" />
											</c:url>
											<td><a title="Click to view notes about this vehicle tag : ${vehicle.tag }" href="<c:out value="${url }" />"><span class="glyphicon glyphicon-zoom-in"></span></a></td>
											<td><a title="Click to delete vehicle tag : ${vehicle.tag }"><span onclick="getVehicleID('${vehicle.id}','${vehicle.tag }')" class="glyphicon glyphicon-trash"></span></a></td>
										</tr>
									</c:forEach>
								</table>	
							</div>
							
							<%-- Hidden form for the sorting --%>
							<form id="sortVehicleForm" name="sortVehicleForm" method="get" action="VehicleManagement">
								<input type="hidden" id="action" name="action" value="sortVehicle"/>
								<input type="hidden" id="searchType" name="searchType" value="" />
							</form>
							
							<%-- Hidden form for deleting the membership --%>
							<form id="deleteVehicleForm" name="deleteVehicleForm" method="post" action="VehicleManagement">
								<input type="hidden" id="action" name="action" value="deleteVehicle"/>
								<input type="hidden" id="vehicleID" name="vehicleID" value="" />
							</form>
							
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