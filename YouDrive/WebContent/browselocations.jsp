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
	<title>Browse Locations</title>
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
					<h2>All Locations</h2>
					<c:choose>
						<c:when test="${loggedInUser == null}">
							<p class="error">Please <a href="login.jsp">login</a> to access this page.</p>
						</c:when>
						<c:otherwise>
							<%-- Instantiate locationmgr if it doesn't exists --%>
							<c:if test="${locationMgr == null }">						
								<jsp:useBean id="locationMgr" class="com.youdrive.helpers.LocationDAO" scope="session" />
							</c:if>		
							<c:set var="allLocations" value="${locationMgr.getAllLocations() }" scope="session"/>
							<div class="table-responsive">
								<table class="table table-condensed table-hover">
									<tr>
										<th><a href="#0" class="navSort">Name</a></th>
										<th><a href="#1" class="navSort">Address</a></th>
									</tr>
									<c:forEach items="${a:locationSort(allLocations,searchType)}" var="location"
										varStatus="status">
										<tr id="${ location.id}">
											<c:url value="LocationBrowser" var="url">
												<c:param name="locationID" value="${location.id}" />
											</c:url>
											<td><a title="View this location" href="<c:out value="${url }" />"><c:out value="${ location.name }" /></a></td>
											<td><c:out value="${ location.address}" /></td>
											
										</tr>
									</c:forEach>
								</table>
							</div>
							
							<%-- Hidden form which gets submitted when user clicks on a clickable table heading --%>			
							<form id="sortLocationForm" name="sortLocationForm" method="get" action="LocationBrowser">
								<input type="hidden" id="action" name="action" value="sortLocation"/>
								<input type="hidden" id="searchType" name="searchType" value="" />
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