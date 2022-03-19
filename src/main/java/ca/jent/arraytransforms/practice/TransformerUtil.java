package ca.jent.arraytransforms.practice;

import com.sun.jdi.CharType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransformerUtil {

    // int[] -> int[]
    // remove negative integer
    public int[] removeNegative(int[] xs) {
        return Arrays.stream(xs).filter(x -> x >= 0).toArray();
    }

    // int[] -> int[]
    // remove duplicate
    public int[] removeDuplicate(int[] xs) {
        return Arrays.stream(xs).distinct().toArray();
    }

    // int[] -> int[]
    // remove negative and duplicate integer
    public Function<Integer[], Integer[]> removeNegativeAndDuplicate =  i -> i; // TODO later

    // int[] -> String[]
    public String[] mapIntToString(int[] xs) {
        return Arrays.stream(xs).mapToObj(String::valueOf).toArray(String[]::new);
    }

    // convert List<> to []
    // List<A> -> A[]
//    public <A> A[] mapToA(List<A> list) {
//        return list.stream().toArray(A[]::new); // compile error.
//        you can not create generic arrays in Java because compiler does not know exactly what A represents. In other words creation of array of a non-reifiable type (JLS §4.7) is not allowed in Java.
//    }
//  INSTEAD:
    public <A> A[] mapToArray(A[] a) {
        return new ArrayList<A>(a.length).toArray(a);  // TODO nope, make no sense :-)
    }

    // String -> long
    // Return the number of grouped chars that are odd
    // e.g. "abcaab" -> 2
    // "abcaab" has "a" -> 3 (odd)
    //              "b" -> 2 (even)
    //              "c" -> 1 (odd)
    // So only 2 chars "a" and "c" when grouped has a odd count.
    public static long countRepeatedCharThatIsRepeatedOddTime(String s) {
        char[] chars = s.toCharArray();
        return Arrays.stream(s.split(""))
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                .values()
                .stream()
                .filter(count -> count % 2 != 0)
                .count();
    }

    // count words occurence
    // String -> Map<String, Long>
    public static Map<String, Long> countWordsOccurence(String s) {
        return Arrays.stream(s.split(" "))
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
    }



    // char[] -> char[]
    char[] reverse(char[] cs) {
        if (cs.length == 0) return cs;
        char head = cs[0];
        char[] tail = Arrays.copyOfRange(cs, 1, cs.length);
        return concat(reverse(tail), head);
        // concat: [] + ['e'] + ['d'] + ['c'] + ['b'] + ['a']
        // ---------------------------------------------------
        // concat([], ['e'])        -> ['e']
        // concat(['e'], ['d'])     -> ['e','d']
        // concat(['e','d'], ['c']) -> ['e','d','c']
        // ...
    }
    // char[] -> char[]
    char[] reverseRecursive(char[] cs) {
        if (cs.length == 0) return cs;
        char head = cs[0];
        char[] tail = Arrays.copyOfRange(cs, 1, cs.length);
        return concat(reverseRecursive(tail), head);
    }
    // (char[], char) -> char[]
    private char[] concat(char[] tail, char head) {
        char[] result = new char[tail.length+1];
        // copy the tail into result
        System.arraycopy(tail, 0, result, 0, tail.length);
        // copy head into result (append)
        System.arraycopy(new char[]{head}, 0, result, tail.length, 1);
        return result;
    }

    /**
     * reverse a String recursive using String type only
     * String -> String
     */
    public static String reverse(String s) {
        if (s.isBlank()) return "";
        String head = s.substring(0,1);
        String tail = s.substring(1);
        return reverse(tail) + head;
    }

    /**
     * reverse a String using accumulator
     * *** Because Java is not able to compile a TCE (Tail Call Elimination), it
     * will still be subject to stack overflow.
     * No TCE -> should never use recursion in Java :-)
     * (String, String) -> String
     */
    public static String reverse(String s, String acc) {
        if (s.isBlank()) return acc;

        String head = s.substring(0,1);
        String tail = s.substring(1);
        acc = head + acc;
        return reverse(tail, acc);
    }

    /**
     * reverse a String but try to encapsulate the accumulator
     * String -> String
     */
    // ATTEMPT ONE
//    public static String reversing(String s) {
//         BiFunction<String, String, String> rev = (String str, String acc) -> {
//            if (str.isBlank()) return acc;
//
//            String head = str.substring(0,1);
//            String tail = str.substring(1);
//            acc = head + acc;
//            return rev.apply(tail, acc); // COMPILE ERROR rev is not defined.
//        };
//
//        return rev.apply(s,"");
//    }
    // ATTEMPT TWO - Hide the acc empty string from caller.
    // String -> String
    public static String reverse2(String s) {
        return reverse(s, "");
    }

    // ATTEMPT THREE - using accumulator.  Notice private (so only met to be used within TransformerUtil
    // (String, String) -> String
    private static final BiFunction<String, String, String> rev = (s, acc) -> {
            if (s.isBlank()) return acc;

            String head = s.substring(0,1);
            String tail = s.substring(1);
            acc = head + acc;
            return TransformerUtil.rev.apply(tail, acc);
    };
    // Hide the empty acc "" to the caller
    // String -> String
    public static String reverse3(String s) {
        return TransformerUtil.rev.apply(s,"");
    }

    /**
     * Just a curious concept:  Can Java define a algebraic type such as PositiveInteger
     * such that if trying to construct such type it would give compile errors for:
     * new PositiveInteger(-2)
     * Can we leverage char (range[0..65,535] (hence cannot have -2
     */
//    public void dothis() {
//        class PositiveInteger {
//
//            public static final PositiveInteger getInstance(Integer i) {
//                int v = i.intValue();
//                char c = Character.forDigit(-2, 10);
//                return null;
//            }
//        }
//    }

    public static void main(String[] args) {
        TransformerUtil t  = new TransformerUtil();
        char[] cs = t.reverseRecursive("abcde".toCharArray());
        System.out.println(cs);

        System.out.println(reverse("abcd"));
        System.out.println("  abcd  ".stripLeading());
        System.out.println("  abcd  ".stripTrailing());
        System.out.println("abcd".substring(0,1));
        System.out.println("abcd".substring(1));

        System.out.println(reverse("abcdefghij"));

        System.out.println(reverse("abcdefg", ""));

        System.out.println(reverse2("hijklmn"));
        System.out.println(reverse3("jklmnopqrstuvwxyz"));

    }


}
