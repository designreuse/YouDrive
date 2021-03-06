<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="a" uri="/sortItems" %>

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
	</script>
	<title>${location.name }</title>
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
					<c:if test="${loggedInUser != null}">
						<li class="active"><a href="user.jsp">Home</a></li>
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
					<h2 class="pull-left">Vehicles at ${location.name }</h2>
					<form class="form-search pull-right" action="VehicleBrowser" method="get">
						<div style="padding-top:18px"class="input-append">
							<input type="text" class="span2" name="searchTerms">
						    <button type="submit" class="btn btn-primary">Search</button>
						    <input type="hidden" name="redirect" value="viewlocation.jsp" />
						</div>
					</form>
					<c:choose>
						<c:when test="${loggedInUser == null}">
							<p class="error">Please <a href="login.jsp">login</a> to access this page.</p>
						</c:when>
						<c:otherwise>
							<c:if test="${vehicleMgr == null }">
								<jsp:useBean id="vehicleMgr" class="com.youdrive.helpers.VehicleDAO" scope="session" />
							</c:if>
							<c:choose>
								<c:when test="${searchTerms != null && !searchTerms.isEmpty() }">
									<c:set var="allVehicles" value="${vehicleMgr.searchVehiclesAtLocation(searchTerms, location.id) }" scope="session"/>
								</c:when>
								<c:otherwise>
									<c:set var="allVehicles" value="${vehicleMgr.getVehiclesByLocationId(location.id) }" scope="session"/>
								</c:otherwise>
							</c:choose>
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
										</tr>
									</c:forEach>
								</table>	
							</div>
							
							<%-- Hidden form for the sorting --%>
							<form id="sortVehicleForm" name="sortVehicleForm" method="get" action="VehicleBrowser">
								<input type="hidden" id="action" name="action" value="sortVehicle"/>
								<input type="hidden" id="searchType" name="searchType" value="" />
								<input type="hidden" id="locationID" name="locationID" value="${ location.id }" />
								<input type="hidden" id="searchTerms" name="searchTerms" value="${ searchTerms }" />
								<input type="hidden" name="redirect" value="viewlocation.jsp" />
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
		            <a class="list-group-item active" href="browselocations.jsp">Browse Locations</a>
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

</body>
</html>