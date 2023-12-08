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

public class day06 {
    public static void main(String[] args) throws IOException {
        var example = Arrays.asList(new String[] {
                "Time:      7  15   30",
                "Distance:  9  40  200",
        });
        Path filePath = Paths
                .get(".\\sho\\inputs\\day06.txt");
        var input = Files.readAllLines(filePath);

        var value = 1;
        var map = readLinesIntoMap(input, true);
        // System.out.println(map);
        for (Map<String, Long> element : map) {
            var count = getPossibleWinningCombinations(element.get("time"), element.get("distance"));
            value *= count;
        }
        System.out.println(value);
    }

    public static List<Map<String, Long>> readLinesIntoMap(List<String> lines, boolean part2) {
        var times = Arrays.asList(lines.get(0).replaceAll("Time:", "").split(" ")).stream()
                .filter(time -> !time.trim().equals(""))
                .map(time -> Long.parseLong(time.trim())).toList();
        var distances = Arrays.asList(lines.get(1).replaceAll("Distance:", "").split(" ")).stream()
                .filter(time -> !time.trim().equals(""))
                .map(time -> Long.parseLong(time.trim())).toList();

        if (part2) {
            times = Arrays.asList(lines.get(0).replaceAll("[^0-9]", "")).stream()
                    .map(time -> Long.parseLong(time.trim())).toList();
            distances = Arrays.asList(lines.get(1).replaceAll("[^0-9]", "")).stream()
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
