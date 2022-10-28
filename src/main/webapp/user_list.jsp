<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

</head>
<body>
<jsp:include page="header.jsp"/>
<h2> User list for admin</h2>
<hr>
<br>
<input type="button" value="Add User"
       onclick="window.location.href='add_user.jsp'">
<br>
<hr>

<div id="container" class="col-sm-6">
    <table border="1">
        <tr>
            <th> id</th>
            <th> login</th>
            <th> email</th>
            <th> role</th>
            <th> isActive</th>
            <th> Action</th>
        </tr>
        <c:forEach var="tempUser" items="${user_list}">
            <c:url var="tempLink" value="UserService">
                <c:param name="command" value="LOAD"/>
                <c:param name="userId" value="${tempUser.id}"/>
            </c:url>

            <c:url var="deleteLink" value="UserService">
                <c:param name="command" value="DELETE"/>
                <c:param name="userId" value="${tempUser.id}"/>
            </c:url>

            <tr>
                <td> ${tempUser.id}</td>
                <td> ${tempUser.login}</td>
                <td> ${tempUser.email}</td>
                <td> ${tempUser.role}</td>
                <td> ${tempUser.userStateActive}</td>
                <td><a href="${tempLink}"> Update </a>
                    | <a href="${deleteLink}"
                         onclick="if (!(confirm('Are you sure you want delete User?'))) return false"
                    > Delete </a>
                </td>
            </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>