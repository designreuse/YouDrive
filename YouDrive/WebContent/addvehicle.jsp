<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/homepage.css">
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
    <script>
  $(function() {
    $( "#lastServiced" ).datepicker();
  });
  
  function setbg(color)
  {
  document.getElementById("comment").style.background=color
  }
  </script>
<title>Add Vehicle</title>
</head>
<body>
	<jsp:useBean id="locationDAO" class="com.youdrive.helpers.LocationDAO" scope="session"/>
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
		<input name="lastServiced" id="lastServiced" type="date" /><br />
		<label for="vehicleType">Vehicle Type:</label> 
		<select name="vehicleType">
			<option value="1">Regular Car</option>
			<option value="2">Pickup Truck</option>
			<option value="3">Luxury Car</option>
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