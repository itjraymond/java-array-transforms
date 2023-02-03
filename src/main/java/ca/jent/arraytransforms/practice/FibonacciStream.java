package ca.jent.arraytransforms.practice;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class FibonacciStream {

    public static void main(String[] args) {
        System.out.println(fib1(8));

        System.out.println(fib(100L));
    }

    // long -> long
    public static long fib1(long n) {
        long[] res = Stream.iterate(
                new long[]{0, 1},
                p -> new long[]{p[1], p[0] + p[1]}
        ).limit(n).skip(n - 1).findFirst().get();
        return res[1];
    }

    // BigDecimal -> BigDecimal
    public static BigDecimal fib(long n) {
        return Stream.iterate(
                new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ONE},
                p -> new BigDecimal[]{p[1], p[0].add(p[1])}
        ).limit(n).skip(n-1).findFirst().get()[1];
    }
}
