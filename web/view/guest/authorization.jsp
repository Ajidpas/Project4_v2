<%-- 
    Document   : LogInLogOut
    Created on : 10.01.2016, 23:12:47
    Author     : Sasha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" /> 
<fmt:setBundle basename="controller.properties.text" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="guest.authorization.text.title" /></title>
    </head>
    <body>
        <form action="servlet" method="get">
            <h4><fmt:message key="guest.authorization.text.guest" /> | 
                <a href="${pageContext.request.contextPath}/servlet?action=loginrequest" >
                    <fmt:message key="guest.authorization.link.login" />
                </a> | <a href="${pageContext.request.contextPath}/servlet?action=signup" >
                    <fmt:message key="guest.authorization.link.signup" />
                </a>
            </h4>
            <input type="hidden" name="from" value="${pageContext.request.requestURI}">
        </form>
    </body>
</html>