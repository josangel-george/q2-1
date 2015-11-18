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
<!-- <link href='https://fonts.googleapis.com/css?family=Roboto:400,300,500,700,400italic,300italic&subset=latin,greek,cyrillic-ext,greek-ext,latin-ext,cyrillic,vietnamese' rel='stylesheet' type='text/css'> -->
<link rel="stylesheet" href="<c:url value='/resources/css/material.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/js/material.js'/>"></script>
<script src="<c:url value='/resources/js/script.js'/>"></script>
</head>
<body>

	<p class="msg block">${msg}</p>
	<p class="head block">View Question</p>

	<div class="question mdl-grid mdl-card mdl-shadow--2dp">
	<p class="mdl-cell  mdl-cell--1-col" id="saveStatus_1"></p>
	<div class="mdl-cell">
		<div class="question-text ">
			<p><strong>1</strong>. ${question.text}</p>
			<c:if test="${fn:length(question.image) gt 1}">
				<img class="question-image"
					src="data:image/jpeg;base64,${question.image}" width="200" height="200">
			</c:if>
		</div>
		<div class="question-options">
			<c:set var="prevSelOpt" value="${optionSelected[1]}"/>
			<c:forEach items="${question.options}" var="option" varStatus="oCtr">
			<label class="question-option block mdl-radio mdl-js-radio" 
					for="option_${1}_${oCtr.count}">
					
				<input type="radio" name="option_1" value="${option}"
					id="option_1_${oCtr.count}"
					class="mdl-radio__button"
					${prevSelOpt eq oCtr.count ? 'checked="checked"' : ''}/>
					
					<span class="mdl-radio__label">
						<c:if test="${oCtr.count == 1}">A. </c:if>
						<c:if test="${oCtr.count == 2}">B. </c:if>
						<c:if test="${oCtr.count == 3}">C. </c:if>
						<c:if test="${oCtr.count == 4}">D. </c:if>
						<c:if test="${oCtr.count == 5}">E. </c:if>
						${option}
					</span>
			</label>
			</c:forEach><br>
			<p>ID: ${question.questionId}</p><br>
			<p>Answer: ${question.answer}</p><br>
			<p>Category: ${question.category}</p>
		</div>
	</div>
</div>
	
</body>
</html>