<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Vehicle Type</title>
</head>
<body>
<form id="addVehicleType" name="addVehicleType" action="VehicleManagement" method="post">
<label for="vehicleTypeName">Vehicle Type Name:</label>
<input id="vehicleTypeName" name="vehicleTypeName" type="text"/>
<label for="hourlyPrice">Hourly Price:</label>
<input id="hourlyPrice" name="hourlyPrice" type="text" />
<label for="dailyPrice">Daily Price:</label>
<input id="dailyPrice" name="dailyPrice" type="text"/>
<input type="hidden" name="action" id="action" value="addVehicleType"/>
<input type="submit" value="Submit" />
<input type="reset" value="Reset"/>
</form>

</body>
</html>