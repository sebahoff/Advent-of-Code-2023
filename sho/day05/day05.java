package day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class day05 {
    public static void main(String[] args) throws IOException {
        var example = Arrays.asList(new String[] {
                "seeds: 79 14 55 13",
                "",
                "seed-to-soil map:",
                "50 98 2",
                "52 50 48",
                "",
                "soil-to-fertilizer map:",
                "0 15 37",
                "37 52 2",
                "39 0 15",
                "",
                "fertilizer-to-water map:",
                "49 53 8",
                "0 11 42",
                "42 0 7",
                "57 7 4",
                "",
                "water-to-light map:",
                "88 18 7",
                "18 25 70",
                "",
                "light-to-temperature map:",
                "45 77 23",
                "81 45 19",
                "68 64 13",
                "",
                "temperature-to-humidity map:",
                "0 69 1",
                "1 0 69",
                "",
                "humidity-to-location map:",
                "60 56 37",
                "56 93 4"
        });

        Path filePath = Paths
                .get(".\\sho\\inputs\\day05.txt");
        var input = Files.readAllLines(filePath);

        var list = input;
        var seeds = list.get(0).replaceAll("seeds: ", "").split(" ");

        var seedsToSoilMap = getSeedsToSoilMap(list);
        var soilToFertilizerMap = getSoilToFertilizerMap(list);
        var fertilizerToWaterMap = getFertilizerToWaterMap(list);
        var waterToLightMap = getWaterToLightMap(list);
        var lightToTemperatureMap = getLightToTemperatureMap(list);
        var temperatureToHumidityMap = getTemperatureToHumidityMap(list);
        var humidityToLocationMap = getHumidityToLocationMap(list);

        Long currLocation = null;
        for (String seed : seeds) {
            var soil = optionalGetFromMap(seedsToSoilMap, Long.parseLong(seed));
            var fertilizer = optionalGetFromMap(soilToFertilizerMap, soil);
            var water = optionalGetFromMap(fertilizerToWaterMap, fertilizer);
            var light = optionalGetFromMap(waterToLightMap, water);
            var temperature = optionalGetFromMap(lightToTemperatureMap, light);
            var humidity = optionalGetFromMap(temperatureToHumidityMap, temperature);
            var location = optionalGetFromMap(humidityToLocationMap, humidity);

            System.out.println(seed + ", " + soil + ", " + fertilizer + ", " + water + ", " + light + ", " + temperature
                    + ", " + humidity + ", " + location);

            if (currLocation == null || location < currLocation)
                currLocation = location;
        }

        // System.out.println();
        // System.out.println(seedsToSoilMap);
        // System.out.println(soilToFertilizerMap);
        // System.out.println(fertilizerToWaterMap);
        // System.out.println(waterToLightMap);
        // System.out.println(lightToTemperatureMap);
        // System.out.println(temperatureToHumidityMap);
        // System.out.println(humidityToLocationMap);

        System.out.println(currLocation);
    }

    private static Long optionalGetFromMap(Map<Long, Long> map, Long key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return key;
    }

    private static Map<Long, Long> getHumidityToLocationMap(List<String> example) {
        return readMapListIntoMapObject(
                example.subList(example.indexOf("humidity-to-location map:") + 1,
                        example.size()));
    }

    private static Map<Long, Long> getTemperatureToHumidityMap(List<String> example) {
        return readMapListIntoMapObject(
                example.subList(example.indexOf("temperature-to-humidity map:") + 1,
                        example.indexOf("humidity-to-location map:") - 1));
    }

    private static Map<Long, Long> getLightToTemperatureMap(List<String> example) {
        return readMapListIntoMapObject(
                example.subList(example.indexOf("light-to-temperature map:") + 1,
                        example.indexOf("temperature-to-humidity map:") - 1));
    }

    private static Map<Long, Long> getWaterToLightMap(List<String> example) {
        return readMapListIntoMapObject(example.subList(example.indexOf("water-to-light map:") + 1,
                example.indexOf("light-to-temperature map:") - 1));
    }

    private static Map<Long, Long> getFertilizerToWaterMap(List<String> example) {
        return readMapListIntoMapObject(
                example.subList(example.indexOf("fertilizer-to-water map:") + 1,
                        example.indexOf("water-to-light map:") - 1));
    }

    private static Map<Long, Long> getSoilToFertilizerMap(List<String> example) {
        return readMapListIntoMapObject(
                example.subList(example.indexOf("soil-to-fertilizer map:") + 1,
                        example.indexOf("fertilizer-to-water map:") - 1));
    }

    private static Map<Long, Long> getSeedsToSoilMap(List<String> example) {
        return readMapListIntoMapObject(example.subList(example.indexOf("seed-to-soil map:") + 1,
                example.indexOf("soil-to-fertilizer map:") - 1));
    }

    public static Map<Long, Long> readMapListIntoMapObject(List<String> lines) {
        var linesMap = new HashMap<Long, Long>();

        for (String line : lines) {
            var numbers = Arrays.asList(line.split(" ")).stream().map(num -> Long.parseLong(num)).toList();

            for (int i = 0; i < numbers.get(2); i++) {
                linesMap.put(numbers.get(1) + i, numbers.get(0) + i);
            }
        }
        return linesMap;
    }
}
