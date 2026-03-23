package project20280.tree;

public class Fib {
    public static void main(String[] args) {
        Fib fib = new Fib();
        int n = 10;
        long timeTaken = 0;
        long start = 0;
         long end = 0;
         System.out.println("____Fib____");
        while (timeTaken < 1000) {
            start = System.currentTimeMillis();
            fib.fib(n);
            end = System.currentTimeMillis();

            timeTaken = end - start;
            n++;
        }
        System.out.println("Time taken: " + timeTaken + "ms");
        System.out.println("n: " + n);

        System.out.println("____Trib____");
        timeTaken = 0;
        while (timeTaken < 1000) {
            start = System.currentTimeMillis();
            fib.fib(n);
            end = System.currentTimeMillis();

            timeTaken = end - start;
            n++;
        }
        System.out.println("Time taken: " + timeTaken + "ms");
        System.out.println("n: " + n);

    }
    public int fib(int n) {
        if (n <= 1) return n;
        return fib(n - 1) + fib(n - 2);
    }
    public int trib(int n) {
        if (n <= 1) return n;
        if (n == 2) return 1;
        return trib(n - 1) + trib(n - 2) + trib(n - 3);
    }

    public int McCarthy91(int n) {
        if (n > 100) return n - 10;
        return McCarthy91(McCarthy91(n + 11));
    }
}
//60000
