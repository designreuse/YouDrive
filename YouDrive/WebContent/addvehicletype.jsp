<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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