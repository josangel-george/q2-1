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
<title>Finalize Confirmation</title>
<link href='https://fonts.googleapis.com/css?family=Roboto:400,300,500,700,400italic,300italic&subset=latin,greek,cyrillic-ext,greek-ext,latin-ext,cyrillic,vietnamese' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="<c:url value='/resources/css/material.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/js/material.js'/>"></script>
<script src="<c:url value='/resources/js/script.js'/>"></script>
</head>
<body class="finishing">
	
	<div class="bg"></div>

	<header>
		<a class="navbar-brand" href="#">Mytrah Assesment Test</a>
	</header>
	
	Are you sure you want to finalize the test.
	<form:form class="body finalize-test">
		<pre class="danger block f-16">
${expiryMessage}
By clicking the "SUBMIT TEST" button you agree that your test is completed.
Upon submission, no more further updates will be allowed.</pre>

		<div class="mdl-textfield mdl-js-textfield feedback">
			<textarea class="mdl-textfield__input" type="text" name="feedback" 
						id="candidateFeedback"></textarea>
		    <label class="mdl-textfield__label" for="candidateFeedback">Kindly share your thoughts on the test.</label>
		</div>
  
		<input type="submit" value="Submit Test"
			class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent finalize-test-btn"/>
	</form:form>
	
</body>
</html>