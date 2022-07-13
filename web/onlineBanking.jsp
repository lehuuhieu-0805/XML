<%-- 
    Document   : onlineBanking
    Created on : 16-Jun-2022, 10:36:24
    Author     : lehuuhieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bank</title>
    </head>
    <body>
        <h1>Online Banking</h1>
        <form action="loginProcess.jsp" method="POST">
            Username <input type="text" name="txtUsername" value="" /><br/>
            Pin <input type="password" name="txtPin" value="" /><br/>
            <input type="submit" value="Login" />
            <input type="submit" value="Reset" />
        </form>
    </body>
</html>
