<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="css/homepage.css">
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
	<title>Edit Membership</title>
</head>
<body>
	<h3>Edit Rental Location</h3>
	<c:if test="${loggedInUser != null }">
		<p class="userInfo">Hello, <c:out value="${loggedInUser.firstName }" /></p>
	</c:if>
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
	<div class="body">
		<p class="error">
			<c:out value="${errorMessage }" />
		</p>
		<c:choose>
			<c:when test="${loggedInUser != null && loggedInUser.isAdmin() }">
				<c:choose>
					<c:when test="${membership != null }">
						<form method="post" action="MembershipManagement" name="editMembership" id="editMembership">
								<label for="membershipLevel">Membership Level:</label>
								<input  required type="text"id="membershipLevel" name="membershipLevel"
									value="<c:out value="${membership.name }"/>" />
								<br />
								<label for="price">Price:</label>
								<input  required type="text" id="price" name="price"
									value="<c:out value="${membership.price }"/>" />
								<br />
								<label for="duration">Duration (months):</label>
								<input  required type="text" id="duration" name="duration"
									value="<c:out value="${membership.duration }"/>" />
								<br />
								<input type="hidden" name="action" id="action" value="editMembership" />
								<input type="hidden" name="membershipID" id="membershipID"
									value="<c:out value="${membership.id}" />" />
								<input type="submit" value="Update" />
								<input type="button"
									onclick="window.location.replace('managememberships.jsp')"
									value="Cancel" />
							
						</form>
					</c:when>
					<c:otherwise>
						<p class="error">Member object not found.</p>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<p class="error">Please <a href="login.jsp">login</a> as an admin to access this page.</p>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>