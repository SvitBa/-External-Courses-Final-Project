<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <title> Customer menu </title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <span class="navbar-brand mb-0 h1"> Menu </span>
    <form class="form-inline">
        <a href="my_profile.jsp"><button class="btn btn-outline-secondary" type="button"> My Profile </button> </a>
        <a href="CarServlet"><button class="btn btn-outline-secondary" type="button"> Search car </button> </a>
    </form>
</nav>

</body>
</html>