import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Testcode {
    public static void main(String[] args) {

        Set<Integer> s = new HashSet<>();
        Set<Set<Integer>> ss = new HashSet<>();
        ss.add(s);
        s.add(1);
        s.add(2);
        s.add(3);
        Set<Integer> s1 = new HashSet<>();
        s1.add(1);
        s1.add(2);
        s1.add(3);
        for (Set<Integer> ex : ss) {
            if (s1.equals(ex))
                System.out.println("true");
        }
    }
}
