<%-- 
    Document   : shopping
    Created on : Jun 21, 2023, 2:12:58 PM
    Author     : Lc_Tn
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <title>Shopping Page</title>
        <style>
            .navbar {
                background-color: black;
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 5px 60px;
            }

            .navbar-brand {
                font-size: 24px;
                font-weight: bold;
                color: white;
            }

            .navbar-icons {
                display: flex;
                gap: 10px;
                font-size: 20px;
                color: white
            }

            .navbar-icons i {
                cursor: pointer;
            }

            body{
                background-color: #3e3e3f;
            }

            .row{
                margin: 5px 10px;

            }

            .col-md-4{
                width: 345.24px;
                margin: 10px;
                background-color: #323232;
                border-radius: 24px;
                padding: 0;
                /*display: flex; flex-direction: column;*/
            }

            img{
                height: 95%; 
            }
            .img{
                background-color: none;
                height: 250px;
                width: 360px;
                padding-top: 10px;
            }

            input[type="text"]{
                color: white;
                background: none;
                padding: 5px 20px;
                margin: 10px;
                border: none;
                text-align: center;
                font-size: 1.3rem;
            }
            input[type="submit"], input[type="number"], a{
                margin-bottom: 20px;
                background: #222324;
                color: white;
                border-radius: 8px;
                padding: 10px 20px;
                border: none;
            }
            input[type="number"]{
                width: 70px;    
            }

            input[type="hidden"]{
                display: none;   
            }

            .link{
                display: flex;
                width: 100%;
                justify-content: center;
            }

            a{
                text-decoration: none;
                margin: 0px 10px;
            }
            p{
                text-align: center;
                color: greenyellow;
            }
        </style>
    </head>
    <body>
        <!--                <form action="MainController">
                            <select name="select" id="">
                                <option value="P01-Iphone 14 Pro Max-1000">Iphone 14 Pro Max</option>
                                <option value="P02-SamSung Galaxy S23 Ultra-2000">SamSung Galaxy S3 Ultra</option>
                                <option value="P03-Google Pixel 7 Pro-500">Google Pixel 7 Pro</option>
                                <option value="P04-Iphone 13 Pro Max-2000">Iphone 13 Pro Max</option>
                            </select>
                            <input name="quantity" type="number" min="1" value="1"><br>
                            <input type="submit" name="action" value="Add">
                            <input type="submit" name="action" value="View">
                            <br/>${sessionScope.MESSAGE}
                        </form>-->

        <nav class="navbar">
            <div class="navbar-brand">Welcome ${sessionScope.LOGIN_USER.fullName}</div>
            <div class="navbar-icons">
                <form action="MainController" method="POST">
                    <input type="hidden" name="auth" value="user">
                    <input type="hidden" name="page" value="1">
                    <input type="text" name="search" value="${sessionScope.SEARCH}" placeholder="Search Product">
                    <input type="submit" name="action" value="Product">
                </form>

                <c:if test="${sessionScope.LOGIN_USER == null}">
                    <a href="login.jsp" style="background: none;"><i class="fas fa-user"></i></a>
                    </c:if>

                <c:if test="${sessionScope.LOGIN_USER != null}">
                    <a href="MainController?action=ViewCart"><i class="fas fa-cart-shopping"></i></a>
                    <a href="MainController?action=Logout"><i class="fa-solid fa-right-from-bracket"></i></a>
                    </c:if>
            </div>
        </nav>

        <div class="container text-center">
            <div class="row">
                <c:forEach var="product" items="${requestScope.LIST_PRODUCTS}">
                    <div class="col-md-4">
                        <form action="MainController">
                            <div class="img">
                                <input name="image" type="hidden" value="${product.image}"> <br>
                                <img src="${product.image}">
                            </div>
                            <input name="productID" type="text" value="${product.id}"><br>
                            <input name="productName" type="text" value="${product.name}"><br>
                            <input name="price" type="text" value="${product.price}"><br>
                            <input name="quantity" type="number" min="1" value="1"><br>
                            <input type="submit" name="action" value="Add">
                        </form>
                    </div>
                </c:forEach>
            </div>
        </div>
        <c:if test="${requestScope.MESSAGE != null}">
            <p>${requestScope.MESSAGE}</p>
        </c:if>
        <div class="link">
            <c:forEach begin="1" end="${sessionScope.TOTAL_PAGE}" varStatus="counter">
                <a href="MainController?action=Shopping&&page=${counter.count}&&auth=user&&search=${sessionScope.SEARCH}">${counter.count}</a>
            </c:forEach>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://kit.fontawesome.com/11861eee9a.js" crossorigin="anonymous"></script>
    </body>
</html>
