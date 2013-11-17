<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	
<jsp:useBean id="locationMgr" class="com.youdrive.helpers.LocationDAO" scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Locations</title>
</head>
<body>
	<h3>Manage Locations</h3>


	<table border="1">
		<tr>
			<th>Location Name</th>
			<th>Location Address</th>
			<th>Location Capacity</th>
			<th>Edit</th>
		</tr>
		<c:forEach items="${locationMgr.getAllLocations()}" var="location"
			varStatus="status">
			<tr>
				<td><c:out value="${ location.name }" /></td>
				<td><c:out value="${ location.address}" /></td>
				<td><c:out value="${ location.capacity}" /></td>
				<c:url value="LocationManagement" var="url">
					<c:param name="locationID" value="${location.id}"/>
				</c:url>
				<td><a	href="<c:out value="${url }" />">Edit</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>