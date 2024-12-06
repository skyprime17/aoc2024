package org.aoc;

import static org.aoc.DaySix.DIRECTION.DOWN;
import static org.aoc.DaySix.DIRECTION.LEFT;
import static org.aoc.DaySix.DIRECTION.RIGHT;
import static org.aoc.DaySix.DIRECTION.UP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import util.DataLoader;

public class DaySix {

  private static final char WALL = '#';
  private static final char WARDEN = '^';

  enum DIRECTION {
    UP(new int[] {-1, 0}),
    DOWN(new int[] {1, 0}),
    LEFT(new int[] {0, -1}),
    RIGHT(new int[] {0, 1});

    private final int[] dir;

    DIRECTION(int[] dir) {
      this.dir = dir;
    }
  }

  private static void printMap(List<List<Character>> map) {
    for (List<Character> line : map) {
      System.out.println(line);
    }
    System.out.println();
  }

  record Point(int x, int y) {}

  record PointWithDir(int x, int y, DIRECTION direction) {}

  private static int solvePartOne(List<List<Character>> map, int[] wardenPosition) {
    return move(map, wardenPosition).size();
  }

  private static Set<Point> move(List<List<Character>> map, int[] wardenPosition) {
    HashSet<Point> positions = new HashSet<>();
    DIRECTION currentDirection = UP;
    positions.add(new Point(wardenPosition[0], wardenPosition[1]));
    while (isInBounds(map, wardenPosition[0], wardenPosition[1])) {
      var nextPositionX = wardenPosition[0] + currentDirection.dir[0];
      var nextPositionY = wardenPosition[1] + currentDirection.dir[1];
      if (!isInBounds(map, nextPositionX, nextPositionY)) {
        break;
      }
      if (map.get(nextPositionX).get(nextPositionY).equals(WALL)) {
        currentDirection =
            switch (currentDirection) {
              case UP -> RIGHT;
              case RIGHT -> DOWN;
              case DOWN -> LEFT;
              case LEFT -> UP;
            };
        nextPositionX = wardenPosition[0];
        nextPositionY = wardenPosition[1];
      }
      wardenPosition[0] = nextPositionX;
      wardenPosition[1] = nextPositionY;
      positions.add(new Point(wardenPosition[0], wardenPosition[1]));
    }

    return positions;
  }

  private static boolean isInBounds(List<List<Character>> grid, int x, int y) {
    return x >= 0 && y >= 0 && x < grid.size() && y < grid.getFirst().size();
  }

  private static List<List<Character>> copy(List<List<Character>> map) {
    return map.stream().map(ArrayList::new).collect(Collectors.toList());
  }

  private static int solvePartTwo(List<List<Character>> map, int[] wardenPosition) {
    List<List<Character>> copy = copy(map);
    Set<Point> visited = move(copy(map), new int[] {wardenPosition[0], wardenPosition[1]});
    var loops = 0;

    for (Point p : visited) {
      copy.get(p.x()).set(p.y(), WALL);
      loops += moveWithCycle(copy, new int[] {wardenPosition[0], wardenPosition[1]});
      copy.get(p.x()).set(p.y(), '.');
    }

    return loops;
  }

  private static int moveWithCycle(List<List<Character>> map, int[] wardenPosition) {
    HashSet<PointWithDir> positions = new HashSet<>();
    DIRECTION currentDirection = UP;
    int cycles = 0;
    positions.add(new PointWithDir(wardenPosition[0], wardenPosition[1], currentDirection));
    while (isInBounds(map, wardenPosition[0], wardenPosition[1])) {
      var nextPositionX = wardenPosition[0] + currentDirection.dir[0];
      var nextPositionY = wardenPosition[1] + currentDirection.dir[1];
      if (!isInBounds(map, nextPositionX, nextPositionY)) {
        break;
      }
      if (map.get(nextPositionX).get(nextPositionY).equals(WALL)) {
        currentDirection =
            switch (currentDirection) {
              case UP -> RIGHT;
              case RIGHT -> DOWN;
              case DOWN -> LEFT;
              case LEFT -> UP;
            };
        nextPositionX = wardenPosition[0];
        nextPositionY = wardenPosition[1];
      }
      wardenPosition[0] = nextPositionX;
      wardenPosition[1] = nextPositionY;
      PointWithDir p = new PointWithDir(wardenPosition[0], wardenPosition[1], currentDirection);
      if (positions.contains(p)) {
        cycles++;
        break;
      }
      positions.add(p);

    }
    return cycles;
  }

  public static void main(String[] args) {
    DataLoader dataLoader = new DataLoader();
    List<List<Character>> input =
        dataLoader.getInput("day6").stream()
            .map(s -> s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
            .collect(Collectors.toList());
    int[] wardenPosition = null;
    for (int i = 0; i < input.size(); i++) {
      List<Character> line = input.get(i);
      for (int j = 0; j < line.size(); j++) {
        if (line.get(j) == WARDEN) {
          wardenPosition = new int[] {i, j};
          break;
        }
      }
    }
    // System.out.println(solvePartOne(input, wardenPosition));
    System.out.println(solvePartTwo(input, wardenPosition));
  }
}
