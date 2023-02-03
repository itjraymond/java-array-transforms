package ca.jent.arraytransforms.practice;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReverseArray {

    public static void main(String[] args) {

    }

    public <T> T[] reverse(T[] ts) {
        T[] ts1 = ts.clone();
        for (int i=0; i<ts1.length/2; i++) {
            T t = ts1[ts1.length-1-i];
            ts1[ts1.length-1-i] = ts1[i];
            ts1[i] = t;
        }
        return ts1;
    }

    public static <T> Iterator<T> rev(Stream<T> stream) {
        return stream.collect(Collectors.toCollection(LinkedList::new)).descendingIterator();
    }


    public static <T> Iterator<T> rev2(Stream<T> s) {
        return s.collect(Collectors.toCollection(LinkedList::new)).descendingIterator();
    }

    public static <T> Iterator<T> rev3(Stream<T> s) {
        return s.collect(Collectors.toCollection(LinkedList::new)).descendingIterator();
    }
}
