package org.aoc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import util.DataLoader;

public class DaySix {

  private static final char WALL = '#';
  private static final char WARDEN = '^';

  private static final int[] UP = {-1, 0};
  private static final int[] DOWN = {1, 0};
  private static final int[] LEFT = {0, -1};
  private static final int[] RIGHT = {0, 1};

  private static void printMap(List<List<Character>> map) {
    for (List<Character> line : map) {
      for (Character c : line) {
        System.out.print(c);
      }
    }
    System.out.println();
  }

  private record Point(int x, int y){}

  private static int solvePartOne(List<List<Character>> map, int[] wardenPosition) {
    int[] currentWardenPosition = wardenPosition;
    HashSet<Point> positions = new HashSet<>();
    int[] currentDirection = UP;
    printMap(map);

    while (isInBounds(map, currentWardenPosition[0], currentWardenPosition[1])) {
      var nextPositionX = currentWardenPosition[0] + currentDirection[0];
      var nextPositionY = currentWardenPosition[1] + currentDirection[1];
      if (!isInBounds(map, nextPositionX, nextPositionY)) {
        break;
      }
      if (map.get(nextPositionX).get(nextPositionY).equals(WALL)) {
        if (Arrays.equals(currentDirection, UP)) {
          currentDirection = RIGHT;
        } else if (Arrays.equals(currentDirection, RIGHT)) {
          currentDirection = DOWN;
        } else if (Arrays.equals(currentDirection, DOWN)) {
          currentDirection = LEFT;
        } else {
          currentDirection = UP;
        }
        nextPositionX = currentWardenPosition[0];
        nextPositionY = currentWardenPosition[1];
      }
      positions.add(new Point(currentWardenPosition[0], currentWardenPosition[1]));

      map.get(currentWardenPosition[0]).set(currentWardenPosition[1], 'X');
      currentWardenPosition[0] = nextPositionX;
      currentWardenPosition[1] = nextPositionY;
      map.get(currentWardenPosition[0]).set(currentWardenPosition[1], '^');
      //printMap(map);
    }

    return positions.size() + 1;
  }

  private static boolean isInBounds(List<List<Character>> grid, int x, int y) {
    return x >= 0 && y >= 0 && x < grid.size() && y < grid.getFirst().size();
  }

  private static int solvePartTwo(List<List<Character>> map) {
    return 0;
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
    System.out.println(solvePartOne(input, wardenPosition));
  }
}
