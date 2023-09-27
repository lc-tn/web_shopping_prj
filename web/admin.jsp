<%-- 
    Document   : admin
    Created on : Jun 21, 2023, 9:24:11 AM
    Author     : Lc_Tn
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="sample.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
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

            .form{
                margin-top: 20px;
            }

            .search{
                display: flex;
                flex-direction: column;
                margin: 0 auto;
            }

            .form{
                display: flex;
                justify-content: center;
            }

            .form input[type="submit"]{
                padding: 10px 20px;
                border-radius: 20px;
                align-items: center;
                background-color: rgba(71, 113, 149, 0.8);
                margin: 0 20px 30px 10px;
            }

            .form input[type="text"]{
                padding: 10px 20px;
                border: 1px solid #ccc;
                border-radius: 20px;
            }

            th{
                color: white;
                background-color: rgb(90, 91, 91);
            }

            th, td{
                text-align: center;
                padding: 15px;
            }

            td{

                border-bottom: 1px solid rgb(185, 184, 184);
            }

            input{
                text-align: center;
                border: none;
            }

            .link a{
                text-decoration: none;
                margin: 10px;
                background: #222324;
                color: white;
                border-radius: 8px;
                padding: 5px 12px;
                border: none;
            }

            .link{
                display: flex;
                width: 100%;
                justify-content: center;
            }

            img{
                width: 70px;
                height: auto;
            }
            .items {
                display: flex;
                justify-content: space-around;
                margin-bottom: 20px;
                border: 1px solid #ccc;
                border-radius: 8px;
                padding: 10px;
            }

            .left {
                display: flex;
                align-items: center;
            }

            .left img {
                width: 70px;
                height: auto;
                margin: 5px;
                border-radius: 10px;
            }

            .right {
                display: flex;
                align-items: center;
            }

            .left p, .right p{
                margin: 0 10px;
            }

            .items input{
                background: none;
                border: none;
            }

            .right input[type="text"] {
                width: 30px;
            }

            .right input[type="submit"] {
                border: none;
                cursor: pointer;
            }
            .uploadForm {
                max-width: 400px;
                margin: 0 auto;
            }

            .uploadForm input[type="text"],
            .uploadForm input[type="file"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            .uploadForm input[type="submit"] {
                background-color: #4CAF50;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                display: flex;
                justify-content: center;
            }

            .uploadForm input[type="submit"]:hover {
                background-color: #45a049;
            }

            .uploadForm hr {
                border: none;
                height: 1px;
                background-color: black;
                margin: 10px 0;
            }

            .uploadForm p {
                color: red;
            }
        </style>
    </head>
    <body>
        <nav class="navbar">
            <div class="navbar-brand">Welcome ${sessionScope.LOGIN_USER.fullName}</div>
            <div class="navbar-icons">
                <a href="MainController?action=Logout"><i class="fa-solid fa-right-from-bracket"></i></a>
            </div>
        </nav>

        <div class="display">
            <div class="search">
                <div class="form">
                    <form action="MainController">                        
                        <input type="text" name="search" placeholder="Search User">
                        <input type="submit" name="action" value="Search">
                        <input type="hidden" name="auth" value="admin">
                        <input type="submit" name="action" value="Product">
                        <input type="submit" name="action" value="ViewOrder">
                        <input type="submit" name="action" value="Upload">
                    </form>

                </div>


                <c:if test="${requestScope.LIST_USERS != null}">
                    <table>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>User ID</th>
                                <th>Full Name</th>
                                <th>Mail</th>
                                <th>Role ID</th>
                                <th>Password</th>
                                <th>Update</th>
                                <th>Delete</th>
                            </tr>
                        </thead>

                        <tbody>     
                            <c:forEach var="user" varStatus="counter" items="${requestScope.LIST_USERS}">

                            <form action="MainController" method="POST">
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>
                                        <input type="text" name="userID" value="${user.userID}" readonly=""/>
                                    </td>
                                    <td>
                                        <input type="text" name="fullName" value="${user.fullName}" required=""/>
                                    </td>
                                    <td>
                                        <input type="text" name="mail" value="${user.mail}" readonly=""/>
                                    </td>
                                    <td>
                                        <input type="text" name="roleID" value="${user.roleID}" required=""/>
                                    </td>
                                    <td>${user.password}</td>
                                    <td>
                                        <input type="submit" name="action" value="UpdateUser"/>
                                        <input type="hidden" name="search" value="${param.search}"/>
                                    </td>
                                    <td>
                                        <c:url var="deleteLink" value="MainController">
                                            <c:param name="action" value="Delete"></c:param>
                                            <c:param name="search" value="${param.search}"></c:param>
                                            <c:param name="userID" value="${user.userID}"></c:param>
                                        </c:url>
                                        <a href="${deleteLink}">Delete</a>
                                    </td>
                                </tr>
                            </form>

                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>

                <c:if test="${requestScope.LIST_PRODUCTS != null}">
                    <table>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Product ID</th>
                                <th>Image</th>
                                <th>Name</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Update</th>
                                <th>Delete</th>
                            </tr>
                        </thead>

                        <tbody>     
                            <c:forEach var="product" varStatus="counter" items="${requestScope.LIST_PRODUCTS}">
                            <form action="MainController" method="POST">
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>
                                        <input type="text" name="productID" readonly="" value="${product.id}">
                                    </td>
                                    <td>
                                        <img src="${product.image}">
                                        <input type="hidden" name="productImage" value="${product.image}">
                                    </td>

                                    <td>
                                        <input type="text" name="productName" required="" value="${product.name}">
                                    </td>
                                    <td>
                                        <input type="text" name="productPrice" required="" value="${product.price}">
                                    </td>
                                    <td>
                                        <input type="number" min="0" name="productQuantity" required="" value="${product.quantity}">
                                    </td>
                                    <td>
                                        <input type="submit" name="action" value="UpdateProduct"/>
                                    </td>
                                    <td>
                                        <c:url var="deleteLink" value="MainController">
                                            <c:param name="action" value="DeleteProduct"></c:param>
                                            <c:param name="productID" value="${product.id}"></c:param>
                                        </c:url>
                                        <a href="${deleteLink}">Delete</a>
                                    </td>
                                </tr> 
                            </form>
                        </c:forEach>
                        </tbody>
                    </table>



                    <div class="link">
                        <c:forEach begin="1" end="${sessionScope.TOTAL_PAGE}" varStatus="counter">
                            <a href="MainController?action=Shopping&&page=${counter.count}&&auth=admin">${counter.count}</a>
                        </c:forEach>
                    </div>

                </c:if>

                <c:if test="${requestScope.ORDER != null}">
                    <c:forEach var="order" items="${requestScope.ORDER}">
                        <table>
                            <thead>
                                <tr>
                                    <th>OrderID</th>
                                    <th>UserID</th>
                                    <th>Order's Date</th>
                                    <th>Total</th>
                                    <th>Address</th>
                                    <th>Status</th>
                                    <th>Edit</th>
                                </tr>
                            </thead>
                            <tbody>
                            <form action="MainController" method="POST">

                                <td>${order.orderID}
                                    <input type="hidden" name="orderID" value="${order.orderID}" readonly="" />
                                </td>
                                <td>${order.userID}</td>
                                <td>${order.date}</td>
                                <td>${order.total}</td>
                                <td>${order.address}</td>
                                <td>

                                    <c:if test="${order.status == 'Ordered'}">
                                        <select name="status">
                                            <option value="${order.status}">${order.status}</option>
                                            <option value="Cancel">Cancel</option>
                                        </select>
                                    </c:if>
                                    <c:if test="${order.status != 'Ordered'}">
                                        ${order.status}
                                    </c:if>

                                </td>
                                <td>
                                    <input type="submit" name="action" value="EditStatus" />
                                </td>
                            </form>
                            </tbody>
                        </table>
                        <c:if test="${requestScope.ORDER_DETAIL != null}">
                            <c:forEach var="product" varStatus="counter" items="${requestScope.ORDER_DETAIL}">
                                <c:if test="${product.orderID == order.orderID}">
                                    <form action="MainController" method="POST">
                                        <div class="items">
                                            <div class="left">
                                                <img src="${product.productImage}">
                                                <p>${product.productName}</p>
                                            </div>
                                            <div class="right">
                                                <p>${product.cartQuantity}</p>
                                                <p>${product.productPrice}</p>
                                            </div>
                                        </div>
                                    </form>
                                </c:if>
                                <c:set var="total" value="${product.total}"></c:set>
                                <c:set var="count" value="${counter.count}"></c:set>
                            </c:forEach>
                        </c:if>
                        <hr>
                    </c:forEach>
                </c:if>
                <c:if test="${requestScope.UPLOAD != null}">
                    <div class="uploadForm">
                        <form action="UploadController" method="POST" enctype="multipart/form-data">

                            <input type="text" name="productID" placeholder="Product's ID"><br>
                            <c:if test="${sessionScope.USER_ERROR != null}">
                                <p style="color: red;">${sessionScope.USER_ERROR.idError}</p>
                            </c:if>
                            <input type="text" name="productName" placeholder="Product's Name">
                            <hr>
                            <input type="text" name="imageName" placeholder="Image's Name">
                            <input type="file" name="photo" placeholder="Upload photo">
                            <hr>
                            <input type="text" name="price" placeholder="Product's price"><br>
                            <input type="text" name="quantity" placeholder="Quantity"><br>
                            <input type="submit" name="action" value="Upload">
                        </form>
                    </div>
                </c:if>

            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://kit.fontawesome.com/11861eee9a.js" crossorigin="anonymous"></script>
    </body>
</html>
