<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Rental Location</title>
</head>
<body>
<h3>Add Rental Location</h3>
<form id="addLocation" name="addLocation" action="LocationManagement" method="post">
<label for="locationName">Location Name:</label>
<input id="locationName" name="locationName" type="text"/><br/>
<label for="locationAddress">Address:</label>
<input id="locationAddress" name="locationAddress" type="text" /><br/>
<label for="capacity">Capacity:</label>
<input id="capacity" name="capacity" type="text"/><br/>
<input type="hidden" id="action" name="action" value="addLocation" />
<input type="submit" value="Submit"/>
<input type="reset" value="Reset"/>
</form>

</body>
</html>