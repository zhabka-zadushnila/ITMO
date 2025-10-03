<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hit Results</title>
    <style>
                @font-face {
                    font-family: "Minecraft 1.1";
                    src: url("/static/fonts/mc_font.ttf");
                }
                @font-face {
                    font-family: "Alagard";
                    src: url("/static/fonts/alagard.ttf");
                }
                body {
                    font-family: "Minecraft 1.1";
                    color: #333;
                    line-height: 1.6;
                    margin: 0;
                    padding: 0;
                    background-color: #f5f5f5;
                }

                .header {
                    width: 80%;
                    margin: 0 25%;
                    overflow: hidden;
                }

                header h1 {
                    font-family: "sans-serif";
                    transition: all 1s;
                    transform: translateX(0%);
                }

                header:hover h1 {
                    transform: translateX(25%);
                    color: deepskyblue;
                }

                .form-group {
                    margin-bottom: 15px;
                }

                .form-container,
                .table-container {
                    margin: 20px 25%;
                    padding: 2%;
                    background: white;
                    border-radius: 10px;
                    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                }

                .form-group label {
                    display: block;
                    margin-bottom: 1%;
                    font-weight: bold;
                }

                button {
                    background-color: #3498db;
                    color: white;
                    border: none;
                    padding: 10px 15px;
                    border-radius: 4px;
                    cursor: pointer;
                    font-size: 16px;
                }

                button:hover {
                    background-color: #2980b9;
                }

                .error {
                    color: #e74c3c;
                    font-size: 0.9em;
                    margin-top: 5px;
                }
                .radio-label-pair,
                .checkbox-label-pair {
                    width: 8%;
                    text-align: center;
                    display: inline-block;
                    padding: 2px;
                    border-style: solid;
                    border-width: 1px;
                    border-radius: 5px;
                    border-color: black;
                    margin: 2px;
                }

                .fancy-input {
                    width: 100%;
                    height: 16pt;
                    font-family: "Minecraft 1.1";
                    transition: all 10s;
                }
                .fancy-input:focus {
                    width: 80px;
                }

                .input-form {
                    margin: 0 0 10px 0;
                }
                .areas-img {
                    display: block;
                    width: 65%;
                    margin: auto;
                }

                .results-table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 20px;
                }

                .results-table th,
                .results-table td {
                    border: 1px solid #ddd;
                    padding: 8px;
                    text-align: left;
                }

                .results-table th {
                    background-color: #f2f2f2;
                    font-family: "Alagard";
                }

                footer {
                    text-align: center;
                    margin-top: 2rem;
                    padding: 1rem 0;
                    background-color: #2c3e50;
                    color: white;
                }
                input[type="text"] {
                    border: 2px solid #ccc;
                    border-radius: 4px;
                    padding: 12px 0px 12px 8px;
                    box-sizing: border-box;
                }
                input[type="text"]:focus {
                    border-color: #3498db;
                    box-shadow: 0 0 8px rgba(52, 152, 219, 0.5);
                    outline: none;
                    padding: 36px 0px 36px 6px;
                }
            </style>
</head>
<body>

   <div class="table-container">
                   <h2>Результаты проверок</h2>
                   <table class="results-table">
                       <thead>
                           <tr>
                               <th>X</th>
                               <th>Y</th>
                               <th>R</th>
                               <th>Попадание</th>
                               <th>Время</th>
                               <th>Затрачено</th>
                           </tr>
                       </thead>
                       <tbody id="results-table-body">
                       <c:forEach items="${sessionScope.allUserHits.hits}" var="hit">
                                       <tr>
                                           <td>${hit.x}</td>
                                           <td>${hit.y}</td>
                                           <td>${hit.r}</td>


                                           <td>
                                               <c:choose>
                                                   <c:when test="${hit.isHit()}">Да</c:when>
                                                   <c:otherwise>Нет</c:otherwise>
                                               </c:choose>
                                           </td>

                                           <td>${hit.formattedTimestamp}</td>
                                           <td>${hit.executionTime}</td>
                                       </tr>
                                   </c:forEach></tbody>
                   </table>
               </div>
           </div>

    <c:if test="${empty sessionScope.allUserHits.hits}">
        <p>No hits have been recorded yet.</p>
    </c:if>

    <br>
    <a href="index.jsp">Make another attempt</a>

</body>
</html>