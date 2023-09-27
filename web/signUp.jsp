<%-- 
    Document   : signUp
    Created on : Jun 23, 2023, 3:18:49 PM
    Author     : Lc_Tn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify Mail Page</title>
        <style>
            body {
                margin: 0;
                padding: 0;
                font-family: Arial, sans-serif;
                background-image: url("img/scenery-gf2f39351a_1280.jpg");
            }

            .signup {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                width: 350px;
                height: 450px;
                padding: 20px;
                border-radius: 10px;
            }

            h1{
                text-align: center;
            }
            input {
                padding: 5px 20px;
                margin: 5px;
                width: 90%;
                height: 30px;
                margin: 5px 0;
                border: none;
                border-radius: 20px;
                background-color: rgba(255, 255, 255, 0.4);
                color: white;
            }

            input[type="submit"]{
                margin-top: 20px;
                height: 45px;
                width: 100%;
                align-items: center;
                background-color: rgba(71, 113, 149, 0.8);
            }
            ::placeholder { 
                color: white;
            }
            p{
                color: #dd4b39;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <form action="MainController" method="POST">
            <div class="signup">
                <h1>Sign Up</h1>

                <input type="text" name="fullName" placeholder="Full Name" required=""></br>
                <input type="text" name="userID" placeholder="User ID" required=""></br>
                <c:if test="${requestScope.USER_ERROR.idError!=null}">
                    <p>${requestScope.USER_ERROR.idError}</p>
                </c:if>
                <input type="text" name="roleID" value="US" readonly=""></br>
                <input type="text" name="mail" placeholder="Mail" required="" value="${sessionScope.EMAIL}"></br>
<!--                <c:if test="${sessionScope.SIGNUP != null}">
                    <input type="text" name="code" placeholder="Code" required=""/>
                </c:if>-->
                <input type="password" name="password" placeholder="Password" required=""></br>
                <input type="password" name="confirmPassword" placeholder="Repeat your password" required=""></br>
                <c:if test="${requestScope.USER_ERROR.confirmError!=null}">
                    <p>${requestScope.USER_ERROR.confirmError}</p>
                </c:if>
                <c:if test="${sessionScope.SIGNUP == null}">
                    <input type="submit" name="action" value="SignUp"/>
                </c:if>
<!--                <c:if test="${sessionScope.SIGNUP != null}">
                    <input type="submit" name="action" value="VerifyMail"/>
                </c:if>-->
            </div>

        </form>
    </body>
</html>
