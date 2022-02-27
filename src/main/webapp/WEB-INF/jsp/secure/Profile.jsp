<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
        <h1><c:out value="${sessionScope.user.getFirstName()} ${sessionScope.user.getLastName()}, ${sessionScope.user.getEmail()}"/></h1>
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
        <hr/>
        <table>
            <tr>
                <th style="text-align:center">File name</th>
                <th style="text-align:center">Size</th>
                <th style="text-align:center">MIME</th>
            </tr>
            <c:forEach var="image" items="${sessionScope.images}">
                <tr>
                    <td style="text-align:left">
                        <a href="/images/${image.getUniqueName()}" target="_blank">
                            ${image.getOriginalName()}
                        </a>
                    </td>
                    <td style="text-align:left">
                        <fmt:formatNumber maxFractionDigits="2">
                            ${image.getImageSize()}
                        </fmt:formatNumber>KB
                    </td>
                    <td style="text-align:left">${image.getMIME()}</td>
                </tr>
            </c:forEach>
        </table>
        <hr/>
        <form enctype="multipart/form-data" action="/images" method="POST">
            <h2>Upload new image</h2>
            <input type="file" name="image" multiple accept="*/*"><br/>
            <input type="submit" value="upload">
        </form>
    </body>
</html>
