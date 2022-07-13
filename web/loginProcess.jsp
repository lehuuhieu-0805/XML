<%-- 
    Document   : loginProcess
    Created on : 16-Jun-2022, 10:39:40
    Author     : lehuuhieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bank</title>
    </head>
    <body>
        <c:set var="username" value="${param.txtUsername}" scope="session"/>
        <c:set var="pin" value="${param.txtPin}"/>
        <c:if test="${not empty username and not empty pin}">
            <c:import var="xml" url="WEB-INF/accountATM.xml"/>
            <x:parse var="document" doc="${xml}" scope="application"/>
            <c:if test="${not empty document}">
                <x:set var="checkLogin" select="$document//*[local-name()='allowed' and @username=$username and pin=$pin]"/>
                <x:if select="$checkLogin">
                    <jsp:forward page="transaction.jsp"/>
                </x:if>
            </c:if>
        </c:if>
        
        <h2>
            <font color="red">
                Invalid username or password!!!
            </font>
        </h2>
        <a href="onlineBanking.jsp">Click here to try again.</a>
    </body>
</html>
