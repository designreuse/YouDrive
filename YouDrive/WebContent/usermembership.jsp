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
	<title>My Membership</title>
	<script type="text/javascript">
		function confirmTermination(){
			var msg = "You are about to terminate your membership. If you have active reservations, you will not be able to cancel your membership until you have returned or cancelled your reservations. You will be immediately logged out after performing this operation. Click \"OK\" to continue.";
			alertify.confirm(msg, function(result) {
			    if (result) {
					$('#terminateMembershipForm').submit();
			    } else {
			        console.log("Cancel clicked.");
			    }
			});
		}
		
		function confirmExtension(){
			var msg = "You are about to extend your membership for 6 months which will be added to the current membership expiration date. You will be charged the $150.00 for this plan.";
			alertify.confirm(msg, function(result) {
			    if (result) {
					$('#extendMembershipForm').submit();
			    } else {
			        console.log("Cancel clicked.");
			    }
			});
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
					<c:if test="${infoMessage != null && infoMessage.length() > 0}">
						<div id="infoDisplay" class="alert alert-success">
							<c:out value="${infoMessage }" />
						</div>
					</c:if>
					<c:choose>
						<c:when test="${loggedInUser == null}">
							<p class="error">Please <a href="login.jsp">login</a> to access this page.</p>
						</c:when>
						<c:otherwise>
							<c:if test="${ membershipMgr == null}">
								<jsp:useBean id="membershipMgr" class="com.youdrive.helpers.MembershipDAO" scope="session" />	
							</c:if>
							<c:set var="membershipObj" value="${ membershipMgr.getMembership(loggedInUser.membershipLevel)}"/>
							<c:choose>
								<c:when test="${membershipObj != null }">
									<h3>
										Your Membership Details
									</h3>
									<article class="membershipDisplay">
										<span >Membership: <strong><c:out value="${membershipObj.name }"/></strong></span>
										<span>Membership Price: <strong><fmt:formatNumber value="${membershipObj.price }" type="currency" /> </strong></span>
										<span>Membership Duration: <strong><c:out value="${membershipObj.duration }"/></strong> months</span>
										<span>Membership Expiration Date: <strong><fmt:formatDate type="both" dateStyle="long" timeStyle="short" value="${ loggedInUser.memberExpiration}"/></strong></span>
										
										<br/><br/>
										<%-- Extend Membership Form --%>
										<button onclick="confirmExtension()" class="btn btn-success btn-lg">Extend Membership</button>
										<form id="extendMembershipForm" name="extendMembershipForm" method="post" action="MembershipManagement">
											<input type="hidden" type="hidden" id="action" name="action" value="extendMembership" />
											<input type="hidden"  id="customerID" name="customerID" value="${loggedInUser.id }"/>
										</form>
										
										<br/>
										<%-- Terminate Membership form --%>
										
										<button onclick="confirmTermination()" class="btn btn-danger">Terminate Membership</button>
										<form id="terminateMembershipForm" name="terminateMembershipForm" method="post" action="MembershipManagement">
											<input type="hidden" id="action" name="action" value="terminateUserMembership" />
											<input type="hidden" id="customerID" name="customerID" value="${loggedInUser.id }"/>
										</form>
									</article>
								</c:when>
								<c:otherwise>
									Invalid membership found.
								</c:otherwise>
							</c:choose>
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
					<a class="list-group-item" href="userreservations.jsp">My Reservations</a>
		            <a class="list-group-item active" href="usermembership.jsp">My Membership</a>
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