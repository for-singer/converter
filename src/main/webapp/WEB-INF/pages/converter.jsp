<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Converter - Converter</title>
        </head>
        <body>
            
            <c:choose>
                <c:when test="${!hasError}">
                    <h3>Amount: ${result}</h3>
                </c:when>
               <c:otherwise>
                   <h3>Error ${result}</h3>
               </c:otherwise>
            </c:choose>
            
        </body>
    </html>

