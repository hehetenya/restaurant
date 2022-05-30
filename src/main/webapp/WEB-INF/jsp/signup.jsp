<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<c:set var="title" value="Sign Up" scope="page"/>
<%@include file="../jspf/head.jspf"%>
<body>

<div class="login-box">
    <p class="login-title">Sign Up</p>
    <form class="login-form" action="${pageContext.request.contextPath}/signup" method="post">
        <input name="login" class="login-input" placeholder="Login" required minlength="8" maxlength="20">
        <input type="password" name="password" class="login-input" placeholder="Password" required minlength="8" maxlength="20">
        <input type="submit" class="login-button" value="Sing up!">
    </form>
    <c:if test="${param.err != null}">
        <p class="login-error">User with this login already exists.</p>
    </c:if>
    <p class="login-register">Already have an account? <a href="${pageContext.request.contextPath}/login">Log In</a> </p>
</div>

</body>
</html>
