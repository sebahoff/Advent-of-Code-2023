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

public class day08 {
    public static void main(String[] args) throws IOException {
        var node = Arrays.asList(new String[] {
                "RL",
                "",
                "AAA = (BBB, CCC)",
                "BBB = (DDD, EEE)",
                "CCC = (ZZZ, GGG)",
                "DDD = (DDD, DDD)",
                "EEE = (EEE, EEE)",
                "GGG = (GGG, GGG)",
                "ZZZ = (ZZZ, ZZZ)",
        });
        var node2 = Arrays.asList(new String[] {
                "LLR",
                "",
                "AAA = (BBB, BBB)",
                "BBB = (AAA, ZZZ)",
                "ZZZ = (ZZZ, ZZZ)",
        });

        Path filePath = Paths
                .get(".\\sho\\inputs\\day08.txt");
        var input = Files.readAllLines(filePath);

        var list = input;
        var instructions = list.get(0);
        var directions = list.subList(2, list.size());

        System.out.println(instructions);
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
            var lineLetters = line.replaceAll("[^A-Z]", "");
            var lineLettersList = splitEveryNthChar(lineLetters, 3);

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
        int count = 0;
        boolean onZZZ = false;

        var listItem = directions.get("AAA");
        while (!onZZZ) {
            var name = listItem.get(instructions[count % instructions.length]);
            if (name.equals("ZZZ")) {
                onZZZ = true;
            } else {
                // System.out.println(name);
                listItem = directions.get(name);
                // System.out.println(listItem);

                count++;
            }
            // System.out.println(count);
        }

        return count + 1;
    }
}
