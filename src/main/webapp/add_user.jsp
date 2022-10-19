<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
<title> Add user </title>
</head>
    <body>
        <h3> Add user for admin </h3>
                <hr>
                <br>

                <div id="container" class="col-sm-6">

                <form action="UserServlet" metod="POST">
                    <input type="hidden" name="command" value="ADD"/>
                    <table>
                        <tbody>
                            <tr>
                                <td> <label> login</label> </td>
                                <td> <input type="text" name="login"/> </td>
                            </tr>
                            <tr>
                                <td> <label> email </label> </td>
                                <td> <input type="text" name="email"/> </td>
                            </tr>
                            <tr>
                                <td> <label> passport </label> </td>
                                <td> <input type="text" name="passport"/> </td>
                            </tr>
                            <tr>
                                <td> <label> password </label> </td>
                                <td> <input type="password" name="password"/> </td>
                            </tr>

                            <tr>
                                <td> <label> </label> </td>
                                <td> <input type="submit" value="save" class="save"/> </td>
                            </tr>
                        </tbody>
                    </table>
                    <div style="clear: both;"> </div>
                    <p>
                        <a href="UserServlet"> Back to list </a>
                    </p>

                </form>
    </div>
    </body>
</html>