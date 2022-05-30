
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<c:set var="title" value="Orders" scope="page"/>
<%@include file="../jspf/head.jspf"%>
<body>

<%@include file="../jspf/header.jspf"%>

<div class="grey_background">


    <table class="table">
        <thead>
        <tr>
            <th>Order id</th>
            <th>User id</th>
            <th>Status</th>
            <th>Dishes</th>
            <th>Total</th>
        </tr>
        </thead>
        <tbody>
        <jsp:useBean id="receipts" scope="session" type="java.util.List"/>
        <c:forEach items="${receipts}" var="receipt">
            <jsp:useBean id="receipt" class="com.epam.koval.restaurant.database.entity.Receipt"/>

            <tr>
                <td>${receipt.id}</td>
                <td>${receipt.user.id}</td>
                <td>${receipt.status}
                        <form method="post" action="${pageContext.request.contextPath}/manageOrders">
                            <div class="edit-status">
                                    <button class="edit-button">Edit</button>
                                    <div class="status-content">
                                        <input value="${receipt.id}" name="id" style="display: none">
                                        <input type="submit" name="status" value="New">
                                        <input type="submit" name="status" value="Approved">
                                        <input type="submit" name="status" value="Cancelled">
                                        <input type="submit" name="status" value="Cooking">
                                        <input type="submit" name="status" value="Delivering">
                                        <input type="submit" name="status" value="Received">
                                    </div>
                            </div>
                        </form>
                </td>
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
    <form method="get" action="${pageContext.request.contextPath}/manageOrders">
        <c:forEach var="number" begin="1" end="${maxPage}">
            <div class="menu-pagination-item">
                <input type="submit" name="currentPage" value="${number}" >
            </div>
        </c:forEach>
    </form>
</div>

</body>
</html>
