package org.ITMO.s435169;

public class Utility {

    public static boolean checkArea(Double x, Double y, Double r){
        if((x*x)/(r*r) + (y*y)/((r/2)*(r/2)) <= 1){
            if( ((x-r/2)*(x-r/2))/((r/8)*(r/8)) + ((y+r/2)*(y+r/2))/((r/4)*(r/4)) >1){
                if(((x+r/2)*(x+r/2))/((r/8)*(r/8)) + ((y+r/2)*(y+r/2))/((r/4)*(r/4)) >1){
                    if(((x-r/4)*(x-r/4))/((r/8)*(r/8)) + ((y+(3*r)/5)*(y+(3*r)/5))/((r/4)*(r/4)) >1){
                        if(((x+r/4)*(x+r/4))/((r/8)*(r/8)) + ((y+(3*r)/5)*(y+(3*r)/5))/((r/4)*(r/4)) >1){
                            if(((x-r/3)*(x-r/3))/((r/8)*(r/8)) + ((y-r/2)*(y-r/2))/((r/3)*(r/3)) >1){
                                if(((x+r/3)*(x+r/3))/((r/8)*(r/8)) + ((y-r/2)*(y-r/2))/((r/3)*(r/3)) >1){
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
}
