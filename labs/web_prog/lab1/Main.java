
import com.fastcgi.FCGIInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        FCGIInterface fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            long startTime = System.nanoTime();

            try {
                String queryString = FCGIInterface.request.params.getProperty("QUERY_STRING");
                if (queryString == null || queryString.isEmpty()) {
                    continue;
                }
                System.out.println(queryString);
                //Map<String, String> params = parseQuery(queryString);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static Map<String, String> parseQuery(String queryString){
        Map<String, String> params = new HashMap<>();
        for (String param : queryString.split("&")) {
            String[] pair = param.split("=");
            if (pair.length > 1) {
                String key = pair[0];
                String value = pair[1];
                params.put(key, value);
            }
        }
        return params;
    }

}
