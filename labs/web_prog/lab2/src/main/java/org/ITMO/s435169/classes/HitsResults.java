package org.ITMO.s435169.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HitsResults implements Serializable {
    private List<Hit> hits;

    public HitsResults() {
        this.hits = new ArrayList<>();
    }

    public List<Hit> getHits() {
        List<Hit> copy_hits = new ArrayList<Hit>(hits);
        Collections.reverse(copy_hits);
        return copy_hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }

    public void addHit(Hit hit) {
        if (this.hits != null) {
            this.hits.add(hit);
        }
    }
}