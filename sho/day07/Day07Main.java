package day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Day07Main {

    public static void main(String[] args) throws IOException {
        var example = Arrays
                .asList("32T3K 765", "T55J5 684", "KK677 28", "KTJJT 220", "QQQJA 483");

        Path filePath = Paths
                .get(".\\sho\\inputs\\day07.txt");
        var input = Files.readAllLines(filePath);

        var cardStrength = Arrays
                .asList("J", "2", "3", "4", "5", "6", "7", "8", "9", "T", "Q", "K", "A");

        var list = new ArrayList<Map>();
        input.forEach(element -> {
            String[] split = element.split(" ");
            var hand = split[0];
            var bid = split[1];
            var handElements = hand.split("");

            var jokerCount = 0;

            var object = new HashMap<>();
            for (String string : handElements) {
                if (string.equals("J")) {
                    jokerCount++;
                } else if (object.containsKey(string)) {
                    object.put(string, (int) object.get(string) + 1);
                } else {
                    object.put(string, 1);
                }
            }
            object.put("type", getType(object, jokerCount));

            object.put("hand", hand);
            object.put("bid", bid);

            list.add(object);
        });

        list.sort((o1, o2) -> {
            int o1Type = (int) o1.get("type");
            int o2Type = (int) o2.get("type");
            if (o1Type > o2Type)
                return 1;
            else if (o1Type < o2Type)
                return -1;

            var split1 = Arrays.asList(((String) o1.get("hand")).split(""));
            var split2 = Arrays.asList(((String) o2.get("hand")).split(""));
            for (int i = 0; i < 5; i++) {
                var char1 = split1.get(i);
                var char2 = split2.get(i);

                if (cardStrength.indexOf(char1) > cardStrength.indexOf(char2))
                    return 1;
                else if (cardStrength.indexOf(char1) < cardStrength.indexOf(char2))
                    return -1;
            }
            return 0;
        });

        var value = 0;
        for (int i = 0; i < list.size(); i++) {
            var a = ((String) list.get(i).get("hand")).split("");
//            var s = String.join("', '", a);
            // System.out.println("['" + s + "']");
            // System.out.println(list.get(i).get("hand") + ": " + list.get(i).get("type"));
            value += Integer.parseInt((String) list.get(i).get("bid")) * (i + 1);
        }
        System.out.println(value);
    }

    public static int optionalUpdateType(int currType, int newType) {
        return Math.max(currType, newType);
    }

    // Five of a kind = 6, Four of a kind = 5, Full house = 4, Three of a kind = 3,
    // Two pair = 2, One pair = 1, High card = 0
    public static int getType(Map map, int jokerCount) {
        // if we have a joker count of 5 we dont have map values hence we can return
        // early here
        if (jokerCount == 5) {
            return 6;
        }

        var counter = new int[]{0};
        var usedCards = new ArrayList<>();
        var usedJokers = new boolean[]{false};

        counter[0] = -1;
        var fiveOfAKind = map.values().stream()
                .filter(extractedFilter(map, jokerCount, counter, usedCards, usedJokers, 5))
                .toList().size();
        if (fiveOfAKind == 1) {
            return 6;
        }

        counter[0] = -1;
        var fourOfAKind = map.values().stream()
                .filter(extractedFilter(map, jokerCount, counter, usedCards, usedJokers, 4)).toList().size();
        if (fourOfAKind == 1) {
            return 5;
        }

        counter[0] = -1;
        var threeOfAKind = map.values().stream()
                .filter(extractedFilter(map, jokerCount, counter, usedCards, usedJokers, 3)).toList().size();

        counter[0] = -1;
        var pairsOfAKind = map.values().stream()
                .filter(extractedFilter(map, jokerCount, counter, usedCards, usedJokers, 2)).toList().size();

        if (threeOfAKind == 1 && pairsOfAKind == 1) {
            return 4;
        }
        if (threeOfAKind == 1) {
            return 3;
        }
        if (pairsOfAKind == 2) {
            return 2;
        }
        if (pairsOfAKind == 1) {
            return 1;
        }
        return 0;
    }

    private static Predicate extractedFilter(Map map, int jokerCount, int[] counter, ArrayList<Object> usedCards,
                                             boolean[] usedJokers, int value) {
        return item -> {
            counter[0]++;
            var intValue = (int) item;
            var usedCardValue = map.keySet().toArray()[counter[0]];
            if (intValue == value && !usedCards.contains(usedCardValue)) {
                usedCards.add(usedCardValue);
                return true;
            }
            if (!usedJokers[0] && (intValue + jokerCount) == value) {
                usedCards.add(usedCardValue);
                usedJokers[0] = true;
                return true;
            }
            return false;
        };
    }
}
