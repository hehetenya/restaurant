<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<c:set var="title" value="Menu" scope="page"/>
<%@include file="../jspf/head.jspf"%>
<body>
<%@include file="../jspf/header.jspf"%>

<jsp:useBean id="dishes" scope="session" type="java.util.List"/>
<jsp:useBean id="maxPage" scope="session" type="java.lang.Integer"/>

<div class="sort-filter-bar">
    <form class="menu_filter_sort" method="get" action="${pageContext.request.contextPath}/menu">
        <label for="category">Choose a category:</label>
        <select id="category" name="category">
            <option value="All">All categories</option>
            <option value="pizza">Pizza</option>
            <option value="sushi">Sushi</option>
            <option value="burger">Burger</option>
            <option value="drink">Drink</option>
            <option value="salad">Salad</option>
            <option value="dessert">Dessert</option>
        </select>

        <label for="sortBy">Sort by:</label>
        <select id="sortBy" name="sortBy">
            <option value="category">Category</option>
            <option value="name">Name</option>
            <option value="price">Price</option>
        </select>
        <select name="currentPage" style="display: none">
            <option value="1" selected></option>
        </select>

        <input class="menu-apply-button" type="submit" value="Apply">

    </form>
</div>


    <div class="menu_dishes">
        <c:forEach var="dish" items="${dishes}">
            <%@include file="../jspf/dish.jspf"%>
        </c:forEach>
    </div>

<div class="menu-pagination">
    <form method="get" action="${pageContext.request.contextPath}/menu">
        <c:forEach var="number" begin="1" end="${maxPage}">
            <div class="menu-pagination-item">
                <input name="sortBy" value="${param.sortBy}" style="display: none">
                <input name="category" value="${param.category}" style="display: none">
                <input type="submit" name="currentPage" value="${number}" >
            </div>
        </c:forEach>
    </form>
</div>

</body>
</html>
