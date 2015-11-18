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
<!-- <link href='https://fonts.googleapis.com/css?family=Roboto:400,300,500,700,400italic,300italic&subset=latin,greek,cyrillic-ext,greek-ext,latin-ext,cyrillic,vietnamese' rel='stylesheet' type='text/css'> -->
<link rel="stylesheet" href="<c:url value='/resources/css/material.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/js/material.js'/>"></script>
<script src="<c:url value='/resources/js/script.js'/>"></script>

</head>
<body class="registration-page">

	<div class="bg"></div>
	
	<!-- Common code -->
	<header>
		<a class="navbar-brand" href="#">Mytrah Assesment Test</a>
	</header>

	<form:form method="post" commandName="candidate" modelAttribute="candidate"
		id="registerCandidate" class = "form bg-transparent body mdl-card mdl-shadow--2dp">
		
		
		<div class="mdl-card__title mdl-card--border">
		    <h2 class="mdl-card__title-text">Registration</h2>
		</div>
  
		<div class="section-center mdl-grid">
			<div class="mdl-textfield mdl-js-textfield  block mdl-cell mdl-cell--12-col-desktop">
			  <form:input path="name" class="mdl-textfield__input" required="required"/>
		      <label class="mdl-textfield__label" for="name">Name...</label>
		      <div class="mdl-tooltip" for="name">Please enter your full name.</div>
		    </div>
		    <div class="mdl-textfield mdl-js-textfield  block mdl-cell mdl-cell--12-col-desktop">
		      <form:input path="candidateId" class="mdl-textfield__input" required="required"/>
		      <label class="mdl-textfield__label" for="candidateId">College ID #</label>
		      <div class="mdl-tooltip" for="candidateId">Please enter College ID #.</div>
		    </div>
		    <div class="mdl-textfield mdl-js-textfield  block mdl-cell mdl-cell--12-col-desktop">
		      <form:input type="date" path="dob" required="required" class="red"/>
		      <div class="mdl-tooltip" for="dob">Please select Date of birth [MM-DD-YYYY]</div>
		    </div>
		    <div class="mdl-textfield mdl-js-textfield  block mdl-cell mdl-cell--12-col-desktop">
				<form:select path="originalStream" required="required" class="red">
					<form:option value="" disabled="disabled"> -- Select Stream -- </form:option>
					<form:options items="${streams}"></form:options>
				</form:select>
				<div class="mdl-tooltip" for="stream">Please select your stream.</div>
			</div>
			<div class="block mdl-cell mdl-cell--12-col-desktop" id="gender_select">
				<label class="mdl-radio mdl-js-radio" for="gender_m">
                  <form:radiobutton path="gender" value="M" required="required" 
                  			id="gender_m" class="mdl-radio__button"/>
				  <span class="mdl-radio__label">Male</span>
				</label>
				<label class="mdl-radio mdl-js-radio" for="gender_f">
					<form:radiobutton path="gender" value="F" required="required" 
							id="gender_f" class="mdl-radio__button"/>
					<span class="mdl-radio__label">Female</span>
				</label>
				<div class="mdl-tooltip" for="gender_select">Please select your gender.</div>
			</div>
		<input type="submit" value="Register" 
			class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--raised mdl-button--colored mdl-cell mdl-cell--12-col-desktop"/>
		<p class="register-msg">${msg}</p>
		</div>
	</form:form>
	
	<div>
		<pre class="instrictions">
		Instructions
			- ID should match the exact ID of candidate. else test will be render invalid.
			- While leaving the hall ensure you SUBMIT the test (SUBMIT button is on top right).
			- Once registered. User ID will be valid for 50 minutes.
			
		Question Pattern
			- Total 50 questions. [No negative marking]
			- First 25 Questions based on 'Aptitude'
			- Next 5 from 'General Knowledge'
			- Remaining 20 based on your stream
			- Separate weightage will given for each section.
		</pre> 
	</div>
</body>
</html>