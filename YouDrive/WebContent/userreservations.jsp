<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy hh:mm:ss a"); %>
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
	<title>Return Vehicle</title>
	<script type="text/javascript">
		/* submit the hidden form to return the vehicle but need to confirm with user first*/
		function returnReservation(reservationID, vehicleTag){
			var msg = "You are about to return reservation #" + reservationID + " for vehicle tag #" + vehicleTag + ". Please enter comments about the vehicle's condition if there were problems. To continue, press \"OK\"; otherwise, hit \"Cancel\"";
			// prompt dialog
			alertify.prompt(msg, function (e, str) {
			    // str is the input text
			    if (e) {
			        // user clicked "ok"
			    	console.log("OK clicked.");
					document.getElementById("reservationID_return").value = reservationID;
					document.getElementById("returnedVehicleComment").value = str;
					$('#returnReservationForm').submit();
			    } else {
			        // user clicked "cancel"
			    	console.log("Cancel clicked.");
			    }
			}, "Vehicle returned in good condition.");
			
			console.log(reservationID);
		}
		
		/* submit the hidden form to cancel the reservation but need to confirm with user first*/
		function cancelReservation(reservationID, vehicleTag){
			// confirm dialog
			alertify.confirm("You are about to cancel reservation #" + reservationID + " for vehicle tag #" + vehicleTag + ". To continue, press \"OK\"; otherwise, hit \"Cancel\"", function (e) {
			    if (e) {
			    	console.log("OK clicked.");
					document.getElementById("reservationID_cancel").value = reservationID;
					$('#cancelReservationForm').submit();
			    } else {
			        console.log("Cancel clicked.");
			    }
			});
			console.log(reservationID);
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
				<a class="navbar-brand" href="http://localhost:8080/YouDrive">YouDrive</a>
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
					<c:if test="${penalty != null && hoursOver != null }">
						<div id="pricingDisplay" class="alert alert-info">
							For going over by <c:out value="${hoursOver }"/> hours, you were charged 
							<fmt:formatNumber value="${ penalty}" type="currency" />
						</div>
					</c:if>
					<c:choose>
						<c:when test="${loggedInUser == null}">
							<p class="error">Please <a href="login.jsp">login</a> to access this page.</p>
						</c:when>
						<c:otherwise>
							<c:if test="${reservationMgr == null }">
								<jsp:useBean id="reservationMgr" class="com.youdrive.helpers.ReservationDAO" scope="session" />	
							</c:if>
							<c:if test="${locationMgr == null }">
								<jsp:useBean id="locationMgr" class="com.youdrive.helpers.LocationDAO" scope="session" />
							</c:if>
							<c:if test="${vehicleMgr == null }">
								<jsp:useBean id="vehicleMgr" class="com.youdrive.helpers.VehicleDAO" scope="session" />
							</c:if>
							<jsp:useBean id="now" class="java.util.Date"/>
							<h2>Returning a vehicle late will incur a flat $50 in addition to the standard hourly rate for the car.<br/>
							Current Date: <%= df.format(new java.util.Date()) %>
							</h2>
							<div class="table-responsive">
								<table class="table table-condensed table-hover">
									<tr>
										<th>Reservation #</th>
										<th>Location </th>
										<th>Vehicle</th>
										<th>Start Date</th>
										<th>End Date</th>
										<th class="hidden">Hours Late</th>
										<th>Return</th>
										<th>Cancel</th>
									</tr>
									<c:forEach items="${reservationMgr.getUserReservations(loggedInUser.id)}" var="reservation" varStatus="status">
										<c:set var="reservationStatusList" value="${reservation.reservationStatusList}" />
										<c:if test="${fn:length(reservationStatusList) == 1}">
											<tr id="reservation_${reservation.id}">
												<td><c:out value="${reservation.id}"/></td>
												<td><c:out value="${vehicleMgr.getVehicleLocation(reservation.locationID)}"/></td>
												<c:set var="vehicleObj" value="${vehicleMgr.getVehicle(reservation.vehicleID)}" />
												<td><c:out value="${vehicleObj.make}"/>, <c:out value="${vehicleObj.model}"/></td>
												<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${reservation.reservationStart}" /></td>
												<td><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${reservation.reservationEnd}" /></td>
												<td class="hidden" id="dateAdded_${reservation.id }">
													<c:set var="reservationStatus" value="${reservationStatusList.get(0)}" />
												</td>
												
												<td><a title="Click to Return Reservation # ${reservation.id }"><span onclick="returnReservation('${reservation.id}','${vehicleObj.tag }')" class="glyphicon glyphicon-share-alt"></span></a></td>
												<td><a title="Click to Cancel Reservation # ${reservation.id }"><span onclick="cancelReservation('${reservation.id}','${vehicleObj.tag }')" class="glyphicon glyphicon-remove"></span></a></td>
											</tr>
										</c:if>
									</c:forEach>
								</table>
							</div>
							<%-- Hidden form which gets submitted when user returns a reservation --%>			
							<form id="returnReservationForm" name="returnReservationForm" method="post" action="ReservationManagement">
								<input type="hidden" id="action" name="action" value="returnReservation"/>
								<input type="hidden" id="returnedVehicleComment" name="returnedVehicleComment" value="" />
								<input type="hidden" id="reservationID_return" name="reservationID_return" value="" />
							</form>
							
							<%-- Hidden form which gets submitted when user cancels a reservation --%>			
							<form id="cancelReservationForm" name="cancelReservationForm" method="post" action="ReservationManagement">
								<input type="hidden" id="action" name="action" value="cancelReservation"/>
								<input type="hidden" id="reservationID_cancel" name="reservationID_cancel" value="" />
							</form>
							
						</c:otherwise>
					</c:choose>
				</div>
				<!--/row-->
			</div>
			<!--/span-->

			<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
				<div class="list-group">
					<a class="list-group-item"><strong>Navigation</strong></a>
		            <a class="list-group-item" href="browselocations.jsp">Browse Locations</a>
		            <a class="list-group-item" href="browsevehicles.jsp">Browse Vehicles</a>
		            <a class="list-group-item" href="reservevehicle.jsp">Reserve Vehicle</a>
					<a class="list-group-item active" href="userreservations.jsp">My Reservations</a>
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

</body>
</html>