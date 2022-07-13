<%-- 
    Document   : transaction
    Created on : 16-Jun-2022, 11:07:17
    Author     : lehuuhieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bank</title>
    </head>
    <body>
        <x:set var="account" select="$document//*[@username=$username]"/>
        <font color="red">
        Welcome, <x:out select="$account/fullname"/>
        </font><br/>
        Your Balance: <x:out select="$account/@balance"/>
        <h1>Welcome to Online Banking</h1>
        <form action="transaction.jsp">
            From Date <input type="text" name="txtFrom" value="${param.txtFrom}" />e.g yyyy/mm/dd<br/>
            To Date <input type="text" name="txtTo" value="${param.txtTo}" />e.g yyyy/mm/dd<br/>
            <input type="submit" value="View Transaction" />
        </form>
        <br/>
        <c:set var="fromDate" value="${param.txtFrom}"/>
        <c:set var="toDate" value="${param.txtTo}"/>
        <c:if test="${not empty fromDate and not empty toDate}">
            <c:set var="fromDate" value="${fn:replace(fromDate, '/', '')}"/>
            <c:set var="toDate" value="${fn:replace(toDate, '/', '')}"/>
            <x:set var="getTransactions" select="$account//transaction[translate(date,'/','')>=$fromDate and translate(date,'/','')<=$toDate]"/>
            <x:if select="$getTransactions">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Amount</th>
                            <th>Date</th>
                            <th>Type</th>
                        </tr>
                    </thead>
                    <tbody>
                        <x:forEach var="transaction" select="$getTransactions" varStatus="counter">
                            <tr>
                                <td>${counter.count}.</td>
                                <td><x:out select="$transaction//amount"/></td>
                                <td><x:out select="$transaction//date"/></td>
                                <td>
                                    <x:choose>
                                        <x:when select="$transaction[type=0]">
                                            Withdrawal
                                        </x:when>
                                        <x:when select="$transaction[type=1]">
                                            Deposit
                                        </x:when>
                                        <x:when select="$transaction[type=2]">
                                            Transfer
                                        </x:when>
                                        <x:otherwise>
                                            Hacked
                                        </x:otherwise>
                                    </x:choose>
                                </td>
                            </tr>
                        </x:forEach>
                    </tbody>
                </table>
            </x:if>
            <x:if select="not($getTransactions)">
                <h2>No Transaction!!</h2>
            </x:if>
        </c:if>
    </body>
</html>
