<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Converter - Add Data</title>
        </head>
        <body>
            
            <h1>AddData at ${request} has been added</h1>
            
            <c:forEach items="${list}" var="item">
                <c:out value="${item}"/>
                <br>
            </c:forEach>
            
        </body>
    </html>

