<%-- 
    Document   : search
    Created on : 01-Jun-2022, 22:46:57
    Author     : lehuuhieu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>SEARCH PAGE</h1>
        <!--<p>Welcome ${sessionScope.STUDENTINFO.fullName}</p>-->
        <!--<p>Welcome ${sessionScope.FULLNAME}</p>-->
        <p>Welcome ${sessionScope.STUDENTINFO.fullName}</p>
        <form action="DispatchController" method="POST">
            Search: <input name="txtSearch" type="text" value="${param.txtSearch}"/><br/>
            <input type="submit" value="Search" name="btAction"/>
        </form>
        <br/>
        <c:set var="searchValue" value="${param.txtSearch}"/>
        <%--<c:if test="${requestScope.SEARCH_RESULT != null}">--%>
        <%--<c:if test="${not empty searchValue}">--%>
        <%--<c:if test="${not empty requestScope.SEARCH_RESULT}" var="checkList">--%>
        <%--<c:if test="${not empty requestScope.SEARCH_RESULT}" var="checkList">--%>
        <c:set var="result" value="${requestScope.SEARCH_RESULT}"/>
        <c:if test="${not empty searchValue or not empty result}">
            <c:if test="${not empty result}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Id</th>
                            <th>Class</th>
                            <th>Full name</th>
                            <th>Address</th>
                            <th>Sex</th>
                            <th>Status</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${result}" varStatus="counter">
                        <form action="DispatchController">
                            <tr>
                                <td>${counter.count}</td>
                                <td>
                                    <input type="hidden" name="txtId" value="${dto.id}"/>
                                    ${dto.id}
                                </td>
                                <td>
                                    <input type="text" name="txtSClass" value="${dto.sClass}"/>
                                </td>
                                <td>${dto.fullName}</td>
                                <td>
                                    <input type="text" name="txtAddress" value="${dto.address}"/>
                                </td>
                                <td>
                                    <c:if test="${dto.sex == '1'}" var="checkSex">Female</c:if> 
                                    <c:if test="${!checkSex}">Male</c:if>
                                    </td>
                                    <td>
                                        <input type="text" name="txtStatus" value="${dto.status}"/>
                                </td>
                                <td>
                                    <c:url var="deleteStudent" value="DispatchController">
                                        <c:param name="btAction" value="Delete"/>
                                        <c:param name="id" value="${dto.id}"/>
                                        <c:param name="lastSearchValue" value="${searchValue}"/>
                                    </c:url>
                                    <a href="${deleteStudent}">Delete</a>
                                </td>
                                <td>
                                    <input type="submit" value="Update" name="btAction"/>
                                    <input type="hidden" name="lastSearchValue" value="${searchValue}"/>
                                </td>
                            </tr> 
                        </form>
                    </c:forEach>
                </tbody>                   
            </table>
        </c:if>
        <c:if test="${empty result}">
            <font color="red">
            No record found
            </font>
        </c:if>
    </c:if>
    <%--</c:if>--%>
    <%--</c:if>--%>
</body>
</html>
