package org.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DataLoader;

public class DayOne {


  private static int solvePartOne(List<String> input) {
    List<Integer> leftPart = new ArrayList<>();
    List<Integer> rightPart = new ArrayList<>();
    for (String s : input) {
      String[] s1 = s.split(" {3}");
      leftPart.add(Integer.parseInt(s1[0]));
      rightPart.add(Integer.parseInt(s1[1]));
    }

    Collections.sort(leftPart);
    Collections.sort(rightPart);
    assert leftPart.size() == rightPart.size();

    int dif = 0;
    for (int j = 0; j < leftPart.size(); j++) {
      Integer l = leftPart.get(j);
      Integer r = rightPart.get(j);
      dif += Math.abs(l - r);
    }
    return dif;
  }

  private static int solvePartTwo(List<String> input) {
    List<Integer> leftPart = new ArrayList<>();
    Map<Integer, Integer> rightMap = new HashMap<>();
    for (String s : input) {
      String[] split = s.split(" {3}");
      leftPart.add(Integer.parseInt(split[0]));
      rightMap.compute(Integer.parseInt(split[1]), (a, b) -> b == null ? 1 : b + 1);
    }

    int similarity = 0;
    for (Integer l : leftPart) {
      similarity += l * rightMap.getOrDefault(l, 0);
    }
    return similarity;
  }


  public static void main(String[] args) {
    DataLoader dataLoader = new DataLoader();
    List<String> input = dataLoader.getInput("day1.txt");
    System.out.println(solvePartOne(input));
    System.out.println(solvePartTwo(input));
  }
}