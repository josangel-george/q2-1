<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Stats</title>
<link href='https://fonts.googleapis.com/css?family=Roboto:400,300,500,700,400italic,300italic&subset=latin,greek,cyrillic-ext,greek-ext,latin-ext,cyrillic,vietnamese' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="<c:url value='/resources/css/material.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/js/material.js'/>"></script>
<script src="<c:url value='/resources/js/script.js'/>"></script></head>
<body>

	<a href="<c:url value='/admin/home'/>">Admin Home</a>
	<a href="<c:url value='/admin/stats'/>" class="active">Admin Stats</a>
	<a href="<c:url value='/admin/question/'/>">Question Home</a>
	<a href="download">
		Download Stats
	</a>
	
	<p class="msg">${msg}</p>
	
	
	<br><br>
	<pre>${stats}</pre>

	<br><br>
	<pre>${candidateToString}</pre>
</body>
</html>