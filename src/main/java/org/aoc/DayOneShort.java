package org.aoc;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import util.DataLoader;

public class DayOneShort {

  private static int solvePartOne(List<String> input) {
    List<int[]> pairs =
        input.stream()
            .map(s -> Stream.of(s.split(" {3}")).mapToInt(Integer::parseInt).toArray())
            .toList();
    int[] left = pairs.stream().mapToInt(pair -> pair[0]).sorted().toArray();
    int[] right = pairs.stream().mapToInt(pair -> pair[1]).sorted().toArray();
    return IntStream.range(0, left.length).map(i -> Math.abs(left[i] - right[i])).sum();
  }

  private static int solvePartTwo(List<String> input) {
    Map<Integer, Long> rightMap =
        input.stream()
            .map(s -> s.split(" {3}"))
            .collect(
                Collectors.groupingBy(parts -> Integer.parseInt(parts[1]), Collectors.counting()));
    return input.stream()
        .mapToInt(
            s -> {
              int left = Integer.parseInt(s.split(" {3}")[0]);
              return left * rightMap.getOrDefault(left, 0L).intValue();
            })
        .sum();
  }

  public static void main(String[] args) {
    DataLoader dataLoader = new DataLoader();
    List<String> input = dataLoader.getInput("day1.txt");
    System.out.println(solvePartOne(input));
    System.out.println(solvePartTwo(input));
  }
}
