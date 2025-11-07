package org.ITMO.s435169.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.ITMO.s435169.HitDAO;
import org.ITMO.s435169.idComparator;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Named("hitsManagerBean")
@ApplicationScoped
public class HitsManagerBean implements Serializable {
    private Hit hit;
    private HitDAO hitDAO;
    private List<Hit> hits;

    @PostConstruct
    public void init() {
        hit = new Hit();
        hitDAO = new HitDAO();
        System.out.println("hitDAO was created at least");
        hits = Collections.synchronizedList(hitDAO.getAllResults());
    }

    public void addHit() {
        hit.setHit(checkHit(hit.getX(), hit.getY(), hit.getR()));
        hitDAO.saveResult(hit);
        hits.add(hit);
        hit = new Hit();
    }

    private boolean checkHit(double x, double y, double r) {
        if (x >= -r && x <= 0 && y >= 0 && y <= r / 2) {
            return true;
        }
        if (x >= 0 && y <= 0 && y >= x - r/2) {
            return true;
        }
        if (x >= 0 && y >= 0 && x * x + y * y <= ((r/2) * (r/2))) {
            return true;
        }
        return false;
    }

    public List<Hit> getSortedHits(){
        List<Hit> hitsCopy = hits.subList(0, hits.size());
        hitsCopy.sort(new idComparator());
        return hitsCopy;
    }
    public String getHitsAsJson() {
        List<Hit> hits = getSortedHits();
        if (hits == null || hits.isEmpty()) {
            return "[]";
        }

        String json = hits.stream()
                .map(hit -> String.format(
                        "{\"x\":%s,\"y\":%s,\"hit\":%b}",
                        hit.getX(), hit.getY(), hit.isHit()
                ))
                .collect(Collectors.joining(",", "[", "]"));

        return json;
    }


    public Hit getHit() {
        return hit;
    }

    public void setHit(Hit hit) {
        this.hit = hit;
    }

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }
}
