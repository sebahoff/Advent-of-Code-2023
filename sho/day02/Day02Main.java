package day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Day02Main {
    private static final String[] availableBoxColors = new String[] { "blue", "red", "green" };
    private static final Map<String, Integer> availableBoxes = Map.of("red", 12, "green", 13, "blue", 14);

    public static void main(String[] args) throws IOException {
        var games = new String[] {
                "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
                "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
                "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
                "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
                "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green",
        };
        Path filePath = Paths
                .get(".\\sho\\inputs\\day02.txt");
        var input = Files.readAllLines(filePath);

        var list = new ArrayList<Map<String, Integer>>();
        for (String game : input) {
            list.add(getMaxCubesInGame(game));
        }

        var allGames = new ArrayList<Integer>();
        var impossibleGames = new ArrayList<Integer>();

        var valuePart2 = 0;
        for (int i = 0; i < list.size(); i++) {
            var item = list.get(i);
            var boxSum = 1;
            for (String color : availableBoxColors) {
                allGames.add(i + 1);
                if (item.get(color) > availableBoxes.get(color)) {
                    impossibleGames.add(i + 1);
                }
                boxSum *= item.get(color);
            }
            valuePart2 += boxSum;
        }
        var uniqueList = new HashSet<>(allGames);
        var value = new int[] { 0 };
        uniqueList.forEach(item -> {
            if (!impossibleGames.contains(item)) {
                value[0] += item;
            }
        });
        // System.out.println(value[0]);
        System.out.println("valuePart2: " + valuePart2);
    }

    public static Map<String, Integer> getMaxCubesInGame(String s) {
        var sets = s.replaceAll("(Game) \\d+: ", "").split(";");
        var map = new HashMap<String, Integer>();

        for (String set : sets) {
            var boxes = set.split(",");

            for (String box : boxes) {
                var splitBox = box.trim().split(" ");
                var amount = Integer.parseInt(splitBox[0]);
                var color = splitBox[1];

                if (!map.containsKey(color) || map.containsKey(color) && map.get(color) < amount)
                    map.put(color, amount);
            }
        }

        return map;
    }
}
