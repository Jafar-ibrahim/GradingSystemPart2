<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Courses</title>
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
            color: #D8000C;
            background-color: #FFBABA;
        }

        .success-message {
            color: #270;
            background-color: #DFF2BF;
        }
        .error-message ,
        .success-message {
            margin: 10px 0;
            padding: 10px;
            border-radius: 3px 3px 3px 3px;
        }
        .error-message.active ,
        .success-message.active {
            display: block;
        }
        .blocked {
            background-color: #666; /* Medium-dark gray color for disabled state */
            color: #fff; /* Text color for better contrast */
        }
        .blocked-text {
            color: red;
            font-size: 12px;
            margin-top: 5px;
            width: 250px;
        }

        .actions {
            width: 50%; /* 50% of remaining screen width */
            padding: 30px; /* Padding for content within the section */
            display: flex;
            flex-direction: column;
        }

        select, input {
            margin-bottom: 10px; /* Add spacing between dropdown and inputs */
        }
    </style>
    <script type="text/javascript">
        function handleActionChange() {
            var selectedAction = document.getElementById("Action").value;

            // Get all input elements with the class 'dynamic-input'
            var firstInputElement = document.querySelector('.first-input');

            // Iterate through input elements
            if (firstInputElement) {
                // Check if the element is disabled
                firstInputElement.disabled = (selectedAction === 'add');
                var isDisabled = firstInputElement.disabled;

                // Find the corresponding blocked text element
                var blockedTextElement = firstInputElement.nextElementSibling;

                // Show or hide the blocked text based on the selection and disabled state
                if (isDisabled) {
                    blockedTextElement.style.display = 'block';
                    firstInputElement.value = '';
                    firstInputElement.classList.add('blocked');
                } else {
                    blockedTextElement.style.display = 'none';
                    firstInputElement.classList.remove('blocked');
                }
            }
        }
    </script>

</head>

<body>


<div class="container">
    <h2>${tableCapitalized} Table</h2>
    <table>
        <thead>
        <tr>
            <c:forEach var="column" items="${columns}">
                <th>${column}</th>
            </c:forEach>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="entry" items="${content}">
            <tr>
                <c:forEach var="value" items="${entry}">
                    <td>${value}</td>
                </c:forEach>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<form action="${pageContext.request.contextPath}/admin_crud" method="post">
    <div class="actions">
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
        <h3>Course Actions:</h3>
        <select id="Action" name="action" onchange="handleActionChange()">
            <option value="add">Add ${table}</option>
            <option value="delete">Delete ${table}</option>
            <c:if test="${param.table ne 'student_section' and param.table ne 'instructor_section'}">
                <option value="update" selected >Modify ${table}</option>
            </c:if>
        </select>
        <c:forEach var="column" items="${columns}" varStatus="loopStatus">
            <br>
            <label for="${column}">${column}</label>
            <c:if test="${param.table ne 'student_section' and param.table ne 'instructor_section'}">
                <input type="text" id="${column}" name="${column}" class="${loopStatus.index == 0 ? 'first-input' : ''}">
                <div class="blocked-text" style="display: none;">Blocked ( ID is are auto generated )</div>
            </c:if>
            <c:if test="${param.table == 'student_section' or param.table == 'instructor_section'}">
                <input type="text" id="${column}" name="${column}">
            </c:if>
            <br>
        </c:forEach>

        <input type="hidden" name="user_id" value="${user_id}">
        <input type="hidden" name="instructor_id" value="${admin_id}">
        <input type="hidden" name="table" value="<%= request.getAttribute("table") %>">
        <button type="submit" >Save</button>
    </div>
</form>

</body>

</html>
