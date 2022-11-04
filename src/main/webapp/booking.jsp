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
<br>
<div id="container" class="col-sm-8">


    <div class="row">
        <h3> My Booking </h3>
    </div>
    <div class="row" >
        <table class="sortable" border="1">
            <tr align="center">

                <th> brand</th>
                <th> model</th>
                <th> Pick up date</th>
                <th> Return date</th>
                <th> Number of days</th>
                <th> Price pre day</th>
                <th> Driver</th>
                <th> Total price</th>
                <th> Action</th>
            </tr>

            <c:forEach var="tempBooking" items="${booking_list}">
                <c:url var="tempLink" value="BookingServlet">
                    <c:param name="command" value="LIST"/>
                    <c:param name="userId" value="${USER_ID}"/>
                </c:url>
                <c:url var="tempLink" value="BookingServlet">
                    <c:param name="command" value="PREPARE"/>
                    <c:param name="bookingId" value="${tempBooking.id}"/>
                    <c:param name="userId" value="${user.id}"/>
                    <c:param name="carId" value="${tempBooking.car.id}"/>
                </c:url>
                <c:url var="deleteLink" value="BookingServlet">
                    <c:param name="command" value="DELETE"/>
                    <c:param name="userId" value="${user.id}"/>
                    <c:param name="bookingId" value="${tempBooking.id}"/>
                </c:url>

                <tr align="center">

                    <td> ${tempBooking.car.carModel.brand}</td>
                    <td> ${tempBooking.car.carModel.model}</td>
                    <td> ${tempBooking.pickUpDate}</td>
                    <td> ${tempBooking.returnDate}</td>
                    <td> ${tempBooking.numberOfDays}</td>
                    <td> ${tempBooking.car.rentPricePerDay}</td>
                    <td> ${tempBooking.driver}</td>
                    <td> ${tempBooking.totalPrice}</td>
                    <td><a href="${tempLink}">
                        <button class="btn btn-info"> update</button>
                    </a>
                        | <a href="${deleteLink}">
                            <button class="btn btn-warning"> delete</button>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

</div>

</body>
</html>