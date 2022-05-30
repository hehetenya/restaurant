<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:set var="title" value="My orders" scope="page"/>
<%@include file="../jspf/head.jspf"%>
<body>
<%@include file="../jspf/header.jspf"%>
<jsp:useBean id="receipts" scope="session" type="java.util.List"/>
<div class="grey_background">
<c:if test="${receipts.size()==0}">
    <div class="cart-empty">
        You didn't order anything yet.
    </div>
</c:if>
<c:if test="${receipts.size() != 0}">
    <table class="table">
        <thead>
        <tr>
            <th>Order id</th>
            <th>Status</th>
            <th>Dishes</th>
            <th>Total</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${receipts}" var="receipt">
            <jsp:useBean id="receipt" class="com.epam.koval.restaurant.database.entity.Receipt"/>

            <tr>
                <td>${receipt.id}</td>
                <td>${receipt.status}</td>
                <td>
                    <c:forEach items="${receipt.dishes}" var="dishAndAmount">

                        ${dishAndAmount.key.name}: ${dishAndAmount.key.price} * ${dishAndAmount.value}<br>
                    </c:forEach>
                </td>
                <td>${receipt.total}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="menu-pagination">
    <form method="get" action="${pageContext.request.contextPath}/myOrders">
        <c:forEach var="number" begin="1" end="${maxPage}">
            <div class="menu-pagination-item">
                <input type="submit" name="currentPage" value="${number}" >
            </div>
        </c:forEach>
    </form>
</div>
</c:if>

</body>
</html>
