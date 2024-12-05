package org.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import util.DataLoader;

public class DayFive {

  private static int solvePartOne(Map<String, List<String>> rules, List<List<String>> updates) {
    return updates.stream()
        .filter(u -> isCorrect(rules, u))
        .mapToInt(u -> Integer.parseInt(u.get(u.size() / 2)))
        .sum();
  }

  private static boolean isCorrect(Map<String, List<String>> rules, List<String> updates) {
    for (int i = 0; i < updates.reversed().size(); i++) {
      List<String> right = rules.getOrDefault(updates.get(i), List.of());
      Optional<String> error = updates.subList(0, i).stream().filter(right::contains).findAny();
      if (error.isPresent()) {
        return false;
      }
    }
    return true;
  }

  private static int solvePartTwo(Map<String, List<String>> rules, List<List<String>> updates) {
    int sum = 0;
    for (List<String> u : updates) {
      if (!isCorrect(rules, u)) {
        List<String> fix = fixNaive(rules, u);
        int i = Integer.parseInt(fix.get(fix.size() / 2));
        sum += i;
      }
    }
    return sum;
  }

  private static List<String> fixNaive(Map<String, List<String>> rules, List<String> updates) {
    System.out.println(updates);
    Map<String, List<String>> reversedMap = new HashMap<>();
    for (Map.Entry<String, List<String>> entry : rules.entrySet()) {
      String key = entry.getKey();
      List<String> value = entry.getValue();
      for (String v : value) {
        reversedMap.computeIfAbsent(v, k -> new ArrayList<>()).add(key);
      }
    }

    boolean fixed = false;
    List<String> toCheck = updates;
    List<String> update = new ArrayList<>();
    while (!fixed) {
      boolean hasError = false;
      for (int i = 0; i < toCheck.size(); i++) {
        String u = toCheck.get(i);
        List<String> left = reversedMap.getOrDefault(u, List.of());
        List<String> rightViolations = toCheck.subList(i, updates.size()).stream().filter(left::contains).toList();
        if (!rightViolations.isEmpty()) {
          update = new ArrayList<>(toCheck);
          for (String s: rightViolations) {
            update.remove(s);
            update.addFirst(s);
          }
          hasError = true;
          break;
        }
      }
      if (!hasError) {
        break;
      }
      fixed = update.isEmpty();
      toCheck = update;
      System.out.println(update);
    }
    return update;
  }

  public static void main(String[] args) {
    DataLoader dataLoader = new DataLoader();
    List<String> input = dataLoader.getInput("day5");
    Map<String, List<String>> rules = new HashMap<>();
    List<List<String>> updates = new ArrayList<>();

    for (String s : input) {
      if (s.contains("|")) {
        String[] split = s.split("\\|");
        rules.computeIfAbsent(split[0], k -> new ArrayList<>()).add(split[1]);
      } else if (s.contains(",")) {
        String[] split = s.split(",");
        updates.add(Arrays.asList(split));
      }
    }
    System.out.println(solvePartOne(rules, updates));
    var timeNow = System.currentTimeMillis();
    System.out.println(solvePartTwo(rules, updates));
    System.out.println(System.currentTimeMillis() - timeNow);
  }
}
