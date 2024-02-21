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
            width: 50%; /* Adjust width as needed */
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
        .error-message {
            color: #d9534f;
            background-color: #f9f2f4;
            border: 1px solid #dab2b5;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .success-message {
            color: #5bc0de;
            background-color: #eef7fa;
            border: 1px solid #add9e4;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .error-message.active ,
        .success-message.active {
            display: block;
        }

        .grade-actions {
            width: 50%; /* 50% of remaining screen width */
            padding: 30px; /* Padding for content within the section */
            display: flex;
            flex-direction: column;
        }

        select, input {
            margin-bottom: 10px; /* Add spacing between dropdown and inputs */
        }
    </style>

</head>

<body>


<div class="container">
    <h2>Section ${section_id} Grades</h2>
    <h3>Course : ${course_name}</h3>
    <h3>Instructor : ${instructor_name}</h3>
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
        <input type="hidden" name="user_id" value="${user_id}">
        <input type="hidden" name="instructor_id" value="${instructor_id}">
        <input type="hidden" name="role" value="INSTRUCTOR">
        <button type="submit">View Grades</button>
    </div>
</form>

<form action="${pageContext.request.contextPath}/grades_crud" method="get">
<div class="grade-actions">
    <c:if test="${not empty error}">
        <div class="error-message active">
            <span>${error}</span>
        </div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="success-message active">
            <span>${success}</span>
        </div>
    </c:if>
    <h3>Grade Actions:</h3>
    <select id="gradeAction" name="grade_action">
        <option value="add">Add Grade</option>
        <option value="delete">Delete Grade</option>
        <option value="modify">Modify Grade</option>
    </select>
    <br>
    <label for="studentId">Student ID:</label>
    <input type="text" id="studentId" name="student_id">
    <br>
    <label for="grade">Grade:</label>
    <input type="text" id="grade" name="grade">
    <br>
    <input type="hidden" name="section_id" value="${section_id}">
    <input type="hidden" name="user_id" value="${user_id}">
    <input type="hidden" name="instructor_id" value="${instructor_id}">

    <button type="submit" >Save</button>
</div>
</form>

</body>

</html>
