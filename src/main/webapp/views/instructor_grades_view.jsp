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

        table {
            width: 100%; /* Full width within the container */
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

        /* Target the "Student Name" column */
        th:nth-child(2), td:nth-child(2) {
            width: 200px; /* Adjust width as needed */
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
            margin-top: 15px; /* Adjust spacing as desired */
        }
    </style>
</head>

<body>
<div class="container">
    <h2>Section ${section_id} Grades</h2>
    <h3>Course : ${course_name}</h3>
    <table>
        <thead>
        <tr>
            <th>Student ID</th>
            <th>Student Name</th>
            <th>Grade</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="grade" items="${section_grades}">
            <tr>
                <td>${grade.getStudentId()}</td>
                <td>${grade.getStudentFullName()}</td>
                <td>${grade.getGrade()}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<form action="${pageContext.request.contextPath}/section_grades" method="get">
<div class="section-selector">
    <h3>Select Section:</h3>
    <select id="sectionId" name="section_id">
        <c:forEach var="section" items="${sections}">
            <option value="${section.getId()}">${section.getId()}</option>
        </c:forEach>
    </select>
    <br>
    <button type="submit">View Grades</button>
</div>
    </form>
</body>

</html>
