import java.util.ArrayList;

public class MainTest {
    public static void main(String[] args) {
        ArrayList arr = new ArrayList();

        arr.add("nick1");
        arr.add("nick2");

        if (arr.contains("nick3")) {
            System.out.println(arr.contains("nick2"));
        }
        System.out.println(arr);
    }
}
