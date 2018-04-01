package tools;

import users.Patron;

import java.util.Comparator;
/**
 * Comparator to compare priority in PriorityQueue
 * From smaller to bigger priority
 */
public class ComparatorPriority implements Comparator<Patron> {
    @Override
    public int compare(Patron t1, Patron t2){
        return Integer.compare(t2.getPriority(), t1.getPriority());
    }
}
