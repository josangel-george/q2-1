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
<title>Admin Candidate Home</title>
<!-- <link href='https://fonts.googleapis.com/css?family=Roboto:400,300,500,700,400italic,300italic&subset=latin,greek,cyrillic-ext,greek-ext,latin-ext,cyrillic,vietnamese' rel='stylesheet' type='text/css'> -->
<link rel="stylesheet" href="<c:url value='/resources/css/material.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/js/material.js'/>"></script>
<script src="<c:url value='/resources/js/script.js'/>"></script>
</head>
<body>
	
	<a href="<c:url value='/admin/home'/>">Admin Home</a>
	<a href="<c:url value='/admin/question/'/>">Question Home</a>
	<a href="<c:url value='/admin/candidate/'/>" class="active">Candidate Home</a>

	<table class="border">
		<thead>
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>DOB</th>
				<th>Gender</th>
				<th>Stream</th>
				<th>Completed</th>
				<th>Feedback</th>
				<th>login IPs</th>
				<th>login times</th>
				<th>active start time</th>
				<th>Correct Answers</th>
				<th>correct ANswer per catogory</th>
				<th>question Sequecne</th>
				<th>attempts</th>
			</tr>	
		</thead>
		<tbody>
			<c:forEach items="${candidates}" var="c">
			<tr>
				<td>${c.candidateId}</td>
				<td>${c.name}</td>
				<td>${c.dob}</td>
				<td>${c.gender}</td>
				<td>${c.stream}</td>
				<td>${c.completed}</td>
				<td>${c.feedback}</td>
				<td>${c.candidateIPs}</td>
				<td>${c.loginTimes}</td>
				<td>${c.activeStartTime}</td>
				<td>${c.correctAnswers}</td>
				<td>${c.correctAnswerPerCategory}</td>
				<td>${c.questionSequence}</td>
				<td>${c.attempts}</td>
			</tr>				
			</c:forEach>
		</tbody>
	</table>
	
</body>
</html>