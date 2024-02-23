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
            padding-top: 80px;
            font-family: sans-serif;
            display: flex;
            background: #e0e6f6 linear-gradient(to right, #e0e6f6, #d6e2ef);
        }

        .container {
            background-color: #fff;
            border-radius: 10px;
            padding: 30px; /* Padding for content within the container */
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            text-align: left; /* Align content to left */
            width: 50%; /* 50% of screen width */
            margin: 0 auto;

        }

        h2 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }
        input[type="text"] {
            width: 200px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 15px;
        }

        table {
            width: 50%; /* Full width within the container */
            border-collapse: collapse;
            margin-top: 20px; /* Add top margin for spacing */
            padding: 15px; /* Add padding for table content */
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

        .section-selector {
            width: 50%; /* 50% of remaining screen width */
            padding: 30px; /* Padding for content within the section */
        }

        select {
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            width: 300px; /* Increased width for the dropdown (1.5 times bigger) */
            margin-bottom: 10px; /* Add spacing below the dropdown */
            padding: 10px; /* Add padding for dropdown options */
        }

        button {
            background-color: #428bca;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        /* Add margin-top to the button */
        button {
            width: 100px;
            length: 100px;
            margin-top: 15px; /* Adjust spacing as desired */
        }

        select, input {
            margin-bottom: 10px; /* Add spacing between dropdown and inputs */
        }
        .logout-button,
        .back-link{
            position: absolute;
            background-color: #428bca;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            font-size: 20px;
            text-decoration:none
        }
        .logout-button:hover {
            background-color: #cc0000;
        }
        .logout-button{
            top: 25px;
            left: 25px;
        }
        .back-link {
            top: 25px;
            right: 25px;
        }
        .back-link:hover {
            background-color: #cccc00;
        }
    </style>

</head>

<body>


<div class="container">
    <form action="${pageContext.request.contextPath}/logout" method="post">
        <button type="submit" class="logout-button">Logout</button>
    </form>
    <h2>Section ${section_id} Statistical Info</h2>
    <h3>Course : ${course_name}</h3>
    <h3>Instructor : ${instructor_name}</h3>
    <table>
        <thead>
        <tr>
            <th>Min</th>
            <th>Max</th>
            <th>Median</th>
            <th>Average</th>
        </tr>
        </thead>
        <tbody>

        <tr>
            <td>${statistics.get(0)}</td>
            <td>${statistics.get(1)}</td>
            <td>${statistics.get(2)}</td>
            <td>${statistics.get(3)}</td>
        </tr>

        </tbody>
    </table>
</div>
<form action="${pageContext.request.contextPath}/instructor/statistical_info" method="get">
    <div class="section-selector">
        <h3>Select Section:</h3>
        <select id="sectionId" name="section_id">
            <c:forEach var="section" items="${sections}">
                <option value="${section.getId()}">${section.getId()}</option>
            </c:forEach>
        </select>
        <br>
        <input type="hidden" name="user_id" value="${user_id}">
        <input type="hidden" name="instructor_id" value="${instructor_id}">
        <button type="submit">View Grades</button>
    </div>
</form>

</body>

</html>
