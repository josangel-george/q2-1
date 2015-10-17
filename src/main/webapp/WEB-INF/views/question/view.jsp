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
<title>Question View</title>
<link href='https://fonts.googleapis.com/css?family=Roboto:400,300,500,700,400italic,300italic&subset=latin,greek,cyrillic-ext,greek-ext,latin-ext,cyrillic,vietnamese' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="<c:url value='/resources/css/material.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/js/material.js'/>"></script>
<script src="<c:url value='/resources/js/script.js'/>"></script>
</head>
<body>

	<p class="msg">${msg}</p>
	<p class="head">View Question</p>
	
	<p>ID		: ${question.id}</p>
	<p>Question ID: ${question.questionId }</p>
	<p>Text	: ${question.text}</p>
	<p>Category: ${question.category}</p>
	<p>Answer  : ${question.answer}</p>
	<p>Option A: ${question.options[0]}</p>
	<p>Option B: ${question.options[1]}</p>
	<p>Option C: ${question.options[2]}</p>
	<p>Option D: ${question.options[3]}</p>
	<p>Option E: ${question.options[4]}</p>
	
</body>
</html>