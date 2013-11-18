<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="userMgr" class="com.youdrive.helpers.UserDAO" scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/homepage.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<title>Manage Users</title>
</head>
<body>
	<h3>Manage Users</h3>
	<ol class="nav">
		<li><a href="addvehicle.jsp">Add Vehicle</a></li>
		<li><a href="addvehicletype.jsp">Add Vehicle Type</a></li>
		<li><a href="addlocation.jsp">Add Location</a></li>
		<li><a href="adduser.jsp">Add Admin User</a></li>
		<li><a href="managevehicles.jsp">Manage Vehicles</a></li>
		<li><a href="managevehicletypes.jsp">Manage Vehicle Types</a></li>
		<li><a href="managelocations.jsp">Manage Locations</a></li>
		<li><a href="manageusers.jsp">Manage Users</a></li>
	</ol>
<div class="body">
		<p class="error">
			<c:out value="${errorMessage }" />
		</p>
		<table border="1">
			<caption>All Users</caption>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Username</th>
				<th>Email</th>
				<th>Membership Expiration</th>
				<th>Is Admin</th>
				<th>Edit</th>
			</tr>
			<c:forEach items="${userMgr.getAllUsers()}" var="user"
				varStatus="status">
				<tr>
					<td><c:out value="${ user.id }" /></td>
					<td><c:out value="${ user.firstName}" /> <c:out value="${ user.lastName }" /></td>
					<td><c:out value="${ user.username}" /></td>
					<td><c:out value="${ user.email}" /></td>
					<td><fmt:formatDate type="date"  value="${user.memberExpiration}" /></td>
					<td>
						<c:choose>
						<c:when test="${ user.isAdmin() == null }">
							<input type="checkbox" disabled name="isAdmin" id="isAdmin"/>
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="isAdmin" id="isAdmin" disabled checked />
						</c:otherwise>
						</c:choose>
					</td>
					<c:url value="UserManagement" var="url">
						<c:param name="userID" value="${user.id}" />
					</c:url>
					<td><a href="<c:out value="${url }" />">Edit</a></td>
				</tr>
			</c:forEach>
		</table>
</div>
</body>
</html>