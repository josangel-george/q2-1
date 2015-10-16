<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Question Add</title>
<link rel="stylesheet" href="<c:url value='/resources/css/material.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
<script src="<c:url value='/resources/js/material.js'/>"></script>
<script src="<c:url value='/resources/js/script.js'/>"></script></head>
<body>

Add question

	<p class="msg">${msg}</p>
	
	<form:form method="post" commandName="question" modelAttribute="question"
		id="adminLogin" class="form">
		<label>ID: * <form:input path="questionId" required="required"/></label>
		<label>Text: * <form:textarea path="text" required="required"/></label>
		<label>Category: * <form:select path="category" required="required">
				<form:option value=""> -- Select --</form:option>
				<form:options items="${category}"></form:options>
			</form:select></label>
		<label>Option A: *<form:input path="options[0]" required="required"/></label>
		<label>Option B: *<form:input path="options[1]" required="required"/></label>
		<label>Option C: *<form:input path="options[2]" required="required"/></label>
		<label>Option D: *<form:input path="options[3]" required="required"/></label>
		<label>Option E: <form:input path="options[4]"/></label>
		<label>Answer: *<form:select path="answer" required="required">
				<form:option value="A">A</form:option>
				<form:option value="B">B</form:option>
				<form:option value="C">C</form:option>
				<form:option value="D">D</form:option>
				<form:option value="E">E</form:option>
			</form:select></label>
		<input type="submit"/>
	</form:form>
	
</body>
</html>