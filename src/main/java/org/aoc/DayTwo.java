package org.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import util.DataLoader;

public class DayTwo {

  private static int solvePartOne(List<List<Integer>> reports) {
    return (int) reports.stream().filter(DayTwo::isSaveReport).count();
  }

  private static int solvePartTwo(List<List<Integer>> reports) {
    return (int) reports.stream().filter(x -> isSaveReport(x) || isSaveReportWithError(x)).count();
  }

  private static boolean isSaveReport(List<Integer> report) {
    Boolean decreasing = null;
    for (int i = 0; i < report.size(); i++) {
      if (i + 1 == report.size()) {
        break;
      }
      Integer a = report.get(i);
      Integer b = report.get(i + 1);
      int absDiff = Math.abs(a - b);
      if (absDiff < 1 || absDiff > 3) {
        return false;
      }
      boolean localDecrease = a - b < 0;
      if (decreasing != null && decreasing != localDecrease) {
        return false;
      }
      decreasing = localDecrease;
    }
    return true;
  }

  private static boolean isSaveReportWithError(List<Integer> report) {
    for (int i = 0; i < report.size(); i++) {
      List<Integer> levels = new ArrayList<>();
      for (int j = 0; j < report.size(); j++) {
        if (j != i) {
          levels.add(report.get(j));
        }
      }
      if (isSaveReport(levels)) {
        return true;
      }
    }

    return false;
  }

  public static void main(String[] args) {
    DataLoader dataLoader = new DataLoader();
    List<String> input = dataLoader.getInput("day2.txt");
    List<List<Integer>> reports =
        input.stream()
            .map(
                x -> {
                  String[] s = x.split(" ");
                  return Arrays.stream(s).map(Integer::parseInt).toList();
                })
            .toList();
    System.out.println(solvePartOne(reports));
    System.out.println(solvePartTwo(reports));
  }
}
