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
<title>Edit Vehicle Type</title>
</head>
<body>
<h3>Edit Vehicle Type</h3>
<p class="error"><c:out value="${errorMessage }"/></p>
	<form method="post" action="VehicleTypeManagement" name="editVehicleType" id="editVehicleType">
		<c:if test="${vehicleType != null }">
			<label for="vehicleTypeName">Vehicle Type:</label>
			<input id="vehicleTypeName" name="vehicleTypeName" value="<c:out value="${vehicleType.type }"/>" />
			<br />
			<label for="hourlyPrice">Hourly Price:</label>
			<input id="hourlyPrice" name="hourlyPrice" value="${vehicleType.hourlyPrice}" />
			<br />
			<label for="dailyPrice">Daily Price:</label>
			<input id="dailyPrice" name="dailyPrice" value="${vehicleType.dailyPrice}" />
			<br />
			<input type="hidden" name="action" id="action" value="editVehicleType" />
			<input type="hidden" name="vehicleTypeID" id="vehicleTypeID" value="<c:out value="${vehicleType.id}" />" />
			<input type="submit" value="Update" />
			<input type="button"
				onclick="window.location.replace('managevehicles.jsp')"
				value="Cancel" />
		</c:if>
	</form>
</body>
</html>