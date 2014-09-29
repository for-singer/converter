<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Converter - Exchange History</title>
        </head>
        <body>
            <h3>All records:</h3>
            <c:choose>
                <c:when test="${exchangeHistoryAll.size() > 0}">
                <table border="1">
                <tr>
                    <th>From currency</th>
                    <th>To currency</th>
                    <th>Amount</th>
                    <th>Trans date</th>
                </tr>
                <c:forEach items="${exchangeHistoryAll}" var="item">
                    <tr>
                    <td>${item.getFromCurrency().getName()}</td>
                    <td>${item.getToCurrency().getName()}</td>
                    <td>${item.getAmount()}</td>
                    <td>${item.getTransDate()}</td>
                    </tr>
                </c:forEach>
                </table>
                </c:when>
               <c:otherwise>
                   <p>Data is empty</p>
               </c:otherwise>
            </c:choose>
            
            <h3>Number Of transations for each from currency:</h3>
            <c:choose>
                <c:when test="${exchangeHistoryForFromCurrency.size() > 0}">
                   <table border="1">
                   <tr>
                    <th>From currency</th>
                    <th>To currency</th>
                    <th>Amount</th>
                    <th>Trans date</th>
                   </tr>
                   <c:forEach items="${exchangeHistoryForFromCurrency}" var="item">
                       <tr>
                       <td>${item.getFromCurrency().getName()}</td>
                       <td>${item.getToCurrency().getName()}</td>
                       <td>${item.getAmount()}</td>
                       <td>${item.getTransDate()}</td>
                       </tr>
                   </c:forEach>
                   </table>
                </c:when>
                <c:otherwise>
                    <p>Data is empty for param 'from_currency'='${from_currency}'</p>
                </c:otherwise>
            </c:choose>
                    
            <h3>Number Of transations for each from currency:</h3>
            <c:choose>
                <c:when test="${exchangeHistoryNumberOfTransationsForEachFromCurrency.size() > 0}">
                   <table border="1">
                   <tr>
                    <th>From currency</th>
                    <th>To currency</th>
                    <th>Amount</th>
                   </tr>
                   <c:forEach items="${exchangeHistoryNumberOfTransationsForEachFromCurrency}" var="item">
                    <tr>
                        <td><c:out value="${item[0].getName()}"/></td>
                        <td><c:out value="${item[1].getName()}"/></td>
                        <td><c:out value="${item[2]}"/></td>
                    </tr>
                   </c:forEach>
                   </table>
                </c:when>
                <c:otherwise>
                    <p>Data is empty</p>
                </c:otherwise>
            </c:choose>
                    
            <h3>Currency with max transaction count:</h3>
            <c:choose>
                <c:when test="${exchangeHistoryMaxNumberOfTransationsForFromCurrency.size() > 0}">
                   <table border="1">
                   <tr>
                    <th>From currency</th>
                    <th>To currency</th>
                    <th>Amount</th>
                   </tr>
                   <c:forEach items="${exchangeHistoryMaxNumberOfTransationsForFromCurrency}" var="item">
                       <tr>
                        <td><c:out value="${item[0].getName()}"/></td>
                        <td><c:out value="${item[1].getName()}"/></td>
                        <td><c:out value="${item[2]}"/></td>
                       </tr>
                   </c:forEach>
                   </table>
                </c:when>
                <c:otherwise>
                    <p>Data is empty</p>
                </c:otherwise>
            </c:choose>
            
        </body>
    </html>
    </html>
