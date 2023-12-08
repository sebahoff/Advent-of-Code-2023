package day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class day03 {
    /**
     *
     */
    private static final String symbolRegEx = "[^A-Za-z0-9.]";

    public static void main(String[] args) throws IOException {
        var schematic = Arrays.asList(new String[] {
                "467..114..",
                "...*......",
                "..35..633.",
                "......#...",
                "617*......",
                ".....+.58.",
                "..592.....",
                "......755.",
                "...$.*....",
                ".664.598..",
        });

        Path filePath = Paths
                .get(".\\sho\\inputs\\day03.txt");
        var input = Files.readAllLines(filePath);
        var relevantNumbers = getRelevantNumbers(input);

        var value = new int[] { 0 };
        var counter = new int[] { 0 };
        relevantNumbers.values().forEach(list -> {
            counter[0]++;
            // System.out.println(counter[0] + ": " + list);
            list.forEach(num -> {
                value[0] += num;
            });
        });
        System.out.println(value[0]);
    }

    private static Map<Integer, List<Integer>> getRelevantNumbers(List<String> schematic) {
        var relevantNumbers = new HashMap<Integer, List<Integer>>();
        var nonRelevantNumbers = new HashMap<Integer, List<Integer>>();

        for (int i = 0; i < schematic.size(); i++) {
            var line = schematic.get(i);
            var chars = line.split("");

            var isRelevant = false;
            var numberString = "";
            var list = new ArrayList<Integer>();
            var nonRelevantList = new ArrayList<Integer>();
            for (int j = 0; j < chars.length; j++) {
                var charElement = chars[j];
                if (charElement.matches("[0-9]")) {
                    boolean previousIsSymbol = j != 0 && chars[j - 1].matches(symbolRegEx);
                    boolean nextIsSymbol = j < chars.length - 1 && chars[j + 1].matches(symbolRegEx);

                    boolean topIsSymbol = i != 0 && schematic.get(i - 1).split("")[j].matches(symbolRegEx);
                    boolean topLeftIsSymbol = i != 0 && j != 0
                            && schematic.get(i - 1).split("")[j - 1]
                                    .matches(symbolRegEx);
                    boolean topRightIsSymbol = i != 0 && j < chars.length - 1
                            && schematic.get(i - 1).split("")[j + 1]
                                    .matches(symbolRegEx);

                    boolean bottomIsSymbol = i < schematic.size() - 1
                            && schematic.get(i + 1).split("")[j].matches(symbolRegEx);
                    boolean bottomLeftIsSymbol = i < schematic.size() - 1 && j != 0
                            && schematic.get(i + 1).split("")[j - 1].matches(symbolRegEx);
                    boolean bottomRightIsSymbol = i < schematic.size() - 1 && j < chars.length - 1
                            && schematic.get(i + 1).split("")[j + 1]
                                    .matches(symbolRegEx);

                    if (previousIsSymbol
                            || nextIsSymbol
                            || topIsSymbol
                            || bottomIsSymbol
                            || bottomLeftIsSymbol
                            || bottomRightIsSymbol
                            || topRightIsSymbol
                            || topLeftIsSymbol) {
                        isRelevant = true;
                    }
                    numberString = new String(numberString + charElement);

                    // if (numberString.equals("368") && isRelevant)
                    // System.out.println(
                    // previousIsSymbol + " " + nextIsSymbol + " " + topIsSymbol + " " +
                    // bottomIsSymbol + " "
                    // + bottomLeftIsSymbol + " " + topRightIsSymbol + " " + topLeftIsSymbol + ", "
                    // + i
                    // + ":" + j + " " + schematic.get(i + 1).split("")[j]);
                } else {
                    if (!numberString.equals("") && isRelevant) {
                        try {
                            list.add(Integer.parseInt(numberString.trim()));
                        } catch (NumberFormatException e) {
                            // System.out.println(isRelevant);
                            // System.out.println(chars[j - 1] + ":" + chars[j] + ", i: " + i + " j: " + j);
                            // System.out.println(numberString);
                            e.printStackTrace();
                        }
                    } else if (!numberString.equals("")) {
                        nonRelevantList.add(Integer.parseInt(numberString.trim()));
                    }
                    isRelevant = false;
                    numberString = "";
                }
            }

            relevantNumbers.put(i, list);
            nonRelevantNumbers.put(i, nonRelevantList);
        }

        var counter = new int[] { 0 };
        nonRelevantNumbers.values().forEach(list -> {
            counter[0]++;
            System.out.println(counter[0] + ": " + list);
        });

        return relevantNumbers;
    }
}
