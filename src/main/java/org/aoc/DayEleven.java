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

  private static long countStoneBlinks(long stone, int depth, Map<String, Long> cache) {
    if (depth == 0) {
      return 1;
    }
    String cacheKey = stone + ":" + depth;
    if (cache.containsKey(cacheKey)) {
      return cache.get(cacheKey);
    }

    long output = 0;
    String n = stone + "";
    if (stone == 0) {
      output = countStoneBlinks(1, depth - 1, cache);
    } else if (n.length() % 2 == 0) {
      String[] parts = {n.substring(0, n.length() / 2), n.substring(n.length() / 2)};
      output += countStoneBlinks(Long.parseLong(parts[0]), depth - 1, cache);
      output += countStoneBlinks(Long.parseLong(parts[1]), depth - 1, cache);
    } else {
      output = countStoneBlinks(stone * 2024, depth - 1, cache);
    }

    cache.put(cacheKey, output);
    return output;
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
