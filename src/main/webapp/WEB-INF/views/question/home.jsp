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
<title>Question Home</title>
<link rel="stylesheet" href="<c:url value='/resources/css/material.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
<script src="<c:url value='/resources/js/material.js'/>"></script>
<script src="<c:url value='/resources/js/script.js'/>"></script>
</head>
<body>

	<a href="<c:url value='/admin/home'/>">Admin Home</a>
	<a href="<c:url value='/admin/question/'/>" class="active">Question Home</a>
	<a href="<c:url value='/admin/question/add'/>">Add Question</a>

	<p class="msg">${msg}</p>
	
	<br><br>
	<table class="border">
		<colgroup></colgroup>
		<thead>
			<tr>
				<th>Question ID</th>
				<th>Text</th>
				<th>Category</th>
				<th>Answer</th>
				<th>Option A</th>
				<th>Option B</th>
				<th>Option C</th>
				<th>Option D</th>
				<th>Option E</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${questions}" var="q" varStatus="counter">
				<tr>
					<td>
						<a href="<c:url value='${q.questionId}'/>">
							${q.questionId}
						</a>
					</td>
					<td>${q.text}</td>
					<td>${q.category}</td>
					<td>${q.answer}</td>
					<td>${q.options[0]}</td>
					<td>${q.options[1]}</td>
					<td>${q.options[2]}</td>
					<td>${q.options[3]}</td>
					<td>${q.options[4]}</td>
					<td>
						<a href="<c:url value='delete/${q.questionId}'/>">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>