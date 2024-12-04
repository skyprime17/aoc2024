package org.aoc;

import java.util.List;
import util.DataLoader;

public class DayFour {

  private static int solvePartOne(List<List<Character>> grid) {
    int res = 0;
    int[][] directions = {
      {0, 1}, // Right
      {0, -1}, // Left
      {1, 0}, // Down
      {-1, 0}, // Up
      {1, 1}, // Diagonal Down-Right
      {-1, -1}, // Diagonal Up-Left
      {1, -1}, // Diagonal Down-Left
      {-1, 1} // Diagonal Up-Right
    };

    for (int i = 0; i < grid.size(); i++) {
      List<Character> line = grid.get(i);

      for (int j = 0; j < line.size(); j++) {
        Character c = line.get(j);
        if (c != 'X') {
          continue;
        }
        for (int[] dir : directions) {
          if (isMatch(grid, i, j, dir[0], dir[1])) {
            res++;
          }
        }
      }
    }

    return res;
  }

  private static boolean isMatch(
      List<List<Character>> grid, int startX, int startY, int dx, int dy) {
    int x2 = startX + dx, y2 = startY + dy;
    int x3 = startX + 2 * dx, y3 = startY + 2 * dy;
    int x4 = startX + 3 * dx, y4 = startY + 3 * dy;
    if (!isInBounds(grid, startX, startY)
        || !isInBounds(grid, x2, y2)
        || !isInBounds(grid, x3, y3)
        || !isInBounds(grid, x4, y4)) {
      return false;
    }

    return checkXmas(
        grid.get(startX).get(startY),
        grid.get(x2).get(y2),
        grid.get(x3).get(y3),
        grid.get(x4).get(y4));
  }

  private static boolean isInBounds(List<List<Character>> grid, int x, int y) {
    return x >= 0 && y >= 0 && x < grid.size() && y < grid.getFirst().size();
  }

  private static boolean checkXmas(Character c1, Character c2, Character c3, Character c4) {
    return c1 == 'X' && c2 == 'M' && c3 == 'A' && c4 == 'S';
  }

  private static int solvePartTwo(List<List<Character>> grid) {
    int res = 0;

    for (int i = 0; i < grid.size(); i++) {
      List<Character> line = grid.get(i);

      for (int j = 0; j < line.size(); j++) {
        Character c = line.get(j);
        if (c != 'A') {
          continue;
        }

        int downRightX = i + 1;
        int downRightY = j + 1;

        int upLeftX = i - 1;
        int upLeftY = j - 1;

        int upRightX = i - 1;
        int upRightY = j + 1;

        int downLeftX = i + 1;
        int downLeftY = j - 1;

        if (!isInBounds(grid, downRightX, downRightY)
            || !isInBounds(grid, upLeftX, upLeftY)
            || !isInBounds(grid, upRightX, upRightY)
            || !isInBounds(grid, downLeftX, downLeftY)) {
          continue;
        }

        if (checkSmOrMs(grid.get(downRightX).get(downRightY), grid.get(upLeftX).get(upLeftY))
            && checkSmOrMs(grid.get(downLeftX).get(downLeftY), grid.get(upRightX).get(upRightY))) {
          res++;
        }
      }
    }

    return res;
  }

  private static boolean checkSmOrMs(Character c1, Character c2) {
    return (c1 == 'S' && c2 == 'M') || (c1 == 'M' && c2 == 'S');
  }

  public static void main(String[] args) {
    DataLoader dataLoader = new DataLoader();
    List<List<Character>> input =
        dataLoader.getInput("day4").stream()
            .map(s -> s.chars().mapToObj(c -> (char) c).toList())
            .toList();
    System.out.println(solvePartOne(input));
    System.out.println(solvePartTwo(input));
  }
}
