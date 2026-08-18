import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Test {
    public static void main(String[] args) {
        List<Integer> ll = new LinkedList<>();
        ll.add(0);
        ll.add(1);
        ll.add(4);
        ll.add(5);
        ll.get(2);
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        ll.add(2, 2);
        ll.add(3, 3);
        ll.add(6, 6);
        for (int i : ll) {
            System.out.print(i + " ");
        }
        String ts = "abcde";

        char[] tsarr = ts.toCharArray();
        for (char cc : tsarr) {

        }
        System.out.println(Arrays.toString(tsarr));

        int[] arr = new int[]{1,2,3,4,5};
        System.out.println(Arrays.toString(arr));

    }
}
