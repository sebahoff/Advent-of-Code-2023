package day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class day04 {
    public static void main(String[] args) throws IOException {
        var scratchCards = Arrays.asList(new String[] {
                "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
                "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
                "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
                "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
                "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
                "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11",
        });

        Path filePath = Paths
                .get(".\\sho\\inputs\\day04.txt");
        var input = Files.readAllLines(filePath);

        var value = new int[] { 0 };
        var matches = getMatchesForScratchCards(scratchCards);

        System.out.println(getAmountOfScratchCards(input));

        matches.forEach(el -> {
            if (el == 0)
                return;
            var startValue = 1;
            for (int i = 1; i < el; i++) {
                startValue *= 2;
            }
            value[0] += startValue;
        });
        // System.out.println(value[0]);
    }

    public static List<Integer> getMatchesForScratchCards(List<String> lines) {
        var matches = new ArrayList<Integer>();
        var additionalLines = new ArrayList<String>();

        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            var winningNumbersAmount = getWinningNumbersAmount(line);

            matches.add(winningNumbersAmount);
            if (winningNumbersAmount > 0) {
                additionalLines.addAll(lines.subList(i + 1, i + 1 + winningNumbersAmount));
            }
        }

        return matches;
    }

    public static int getAmountOfScratchCards(List<String> lines) {
        var additionalCards = new ArrayList<String>();
        processScratchCards(lines, lines, additionalCards, 0);

        return additionalCards.size() + lines.size();
    }

    private static String getCardName(String s) {
        return s.split(":")[0];
    }

    private static void processScratchCards(List<String> originalScratchCards, List<String> lines,
            List<String> additionalLines, int recursion) {
        var list = new ArrayList<String>();

        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            var winningNumbersAmount = getWinningNumbersAmount(line);

            if (winningNumbersAmount > 0) {
                var cardNumber = Integer.parseInt(getCardName(line).replaceAll("\\D", ""));
                additionalLines
                        .addAll(originalScratchCards.subList(cardNumber, cardNumber + winningNumbersAmount));
                list.addAll(originalScratchCards.subList(cardNumber, cardNumber + winningNumbersAmount));
            }
        }
        if (list.size() > 0) {
            processScratchCards(originalScratchCards, list, additionalLines, recursion + 1);
        }
    }

    private static int getWinningNumbersAmount(String line) {
        var lineWithoutCard = line.replaceAll("Card.*\\d*:", "");
        var splitLine = lineWithoutCard.split("\\|");

        var winningNumbersList = Arrays.asList(splitLine[0].split(" "));
        var winningNumbers = winningNumbersList.stream().filter(el -> !el.equals(""))
                .map(el -> Integer.parseInt(el.trim()));
        var winningNumbersSet = new HashSet<Integer>();
        winningNumbers.forEach(el -> winningNumbersSet.add(el));

        var cardNumbersList = Arrays.asList(splitLine[1].split(" "));
        var cardNumbers = cardNumbersList.stream().filter(el -> !el.equals(""))
                .map(el -> Integer.parseInt(el.trim()));
        var winningNumbersAmount = cardNumbers.filter(el -> winningNumbersSet.contains(el)).toList().size();

        return winningNumbersAmount;
    }
}
