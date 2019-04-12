package shared;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set g = new LinkedHashSet();
        g.add(2); g.add(1); g.add(3); g.add(2); g.add(4); g.remove(3);
        System.out.println(Arrays.toString(g.toArray()));
    }
}
