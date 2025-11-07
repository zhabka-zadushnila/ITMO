package org.ITMO.s435169;

import org.ITMO.s435169.beans.Hit;

import java.util.Comparator;

public class idComparator implements Comparator<Hit> {
    @Override
    public int compare(Hit a, Hit b) {
        return a.getId().compareTo(b.getId()) * -1;
    }

}
