<%-- 
    Document   : top1
    Created on : Jul 17, 2023, 10:38:35 AM
    Author     : Lc_Tn
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:if test="${requestScope.TOP1_USER != null}">
            UserID: ${requestScope.TOP1_USER.userID}<br>
            FullName: ${requestScope.TOP1_USER.fullName}
        </c:if>
    </body>
</html>
