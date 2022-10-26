<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
            crossorigin="anonymous"></script>
    <script src="sorttable.js"></script>

    <title> My booking </title>
</head>
<body>
<h3> Add booking </h3>
<hr>
<br>
<div id="container" class="col-sm-8">

    <div class="row">
        <form action="/carRentApp/BookingServlet" metod="POST">
            <input type="hidden" name="command" value="ADD"/>
            <input type="hidden" name="carId" value="${SELECTED_CAR.id}"/>
            <input type="hidden" name="userId" value="${USER_ID}"/>
            <table>
                <tbody>
                <tr>
                    <td><label> Brand </label></td>
                    <td> ${SELECTED_CAR.carModel.brand}</td>
                </tr>

                <tr>
                    <td><label> Model </label></td>
                    <td> ${SELECTED_CAR.carModel.model}</td>
                </tr>
                <tr>
                    <td><label> Rent Price per day </label></td>
                    <td> ${SELECTED_CAR.rentPricePerDay}</td>
                </tr>

                <tr>
                    <td><label> Pick Up Date </label></td>
                    <td><input type="date" name="pickUpDate"/></td>
                </tr>
                <tr>
                    <td><label> Return Date </label></td>
                    <td><input type="date" name="returnDate"/></td>
                </tr>
                <tr>
                    <td><label> Add driver </label></td>
                    <td><input type="radio" name="driver" value="true"/> yes
                        | <input type="radio" name="driver" value="false"/> no
                    </td>
                </tr>
                <tr>
                    <td><label> </label></td>
                    <td><input type="submit" value="add booking" class="save"/></td>
                </tr>
                </tbody>
            </table>
            <div style="clear: both;"></div>

            <p><a href="CarServlet"> Back to search car </a></p>


        </form>
    </div>
    <jsp:include page="booking.jsp"/>

</div>


</body>
</html>