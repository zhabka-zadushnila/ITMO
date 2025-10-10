package org.ITMO.s435169;

public class Utility {

    public static boolean checkArea(Double x, Double y, Double r) {
        if(inElipse(x,y, r, r/2)){
            if(! inElipse(x-r/2, y+r/2, r/8, r/4)){
                if(! inElipse(x+r/2, y+r/2, r/8, r/4)){
                    if(! inElipse(x-r/4, y+(3*r)/5, r/8, r/4)){
                        if(! inElipse(x+r/4, y+(3*r)/5, r/8, r/4)){
                            if(! inElipse(x-r/3, y-r/2, r/8, r/3)){
                                if(! inElipse(x+r/3, y-r/2, r/8, r/3)){
                                    if(!(((-1*r/6 < x ) && (x < r/6))&& (y>r/3) ) ){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean inElipse(Double x, Double y, Double r1, Double r2) {
        return (x*x)/(r1*r1) + (y*y)/(r2*r2) <= 1;
    }
}
