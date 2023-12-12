package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day10Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        var example = Arrays.asList(
                ".....",
                ".S-7.",
                ".|.|.",
                ".L-J.",
                "....."
        );
        var example2 = Arrays.asList(
                "..F7.",
                ".FJ|.",
                "SJ.L7",
                "|F--J",
                "LJ..."
        );

        Path filePath = Paths
                .get(".\\sho\\inputs\\day10.txt");
        var list = Files.readAllLines(filePath);

//        var value = getFarthestAwayPoint(example);
//        System.out.println(value);
//        System.out.println("---");
//        var value2 = getFarthestAwayPoint(example2);
//        System.out.println(value2);

        System.out.println(getFarthestAwayPoint(list));
    }

    public static String replaceChars(String str, String chars, int index) {
        if (str.charAt(index) == 'S') return str;
        return str.substring(0, index) + chars + str.substring(index + chars.length());
    }


    public static int getFarthestAwayPoint(List<String> lines) throws InterruptedException {
        var sX = -1;
        var sY = -1;

        var routesToCheck = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            if (line.contains("S")) {
                sX = line.indexOf("S");
                sY = i;

                if (sX + 1 < line.length() && line.charAt(sX + 1) != '.') {
                    var map = new HashMap<String, Object>();
                    map.put("x", sX + 1);
                    map.put("y", sY);
                    map.put("move", "+x");
                    routesToCheck.add(map);
                }
                if (sX > 0 && line.charAt(sX - 1) != '.') {
                    var map = new HashMap<String, Object>();
                    map.put("x", sX - 1);
                    map.put("y", sY);
                    map.put("move", "-x");
                    routesToCheck.add(map);
                }
                if (sX + 1 < lines.size() && lines.get(i + 1).charAt(sX) != '.') {
                    var map = new HashMap<String, Object>();
                    map.put("x", sX);
                    map.put("y", sY + 1);
                    map.put("move", "+y");
                    routesToCheck.add(map);
                }
                if (sY > 0 && lines.get(i - 1).charAt(sX) != '.') {
                    var map = new HashMap<String, Object>();
                    map.put("x", sX);
                    map.put("y", sY - 1);
                    map.put("move", "-y");
                    routesToCheck.add(map);
                }
                break;
            }
        }

//        System.out.println("routesToCheck: " + routesToCheck);

        var numbersMap = new HashMap<String, Integer>();
        for (Map<String, Object> start : routesToCheck) {
//            System.out.println("start: " + start);

            var counter = 1;
            var overlapping = false;
            var startX = (Integer) start.get("x");
            var startY = (Integer) start.get("y");
            var firstMove = (String) start.get("move");

            numbersMap.put(startX + ":" + startY, counter);

            counter++;
            var map = Day10Thread.followPath(startX, startY, lines.get(startY).charAt(startX), firstMove);
            var listIndex0 = (Integer) map.get("y");
            var index0 = (Integer) map.get("x");
            numbersMap.put(index0 + ":" + listIndex0, counter);

//            System.out.println("map0: " + map);
            while (!((Boolean) map.get("finished")) && !overlapping) {
                counter++;

                var x = (Integer) map.get("x");
                var y = (Integer) map.get("y");
                var move = (String) map.get("move");

                var c = lines.get(y).charAt(x);
                map = Day10Thread.followPath(x, y, c, move);

//                System.out.println("map: " + map);

                var listIndex = (Integer) map.get("y");
                var index = (Integer) map.get("x");
                if (numbersMap.containsKey(index + ":" + listIndex) && numbersMap.get(index + ":" + listIndex).equals(counter))
                    overlapping = true;
                else {
//                    System.out.println(counter);
                    numbersMap.put(index + ":" + listIndex, counter);
                }

            }
        }
        numbersMap.remove(sX + ":" + sY);

        return Collections.max(numbersMap.values());
    }
}
