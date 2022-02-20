<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>SignUp</title>
    </head>
    <body>
        <h1>Sign up page</h1>
        <form action="/signUp" method="POST">
            First Name: <input type="text" name="firstName" required="required"/><br/>
            Last Name: <input type="text" name="lastName" required="required"/><br/>
            Phone Number: <input type="text" name="phoneNumber" required="required"/><br/>
            Email: <input type="email" name="email" required="required"/><br/>
            Password: <input type="password" name="password" required="required"/><br/>
            <input type="submit" value="sign up"/>
        </form>
    </body>
</html>
