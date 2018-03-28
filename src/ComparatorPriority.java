import java.util.Comparator;
/**
 * Comparator to compare priority in PriorityQueue
 * From smaller to bigger priority
 */
public class ComparatorPriority implements Comparator<Patron> {
    @Override
    public int compare(Patron t1, Patron t2){
        if (t1.getPriority() > t2.getPriority()){
            return -1;
        } else if (t2.getPriority() > t1.getPriority()){
            return 1;
        } else{
            return 0;
        }
    }
}
