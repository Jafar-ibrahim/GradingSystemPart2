<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard</title>
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
            width: 250px;
            height: 170px;
            transition: transform 0.2s ease-in-out;
            margin-top: 20px;
        }

        .card:hover {
            transform: scale(1.02);
        }

        .card h4 {
            font-size: 18px;
            margin-bottom: 15px;
        }

        .card p {
            font-size: 14px;
            color: #555;
            margin-bottom: 20px;
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
<h1>Instructor Dashboard</h1>
<div style="text-align: center; margin-top: 20px;">
    <h2>Welcome, Instructor  <strong>${user_fullName}</strong> </h2>
</div>
<div class="card-container">
    <div class="card">
        <h4>View Sections Grades</h4>
        <p>View your sections grades , and perform operations on them.</p>
        <form action="${pageContext.request.contextPath}/section_grades" method="GET">
            <input type="hidden" name="user_id" value="${user_id}">
            <input type="hidden" name="instructor_id" value="${instructor_id}">
            <button type="submit">View Grades</button>
        </form>
    </div>
    <div class="card">
        <h4>View Assigned Sections</h4>
        <p>View the sections you are assigned</p>
        <form action="${pageContext.request.contextPath}/InstructorSections" method="GET">
            <input type="hidden" name="user_id" value="${user_id}">
            <input type="hidden" name="instructor_id" value="${instructor_id}">
            <input type="hidden" name="role" value="INSTRUCTOR">
            <button type="submit">View Sections</button>
        </form>
    </div>

</div>
</body>
</html>
