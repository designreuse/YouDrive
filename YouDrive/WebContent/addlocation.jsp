<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Rental Location</title>
</head>
<body>
<form id="addLocation" name="addLocation" action="LocationManagement" method="post">
<label for="locationName">Location Name:</label>
<input id="locationName" name="locationName" type="text"/>
<label for="locationAddress">Address:</label>
<input id="locationAddress" name="locationAddress" type="text" />
<label for="capacity">Capacity:</label>
<input id="action" name="action" value="addLocation" type="text"/>
<input type="hidden" name="action" id="action"/>
<input type="submit" value="Submit"/>
<input type="reset" value="Reset"/>
</form>

</body>
</html>