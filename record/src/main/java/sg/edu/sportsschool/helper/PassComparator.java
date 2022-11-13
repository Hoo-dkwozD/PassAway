package sg.edu.sportsschool.Helper;

import java.util.Comparator;

import sg.edu.sportsschool.Entities.Pass;

public class PassComparator implements Comparator<Pass> {
    @Override
    public int compare(Pass p1, Pass p2) {
        return p1.getPassId().compareTo(p2.getPassId());
    }
}
