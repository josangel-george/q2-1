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

	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	
	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">

	
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>

	<!-- Common code -->
	<header>
		<nav class="navbar navbar-default navbar-fixed-top">
		  <div class="container-fluid">
		     <a class="navbar-brand" href="#">Brand</a>
		     <div class="pull-right">
		     </div>
		  </div>
		</nav>
	</header>
	
	<div class="body">
		<!-- Messages from server -->
		<div class="message inline">
			<p class="inline">${msg}</p>
			<p class="inline">Name: <strong>${candidateName}</strong></p>
			<p class="inline">ID: <strong>${candidateId}</strong></p>
			<p class="inline">Stream: <strong>${candidateStream}</strong></p>
		</div>
		
		<!-- Pagination -->
		<nav class="pull-right inline">
		  <ul class="pagination">
		    <li class="${currentPage == 1? 'disabled' : ''}">
		      <a href="#" aria-label="Previous">
		        <span aria-hidden="true">&laquo;</span>
		      </a>
		    </li>
		    <c:forEach begin="1" end="${pageCount}" var="page">
		    	<li class="${currentPage == page? 'active': ''}">
		    		<a href="<c:url value='/exam/${page}'/>">${page}</a></li>
		    </c:forEach>
		    <li class="${currentPage == 10? 'disabled' : ''}">
		      <a href="#" aria-label="Next">
		        <span aria-hidden="true">&raquo;</span>
		      </a>
		    </li>
		  </ul>
		</nav>
		
		<div class="question-body">
			<c:forEach items="${questions}" var="question" varStatus="qCtr">
				<p>${question.key}. ${question.value.text}</p>
				<c:forEach items="${question.value.options}" var="option" varStatus="oCtr">
					<input type="radio" name="option" value="${option}" class="exam-option"
						id="option_${question.key}_${qCtr.count}_${oCtr.count}">
						
						<c:if test="${oCtr.count == 1}">A. </c:if>
						<c:if test="${oCtr.count == 2}">B. </c:if>
						<c:if test="${oCtr.count == 3}">C. </c:if>
						<c:if test="${oCtr.count == 4}">D. </c:if>
						<c:if test="${oCtr.count == 5}">E. </c:if>
						${option}
					</input>
				</c:forEach>
			</c:forEach>
		</div>
	</div>

</body>
</html>