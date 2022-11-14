package sg.edu.sportsschool.Helper;

import java.util.Comparator;

import sg.edu.sportsschool.Entities.Pass;

public class PassComparator implements Comparator<Pass> {
    @Override
    public int compare(Pass p1, Pass p2) {
        // if pass id are the same, compare by attraction id
        if (p1.getPassId().equals(p2.getPassId())) {
            return p1.getAttraction().getAttractionId() - p2.getAttraction().getAttractionId();
        }
        return p1.getPassId().compareTo(p2.getPassId());
    }
}
