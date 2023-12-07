package day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class day07 {

    public static void main(String[] args) throws IOException {
        var example = new String[] { "32T3K 765", "T55J5 684", "KK677 28", "KTJJT 220", "QQQJA 483" };
        var example2 = new String[] { "32T3K 765", "T55J5 684", "KK677 28", "KTJJT 220", "QQQJA 483" };

        Path filePath = Paths
                .get(".\\sho\\inputs\\day07.txt");
        var input = Files.readAllLines(filePath);

        var cardStrength = Arrays
                .asList(new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A", "J" });

        var list = new ArrayList<Map>();
        input.stream().forEach(element -> {
            String[] split = element.split(" ");
            var hand = split[0];
            var bid = split[1];
            var handElements = hand.split("");

            var object = new HashMap<>();
            for (String string : handElements) {
                if (object.containsKey(string)) {
                    object.put(string, (int) object.get(string) + 1);
                } else {
                    object.put(string, 1);
                }
            }
            object.put("type", getType(object));
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

            String[] split1 = ((String) o1.get("hand")).split("");
            String[] split2 = ((String) o2.get("hand")).split("");
            for (int i = 0; i < 5; i++) {
                var char1 = split1[i];
                var char2 = split2[i];

                if (cardStrength.indexOf(char1) > cardStrength.indexOf(char2))
                    return 1;
                else if (cardStrength.indexOf(char1) < cardStrength.indexOf(char2))
                    return -1;
            }
            return 0;
        });

        var value = 0;
        for (int i = 0; i < list.size(); i++) {
            value += (int) Integer.parseInt((String) list.get(i).get("bid")) * (i + 1);
        }
        System.out.println(value);
    }

    // Five of a kind = 6, Four of a kind = 5, Full house = 4, Three of a kind = 3,
    // Two pair = 2, One pair = 1, High card = 0
    public static int getType(Map map) {
        int type = 0;
        for (Object value : map.values()) {
            int intValue = (int) value;

            if (type == 1 && intValue == 3 || type == 3 && intValue == 2)
                type = 4;
            else if (type == 1 && intValue == 2)
                type = 2;
            else if (intValue == 5)
                type = 6;
            else if (intValue == 4)
                type = 5;
            else if (intValue == 3)
                type = 3;
            else if (intValue == 2)
                type = 1;
        }
        return type;
    }
}
