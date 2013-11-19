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
<title>Logout of YouDrive System</title>
</head>
<body>
	<c:choose>
		<c:when test="${ loggedInUser != null }">
			<c:remove var="loggedInUser"/>
			You are now logged out! Go <a href="http://localhost:8080/YouDrive/">home</a>.
		</c:when>
		<c:otherwise>
			Already logged out! <a href="http://localhost:8080/YouDrive/login.jsp">Login here</a>
		</c:otherwise>
	</c:choose>

</body>
</html>