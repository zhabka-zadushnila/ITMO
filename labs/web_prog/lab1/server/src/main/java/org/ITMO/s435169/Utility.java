package org.ITMO.s435169;

public class Utility {

    public static boolean checkArea(Double x, Double y, Double r){
        if(x >= 0 && y >= 0){
            if (x <= r && y <= r/2) {
                return true;
            }
        } else if (x < 0 && y >= 0) {
            if (-1*x + 2*y <= r){
                return true;
            }
        } else if (x < 0 && y < 0) {
            return false;
        } else if (x > 0 && y < 0) {
            if(x*x + y*y < (r/2)*(r/2)) {
                return true;
            }
        }

        return false;
    }
}
