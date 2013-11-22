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
<title>Loggin out of YouDrive System</title>
</head>
<body>
	<div class="logo">YouDrive</div>
	<c:choose>
		<c:when test="${ loggedInUser != null }">
			<c:remove var="loggedInUser"/>
			<c:remove var="errorMessage"/>
			You are now logged out! Go <a href="http://localhost:8080/YouDrive/">home</a> or <a href="http://localhost:8080/YouDrive/login.jsp">Login here</a>.
		</c:when>
		<c:otherwise>
			Already logged out! <a href="http://localhost:8080/YouDrive/login.jsp">Login here</a>
		</c:otherwise>
	</c:choose>

</body>
</html>