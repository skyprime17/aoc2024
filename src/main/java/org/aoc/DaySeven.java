package org.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import util.DataLoader;

public class DaySeven {

  enum Op {
    ADD,
    MUL,
    CONCAT
  }

  private static long solvePartOne(List<String> input) {
    return calibrate(input, List.of(Op.ADD, Op.MUL));
  }

  private static long solvePartTwo(List<String> input) {
    return calibrate(input, List.of(Op.ADD, Op.MUL, Op.CONCAT));
  }

  private static long calibrate(List<String> input, List<Op> supportedOps) {
    Map<Integer, List<List<Op>>> variationCache = new HashMap<>();
    long calibrationResult = 0;
    for (String l : input) {
      String[] split = l.split(": ");
      long targetSum = Long.parseLong(split[0]);
      List<Integer> calibrationNumbers = Arrays.stream(split[1].split(" ")).map(Integer::parseInt).toList();

      List<List<Op>> ops =
          variationCache.compute(
              calibrationNumbers.size(),
              (k, v) -> {
                if (v == null) {
                  return generateOpVariations(calibrationNumbers.size() - 1, supportedOps);
                }
                return v;
              });

      for (List<Op> opsVaration : ops) {
        long sum = calibrationNumbers.getFirst();
        for (int j = 1, xSize = calibrationNumbers.size(); j < xSize; j++) {
          Integer i = calibrationNumbers.get(j);
          switch (opsVaration.get(j - 1)) {
            case ADD -> sum += i;
            case MUL -> sum *= i;
            case CONCAT -> sum = Long.parseLong((sum + "" + i));
          }
        }
        if (sum == targetSum) {
          calibrationResult += targetSum;
          break;
        }
      }
    }
    return calibrationResult;
  }

  private static List<List<Op>> generateOpVariations(int length, List<Op> supportedOps) {
    List<List<Op>> result = new ArrayList<>();
    if (length <= 0) {
      return List.of();
    }
    int numVariations = (int) Math.pow(supportedOps.size(), length);
    for (int i = 0; i < numVariations; i++) {
      List<Op> variation = new ArrayList<>();
      int index = i;
      for (int j = 0; j < length; j++) {
        variation.add(supportedOps.get(index % supportedOps.size()));
        index /= supportedOps.size();
      }
      result.add(variation);
    }
    return result;
  }

  public static void main(String[] args) {
    DataLoader dataLoader = new DataLoader();
    List<String> input = dataLoader.getInput("day7");

    System.out.println(solvePartOne(input));
    System.out.println(solvePartTwo(input));
  }
}
