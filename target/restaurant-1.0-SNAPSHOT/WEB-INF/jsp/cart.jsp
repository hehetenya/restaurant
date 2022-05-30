<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:set var="title" value="Cart" scope="page"/>
<%@include file="../jspf/head.jspf"%>
<body>
 <%@include file="../jspf/header.jspf"%>

<jsp:useBean id="cart" scope="session" type="java.util.Map"/>
 <div class="grey_background">
<c:if test="${cart.size()==0}">
    <div class="cart-empty">
        Your cart is empty, try adding some dishes.
    </div>
</c:if>
<c:if test="${cart.size() != 0}">

    <table class="table">
        <thead>
        <tr>
            <th></th>
            <th>Dish description</th>
            <th>Amount</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cart}" var="item">
            <c:set var="dish" value="${item.key}" scope="page"/>

            <tr>
                <td><img src="${pageContext.request.contextPath}/images/${dish.id}.jpg" class="cart-dish-img" alt="${dish.name}.jpg"/></td>
                <td>
                    <div class="dish_cart_text">
                        <p><strong>${dish.name}</strong></p>
                    <p>${dish.description}</p>
                    <p>${dish.weight} g</p>
                    <p>${dish.price} UAH</p>
                </div>
                </td>
                <td>
                        <form class="dish_cart_amount" method="post" action="${pageContext.request.contextPath}/cart">
                            <input name="amount" type="number" min="1" value="${item.value}">
                            <input name="id" style="display: none" value="${dish.id}">
                            <input type="submit" value="Apply">
                        </form>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/cart">
                        <input name="amount" style="display: none" type="number" value="0">
                        <input name="id" style="display: none" value="${dish.id}">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <jsp:useBean id="total" scope="session" type="java.lang.Integer"/>
    <div class="cart-submit-order">
        <p>Total: ${total} UAH</p>
        <form action="${pageContext.request.contextPath}/myOrders" method="post">
            <input class="cart-submit-button" type="submit" value="Order"/>
        </form>
    </div>

</c:if>
 </div>
</body>
</html>
