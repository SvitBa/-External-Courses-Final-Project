<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <title> Add car model </title>
</head>
<body>
<h3> Add car model </h3>
<hr>
<br>

<div id="container" class="col-sm-6">

    <form action="CarModelServlet" metod="GET">
        <input type="hidden" name="command" value="ADD"/>
        <table>
            <tbody>
            <tr>
                <td><label> Brand </label></td>
                <td><input type="text" name="brand"/></td>
            </tr>
            <tr>
                <td><label> Model </label></td>
                <td><input type="text" name="model"/></td>
            </tr>
            <tr>
                <td><label> Quality class </label></td>
                <td><input type="text" name="qualityClass"/></td>
            </tr>
            <tr>
                <td><label> </label></td>
                <td><input type="submit" value="save" class="save"/></td>
            </tr>
            </tbody>
        </table>
        <div style="clear: both;"></div>
        <p>
            <a href="CarModelServlet"> Back to list </a>
        </p>

    </form>
</div>
</body>
</html>