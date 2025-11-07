package org.ITMO.s435169.beans;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "HITS")
public class Hit implements Serializable {


    @Id
    @GeneratedValue
    private Long id;

    private double x;
    private double y;
    private double r;
    private boolean isHit;



    private String hitText;
    private LocalDateTime timestamp;
    private long executionTime;

    public Hit() {
    }

    public Hit(double x, double y, double r, boolean isHit, long executionTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isHit = isHit;
        if(isHit){
            this.hitText = "Да";
        }
        else{
            this.hitText = "Нет";
        }
        this.timestamp = LocalDateTime.now();
        this.executionTime = executionTime;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getR() { return r; }
    public void setR(double r) { this.r = r; }

    public boolean isHit() { return isHit;
    }
    public void setHit(boolean isHit) {
        this.isHit = isHit;
        if(isHit){
            this.hitText = "Да";
        }
        else{
            this.hitText = "Нет";
        }
    }

    public long getExecutionTime() { return executionTime; }

    public void setExecutionTime(long executionTime) { this.executionTime = executionTime; }

    public String getHitText() {
        return hitText;
    }

    public void setHitText(String hitText) {
        this.hitText = hitText;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public String getFormattedTimestamp() {
        if (timestamp == null) return "";
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
