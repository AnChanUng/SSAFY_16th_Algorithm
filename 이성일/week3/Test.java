import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer>[] list = new ArrayList[2];
        list[1].add(1);
        list[1].add(2);
        for (int i : list[1]) {
            System.out.println(i);
        }
        System.out.println();
    }
}
