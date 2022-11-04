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


</head>
<body>
<div id="container" class="col-sm-8">
<div class="row">
        <h3> My Invoice </h3>
    </div>

    <div class="row">

        <table class="sortable" border="1">
            <tr align="center">

                <th> InvoiceId </th>
                <th> Type </th>
                <th> State </th>
                <th> BookingId </th>
      <!--              <th> User login </th>
                <th> Car description </th>
                <th> Pick up date</th>
                <th> Return date</th>
                <th> Number of days</th>
                <th> Price pre day</th>
                <th> Driver</th> -->
                <th> Total price</th>
            </tr>

            <c:forEach var="tempInvoice" items="${invoice_list}">

                <tr align="center">

                    <td> ${tempInvoice.id}</td>
                    <td> ${tempInvoice.type}</td>
                    <td> ${tempInvoice.status} </td>
                    <td> ${tempInvoice.booking.id}</td>
  <!--                  <td> ${tempBooking.car.carModel.brand}" "${tempBooking.car.carModel.model}</td>
                    <td> ${tempBooking.pickUpDate}</td>
                    <td> ${tempBooking.returnDate}</td>
                    <td> ${tempBooking.numberOfDays}</td>
                    <td> ${tempBooking.car.rentPricePerDay}</td>
                    <td> ${tempBooking.driver}</td> -->
                    <td> ${tempInvoice.totalPrice}</td>

                </tr>
            </c:forEach>
        </table>
    </div>

</div>

</body>
</html>