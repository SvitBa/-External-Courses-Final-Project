<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
<title> Add car </title>
</head>
    <body>
    <h3> Add car </h3>
    <hr>
                   <br>
    <div id="container" class="col-sm-6">

                    <form action="/carRentApp/CarServlet" metod="POST">
                        <input type="hidden" name="command" value="ADD"/>
                        <input type="hidden" name="carModelId" value="${CAR_MODEL_ID.id}"/>
                        <table>
                            <tbody>
                                <tr>
                                   <td> <label> Quality class </label> </td>
                                   <td> "${CAR_MODEL_ID.qualityClass}" </td>
                                </tr>
                                <tr>
                                    <td> <label> Brand</label> </td>
                                    <td> "${CAR_MODEL_ID.brand}"</td>
                                </tr>
                                <tr>
                                   <td> <label> Brand</label> </td>
                                   <td> "${CAR_MODEL_ID.model}"</td>
                                </tr>

                                <tr>
                                    <td> <label> Rent Price per day </label> </td>
                                    <td> <input type="text" name="price"/> </td>
                                </tr>
                                <tr>
                                    <td> <label> </label> </td>
                                    <td> <input type="submit" value="add car" class="save"/> </td>
                                </tr>
                            </tbody>
                        </table>
                        <div style="clear: both;"> </div>
                        <p>
                            <a href="CarModelServlet"> Back to Car Model list </a>
                        </p>

                    </form>


    </body>
</html>