<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Profile</title>
        <style>
            table {
                border: 1px solid rgb(182, 3, 3);
                border-collapse: collapse;
            }
            th {
                border: 1px solid rgb(182, 3, 3);
                padding: 4px;
                text-align: center;
            }
            td {
                border: 1px solid rgb(182, 3, 3);
                padding: 4px;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <h1><c:out value="Welcome back ${sessionScope.user.getFirstName()} ${sessionScope.user.getLastName()}!"/></h1>
        <table>
            <tr>
                <th style="text-align:center">Date</th>
                <th style="text-align:center">Time</th>
                <th style="text-align:center">Ip</th>
            </tr>
            <c:forEach var="auth" items="${sessionScope.authentications}">
                <tr>
                    <td style="text-align:left">${auth.getAuthDate()}</td>
                    <td style="text-align:left">${auth.getAuthTime()}</td>
                    <td style="text-align:left">${auth.getAuthIp()}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
