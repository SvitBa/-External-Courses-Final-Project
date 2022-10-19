<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">

<script>
function validate()
{
     var login = document.form.login.value;
     var email = document.form.email.value;
     var passport = document.form.passport.value;
     var password = document.form.password.value;
     var conpassword= document.form.conpassword.value;

     if (login==null || login=="")
     {
     alert("Login can't be blank");
     return false;
     }
     else if (email==null || email=="")
     {
     alert("Email can't be blank");
     return false;
     }
     else if (passport==null || passport=="")
     {
     alert("Passport can't be blank");
     return false;
     }
     else if(password.length<6)
     {
     alert("Password must be at least 6 characters long.");
     return false;
     }
     else if (password!=conpassword)
     {
     alert("Confirm Password should match with the Password");
     return false;
     }
 }
</script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-3">
        </div>
        <div class="col-sm-6">
            <form method="POST" class="form-register" action="/carRentApp/UserServlet">
                <input type="hidden" name="command" value="ADD"/>
                <div class="container">
                    <h1>Register</h1>
                    <p>Please fill in this form to create an account.</p>
                    <hr>

                    <label for="login"><b>Login</b></label>
                    <input type="text" placeholder="Enter login" name="login" id="login" required>
                    <br>

                    <label for="email"><b>Email</b></label>
                    <input type="text" placeholder="Enter Email" name="email" id="email" required>
                    <br>

                    <label for="passport"><b>Passport</b></label>
                    <input type="text" placeholder="Enter Passport" name="passport" id="passport" required>
                    <br>

                    <label for="psw"><b>Password</b></label>
                    <input type="password" placeholder="Enter Password" name="password" id="password" required>
                    <br>

                    <label for="psw-repeat"><b>Repeat Password</b></label>
                    <input type="password" placeholder="Repeat Password" name="conpassword" id="password-repeat" required>
                    <hr>
                    <tr>
                             <td><%=(request.getAttribute("errMessage") == null) ? ""
                             : request.getAttribute("errMessage")%></td>
                      </tr>

                    <p>By creating an account you agree to our <a href="#">Terms & Privacy</a>.</p>
                    <button type="submit" class="btn btn-sm btn-secondary" >Register</button>
                </div>

                <div class="container signin">
                    <p>Already have an account? <a href="index.jsp">Sign in</a>.</p>
                </div>
            </form>
        </div>
        <div class="col-sm-3">
        </div>
    </div>
</div>

</body>
</html>