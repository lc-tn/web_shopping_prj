<%-- 
    Document   : changepassword
    Created on : Jun 23, 2023, 9:58:38 PM
    Author     : Lc_Tn
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password Page</title>
        <style>
            body {
                margin: 0;
                padding: 0;
                font-family: Arial, sans-serif;
                background-image: url("img/scenery-gf2f39351a_1280.jpg");
            }
            .changePassword{
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
            input {
                /* box-sizing: border-box; */
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
        <div class="changePassword">
            <form action="MainController" method="POST">
                <h1>Change Password</h1>
                <input type="text" name="ID" placeholder="UserID"/></br>           
                <input type="password" name="password" placeholder="Recent password"></br>
                <c:if test="${requestScope.USER_ERROR.idError!=null}">
                    <p>${requestScope.USER_ERROR.idError}</p>
                </c:if>
                
                <input type="password" name="newPassword" placeholder="New password"></br>
                <input type="password" name="confirmPassword" placeholder="Confirm new password"></br>
                <c:if test="${requestScope.USER_ERROR.confirmError!=null}">
                    <p>${requestScope.USER_ERROR.confirmError}</p>
                </c:if>
                <c:if test="${requestScope.MESSAGE != null}">
                    <p style="color: greenyellow">${requestScope.MESSAGE}</p>
                </c:if>
                <input type="submit" name="action" value="ChangePass"/>
            </form>
                
        </div>
        

    </body>
</html>
