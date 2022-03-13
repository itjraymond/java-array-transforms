package ca.jent.arraytransforms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TransformerTest {

    private Transformer t = new Transformer();

    @Test
    void removeNegativeAndDuplicates() {
        int[] expected = new int[]{1,2,4,5};
        int[] xs = Transformer.createIntArray(Transformer.ARRAY_SCENARIO.ORDERED_N);
        int[] ys = t.removeNegativeAndDuplicates(xs);
        Arrays.stream(ys).forEach(System.out::println);

        Assertions.assertArrayEquals(expected, ys);

        expected = new int[]{5,1,4,2};
        xs = Transformer.createIntArray(Transformer.ARRAY_SCENARIO.UNORDERED_N_WITH_DUPLICATE);
        ys = t.removeNegativeAndDuplicates(xs);
        Arrays.stream(ys).forEach(System.out::println);

        Assertions.assertArrayEquals(expected, ys);
    }

    @Test
    void removeDuplicates() {
        int[] expected = new int[]{-2,-1,0,1,2,4,5};
        int[] xs = Transformer.createIntArray(Transformer.ARRAY_SCENARIO.ORDERED_N_WITH_DUPLICATE);
        int[] ys = t.removeDuplicates(xs);
        Arrays.stream(ys).forEach(System.out::println);

        Assertions.assertArrayEquals(expected, ys);
    }

    @Test
    void removeNegativeAndZero() {
        int[] expected = new int[]{1,2,4,4,5};
        int[] xs = Transformer.createIntArray(Transformer.ARRAY_SCENARIO.ORDERED_N_WITH_DUPLICATE);
        int[] ys = t.removeNegativeAndZero(xs);
        Arrays.stream(ys).forEach(System.out::println);

        Assertions.assertArrayEquals(expected, ys);
    }

    @Test
    void mapToString() {
        String[] expected = new String[]{ "0", "-2", "5", "0", "1", "-1", "1", "4", "2", "5", "5", "2", "-2" };
        int[] xs = Transformer.createIntArray(Transformer.ARRAY_SCENARIO.UNORDERED_N_WITH_DUPLICATE);
        String[] ys = t.mapToString(xs);
        Arrays.stream(ys).forEach(System.out::println);

        Assertions.assertArrayEquals(expected, ys);
    }

    @Test
    void toInteger() {
        int[] expected = new int[]{-2,-1,0,1,2,4,5};
        List<Integer> list = Transformer.createIntegerList(Transformer.ARRAY_SCENARIO.ORDERED_N);
        int[] ys = t.toInteger(list);
        Arrays.stream(ys).forEach(System.out::println);

        Assertions.assertArrayEquals(expected, ys);
    }

    @Test
    void mapToListOfString() {
        List<String> expected = new ArrayList<String>() {{
            add("-2");add("-1");add("0");add("1");add("2");add("4");add("5");
        }};
        int[] xs = Transformer.createIntArray(Transformer.ARRAY_SCENARIO.ORDERED_N);
        List<String> ys = t.mapToListOfString(xs);

        Assertions.assertIterableEquals (expected, ys);
    }

    @Test
    void mapToListOfDto() {
        List<Transformer.Dto> expected = new ArrayList<Transformer.Dto>() {{
            add(new Transformer.Dto("-2", "Joss"));
            add(new Transformer.Dto("-1", "Joss"));
            add(new Transformer.Dto("0", "Joss"));
            add(new Transformer.Dto("1", "Joss"));
            add(new Transformer.Dto("2", "Joss"));
            add(new Transformer.Dto("4", "Joss"));
            add(new Transformer.Dto("5", "Joss"));
        }};

        int[] xs = Transformer.createIntArray(Transformer.ARRAY_SCENARIO.ORDERED_N);
        List<Transformer.Dto> list = t.mapToListOfDto(xs);

        Assertions.assertEquals("0", list.get(2).getId());
        Assertions.assertEquals(expected.size(), list.size());
    }

    @Test
    void createLongList() {
        List<Integer> longList = Transformer.createLongList(50);
        Assertions.assertEquals(50, longList.size());
        longList.forEach(System.out::println);
    }

    @Test
    void createLongListAlternate() {
        List<Integer> longList = Transformer.createLongListAlternate(25);
        Assertions.assertEquals(25, longList.size());
        longList.forEach(System.out::println);
    }

    @Test
    void testCreateLongListAlternate() {
        List<Integer> longList = Transformer.createLongListAlternate(10, 30);
        Assertions.assertEquals(21, longList.size());
        longList.forEach(System.out::println);
    }

    @Test
    void shuffle() {

        int[] xs = Transformer.createIntArray(Transformer.ARRAY_SCENARIO.ORDERED_N_WITH_DUPLICATE);
        Arrays.stream(xs).forEach(System.out::println);
        Transformer.shuffle(xs);

        Arrays.stream(xs).forEach(System.out::println);

        Assertions.assertEquals(10, xs.length);
    }

    @Test
    void createLongArray() {
        int[] xs = new int[] {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
        int[] longArray = Transformer.createLongArray(10, 30);
        Arrays.stream(longArray).forEach(System.out::println);

        Assertions.assertArrayEquals(xs, longArray);
    }

    @Test
    void testShuffle() {

        List<Integer> list = Transformer.createIntegerList(Transformer.ARRAY_SCENARIO.ORDERED_N);
        List<Integer> shuffledList = Transformer.shuffle(list);

        shuffledList.forEach(System.out::println);

        Assertions.assertNotEquals(list.get(0), shuffledList.get(0));
    }

    @Test
    void testCountRepeatedCharThatAreOdd() {
        long c = Transformer.countRepeatedCharThatAreOdd("abcaab");
        assertEquals(2, c);
        c = Transformer.countRepeatedCharThatAreOdd("abcabcabcddeef");
        assertEquals(4, c);
    }

    @Test
    void testCountWords() {
        Map<String, Long> map = Transformer.countWords("The Fox jumped. The Fox was sad. The Fox was happy.");
        assertEquals(3, map.getOrDefault("Fox", 0L));
        assertEquals(3, map.getOrDefault("The", 0L));
        assertEquals(2, map.getOrDefault("was", 0L));
    }
}