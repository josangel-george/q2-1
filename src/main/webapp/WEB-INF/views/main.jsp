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
<title>Main</title>
<!-- 	<link href='https://fonts.googleapis.com/css?family=Roboto:400,300,500,700,400italic,300italic&subset=latin,greek,cyrillic-ext,greek-ext,latin-ext,cyrillic,vietnamese' rel='stylesheet' type='text/css'> -->
	<link rel="stylesheet" href="<c:url value='/resources/css/material.css'/>">
	<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
	<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
	<script src="<c:url value='/resources/js/jquery.countdown.js'/>"></script>
	<script src="<c:url value='/resources/js/material.js'/>"></script>
	<script src="<c:url value='/resources/js/script.js'/>"></script>
	<!-- Latest compiled and minified JavaScript -->
</head>
<body>

	<div class="bg"></div>
	<!-- Common code -->
	<header>
		<a class="navbar-brand" href="#">Mytrah Assesment Test</a>
		<input type="hidden" value="${timeRemaining}" id="timeRemaining"/>
		<div id="timer-outer">Time Remaining: <p id="timer"></p></div>
		<a	href="<c:url value='/finalize'/>"
			 class="mdl-button mdl-js-button mdl-js-ripple-effect exam-finalize">
		  SUBMIT
		</a>
	</header>
	
	<div class="body">
		<!-- Messages from server -->
		<div class="exam-header">
			<div class="info inline mdl-card mdl-shadow--2dp">
				<div>
					<p class="inline">Name: <strong class="blue-text hilight">${candidateName}</strong></p>
					<p class="inline">ID: <strong class="blue-text hilight">${candidateId}</strong></p>
					<p class="inline">Stream: <strong class="blue-text hilight">${candidateStream}</strong></p>
				</div>
				<c:if test="${fn:length(msg) gt 0 }">
					<div class="">
						<p class="inline msg">${msg}</p>
					</div>
				</c:if>
				<div class="attempt-summary">
					Summary 
					<c:forEach items="${attemptSlice}" var="attempt">
					<div class="attempt-summary-entry" id="attemptSummary_${attempt.key}">
						<p>${attempt.key}:</p> 
						<strong class="blue-text hilight">${attempt.value}</strong>
					</div>
					</c:forEach>
				</div>
			</div>
			
			<!-- Pagination -->
			<nav class="pull-right inline page-nav">
			  <ul class="pagination">
			    <li>
			      <div class="mdl-tooltip" for="prevPage">Previous Page</div>
			      <c:if test="${currentPage > 1 }">
				      <a href="<c:url value='/exam/${currentPage - 1}'/>" 
				      			id="prevPage" aria-label="Previous">
				        <span aria-hidden="true">&lt;</span>
				      </a>
			      </c:if>
			    </li>
			    <c:forEach begin="1" end="${pageCount}" var="page">
			    	<li class="${currentPage == page? 'active': ''}">
			    		<a href="<c:url value='/exam/${page}'/>">${page}</a></li>
			    </c:forEach>
			    <li>
			       <div class="mdl-tooltip" for="nextPage">Next Page</div>
			       <c:if test="${currentPage < pageCount}">
				      <a href="<c:url value='/exam/${currentPage + 1}'/>" 
				      			id="nextPage" aria-label="Next">
				        <span aria-hidden="true">&gt;</span>
				      </a>
				   </c:if>
			    </li>
			  </ul>
			</nav>
		</div>
		
		<div class="question-body">
			<c:forEach items="${questions}" var="question" varStatus="qCtr">
			
			<div class="question main-card mdl-grid mdl-card mdl-shadow--2dp">
				<p class="mdl-cell  mdl-cell--1-col" id="saveStatus_${question.key}"></p>
				<div class="mdl-cell">
					<div class="question-text ">
						<p><strong>${question.key}</strong>. ${question.value.text}</p>
						<c:if test="${fn:length(question.value.image) gt 1}">
							<img class="question-image"
								src="data:image/jpeg;base64,${question.value.image}" width="200" height="200">
						</c:if>
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
			<div class="main-card bottom-nav mdl-grid mdl-card">
				
				<a class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect"
					href="<c:url value='/exam/${currentPage <= 1 ? 1 : currentPage - 1 }'/>"
					${currentPage <= 1? 'disabled="disabled"' : ''}>
					&lt;  Previous Page
				</a>
				
				<a class="mdl-button mdl-js-button mdl-button--raised pull-right mdl-js-ripple-effect"
					href="<c:url value='/exam/${currentPage >= pageCount? pageCount : currentPage + 1}'/>"
					${currentPage >= pageCount? 'disabled="disabled"' : ''}>
				  	Next Page  &gt;
				</a>
			</div>
		</div>
	</div>

</body>
</html>