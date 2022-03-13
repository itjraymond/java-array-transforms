package ca.jent.arraytransforms;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Transformer {

    public static void main(String[] args) {
        Transformer t = new Transformer();
        String[] xs = t.mapToString(createIntArray(ARRAY_SCENARIO.ORDERED_N));

        for (int i=0; i<xs.length; i++) {
            System.out.println(xs[i]);
        }
    }

    // int[] -> int[]
    public int[] removeNegativeAndDuplicates(int[] xs) {
        return Arrays.stream(xs).distinct().filter(i -> i > 0).toArray();
    }
    // int[] -> int[]
    public int[] removeDuplicates(int[] xs) {
        return Arrays.stream(xs).distinct().toArray();
    }

    // int[] -> int[]
    public int[] removeNegativeAndZero(int[] xs) {
        return Arrays.stream(xs).filter(i -> i>0).toArray();
    }

    // int[] -> String[]
    public String[] mapToString(int[] xs) {
//        return Arrays.stream(xs).mapToObj(x -> String.valueOf(x)).toArray(size -> new String[size]);
        return Arrays.stream(xs).mapToObj(String::valueOf).toArray(String[]::new);
    }

    // List<Integer> -> int[]
    public int[] toInteger(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    // int[] -> List<String>
    public List<String> mapToListOfString(int[] xs) {
        return Arrays.stream(xs).mapToObj(String::valueOf).collect(toList());
    }

    // grouping example
    // String -> int
    // Take string, e.g. "abcaab" and return an int indicating how many
    // group of characters count are odd.
    // "abcaab" has "a" -> 3 (odd)
    //              "b" -> 2 (even)
    //              "c" -> 1 (odd)
    // return 2 characters having a odd count
    // String -> long
    public static long countRepeatedCharThatAreOdd(String s) {
        return Arrays.stream(s.split(""))
              .collect(groupingBy(c -> c, counting()))
                .values()
                .stream()
                .filter(c -> c %2 != 0)
                .count();
    }
    // String -> Map<String,Long>
    public static Map<String, Long> returnRepeatedCharWithCount(String s) {
        return Arrays.stream(s.split(""))
                .collect(groupingBy(Function.identity(), counting()));
    }
    // String -> Map<String,Long>
    public static Map<String, Long> returnRepeatedCharWithCountGT1(String s) {
        return Arrays.stream(s.split(""))
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue()>1)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // List<String> -> Map<Boolean, String> (true->even length, false-> odd length)
    public static Map<Boolean, List<String>> partitionAccordingToEvenOddLength(List<String> list) {
        return list
                .stream()
                .collect(partitioningBy(s -> s.length() % 2 == 0));
    }

    // count unique words
    // String -> Map<String, Long>
    public static Map<String, Long> countWords(String s) {
        return Arrays.stream(s.split(" "))
                .collect(groupingBy(w -> w, counting()));
    }

    @AllArgsConstructor
    @Data
    static class Dto {
        private String id;
        private String name;
    }

    // int[] -> List<Dto>
    public List<Dto> mapToListOfDto(int[] xs) {
        return Arrays.stream(xs)
                     .mapToObj(x -> new Dto(String.valueOf(x), "Joss"))
                     .collect(toList());
    }



    public static int[] createIntArray(ARRAY_SCENARIO scenario) {
        int[] a = new int[]{ -1, -3, -2 };
        switch (scenario) {
            case ORDERED_N:
                a = new int[]{ -2, -1, 0, 1, 2, 4, 5 };
                break;
            case UNORDERED_N:
                a = new int[]{ 1, 5, -2, 0, -1, 2, 4 };
                break;
            case ORDERED_POSITIVE:
                a = new int[]{ 0, 1, 2, 4, 5 };
                break;
            case UNORDERED_POSITIVE:
                a = new int[]{ 2, 0, 5, 1, 4 };
                break;
            case ORDERED_N_WITH_DUPLICATE:
                a = new int[]{ -2, -2, -1, 0, 0, 1, 2, 4, 4, 5 };
                break;
            case UNORDERED_N_WITH_DUPLICATE:
                a = new int[]{ 0, -2, 5, 0, 1, -1, 1, 4, 2, 5, 5, 2, -2 };
                break;
        }
        return a;
    }

    public static List<Integer> createIntegerList(ARRAY_SCENARIO scenario) {
        List<Integer> list = Collections.emptyList();
        List<Integer> listEx = Stream.of(1,2,3,-1,2,-2,3,-2,1,-4,1,0,-2)
                                   .distinct()
                                   .filter(i -> i>0)
                                   .collect(toList());

        switch (scenario) {
            case ORDERED_N:
                list = new ArrayList<Integer>() {{
                    add(-2); add(-1); add(0); add(1); add(2); add(4); add(5);
                }};
                break;
            case UNORDERED_N:
                list = new ArrayList<Integer>() {{
                    add(2); add(5); add(0); add(-1); add(1); add(-2); add(4);
                }};
                break;
            case ORDERED_POSITIVE:
                list = new ArrayList<Integer>() {{
                    add(0); add(1); add(2); add(4); add(5);
                }};
                break;
            case UNORDERED_POSITIVE:
                list = new ArrayList<Integer>() {{
                    add(4); add(1); add(0); add(5); add(2);
                }};
                break;
            case ORDERED_N_WITH_DUPLICATE:
                list = new ArrayList<Integer>() {{
                    add(-2); add(-2); add(-1); add(0); add(0); add(1); add(2); add(4); add(4); add(4); add(5);
                }};
                break;
            case UNORDERED_N_WITH_DUPLICATE:
                list = new ArrayList<Integer>() {{
                    add(2); add(5); add(5); add(0); add(-1); add(1); add(1); add(0); add(-2); add(4); add(-2);
                }};
                break;
        }
        return list;
    }

    public static List<Integer> createLongList(int size){
        return IntStream.rangeClosed(0, size-1).boxed().collect(toList());
    }
    // alternate way
    public static List<Integer> createLongListAlternate(int size) {
        return Stream.iterate(1, n -> n+1).limit(size).collect(toList());
    }
    public static List<Integer> createLongListAlternate(int start, int end) {
        int size = end - start + 1;
        return Stream.iterate(start, n -> n+1).limit(size).collect(toList());
    }

    public static List<Integer> shuffle(List<Integer> list) {
        int[] xs = list.stream().mapToInt(Integer::intValue).toArray();
        shuffle(xs);
        return Arrays.stream(xs).boxed().collect(toList());
    }

    public static int[] createLongArray(int start, int end) {
        if (start >= end || start < 0 || end < 0) return new int[]{};

        int size = end - start + 1;
        List<Integer> list = new ArrayList<>(size);
        for (int i = start; i< start + size; i++) {
            list.add(i);
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    // FYI, side effect
    public static void shuffle(int[] xs) {
        Random r = ThreadLocalRandom.current();
        for (int i=xs.length-1; i>=0; i--) {
            int randomIndex = r.nextInt(i+1);
            // swap
            int x = xs[randomIndex];
            xs[randomIndex] = xs[i];
            xs[i] = x;
        }
    }


    enum ARRAY_SCENARIO {
      ORDERED_N,
      UNORDERED_N,
      ORDERED_POSITIVE,
      UNORDERED_POSITIVE,
      ORDERED_N_WITH_DUPLICATE,
      UNORDERED_N_WITH_DUPLICATE
    }
}
