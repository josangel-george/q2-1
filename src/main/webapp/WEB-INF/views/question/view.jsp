<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Question View</title>
<link rel="stylesheet" href="<c:url value='/resources/css/material.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
<script src="<c:url value='/resources/js/material.js'/>"></script>
<script src="<c:url value='/resources/js/script.js'/>"></script>
</head>
<body>

	<p class="msg">${msg}</p>
	View Question
	
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