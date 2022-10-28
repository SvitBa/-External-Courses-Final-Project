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



    <title> Manager menu</title>
</head>
<body>
<jsp:include page="Manager_menu.jsp"/>
<hr>
<br>
<div id="container" class="col-sm-8">


    <div class="row">

        <table class="sortable" border="1">
            <tr align="center">

                <th> BookingId </th>
                <th> brand</th>
                <th> model</th>
                <th> Pick up date</th>
                <th> Return date</th>
                <th> Number of days</th>
                <th> Price pre day</th>
                <th> Car Available</th>
                <th> Driver</th>
                <th> Total price</th>
                <th colspan="3"> Action</th>
                <th colspan="2"> Invoice</th>
            </tr>

            <c:forEach var="tempBooking" items="${booking_list}">

                <tr align="center">

                    <td> ${tempBooking.id}</td>
                    <td> ${tempBooking.car.carModel.brand}</td>
                    <td> ${tempBooking.car.carModel.model}</td>
                    <td> ${tempBooking.pickUpDate}</td>
                    <td> ${tempBooking.returnDate}</td>
                    <td> ${tempBooking.numberOfDays}</td>
                    <td> ${tempBooking.car.rentPricePerDay}</td>
                    <td> ${tempBooking.car.carCurrentAvailable}</td>
                    <td> ${tempBooking.driver}</td>
                    <td> ${tempBooking.totalPrice}</td>
                    <td> <form method="POST" action="/carRentApp/BookingServlet">
                        <input type="hidden" name="command" value="APPROVE"/>
                        <input type="hidden" name="bookingId" value="${tempBooking.id}"/>
                        <input type="hidden" name="carId" value="${tempBooking.car.id}"/>
                        <input type="hidden" name="carAvailable" value="false"/>
                        <button type="submit" class="btn btn-info"> approve </button>
                    </form>
                    </td>
                    <td> <form method="POST" action="/carRentApp/BookingServlet">
                    <input type="hidden" name="command" value="CANCEL"/>
                    <input type="hidden" name="bookingId" value="${tempBooking.id}"/>
                    <input type="text" name="cancelComment">

                        <button type="submit" class="btn btn-warning"> cancel</button>
                    </form> </td>
                    <td> <form method="POST" action="/carRentApp/CarServlet">
                        <input type="hidden" name="command" value="RETURN"/>
                        <input type="hidden" name="bookingId" value="${tempBooking.id}"/>
                        <input type="hidden" name="carId" value="${tempBooking.car.id}"/>
                        <input type="hidden" name="carAvailable" value="true"/>
                        <button type="submit" class="btn btn-secondary"> return car</button>
                    </form>
                    </td>
                    <td> <form method="POST" action="/carRentApp/InvoiceServlet">
                        <input type="hidden" name="command" value="PREPARE"/>
                        <input type="hidden" name="bookingId" value="${tempBooking.id}"/>
                        <input type="hidden" name="carId" value="${tempBooking.car.id}"/>
                        <input type="radio" name="type" value="REGULAR"/> REGULAR
                        <input type="radio" name="type" value="DAMAGE"/> DAMAGE
                        <button type="submit" class="btn btn-dark"> generate </button>
                    </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

</div>

</body>
</html>