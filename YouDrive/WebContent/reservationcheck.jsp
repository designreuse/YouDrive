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
	<title>Available Vehicles</title>
	<script type="text/javascript">
		/* submit the hidden form to delete the location but need to confirm with user first*/
		function getVehicleID(vehicleID, vehicletag){
			// confirm dialog
			var resultParams = ${resultParams};
			console.log(resultParams);
			var msg = "You are about to reserve " + vehicletag + " from " + resultParams.pickupDate + " to " + resultParams.dropoffDate;
			msg += ".To continue, press \"OK\"; otherwise, hit \"Cancel\"";
			alertify.confirm(msg, function(result) {
			    if (result) {
			    	console.log("OK clicked.");
					document.getElementById("vehicleID").value = vehicleID;
					$('#makeReservationForm').submit();
			    } else {
			        console.log("Cancel clicked.");
			    }
			});
			console.log(vehicleID);
		}
	</script>
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
					<c:if test="${errorMessage != null && errorMessage.length() > 0}">
						<div id="errorDisplay" class="alert alert-danger">
							<c:out value="${errorMessage }" />
						</div>
					</c:if>
					<c:choose>
						<c:when test="${loggedInUser == null}">
							<p class="error">Please <a href="login.jsp">login</a> to access this page.</p>
						</c:when>
						<c:otherwise>
							<c:if test="${ locationMgr == null }">	
								<jsp:useBean id="locationMgr" class="com.youdrive.helpers.LocationDAO" scope="session" />
							</c:if>
							<c:if test="${vehicleTypeMgr  == null}">
								<jsp:useBean id="vehicleTypeMgr" class="com.youdrive.helpers.VehicleTypeDAO" scope="session" />		
							</c:if>
							<div class="alert alert-info">
								You selected:<br/>
								Location: <strong><c:out value="${location.name }"/></strong><br/>
								Vehicle Type: <strong><c:out value="${vehicleType.type }"/></strong><br/>
								Start Date: <strong><fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${startDate}" /></strong><br/>
								Stop Date: <strong><fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${endDate}" /> </strong><br/>							
							</div>
							<div class="table-responsive">
								<table class="table table-condensed table-hover">
									<tr>
										<th>Make</th>
										<th>Model</th>
										<th>Year</th>
										<th>Tag</th>
										<th>Hourly Price</th>
										<th>Daily Price</th>
										<th>Mileage</th>
										<th>Last Serviced</th>
										<th>Reserve</th>
									</tr>
									<c:forEach items="${searchResults}" var="vehicle" varStatus="status">
										<tr id="${vehicle.id }">
											<td><c:out value="${ vehicle.make }" /></td>
											<td><c:out value="${ vehicle.model }" /></td>
											<td><c:out value="${ vehicle.year }" /></td>
											<td><c:out value="${ vehicle.tag }" /></td>
											<td><c:out value="${ vehicleType.hourlyPrice }" /></td>
											<td><c:out value="${ vehicleType.dailyPrice }" /></td>
											<td><c:out value="${ vehicle.mileage }" /></td>
											<td><fmt:formatDate pattern="MM/dd/yyyy" type="date" value="${vehicle.lastServiced}" /></td>
											<td><button type="button" onclick="getVehicleID('${vehicle.id}','${vehicle.tag }')" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-star"></span> Reserve</button></td>
										</tr>
									</c:forEach>
								</table>								
								<button type="button" onclick="window.history.back()" class="btn btn-primary">Go Back</button> 
							</div>
							<%-- Hidden form for the sorting --%>
							<form id="makeReservationForm" name="makeReservationForm" method="post" action="ReservationManagement">
								<input type="hidden" id="action" name="action" value="makeReservation"/>
								<input type="hidden" id="vehicleID" name="vehicleID" value="" />
							</form>
						</c:otherwise>
					</c:choose>
				</div>
				<!--/row-->
			</div>
			<!--/span-->

			<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
				<div class="list-group">
					<a class="list-group-item active"><strong>Navigation</strong></a>
		            <a class="list-group-item" href="browselocations.jsp">Browse Locations</a>
		            <a class="list-group-item" href="browsevehicles.jsp">Browse Vehicles</a>
		            <a class="list-group-item" href="reservevehicle.jsp">Reserve Vehicle</a>
					<a class="list-group-item" href="userreservations.jsp">My Reservations</a>
		            <a class="list-group-item" href="usermembership.jsp">My Membership</a>
		            <c:url value="UserManagement" var="url">
						<c:param name="customerID" value="${loggedInUser.id}" />
					</c:url>
		            <a class="list-group-item" href="<c:out value="${url }" />">My Details</a>       
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
	
	<!-- Modal -->
	<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="confirmModalLabel">Confirm Your Reservation</h4>
				</div>
				<div class="modal-body">
				
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

</body>
</html>