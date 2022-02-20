<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>SignIn</title>
    </head>
    <body>
        <h1>Sign in page</h1>
        <form action="/signIn" method="POST">
            Email: <input type="email" name="email"/><br/>
            Password: <input type="password" name="password"/><br/>
            <input type="submit" value="sign in"/>
        </form>
    </body>
</html>
