package org.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import util.DataLoader;

public class DayEleven {

  private static long solvePartOne(List<Long> stones) {
    for (int i = 0; i < 25; i++) {
      List<Long> adjustedStones = new ArrayList<>();
      System.out.printf("i=%d size=%d%n", i, stones.size());
      for (Long stone : stones) {
        if (stone == 0) {
          adjustedStones.add(1L);
        } else if ((int) (Math.log10(stone) + 1) % 2 == 0) {
          String n = stone + "";
          String[] parts = {n.substring(0, n.length() / 2), n.substring(n.length() / 2)};
          adjustedStones.add(Long.parseLong(parts[0]));
          adjustedStones.add(Long.parseLong(parts[1]));
        } else {
          adjustedStones.add(stone * 2024L);
        }
      }
      stones = adjustedStones;
    }

    return stones.size();
  }

  private static long solvePartTwo(List<Long> stones) {
    Map<String, Long> cache = new HashMap<>();
    long size = 0;
    for (long stone : stones) {
      size += countStoneBlinks(stone, 75, cache);
    }
    return size;
  }

  private record Stones(Long left, Long right) {}

  private static long countStoneBlinks(long stone, int depth, Map<String, Long> cache) {
    String cacheKey = stone + ":" + depth;
    if (cache.containsKey(cacheKey)) {
      return cache.get(cacheKey);
    }

    var result = calcStone(stone);
    if (depth == 1) {
      if (result.right == null) {
        return 1;
      }
      return 2;
    }
    var left = countStoneBlinks(result.left, depth - 1, cache);
    var right = 0L;
    if (result.right != null) {
      right = countStoneBlinks(result.right, depth - 1, cache);
    }
    var output = left + right;
    cache.put(cacheKey, output);
    return output;
  }

  private static Stones calcStone(long stone) {
    if (stone == 0) {
      return new Stones(1L, null);
    }

    String n = stone + "";
    if (n.length() % 2 == 0) {
      String[] parts = {n.substring(0, n.length() / 2), n.substring(n.length() / 2)};
      return new Stones(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }

    return new Stones(stone * 2024, null);
  }

  public static void main(String[] args) {
    DataLoader dataLoader = new DataLoader();

    List<Long> input =
        Arrays.stream(dataLoader.getInput("day11").getFirst().split(" "))
            .map(Long::parseLong)
            .collect(Collectors.toList());
    // System.out.println(input);
    // System.out.println(solvePartOne(input));
    System.out.println(solvePartTwo(input));
  }
}
