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
<title>Login to YouDrive!</title>
</head>
<body>
		<form id="userLogin" name="userLogin" action="UserManagement" method="post">
		<label for="username">Username:</label>
		<input required type="text" id="username" name="username"/><br/>
		<label for="password">Password:</label>
		<input required type="text" id="password" name="password"/><br/>
		<input type="hidden" id="action" name="action" value="login"/>
		<input type="submit" value="Submit"/>
		<input type="reset" value="Reset"/>
		</form>

</body>
</html>