package org.ITMO.s435169;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.ITMO.s435169.classes.Hit;
import org.ITMO.s435169.classes.HitsResults;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "areaCheck")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        HitsResults userHits = (HitsResults) session.getAttribute("allUserHits");

        if (userHits == null) {
            userHits = new HitsResults();
            session.setAttribute("allUserHits", userHits);
        }

        long startTime = System.nanoTime();
        Map<String, String> valuesMap = parseQuery(request.getQueryString(), response);
        if(valuesMap != null){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            boolean isHit = Utility.checkArea(Double.valueOf(valuesMap.get("x")), Double.valueOf(valuesMap.get("y")), Double.valueOf(valuesMap.get("r")));
            //String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            long executionTime = (System.nanoTime() - startTime);
            Hit newHit = new Hit(Double.valueOf(valuesMap.get("x")), Double.valueOf(valuesMap.get("y")), Double.valueOf(valuesMap.get("r")), isHit, executionTime);
            userHits.addHit(newHit);
            response.sendRedirect(request.getContextPath() + "/results.jsp");



        }
    }

    public static Map<String, String> parseQuery(String queryString, HttpServletResponse response) throws IOException {
        Map<String, String> params = new HashMap<>();
        String[] paramsSplit = queryString.split("&");

        if (paramsSplit.length != 3){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный запрос");
            return null;
        }
        for (String param : paramsSplit) {
            if(!(param.matches("\\w+=-?\\d+.?\\d*") && param.length()>2 && param.length()<8)){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный запрос");
                return null;
            }
            String[] pair = param.split("=");

            if (pair.length == 2) {
                params.put(pair[0], pair[1]);
            }
        }
        if(!(params.containsKey("x") && params.containsKey("y") && params.containsKey("r") )){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный запрос");
            return null;
        }
        if (! List.of(-5,-4,-3,-2,-1,0,1, 2,3).contains(Integer.valueOf(params.get("x")))) {// Double.valueOf(params.get("x")) < -5 || Double.valueOf(params.get("x")) > 3){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный запрос");
            return null;
        }
        if (Double.parseDouble(params.get("y")) < -3 || Double.parseDouble(params.get("y")) > 5){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный запрос");
            return null;
        }

        if (! List.of(1,2,3,4,5).contains(Integer.valueOf(params.get("r")))){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный запрос");
            return null;
        }
        return params;
    }

}
