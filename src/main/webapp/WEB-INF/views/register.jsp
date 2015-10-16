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
<title>Register</title>
<link rel="stylesheet" href="<c:url value='/resources/css/material.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
<script src="<c:url value='/resources/js/material.js'/>"></script>
<script src="<c:url value='/resources/js/script.js'/>"></script>

</head>
<body>
	Register
	<form:form class = "form" method="post" commandName="candidate" modelAttribute="candidate"
		id="registerCandidate">
		<label>User Name:
			<form:input path="name" required="required"/></label>
			
		<label>User ID:
			<form:input path="candidateId" required="required"/></label>
			
		<label>Birth Date:
			<form:input type="date" path="dob" required="required"/></label>
			
		<label>Stream: 
			<form:select path="stream" required="required">
			<form:option value=""> -- Select Stream -- </form:option>
			<form:options items="${streams}"></form:options>
		</form:select></label>
		<input type="submit"/>
	</form:form>
</body>
</html>