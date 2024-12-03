package org.aoc;

import java.util.Iterator;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import util.DataLoader;

public class DayThree {

  private static int solvePartOne(String lines) {
    return Pattern.compile("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)")
        .matcher(lines)
        .results()
        .map(x -> Integer.parseInt(x.group(1)) * Integer.parseInt(x.group(2)))
        .reduce(0, Integer::sum);
  }

  private static int solvePartTwo(String lines) {
    Pattern MUL = Pattern.compile("do\\(\\)|don't\\(\\)|mul\\(([0-9]{1,3}),([0-9]{1,3})\\)");
    int res = 0;
    boolean enabled = true;
    for (Iterator<MatchResult> it = MUL.matcher(lines).results().iterator(); it.hasNext(); ) {
      MatchResult matcher = it.next();
      String text = matcher.group();
      if (text.startsWith("don't")) {
        enabled = false;
      } else if (text.startsWith("do()")) {
        enabled = true;
      } else if (enabled)
        res += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
    }
    return res;
  }

  public static void main(String[] args) {
    DataLoader dataLoader = new DataLoader();
    String input = String.join("", dataLoader.getInput("day3"));
    System.out.println(solvePartOne(input));
    System.out.println(solvePartTwo(input));
  }
}
