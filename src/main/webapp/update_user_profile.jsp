<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <title> Update user profile </title>
</head>
<body>
<jsp:include page="header.jsp"/>

<h3> Update Profile </h3>
<hr>
<br>

<div id="container" class="col-sm-6">

    <form method="POST" action="UserService" >
        <input type="hidden" name="command" value="CHANGE"/>
        <input type="hidden" name="userId" value="${user.id}"/>
        <input type="hidden" name="login" value="${user.login}"/>
        <table>
            <tbody>

            <tr>
                <td><label> login</label></td>
                <td> "${user.login}"</td>
            </tr>
            <tr>
                <td><label> email </label></td>
                <td><input type="text" name="email" value="${user.email}"/></td>
            </tr>
            <tr>
                <td><label> passport </label></td>
                <td><input type="text" name="passport" value="${user.passport}"/></td>
            </tr>
            <tr>
                <td><label> password </label></td>
                <td><input type="password" name="password" value="${user.password}"/></td>
            </tr>

            <tr>
                <td><label> </label></td>
                <td><input type="submit" value="save" class="save"/></td>
            </tr>
            </tbody>
        </table>
    </form>
    <div style="clear: both;"></div>

</div>
</body>
</html>