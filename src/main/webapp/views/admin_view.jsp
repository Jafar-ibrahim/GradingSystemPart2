<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Arial', sans-serif;
        }

        body {
            background-color: #f4f4f4;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }



        h1 {
            text-align: center;
            color: #333;
            margin-top: 40px;
        }

        h2 {
            text-align: center;
            color: #555;
            margin-top: 10px;
        }

        .card-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center; /* Center cards horizontally */
            align-items: center; /* Center cards vertically */
            gap: 20px;
            width: 70%;
        }

        .card {
            background-color: #fff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            padding: 20px;
            text-align: center;
            width: 300px;
            height: 300px;
            transition: transform 0.2s ease-in-out;
            margin-top: 20px;
            position: relative;
        }
        .small{
            height: 230px;
        }

        .card:hover {
            transform: scale(1.02);
        }

        .card h4 {
            font-size: 30px;
            margin-bottom: 15px;
        }

        .card p {
            font-size: 20px;
            color: #555;
            margin-bottom: 20px;
            font-weight: bold;
        }

        button {
            background-color: cornflowerblue;
            color: white;
            padding: 8px 16px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
            font-size: 14px;
            transition: background-color 0.2s ease-in-out;
        }
        .button-container {
            position: absolute;
            bottom: 20px; /* Adjust as needed */
            left: 50%;
            transform: translateX(-50%); /* Center the button horizontally */
        }
        button:hover {
            background-color: #357ebd;
        }

        @media (max-width: 768px) {
            .card-container {
                flex-direction: column;
            }

            .card {
                margin-bottom: 20px;
            }
        }
    </style>
</head>
<body>
<h1>Admin Dashboard</h1>
<div style="text-align: center; margin-top: 20px;">
    <h2>Welcome, Admin  <strong>${user_fullName}</strong> </h2>
</div>
<div class="card-container">
    <div class="card small">
        <h4>Manage Courses</h4>
        <p>Perform crud operations on the courses .</p>
        <form action="${pageContext.request.contextPath}/admin_crud" method="GET">
            <input type="hidden" name="user_id" value="${user_id}">
            <input type="hidden" name="admin_id" value="${admin_id}">
            <input type="hidden" name="table" value="course">
            <div class="button-container">
                <button type="submit">View Courses</button>
            </div>
        </form>
    </div>
    <div class="card small">
        <h4>Manage Sections</h4>
        <p>Perform crud operations on the sections .</p>
        <form action="${pageContext.request.contextPath}/admin_crud" method="GET">
            <input type="hidden" name="user_id" value="${user_id}">
            <input type="hidden" name="admin_id" value="${admin_id}">
            <input type="hidden" name="table" value="section">
            <div class="button-container">
                <button type="submit">View Sections</button>
            </div>
        </form>
    </div>
    <div class="card small">
        <h4>Manage Users</h4>
        <p>Perform crud operations on the users(students & instructors).</p>
        <form action="${pageContext.request.contextPath}/admin_crud" method="GET">
            <input type="hidden" name="user_id" value="${user_id}">
            <input type="hidden" name="admin_id" value="${admin_id}">
            <input type="hidden" name="table" value="user">
            <div class="button-container">
                <button type="submit">View Users</button>
            </div>
        </form>
    </div>
    <div class="card">
        <h4>Manage Instructor Sections</h4>
        <p>Assign/Remove instructors to/from sections  .</p>
        <form action="${pageContext.request.contextPath}/admin_crud" method="GET">
            <input type="hidden" name="user_id" value="${user_id}">
            <input type="hidden" name="admin_id" value="${admin_id}">
            <input type="hidden" name="table" value="instructor_section">
            <div class="button-container">
                <button type="submit">View Instructor Sections</button>
            </div>
        </form>
    </div>
    <div class="card small">
        <h4>Manage Student Sections</h4>
        <p>Add/Remove students to/from sections.</p>
        <form action="${pageContext.request.contextPath}/admin_crud" method="GET">
            <input type="hidden" name="user_id" value="${user_id}">
            <input type="hidden" name="admin_id" value="${admin_id}">
            <input type="hidden" name="table" value="student_section">
            <div class="button-container">
                <button type="submit">Student Sections</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
