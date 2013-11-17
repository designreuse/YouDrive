<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Customer/Admin</title>
</head>
<body>
		<form id="addUser" name="addUser" action="UserManagement" method="post">
		<label for="firstName">First Name:</label>
		<input required type="text" id="firstName" name="firstName"/><br/>
		<label for="lastName">Last Name:</label>
		<input required type="text" id="lastName" name="lastName"/><br/>
		<label for="email">Email Address:</label>
		<input required type="text" id="email" name="email"/><br/>
		<label for="username">Username:</label>
		<input required type="text" id="username" name="username"/><br/>
		<label for="password">Password:</label>
		<input required type="text" id="password" name="password"/><br/>
		<label for="isAdmin">Is Admin:</label>
		<input type="checkbox" id="isAdmin" name="isAdmin"/><br/>
		<input type="hidden" id="action" name="action" value="addUser1"/>
		<input type="submit" value="Next"/>
		<input type="reset" value="Reset"/>
		</form>
</body>
</html>