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
<title>Main</title>

	<link rel="stylesheet" href="<c:url value='/resources/css/material.css'/>">
	<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
	<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
	<script src="<c:url value='/resources/js/material.js'/>"></script>
	<script src="<c:url value='/resources/js/script.js'/>"></script>
	<!-- Latest compiled and minified JavaScript -->
</head>
<body>

	<!-- Common code -->
	<header>
		<a class="navbar-brand" href="#">Mytrah</a>
	</header>
	
	<div class="body">
		<!-- Messages from server -->
		<div class="exam-header">
			<div class="info inline">
				<p class="inline">Name: <strong class="blue">${candidateName}</strong></p>
				<p class="inline">ID: <strong class="blue">${candidateId}</strong></p>
				<p class="inline">Stream: <strong class="blue">${candidateStream}</strong></p>
				<br><p class="inline">${msg}</p>
				
				<div class="attempt-summary">
					Summary 
					<c:forEach items="${attemptSlice}" var="attempt">
					<div class="attempt-summary-entry" id="attemptSummary_${attempt.key}">
						<p>${attempt.key}:</p> 
						<strong class="blue">${attempt.value}</strong>
					</div>
					</c:forEach>
				</div>
			</div>
			
			<!-- Pagination -->
			<nav class="pull-right inline page-nav">
			  <ul class="pagination">
			    <li>
			      <c:if test="${currentPage > 1 }">
				      <a href="<c:url value='/exam/${currentPage - 1}'/>" aria-label="Previous">
				        <span aria-hidden="true">&lt;</span>
				      </a>
			      </c:if>
			    </li>
			    <c:forEach begin="1" end="${pageCount}" var="page">
			    	<li class="${currentPage == page? 'active': ''}">
			    		<a href="<c:url value='/exam/${page}'/>">${page}</a></li>
			    </c:forEach>
			    <li>
			       <c:if test="${currentPage < 10}">
				      <a href="<c:url value='/exam/${currentPage + 1}'/>" aria-label="Next">
				        <span aria-hidden="true">&gt;</span>
				      </a>
				   </c:if>
			    </li>
			  </ul>
			</nav>
		</div>
		
		<div class="question-body">
			<c:forEach items="${questions}" var="question" varStatus="qCtr">
			
			<div class="question mdl-grid">
				<p class="mdl-cell  mdl-cell--1-col" id="saveStatus_${question.key}"></p>
				<div class="mdl-cell">
					<div class="question-text">
						<p>${question.key}. ${question.value.text}</p>
					</div>
					<div class="question-options">
						<c:set var="prevSelOpt" value="${optionSelected[question.key]}"/>
						<c:forEach items="${question.value.options}" var="option" varStatus="oCtr">
						<label class="question-option block mdl-radio mdl-js-radio" 
								for="option_${question.key}_${oCtr.count}">
								
							<input type="radio" name="option_${question.key}" value="${option}"
								id="option_${question.key}_${oCtr.count}"
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
						</c:forEach>
					</div>
				</div>
			</div>
			</c:forEach>
		</div>
	</div>

</body>
</html>