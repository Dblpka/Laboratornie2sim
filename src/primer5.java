void main() {

}
public class Recursion {
    public static void main1(String[] args) {
        System.out.println(fact(5));
    }

    public static int fact(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fact(n - 2) + fact(n - 1);
        }
    }
}