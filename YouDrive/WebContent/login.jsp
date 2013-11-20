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
			<button class="btn btn-lg btn-primary btn-block" type="submit" id="action" name="action" value="login">login</button>
			<button class="btn btn-lg btn-block" type="reset">Reset</button>
		</form>
	</div> 
	
	 <%--	<c:out value="${errorMessage }" />
      <form class="form-signin" id="userLogin" name="userLogin" action="UserManagement" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" class="form-control" id="username" name="username" placeholder="Username" required autofocus>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
		<!--   
		<label class="checkbox">
          <input type="checkbox" value="remember-me"> Remember me
        </label> -->
        <input type="hidden" id="action" name="action" value="login" /> 
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        <button class="btn btn-lg btn-block" type="reset">Reset</button>
      </form>--%>

</body>
</html>