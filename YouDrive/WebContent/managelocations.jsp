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
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/offcanvas.js"></script>
	    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	    <!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
	    <![endif]-->
	<title>Edit Rental Location</title>
	<script type="text/javascript">
		//Equivalent to $( document ).ready(function(){});
		$(function() {
			/* When user clicks a clickable table header, the href value which corresponds to the sorting type requested
			is obtained, a hidden input field is updated with this value and the hidden form is submitted.
			HACKY method but it works. I wish I could directly update the JSP variable from JavaScript but
			this method updates the JSP variable by going through GET*/
			$('.navSort').click(function(){
				//Get href value
				//Set hidden input field
				//Submit form which reloads the page
				searchValue = $(this).attr('href').substring(1);
				document.getElementById("searchType").value = searchValue;
				$('#sortLocationForm').submit();
			});
		});
	</script>
<title>Manage Locations</title>
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
					<p class="error">
						<c:out value="${errorMessage }" />
					</p>
					<c:choose>
						<c:when test="${loggedInUser != null && loggedInUser.isAdmin() }">
						<jsp:useBean id="locationMgr" class="com.youdrive.helpers.LocationDAO" scope="session" />			
						<c:set var="allLocations" value="${locationMgr.getAllLocations() }" scope="session"/>
							<div class="table-responsive">
								<table class="table table-condensed table-hover">
									<tr>
										<th class="hidden">ID</th>
										<th><a href="#0" class="navSort">Location Name</a></th>
										<th><a href="#1" class="navSort">Location Address</a></th>
										<th><a href="#2" class="navSort">Max Capacity</a></th>
										<th>Current Capacity</th>
										<th>Edit</th>
									</tr>
									<c:forEach items="${a:locationSort(allLocations,searchType)}" var="location"
										varStatus="status">
										<tr>
											<td class="hidden"><c:out value="${ location.id }" /></td>
											<td><c:out value="${ location.name }" /></td>
											<td><c:out value="${ location.address}" /></td>
											<td><c:out value="${ location.capacity}" /></td>
											<td><c:out value="${ locationMgr.getCurrentCapacity(location.id)}" /></td>
											<c:url value="LocationManagement" var="url">
												<c:param name="locationID" value="${location.id}" />
											</c:url>
											<td><a href="<c:out value="${url }" />">Edit</a></td>
										</tr>
									</c:forEach>
								</table>
							</div>
							<%-- Hidden form which gets submitted when user clicks on a clickable table heading --%>			
							<form id="sortLocationForm" name="sortLocationForm" method="get" action="LocationManagement">
								<input type="hidden" id="action" name="action" value="sortLocation"/>
								<input type="hidden" id="searchType" name="searchType" value="" />
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
		            <a class="list-group-item" href="managevehicles.jsp">Manage Vehicles</a>
		            <a class="list-group-item" href="managevehicletypes.jsp">Manage Vehicle Types</a>
		            <a class="list-group-item active" href="managelocations.jsp">Manage Locations</a>
		            <a class="list-group-item" href="managememberships.jsp">Manage Memberships</a>
		            <a class="list-group-item" href="manageusers.jsp">Manage Users</a>
		            <a class="list-group-item" href="logout.jsp">Logout</a>
				</div>
			</div>
			<!--/span-->
		</div>
		<!--/row-->

		<hr>

		<footer>
			<p>&copy; Company 2013</p>
		</footer>

	</div>
	<!--/.container-->
	
		<!-- Modal -->
	<d iv class="modal fade" id="aboutModal" tabindex="-1" role="dialog"
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