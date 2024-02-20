<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Section Grades Lookup</title>
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

        label {
            display: inline-block;
            width: 120px;
            text-align: right;
            margin-bottom: 5px;
            font-weight: bold;
            color: #777;
        }

        input[type="text"] {
            width: 200px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 15px;
        }

        button {
            background-color: #428bca;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #357ebd;
        }
    </style>
</head>

<body>
<div class="container">
    <h2>Section Grades Lookup</h2>
    <form action="${pageContext.request.contextPath}/section_grades" method="get">
        <label for="sectionId">Enter Section ID:</label>
        <input type="text" id="sectionId" name="section_id" required placeholder="Enter Section ID" />
        <br>
        <button type="submit">Get Grades</button>
    </form>
</div>
</body>

</html>
