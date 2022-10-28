<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
</head>
<body>
<c:choose>
    <c:when test="${user != null}">
        <form action="/carRentApp/logout" method="POST" align="right">
            <button type="submit" class="btn btn-sm btn-secondary"> Logout </button>
        </form>
        <c:if test="${user.userRoleId == 1}">
            <jsp:include page="Customer_menu.jsp"/>
        </c:if>
        <c:if test="${user.userRoleId == 2}">
            <jsp:include page="Manager_menu.jsp"/>
        </c:if>
        <c:if test="${user.userRoleId == 3}">
            <jsp:include page="Admin_menu.jsp"/>
        </c:if>
    </c:when>
    <c:otherwise>
        <p>  please login </p>
    </c:otherwise>
</c:choose>
</body>
</html>