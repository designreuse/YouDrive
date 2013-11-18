<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="vehicleMgr" class="com.youdrive.helpers.VehicleDAO" scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/homepage.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<title>Manage Vehicles and Vehicle Types</title>
</head>
<body>
<h3>Manage Vehicles and Vehicle Types</h3>
	<table border="1">
		<tr>
			<th>Vehicle Type</th>
			<th>Hourly Price</th>
			<th>Daily Price</th>
			<th>Edit</th>
		</tr>
		<c:forEach items="${vehicleMgr.getAllVehicleTypes()}" var="vehicleType"
			varStatus="status">
			<tr>
				<td><c:out value="${ vehicleType.type }" /></td>
				<td><fmt:formatNumber value="${ vehicleType.hourlyPrice}" type="currency" /></td>
				<td><fmt:formatNumber value="${ vehicleType.dailyPrice}" type="currency" /></td>
				<c:url value="VehicleManagement" var="url">
					<c:param name="vehicleTypeID" value="${vehicleType.id}"/>
				</c:url>
				<td><a	href="<c:out value="${url }" />">Edit</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>