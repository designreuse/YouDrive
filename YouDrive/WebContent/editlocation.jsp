<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Location</title>
</head>
<body>
	<form method="post" action="LocationManagement" name="editLocation"
		id="editLocation">
		<c:if test="${location != null }">
			<label for="locationName">Location Name:</label>
			<input id="locationName" name="locationName"
				value="<c:out value="${location.name }"/>" />
			<br />
			<label for="locationAddress">Location Address:</label>
			<input id="locationAddress" name="locationAddress"
				value="<c:out value="${location.address }"/>" />
			<br />
			<label for="capacity">Location Capacity:</label>
			<input id="capacity" name="capacity"
				value="<c:out value="${location.capacity }"/>" />
			<br />
			<input type="hidden" name="action" id="action" value="editLocation" />
			<input type="hidden" name="locationID" id="locationID"
				value="<c:out value="${location.id}" />" />
			<input type="submit" value="Update" />
			<input type="button"
				onclick="window.location.replace('managelocations.jsp')"
				value="Cancel" />
		</c:if>
	</form>
</body>
</html>