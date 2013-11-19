<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="membershipMgr" class="com.youdrive.helpers.MembershipDAO" scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/homepage.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<title>Manage Memberships</title>
</head>
<body>
	<h3>Manage Memberships</h3>
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
	</ol>
	<div class="body">
		<p class="error">
			<c:out value="${errorMessage }" />
		</p>
		<table border="1">
			<caption>Membership Levels</caption>
			<tr>
				<th class="hidden">ID</th>
				<th>Name</th>
				<th>Price</th>
				<th>Duration (months)</th>
				<th>Edit</th>
			</tr>
			<c:forEach items="${membershipMgr.getAllMemberships()}" var="membership" varStatus="status">
				<tr>
					<td class="hidden"><c:out value="${ membership.id }" /></td>
					<td><c:out value="${ membership.name }" /></td>
					<td><fmt:formatNumber value="${ membership.price}" type="currency" /></td>
					<td><c:out value="${ membership.duration}" /></td>
					<c:url value="MembershipManagement" var="url">
						<c:param name="membershipID" value="${membership.id}" />
					</c:url>
					<td><a href="<c:out value="${url }" />">Edit</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>