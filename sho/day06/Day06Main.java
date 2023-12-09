package day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day06Main {
    public static void main(String[] args) throws IOException {
        var example = Arrays.asList("Time:      7  15   30",
                "Distance:  9  40  200");
        Path filePath = Paths
                .get(".\\sho\\inputs\\day06.txt");
        var input = Files.readAllLines(filePath);

        var value = 1;
        var map = readLinesIntoMap(input, true);
        // System.out.println(map);
        for (Map<String, Long> element : map) {
            var count = getPossibleWinningCombinations(element.get("time"), element.get("distance"));
            value *= (int) count;
        }
        System.out.println(value);
    }

    public static List<Map<String, Long>> readLinesIntoMap(List<String> lines, boolean part2) {
        var times = Arrays.stream(lines.get(0).replaceAll("Time:", "").split(" "))
                .filter(time -> !time.trim().isEmpty())
                .map(time -> Long.parseLong(time.trim())).toList();
        var distances = Arrays.stream(lines.get(1).replaceAll("Distance:", "").split(" "))
                .filter(time -> !time.trim().isEmpty())
                .map(time -> Long.parseLong(time.trim())).toList();

        if (part2) {
            times = Stream.of(lines.get(0).replaceAll("[^0-9]", ""))
                    .map(time -> Long.parseLong(time.trim())).toList();
            distances = Stream.of(lines.get(1).replaceAll("[^0-9]", ""))
                    .map(time -> Long.parseLong(time.trim())).toList();
        }

        var list = new ArrayList<Map<String, Long>>();
        for (int i = 0; i < times.size(); i++) {
            var map = new HashMap<String, Long>();

            var time = times.get(i);
            var distance = distances.get(i);

            map.put("time", time);
            map.put("distance", distance);

            list.add(map);
        }
        return list;
    }

    public static long getPossibleWinningCombinations(long time, long distance) {
        var combinationCount = 0;

        for (int i = 0; i < time; i++) {
            var distanceForI = i * (time - i);
            // System.out.println("i: " + i + ", distanceForI: " + distanceForI);
            if (distanceForI > distance) {
                combinationCount++;
            }
        }

        return combinationCount;
    }
}
