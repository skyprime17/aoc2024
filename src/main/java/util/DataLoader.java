package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class DataLoader {

  public List<String> getInput(String path) {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
      assert inputStream != null;
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
        return reader.lines().collect(Collectors.toList());
      }
    } catch (IOException e) {
      throw new RuntimeException("Error reading input file from resources", e);
    }
  }

}
