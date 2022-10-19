<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String [] cities = {"Mumbai","Paris", "London"};
pageContext.setAttribute("myCities", cities);

%>

<html>
<body>
<h3> Hello world </h3>
 The time on server is <%= new java.util.Date() %>
 <c:forEach var="tempCity" items="${myCities}">
 <br> ${tempCity}

</c:forEach>
 </body>
 </html>