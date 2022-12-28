<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <title> Search Car</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
            crossorigin="anonymous"></script>
    <script type="text/javascript" src="/sorttable.js"></script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-sm-4">
            <h2>Filters </h2>
            <input type="button" value="Clear filter"
                   onclick="window.location.href='CarServlet'">
            <form action="/carRentApp/CarServlet" method="POST">
                <input type="hidden" name="command" value="FILTER"/>
                <div class="container">
                    <h3>Quality class</h3>
                    <c:forEach var="tempQuality" items="${quality_list}">
                        <input type="radio" name="quality" value="${tempQuality}">
                        <label> ${tempQuality} </label><br>
                    </c:forEach>
                </div>

                <div class="container">
                    <h3> Brands</h3>
                    <c:forEach var="tempBrand" items="${brand_list}">
                        <input type="radio" name="brand" value="${tempBrand}">
                        <label> ${tempBrand} </label><br>
                    </c:forEach>
                </div>
                <input type="submit" value="filter car" class="save"/>
            </form>

        </div>
        <div class="col-sm-8">
            <div class="row">
                <h4> Car description </h4>

            </div>
            <div class="row"> To sort car click on column</div>

            <div class="row">

                <div id="container" class="col-sm-8">
                    <table class="sortable" border="1">
                        <tr align="center">
                            <th> quality Class</th>
                            <th> brand</th>
                            <th> model</th>
                            <th> Available</th>
                            <th> Price pre day</th>
                            <th> Rent</th>
                        </tr>

                        <c:forEach var="tempCar" items="${car_list}">
                            <c:url var="tempLink" value="BookingServlet">
                                <c:param name="command" value="PREPARE"/>
                                <c:param name="userId" value="${USER_ID}"/>
                                <c:param name="carId" value="${tempCar.id}"/>
                            </c:url>
                            <tr align="center">
                                <td> ${tempCar.carModel.qualityClass}</td>
                                <td> ${tempCar.carModel.brand}</td>
                                <td> ${tempCar.carModel.model}</td>
                                <td> ${tempCar.carCurrentAvailable}</td>
                                <td> ${tempCar.rentPricePerDay}</td>
                                <td><a href="${tempLink}"> now </a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>

            </div>
        </div>
    </div>
</body>
</html>