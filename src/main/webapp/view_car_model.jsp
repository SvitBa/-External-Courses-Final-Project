<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

</head>
<body>
<h2> Car model list </h2>
<hr>
<br>
<input type="button" value="Add Car Model"
       onclick="window.location.href='add_car_model.jsp'">
<br>
<hr>

<div id="container" class="col-sm-6">
    <table border="1">
        <tr align="center">
            <th> brand</th>
            <th> model</th>
            <th> Quality Class</th>
            <th> Action</th>
            <th> Add car</th>
        </tr>
        <c:forEach var="tempModel" items="${model_list}">
            <c:url var="tempLink" value="CarModelServlet">
                <c:param name="command" value="LOAD"/>
                <c:param name="carModelId" value="${tempModel.id}"/>
            </c:url>

            <c:url var="deleteLink" value="CarModelServlet">
                <c:param name="command" value="DELETE"/>
                <c:param name="carModelId" value="${tempModel.id}"/>
            </c:url>
            <c:url var="carModelLink" value="CarModelServlet">
                <c:param name="command" value="ADDCAR"/>
                <c:param name="carModelId" value="${tempModel.id}"/>
            </c:url>

            <tr align="center">
                <td> ${tempModel.brand}</td>
                <td> ${tempModel.model}</td>
                <td> ${tempModel.qualityClass}</td>
                <td><a href="${tempLink}"> Update </a>
                    | <a href="${deleteLink}"
                         onclick="if (!(confirm('Are you sure you want delete Car Model?'))) return false"
                    > Delete </a>
                </td>

                <td><input type="button" value="Add Car" onclick="window.location.href='${carModelLink}'"></td>
            </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>