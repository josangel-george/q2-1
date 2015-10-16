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
<title>Question Add</title>
<link rel="stylesheet" href="<c:url value='/resources/css/material.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
<script src="<c:url value='/resources/js/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/js/material.js'/>"></script>
<script src="<c:url value='/resources/js/script.js'/>"></script></head>
<body>

Add question

	<p class="msg">${msg}</p>
	
	<form:form  commandName="question" modelAttribute="question"
		method="post" id="adminLogin" class="form" enctype="multipart/form-data" 
			action="/admin/question/add">
			
		<label>ID: * <form:input path="questionId" required="required"/></label>
		<label>Text: * <form:textarea path="text" required="required"></form:textarea></label>
		
		<label>Category: * <form:select path="category" required="required">
				<option value=""> -- Select --</option>
				<c:forEach items="${categories}" var="category">
					<option value="${category}">${category}</option>
				</c:forEach>
			</form:select></label>
		
		<label>Option A: *<form:input path="options[0]" required="required"/></label>
		<label>Option B: *<form:input path="options[1]" required="required"/></label>
		<label>Option C: *<form:input path="options[2]" required="required"/></label>
		<label>Option D: *<form:input path="options[3]" required="required"/></label>
		<label>Option E: <form:input path="options[4]"/></label>
		
		<label>Answer: *<form:select path="answer" required="required">
				<option value="A">A</option>
				<option value="B">B</option>
				<option value="C">C</option>
				<option value="D">D</option>
				<option value="E">E</option>
			</form:select></label>
		
		<label>Add Image : 
			<input type="file" name="img-file" id="inputLogoImage"
				accept=".png,.jpg,.gif,.jpeg,.bmp,.tiff,.webp" 
				class="${pdfError}"/>
		</label>
		
		<input type="submit"/>
		<br><br>
		<pre class="">&lt; &gt; &le; &ge; &plusmn; &ne; &divide; &times; &minus;
&radic; &#8731; &#8732; &#8725; &#8734; &#8756;
&sup1; &sup2; &sup3; &#8308; &#8305; &#8319; &#8336; &#8337;
&#8338; &#7525; &#7526; &#7527; &#7529; 
&#956; &#963; &#967; &#8721; &#8719; &#8720;
&#181; &#176; &#8747; &#8710; &#8704; &#8745; &#8746;
&#8712; &#8741; &#8739; &#8736; &#8735;
&#8869; &#8800; &#8776; &#8764; &#8773; &#8816;
&#8817; &#8727; </pre>
	</form:form>
	
</body>
</html>