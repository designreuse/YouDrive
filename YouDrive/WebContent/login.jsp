<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/homepage.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<title>Login to YouDrive!</title>
</head>
<body>
	<div class="logo">YouDrive</div>
	<div class="underlogo">
		<form id="userLogin" name="userLogin" action="UserManagement" action="post">
		<label for="username">Username:</label>
		<input type="text" id="username" name="username"/>
		<input type="submit" value="Submit"/>
		<input type="reset" value="Reset"/>
		</form>
	</div>
</body>
</html>