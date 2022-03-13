package ca.jent.arraytransforms;

import ca.jent.arraytransforms.practice.TransformerUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Solution {
    Transformer t = new Transformer();

    public static void main(String[] args) {
        Solution s = new Solution();
        String str = "a".substring(1); // empty String: str == ""
//        s.solution(Transformer.createIntArray(Transformer.ARRAY_SCENARIO.UNORDERED_N_WITH_DUPLICATE));
//        int v = s.solution(new int[]{1, 3, 6, 4, 1, 2});
//
//        System.out.println("Valu: " + v);

//        System.out.println(s.solution2("acbcbba"));
//        System.out.println(s.solution2("axxaxa"));
//        System.out.println(s.solution2("aaaa"));

        int ans = s.solution3(new int[]{-1, 1, -2, 2});

        System.out.println("Ans: " + ans);
    }

    int solution3(int[] A) {
        int ans = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] < ans) {
                ans = A[i];
            }
        }
        return ans;
    }



    public int solution2(String S) {
        // acbcbba -> aabbbcc  (sorted) -> group by char
        Map<String, Long> map = Arrays.stream(S.split(""))
                                          .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        return map.values().stream().filter(c -> c % 2 != 0).collect(Collectors.toList()).size();
    }





    public int solution(int[] A) {
        int x = 1;
        int[] xs = Arrays.stream(A).distinct().filter(i -> i > 0).toArray();
        if (xs.length == 0) return 1;


        Arrays.sort(xs);
        Arrays.stream(xs).forEach(System.out::println);

        for (int i = 0; i < xs.length; i++) {
            if (i+1 != xs[i]) {
                x = i+1;
                break;
            }
            x = i+1;
        }
        return x;
    }
}
