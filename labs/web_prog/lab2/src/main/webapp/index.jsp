<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>



<html>
    <head>
        <link rel="stylesheet" href="static/css/style.css">
    </head>
    <body>
        <div class="main">
            <div class="left-block">
                <div class="main-block top-name-block">
                    P3232 Гузалов Тимур
                </div>
                <div class="main-block bottom-pick-block">
                    <form id="point-form" method="GET" action="/lab2-v1/api/checkHit">
                        <div class="x-choosing-block">
                            <label>X now is: <p style="display: inline" id="x-picked">?</p></label><br>
                            <input type ="hidden" id="x-val-input" name="x">
                            <button type="button" class="x-pick-button">-5</button>
                            <button type="button" class="x-pick-button">-4</button>
                            <button type="button" class="x-pick-button">-3</button>
                            <button type="button" class="x-pick-button">-2</button>
                            <button type="button" class="x-pick-button">-1</button>
                            <button type="button" class="x-pick-button">0</button>
                            <button type="button" class="x-pick-button">1</button>
                            <button type="button" class="x-pick-button">2</button>
                            <button type="button" class="x-pick-button">3</button>
                        </div>
                        <div class="y-choosing-block">
                            <label>Input Y please:</label>
                            <input class="y-input-field" placeholder="input number (-3...5)" type="text" name="y" id="y">
                        </div>
                        <div class="r-choosing-block">
                            <label>Input R please:</label><br>
                            <input type="radio" id="r-val-1" name="r" value="1" class="r-radio-input">
                            <label for="r-val-1" class="r-radio-label">1</label>

                            <input type="radio" id="r-val-2" name="r" value="2" class="r-radio-input">
                            <label for="r-val-2" class="r-radio-label">2</label>

                            <input type="radio" id="r-val-3" name="r" value="3" class="r-radio-input">
                            <label for="r-val-3" class="r-radio-label">3</label>

                            <input type="radio" id="r-val-4" name="r" value="4" class="r-radio-input">
                            <label for="r-val-4" class="r-radio-label">4</label>

                            <input type="radio" id="r-val-5" name="r" value="5" class="r-radio-input">
                            <label for="r-val-5" class="r-radio-label">5</label>
                        </div>
                        <div class="submit-block">
                            <button type="submit" class="submit-button">Submit</button>
                        </div>
                    </form>
                    <div class="errors-block">
                        <p class="error" id="x-error"></p>
                        <p class="error" id="y-error"></p>
                        <p class="error" id="r-error"></p>
                        <p class="error" id="response-error"></p>
                    </div>
                </div>
                <div class="main-block bottom-list-block">
                    <table class="results-table">
                        <thead>
                            <tr>
                                <th>X</th>
                                <th>Y</th>
                                <th>R</th>
                                <th>HV</th>
                                <th>T</th>
                                <th>E</th>
                            </tr>
                        </thead>
                        <tbody id="results-table-body">
                            <c:forEach
                                items="${sessionScope.allUserHits.hits}"
                                var="hit"
                            >
                                <tr>
                                    <td>${hit.x}</td>
                                    <td>${hit.y}</td>
                                    <td>${hit.r}</td>
    
                                    <td>
                                        <c:choose>
                                            <c:when test="${hit.isHit()}"
                                                >Да</c:when
                                            >
                                            <c:otherwise>Нет</c:otherwise>
                                        </c:choose>
                                    </td>
    
                                    <td>${hit.formattedTimestamp}</td>
                                    <td>${hit.executionTime}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="main-block right-block">
                <canvas id="main-canvas"></canvas>
            </div>
        </div>
    </body>
    <script src="static/js/canvas.js"></script>
    <script src="static/js/script.js"></script>
</html>