<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/homepage.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/signin.css">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<title>Login to YouDrive!</title>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="logo">YouDrive</div>
	
	<div class="login">
		<p class="error"><c:out value="${errorMessage }" /></p>
		<form class="form-signin" id="userLogin" name="userLogin" action="UserManagement" method="post">
			<label for="username" >Username:</label> 
			<input required type="text"	id="username" name="username" /><br /> 
			<label for="password">Password:</label>
			<input required type="password" id="password" name="password" /><br />
			<input type="hidden" id="action" name="action" value="login" /> 
			<button class="btn btn-lg btn-primary btn-block" type="submit" id="action" name="action" value="login">Login</button>
			<button class="btn btn-lg btn-block" type="reset">Reset</button>
			<button class="btn btn-lg btn-default btn-block" onclick="window.location.replace('registration_page1.jsp')">Register</button>
		</form>
	</div>
</body>
</html>