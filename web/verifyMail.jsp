<%-- 
    Document   : verifyMail
    Created on : Jun 30, 2023, 10:19:13 PM
    Author     : Lc_Tn
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify Mail</title>
        <style>
            body {
                margin: 0;
                padding: 0;
                font-family: Arial, sans-serif;
                background-image: url("img/scenery-gf2f39351a_1280.jpg");
            }

            .info {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                width: 350px;
                height: 450px;
                padding: 20px;
            }

            h1{
                text-align: center;
            }

            input[type="hidden"]{
                display: none;
            }

            input {
                padding: 5px 20px;
                margin: 5px;
                width: 85%;
                height: 30px;
                margin: 5px 0;
                border: none;
                border-radius: 20px;
                background-color: rgba(255, 255, 255, 0.8);
                color: black;

            }


            input[type="submit"]{
                margin-top: 20px;
                height: 45px;
                width: 96%;
                align-items: center;
                background-color: rgba(71, 113, 149, 0.5);
            }

            ::placeholder { 
                color: white;
            }
            p{
                color: #dd4b39;
            }
        </style>
    </head>
    <body>
        <form action="MainController" method="POST">
            <div class="info">
                <h1>Verify your mail</h1>
                <input type="hidden" name="fullName" placeholder="Full Name" value="${sessionScope.fullName}"></br>
                <input type="hidden" name="userID" placeholder="Id" value="${sessionScope.userID}"></br>
                <input type="hidden" name="roleID" value="US" readonly=""></br>
                <input type="text" name="mail" value="${sessionScope.mail}" readonly="">
                <input type="text" name="code" placeholder="Verify code" required=""></br>
                <c:if test="${requestScope.USER_ERROR.mail != null}">
                    <p>${requestScope.USER_ERROR.mail}</p>
                </c:if>
                <input type="hidden" name="password" placeholder="Password" value="${sessionScope.password}"></br>
                <input type="hidden" name="confirmPassword" placeholder="Confirm" value="${sessionScope.confirmPassword}"></br>
                <input type="submit" name="action" value="VerifyMail"/>
            </div>
            ${sessionScope.MESSAGE}

        </form>
    </body>
</html>
