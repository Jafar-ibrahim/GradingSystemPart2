<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login Portal</title>
  <style>
    body {
      font-family: sans-serif;
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      background: #e0e6f6 linear-gradient(to right, #e0e6f6, #d6e2ef);
    }

    .container {
      background-color: #fff;
      border-radius: 10px;
      padding: 30px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
      text-align: center;
      width: 350px;
      margin: 0 auto;
    }

    h1 {
      margin-bottom: 20px;
      font-size: 24px;
      color: #333;
    }

    label {
      display: inline-block;
      width: 120px;
      text-align: right;
      margin-bottom: 5px;
      font-weight: bold;
      color: #777;
    }

    input[type="text"],
    input[type="password"] {
      width: 200px;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 5px;
      margin-bottom: 15px;
    }

    input[type="submit"] {
      background-color: #428bca;
      color: #fff;
      padding: 10px 20px;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    input[type="submit"]:hover {
      background-color: #357ebd;
    }

    .error-message {
      background-color: #f44336;
      color: white;
      padding: 10px;
      border-radius: 5px;
      margin-bottom: 15px;
      display: none;
    }

    .error-message.active {
      display: block;
    }

    @media (max-width: 500px) {
      .container {
        width: 90%;
      }
    }
    </style>
</head>

<body>
<div class="container">
  <h1>Grading System - Login </h1>
  <form action="${pageContext.request.contextPath}/auth" method="POST">
    <label for="username">Username:</label>
    <input id="username" name="username" type="text" required placeholder="Enter Username" />
    <br>
    <label for="password">Password:</label>
    <input id="password" name="password" type="password" placeholder="Enter Password" required />
    <br>
    <input type="submit" value="Login" />
    <c:if test="${not empty error}">
      <div class="error-message active">
        <span>${error}</span>
      </div>
    </c:if>
  </form>
</div>
</body>

</html>
