package day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day08Main {
    public static void main(String[] args) throws IOException {
        var node = Arrays.asList("RL",
                "",
                "AAA = (BBB, CCC)",
                "BBB = (DDD, EEE)",
                "CCC = (ZZZ, GGG)",
                "DDD = (DDD, DDD)",
                "EEE = (EEE, EEE)",
                "GGG = (GGG, GGG)",
                "ZZZ = (ZZZ, ZZZ)");
        var node2 = Arrays.asList("LLR",
                "",
                "AAA = (BBB, BBB)",
                "BBB = (AAA, ZZZ)",
                "ZZZ = (ZZZ, ZZZ)");
        var node3 = Arrays.asList("LR",
                "",
                "11A = (11B, XXX)",
                "11B = (XXX, 11Z)",
                "11Z = (11B, XXX)",
                "22A = (22B, XXX)",
                "22B = (22C, 22C)",
                "22C = (22Z, 22Z)",
                "22Z = (22B, 22B)",
                "XXX = (XXX, XXX)");

        Path filePath = Paths
                .get(".\\sho\\inputs\\day08.txt");

        var list = Files.readAllLines(filePath);
        var instructions = list.get(0);
        var directions = list.subList(2, list.size());

        // System.out.println(instructions);
        // System.out.println(directions);

        var map = readLinesIntoMap(directions);
        // System.out.println(map);

        var count = countSteps(instructions.split(""), map);
        System.out.println(count);
    }

    public static List<String> splitEveryNthChar(String text, int n) {
        return Pattern.compile(".{1," + n + "}")
                .matcher(text)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }

    public static Map<String, Map<String, String>> readLinesIntoMap(List<String> lines) {
        var linesMap = new HashMap<String, Map<String, String>>();

        for (String line : lines) {
            var map = new HashMap<String, String>();
            var lineLetters = line.replaceAll("[^a-zA-Z0-9]", "");
            var lineLettersList = splitEveryNthChar(lineLetters, 3);

            // System.out.println(lineLetters);
            // System.out.println(lineLettersList);

            var start = lineLettersList.get(0);
            var left = lineLettersList.get(1);
            var right = lineLettersList.get(2);

            map.put("L", left);
            map.put("R", right);

            linesMap.put(start, map);
        }
        return linesMap;
    }

    public static int countSteps(String[] instructions, Map<String, Map<String, String>> directions) {
        var count = 0;
        var index = new int[] { 0 };
        var onZZZ = false;

        var set = directions.keySet()
                .stream()
                .filter(s -> s.endsWith("A"))
                .toList();
        // System.out.println(set);

        while (!onZZZ) {
            var names = set.stream().map(element -> directions.get(element).get(instructions[index[0] % instructions.length])).toList();
            if (names.stream().allMatch(name -> name.endsWith("Z"))) {
                onZZZ = true;
            } else {
                // System.out.println(count + ": " + names);
                set = names;
                // System.out.println(listItem);

                count++;
                index[0]++;
            }
            System.out.println(count);
        }

        return count + 1;
    }
}
