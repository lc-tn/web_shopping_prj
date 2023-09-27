<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Login Page</title>
        <meta charset="UTF-8">

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            body {
                background-image: url("img/caucasus-gc2ddb8af6_1280.jpg");
                margin: 0;
                padding: 0;
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
            }

            /* Login form styles */
            .login {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                width: 350px;
                padding: 25px;
                background-color: rgba(255, 255, 255, 0.5);
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            }

            .login h1 {
                text-align: center;
                margin-bottom: 20px;
            }

            .info, info i{
                display: flex;
                align-items: center;
            }

            .login input[type="text"],
            .login input[type="password"] {
                width: 90%;
                padding: 10px;
                margin: 0 0px 10px 20px;
                border: none;
                background-color: rgba(255, 255, 255, 0);
                border-bottom: 1px solid rgb(123, 122, 122);
            }

            .login input[type="submit"] {
                width: 100%;
                padding: 10px;
                background-color: #4caf50;
                color: #fff;
                font-size: 18px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin-top: 10px;
            }

            .loginGG {
                display: block;
                text-align: center;
                background-color: #dd4b39;
                color: white;
                padding: 10px;
                border-radius: 4px;
                text-decoration: none;
                margin-top: 10px;
                font-size: 18px;
            }

            .link a {
                text-align: center;
                margin: 10px 0;
                color: #373737;
                text-decoration: none;
                font-size: 0.9em;
            } 

            .link{
                display: flex;
                justify-content: space-between;
            }

            .id{
                display: flex;
            }

            .g-recaptcha{
                justify-content: center;
                display: flex;
            }

            .errorRecaptcha, p{
                color: red;
                justify-content: center;
                display: flex;
            }
        </style>
    </head>
    <body>


        <div class="login">

            <h1>Login</h1>
            <form action="MainController" method="POST">
                <div class="info">
                    <i class="fa fa-user"></i>
                    <input type="text" name="userID" required=""/></br>
                </div>

                <div class="info">
                    <i class="fa-solid fa-lock"></i>
                    <input type="password" name="password" required=""></br>
                </div>
                <c:if test="${requestScope.USER_ERROR.idError != null}">
                    <p>${requestScope.USER_ERROR.idError}</p>
                </c:if>

                <c:if test="${requestScope.MESSAGE != null}">
                    <p style="color: greenyellow">${requestScope.MESSAGE}</p>
                </c:if>
                    
                <input type="submit" name="action" value="Login" />
            </form>

            <a class="loginGG" href="https://accounts.google.com/o/oauth2/auth?scope=profile+email&redirect_uri=http://localhost:8084/LamTanLoc_SE172198/LoginGoogleController&response_type=code
               &client_id=955791800214-ub5mu45mp1c1j76gfruc4r07h1s1ihhi.apps.googleusercontent.com&approval_prompt=force">Login with Google</a>

            <div class="link">
                <a href="signUp.jsp">Sign up</a>
                <a href="changepassword.jsp">Change password</a>
            </div>

            <div class="g-recaptcha" 
                 data-sitekey="6LegWsomAAAAAO-syKgxjEOXI5kYl2zHar-Yquld">   
            </div><br/>
            <div class="errorRecaptcha"></div>

            <a href="MainController?action=Top1">Top 1 User</a>
        </div>


        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <script src="https://www.google.com/recaptcha/api.js"></script>
        <script>
            var loginForm = document.querySelector('.login');
            loginForm.addEventListener("submit", function (e) {
                const response = grecaptcha.getResponse();
                if (!response) {
                    e.preventDefault();
                    document.querySelector('.errorRecaptcha').innerHTML = 'Failed to verify with the reCAPTCHA server.';
                }
            })
        </script>
        <script src="https://kit.fontawesome.com/11861eee9a.js" crossorigin="anonymous"></script>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    </body>

</html>
