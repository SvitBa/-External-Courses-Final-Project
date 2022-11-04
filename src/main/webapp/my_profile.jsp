<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Profile </title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div id="container" class="col-sm-6">
<h4> My Profile </h4>
<table>
    <tbody>

    <tr>
        <td><label> Login</label> </td>
        <td> ${user.login}</td>
    </tr>
    <tr>
        <td><label> Email </label> </td>
        <td> ${user.email}</td>
    </tr>
    <tr>
        <td><label> Passport </label> </td>
        <td> ${user.passport}</td>
    </tr>
    <tr>
        <td> </td>
        <td> <form method="POST" action="/carRentApp/UserService">
            <input type="hidden" name="command" value="PROFILE"/>
            <input type="hidden" name="userId" value="${user.id}"/>
            <button type="submit" class="btn btn-info"> update </button>
            </form>
        </td>
    </tr>
    </tbody>
</table>
</div>

<div id="container" class="col-sm-12">
    <jsp:include page="booking.jsp"/>
</div>
<br>
<div id="container" class="col-sm-12">
    <jsp:include page="user_invoice_list.jsp"/>
</div>

</body>
</html>