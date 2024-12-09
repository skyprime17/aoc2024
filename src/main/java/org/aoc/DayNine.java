package org.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import util.DataLoader;

public class DayNine {

  private static long solvePartOne(List<Character> file) {
    List<Integer> formattedList = new ArrayList<>();

    int id = 0;

    for (int i = 0; i < file.size(); i++) {
      Character c = file.get(i);
      int x = Character.getNumericValue(c);
      if (i % 2 == 0) {
        for (int j = 0; j < x; j++) {
          formattedList.add(id);
        }
        id++;
      } else {
        for (int j = 0; j < x; j++) {
          formattedList.add(-1);
        }
      }
    }

    List<Integer> res = new ArrayList<>();

    while (!formattedList.isEmpty()) {
      Integer i = formattedList.removeFirst();
      if (i == -1) {
        if (!formattedList.isEmpty()) {
          Integer i1 = formattedList.removeLast();
          while (i1 == -1) {
            i1 = formattedList.removeLast();
          }
          res.add(i1);
        }
      } else {
        res.add(i);
      }
    }

    return IntStream.range(0, res.size()).mapToLong(i -> res.get(i) * i).sum();
  }

  private static long solvePartTwo(List<Character> file) {
    List<Integer> formattedList = new ArrayList<>();
    int id = 0;
    for (int i = 0; i < file.size(); i++) {
      Character c = file.get(i);
      int x = Character.getNumericValue(c);
      if (i % 2 == 0) {
        for (int j = 0; j < x; j++) {
          formattedList.add(id);
        }
        id++;
      } else {
        for (int j = 0; j < x; j++) {
          formattedList.add(-1);
        }
      }
    }

    int highestId = formattedList.stream().max(Integer::compareTo).orElseThrow();

    while (highestId >= 0) {
      List<FreeSpace> freeSpaces = findFreeSpaces(formattedList);
      int finalHighestId = highestId;
      List<Integer> idBlock = formattedList.stream().filter(x -> x.equals(finalHighestId)).toList();
      var firstIndexId = formattedList.indexOf(finalHighestId);

      for (var space : freeSpaces) {
        if (space.length >= idBlock.size() && firstIndexId > space.start) {
          for (int i = 0; i < formattedList.size(); i++) {
            if (formattedList.get(i) == highestId) {
              formattedList.set(i, -1);
            }
          }
          for (int i = space.start; i < space.start + idBlock.size(); i++) {
            formattedList.set(i, highestId);
          }
          break;
        }
      }
      highestId--;
    }

    return IntStream.range(0, formattedList.size())
        .mapToLong(i -> formattedList.get(i).equals(-1) ? 0 : formattedList.get(i) * i)
        .sum();
  }

  record FreeSpace(int start, int length) {}

  private static List<FreeSpace> findFreeSpaces(List<Integer> list) {
    List<FreeSpace> freeSpaces = new ArrayList<>();
    int start = -1;
    int length = 0;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) == -1) {
        if (start == -1) {
          start = i;
        }
        length++;
      } else {
        if (start != -1) {
          freeSpaces.add(new FreeSpace(start, length));
          start = -1;
          length = 0;
        }
      }
    }
    if (start != -1) {
      freeSpaces.add(new FreeSpace(start, length));
    }
    return freeSpaces;
  }

  public static void main(String[] args) {
    DataLoader dataLoader = new DataLoader();

    List<Character> input =
        dataLoader.getInput("day9").getFirst().chars().mapToObj(c -> (char) c).toList();
    System.out.println(solvePartOne(input));
    System.out.println(solvePartTwo(input));
  }
}
