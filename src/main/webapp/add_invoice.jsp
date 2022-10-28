<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <title> Add invoice </title>
</head>
<body>
<jsp:include page="Manager_menu.jsp"/>
<h3> Generate invoice </h3>
<hr>
<br>

<div id="container" class="col-sm-6">

    <form action="InvoiceServlet" method="POST">
        <input type="hidden" name="command" value="ADD"/>
        <table>
            <tbody>
            <tr>
                <th><label> bookingId </label></th>
                <th><label> user login </label></th>
                <th><label> Car description </label></th>
                <th><label> invoice Type </label></th>
                <th><label> Total price </label></th>
            </tr>
            <tr>
                <td><label> Invoice type  </label></td>
                <td><input type="text" name="price"/></td>

            <tr>
                <td><label> </label></td>
                <td><input type="submit" value="generate" class="save"/></td>
            </tr>
            </tbody>
        </table>
        <div style="clear: both;"></div>

    </form>
</div>
</body>
</html>