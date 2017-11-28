<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>edit student</title>
</head>
<body>
	<h1>edit student</h1>
	<hr>
	<form action="updateStudentById" method="post">
		<input type="hidden" name="id" value="${stuData.id }">
		<spring:message code="student.form.username"/><input type="text" name="name" value="${stuData.name }"><br>
		<spring:message code="student.form.clazz"/><input type="text" name="clazz" value="${stuData.clazz }"><br>
		<spring:message code="student.form.birthday"/><input name="birthday" value="${stuData.birthday }"><br>
		<input type="submit" value='<spring:message code="student.form.update"/>'><br>
	</form>
</body>
</html>