package day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Day01Main {
    private static final String[] numArr = new String[] { "zero", "one", "two", "three", "four", "five", "six", "seven",
            "eight", "nine" };

    public static void main(String[] args) throws IOException {
        var calibrationValues = new String[] { "1abc2", "pqr3stu8vwx", "a1b2c3d4e5f", "treb7uchet" };
        var calibrationValues2 = new String[] { "two1nine", "eightwothree", "abcone2threexyz", "xtwone3four",
                "4nineeightseven2", "zoneight234", "7pqrstsixteen" };

        Path filePath = Paths
                .get(".\\sho\\inputs\\day01.txt");
        var input = Files.readAllLines(filePath);

        var value = 0;
        for (String line : input) {
            var numbers = extractNumbersFromString(line);
            var num1 = getNumber(numbers, true);
            var num2 = getNumber(numbers, false);

            var concatted = num1 + "" + num2;
            value += Integer.parseInt(concatted);
        }
        System.out.println(value);
    }

    public static String extractNumbersFromString(String s) {
        var newString = new String[] { s };

        while (Stream.of(numArr).noneMatch(num -> newString[0].startsWith(num))
                && !Character.isDigit(newString[0].charAt(
                        0))) {
            newString[0] = newString[0].substring(1);
        }

        while (Stream.of(numArr).noneMatch(num -> newString[0].endsWith(num))
                && !Character.isDigit(newString[0].charAt(
                        newString[0].length() - 1))) {
            newString[0] = newString[0].substring(0, newString[0].length() - 1);
        }
        return newString[0];
    }

    public static int getNumber(String s, boolean first) {
        var index = 0;
        if (!first) {
            index = s.length() - 1;
        }
        try {
            return Integer.parseInt(String.valueOf(s.charAt(index)));
        } catch (Exception e) {
            for (int j = 0; j < numArr.length; j++) {
                var num = numArr[j];
                if (first && s.startsWith(num) || !first && s.endsWith(num)) {
                    return j;
                }
            }
        }
        return 0;
    }
}
