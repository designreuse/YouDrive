<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/homepage.css">
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript">
	$(function() {
		$( "#lastServiced" ).datepicker();
  	});
  
  	function setbg(color){
  		document.getElementById("comment").style.background=color
  	}
</script>
<title>Add Vehicle</title>
</head>
<body>
<h3>Add Vehicle</h3>
	<jsp:useBean id="locationDAO" class="com.youdrive.helpers.LocationDAO" scope="application"/>
	<jsp:useBean id="vehicleTypeDAO" class="com.youdrive.helpers.VehicleTypeDAO" scope="application"/>
	<p class="error"><c:out value="${errorMessage }" /></p>
	<form id="addVehicle" name="addVehicle" action="VehicleManagement" method="post">
		<label for="make">Make:</label> 
		<input name="make" id="make" type="text" /><br /> 
		<label for="model">Model:</label> 
		<input name="model" id="model" type="text" /><br /> 
		<label for="year">Year:</label> 
		<input name="year"	id="year" type="text" /><br /> 
		<label for="tag">Vehicle Tag:</label> 
		<input name="tag" id="tag" type="text" /><br />
		<label for="mileage">Vehicle Mileage:</label> 
		<input name="mileage" id="mileage" type="text" /><br />
		<label for="lastServiced">Last Serviced Date:</label> 
		<input name="lastServiced" id="lastServiced" type="text" /><br />
		<label for="vehicleType">Vehicle Type:</label> 
		<select name="vehicleType">
		<c:forEach items="${vehicleTypeDAO.getAllVehicleTypes()}" var="vehicleType" varStatus="status">
			<option value="<c:out value="${ vehicleType.id }" />"> <c:out value="${vehicleType.type }" /></td>				
		</c:forEach>
		</select><br/>
		<label for="assignedLocation">Vehicle Location:</label> 
		<select name="vehicleLocation">
		<c:forEach items="${locationDAO.getAllLocations()}" var="location" varStatus="status">
			<option value="<c:out value="${ location.id }" />"> <c:out value="${location.name }" /></td>				
		</c:forEach>
		</select><br/>
		<label for="comment">Enter Comments:</label>
		<textarea name="comment" id="comment" onfocus="this.value=''; setbg('#e5fff3');" onblur="setbg('white')"></textarea><br/>
		<input type="hidden" name="action" id="addVehicle" value="addVehicle"/>
		<input type="submit" value="Submit"/>
		<input type="reset" value="Reset" />

	</form>
</body>
</html>