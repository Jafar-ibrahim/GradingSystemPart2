<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Section Grades</title>
    <style>
        body {
            font-family: sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #e0e6f6;
            background: linear-gradient(to right, #e0e6f6, #d6e2ef);
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

        h2 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 10px;
            border: 1px solid #ccc;
        }

        th {
            background-color: #428bca;
            color: #fff;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
    </style>
</head>

<body>
<div class="container">
    <h2>Section Grades</h2>
    <table>
        <thead>
        <tr>
            <th>Student ID</th>
            <th>Grade</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="grade" items="${sectionGrades}">
            <tr>
                <td>${grade.key}</td>
                <td>${grade.value}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>

</html>
