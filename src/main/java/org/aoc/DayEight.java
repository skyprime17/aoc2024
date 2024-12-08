package org.aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import util.DataLoader;

public class DayEight {

  private static boolean isInBounds(List<List<Character>> grid, Point p) {
    return p.x >= 0 && p.y >= 0 && p.x < grid.size() && p.y < grid.getFirst().size();
  }

  private static int solvePartOne(List<List<Character>> input, List<List<Point>> antennas) {
    Set<Point> uniquePositions = new HashSet<>();

    for (List<Point> points : antennas) {
      for (int i = 0; i < points.size(); i++) {
        Point p = points.get(i);
        for (int j = 0; j < points.size(); j++) {
          if (i == j) continue;
          Point p2 = points.get(j);

          var distX = p.x() - p2.x();
          var distY = p.y() - p2.y();

          Point current = new Point(p.x() + distX, p.y() + distY);
          if (isInBounds(input, current)) {
            uniquePositions.add(current);
          }
        }
      }
    }
    return uniquePositions.size();
  }

  private static int solvePartTwo(List<List<Character>> input, List<List<Point>> antennas) {
    Set<Point> uniquePositions = new HashSet<>();

    for (List<Point> points : antennas) {
      for (int i = 0; i < points.size(); i++) {
        Point p = points.get(i);
        for (int j = 0; j < points.size(); j++) {
          if (i == j) continue;
          Point p2 = points.get(j);

          var distX = p.x() - p2.x();
          var distY = p.y() - p2.y();

          uniquePositions.add(p2);
          Point current = new Point(p.x() + distX, p.y() + distY);
          while (isInBounds(input, current)) {
            uniquePositions.add(current);
            current = new Point(current.x() + distX, current.y() + distY);
          }
        }
      }
    }

    return uniquePositions.size();
  }

  public record Point(int x, int y) {}

  public static void main(String[] args) {
    DataLoader dataLoader = new DataLoader();
    Map<String, List<Point>> antennaMap = new HashMap<>();

    List<List<Character>> input =
        dataLoader.getInput("day8").stream()
            .map(s -> s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
            .collect(Collectors.toList());
    for (int i = 0; i < input.size(); i++) {
      List<Character> line = input.get(i);
      for (int j = 0; j < line.size(); j++) {
        char c = line.get(j);
        if (c == '.') {
          continue;
        }
        antennaMap.computeIfAbsent(c + "", k -> new ArrayList<>()).add(new Point(i, j));
      }
    }
    List<List<Point>> pointsList = antennaMap.values().stream().toList();
    System.out.println(solvePartOne(input, pointsList));
    System.out.println(solvePartTwo(input, pointsList));
  }
}
