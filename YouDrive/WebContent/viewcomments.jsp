<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<title>View Vehicle Comments</title>
</head>
<body>
	<h3>View Comments For Vehicle #<c:out value="${vehicle.id }"/></h3>
	<c:if test="${loggedInUser != null }">
		<p class="userInfo">
			Hello, <c:out value="${loggedInUser.firstName }" />
		</p>
	</c:if>
	<c:if test="${ loggedInUser != null && loggedInUser.isAdmin()}">
		<ol class="nav">
			<li><a href="addvehicle.jsp">Add Vehicle</a></li>
			<li><a href="addvehicletype.jsp">Add Vehicle Type</a></li>
			<li><a href="addlocation.jsp">Add Location</a></li>
			<li><a href="addmembership.jsp">Add Membership</a></li>
			<li><a href="adduser.jsp">Add Admin User</a></li>
			<li><a href="managevehicles.jsp">Manage Vehicles</a></li>
			<li><a href="managevehicletypes.jsp">Manage Vehicle Types</a></li>
			<li><a href="managelocations.jsp">Manage Locations</a></li>
			<li><a href="managememberships.jsp">Manage Memberships</a></li>
			<li><a href="manageusers.jsp">Manage Users</a></li>
			<li><a href="logout.jsp">Logout</a></li>
		</ol>
	</c:if>
	<c:choose>
		<%-- Only registered aka logged in users can view comments --%>
		<c:when test="${loggedInUser != null }">
			<div class="body">
				<p class="error">
					<c:out value="${errorMessage }" />
				</p>
				<c:choose>
					<%-- Check if the vehicle object exists --%>
					<c:when test="${vehicle != null }">	
						<%-- True if person lands on this page w/o going through vehiclemanagement servlet --%>
						<c:if test="${vehicleComments == null}">
							<%-- Removing need to instantiate bean if vehicleMgr already exists in session --%>
							<c:if test="${vehicleMgr == null }">
								<jsp:useBean id="vehicleMgr" class="com.youdrive.helpers.VehicleDAO" scope="session" />
							</c:if>
							<%-- set variable to hold the comments associated with the vehicle --%>
							<c:set var="vehicleComments" value="${vehicleMgr.getVehicleComments(vehicle.id) }" scope="session" />
						</c:if>			
						
						<jsp:useBean id="userMgr" class="com.youdrive.helpers.UserDAO" scope="session" />
							
						<table border="1">
							<caption>Comments</caption>
							<tr>
								<th class="hidden">ID</th>
								<th><a href="#0" class="navSort">Date Created</a></th>
								<th><a href="#1" class="navSort">Comment</a></th>
								<th><a href="#2" class="navSort">Author</a></th>
							</tr>
							<c:forEach items="${vehicleComments}" var="comment" varStatus="status">
								<tr>
									<td class="hidden"><c:out value="${comment.id }"/></td>
									<td><fmt:formatDate type="date" value="${comment.createdOn}" /></td>
									<td><c:out value="${ comment.comment}" /></td>							
									<c:choose>
										<c:when test="${userMgr != null }">
											<c:set var="author" value="${userMgr.getUser(comment.author) }" />
										</c:when>
										<c:otherwise>
											<c:set var="author" value="${comment.author }" />
										</c:otherwise>
									</c:choose>
									<td><c:out value="${ author.username }" /></td>
								</tr>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						<p class="error">
							Vehicle not found.
						</p>
					</c:otherwise>
				</c:choose>
			</div>
		</c:when>
		<c:otherwise>
			<p class="error">Please <a href="login.jsp">login</a> to the system to view this page</p>
		</c:otherwise>
	</c:choose>
</body>
</html>