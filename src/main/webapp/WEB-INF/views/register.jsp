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
<title>Register</title>
<link rel="stylesheet" href="<c:url value='/resources/css/material.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/js/material.js'/>"></script>
<script src="<c:url value='/resources/js/script.js'/>"></script>

</head>
<body>
	<!-- Common code -->
	<header>
		<a class="navbar-brand" href="#">Mytrah</a>
	</header>

	<form:form class = "form body" method="post" commandName="candidate" modelAttribute="candidate"
		id="registerCandidate">
		
		<div class="mdl-grid register-form">
			<div class="mdl-cell mdl-cell--4-col">
				<label for="name">User Name:</label>
			</div>
			<div class="mdl-cell mdl-cell--4-col">
				<form:input path="name" required="required" class="mdl-textfield__input"/>
				<label class="mdl-textfield__label" for="name">Name...</label>
			</div>
			
			<div class="mdl-cell mdl-cell--4-col">
				<label for="candidateId" class="mdl-cell mdl-cell--4-col">User ID:</label>
			</div>
			<div class="mdl-cell mdl-cell--4-col ">
				<form:input path="candidateId" required="required"
					class="mdl-cell mdl-cell--4-col"/>
			</div>
			
			<div class="mdl-cell mdl-cell--4-col">
				<label for="dob" class="mdl-cell mdl-cell--4-col">Birth Date:</label>
			</div>
			<div class="mdl-cell mdl-cell--4-col ">
				<form:input type="date" path="dob" required="required"
					class="mdl-cell mdl-cell--4-col"/>
			</div>
			
			<div class="mdl-cell mdl-cell--4-col">
				<label for="stream" class="mdl-cell mdl-cell--4-col">Stream:</label> 
			</div>
			<div class="mdl-cell mdl-cell--4-col ">
				<form:select path="stream" required="required"
					class="mdl-cell mdl-cell--4-col">
				<form:option value=""> -- Select Stream -- </form:option>
				<form:options items="${streams}"></form:options>
			</form:select>
			</div>
			<input class="mdl-cell mdl-cell--2-col" type="submit"/>
		</div>
	</form:form>
	
	<div>
		<pre class="w-100 disclaimer">
		Disclaimer
			- ID should match the exact ID of candidate. else test will be render invalid.
			- While leaving the hall ensure that session is finalized.
		</pre> 
	</div>
</body>
</html>