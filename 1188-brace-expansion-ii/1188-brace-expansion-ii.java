import java.util.*;

class Solution {

    private String s;
    private int index;

    public List<String> braceExpansionII(String expression) {
        s = expression;
        index = 0;

        Set<String> result = parseExpression();

        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);

        return ans;
    }

    private Set<String> parseExpression() {

        Set<String> result = parseTerm();

        while (index < s.length() && s.charAt(index) == ',') {
            index++; 

            Set<String> next = parseTerm();
            result.addAll(next);
        }

        return result;
    }

    private Set<String> parseTerm() {

        Set<String> result = new HashSet<>();
        result.add("");

        while (index < s.length()
                && s.charAt(index) != '}'
                && s.charAt(index) != ',') {

            Set<String> next = parseFactor();

            result = concatenate(result, next);
        }

        return result;
    }

    private Set<String> parseFactor() {

        if (s.charAt(index) == '{') {

            index++; 

            Set<String> result = parseExpression();

            index++; 

            return result;
        }

        char ch = s.charAt(index++);

        Set<String> result = new HashSet<>();
        result.add(String.valueOf(ch));

        return result;
    }

    private Set<String> concatenate(Set<String> a,
                                    Set<String> b) {

        Set<String> result = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}