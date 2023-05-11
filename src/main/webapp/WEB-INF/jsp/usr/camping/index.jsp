<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>
<h2>Input date you want to see</h2>
<h3>Example: 글램핑</h3>
<form method="POST" action="/api">
    <input type="text" name="글램핑" />
    <input type="submit" value="CLick"/>
</form>
</body>
</html>