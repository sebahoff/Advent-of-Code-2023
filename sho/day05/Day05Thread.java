package day05;

import java.util.List;

import static day05.Day05Main.optionalGetFromList;

public class Day05Thread extends Thread {
    private final Long seedsStart;
    private final Long seedsEnd;
    private final List<String> seedsToSoilMap;
    private final List<String> soilToFertilizerMap;
    private final List<String> fertilizerToWaterMap;
    private final List<String> waterToLightMap;
    private final List<String> lightToTemperatureMap;
    private final List<String> temperatureToHumidityMap;
    private final List<String> humidityToLocationMap;
    private Long currLocation;

    public Day05Thread(Long seedsStart, Long seedsEnd, List<String> seedsToSoilMap, List<String> soilToFertilizerMap, List<String> fertilizerToWaterMap, List<String> waterToLightMap, List<String> lightToTemperatureMap, List<String> temperatureToHumidityMap, List<String> humidityToLocationMap, Long currLocation) {
        this.seedsStart = seedsStart;
        this.seedsEnd = seedsEnd;
        this.seedsToSoilMap = seedsToSoilMap;
        this.soilToFertilizerMap = soilToFertilizerMap;
        this.fertilizerToWaterMap = fertilizerToWaterMap;
        this.waterToLightMap = waterToLightMap;
        this.lightToTemperatureMap = lightToTemperatureMap;
        this.temperatureToHumidityMap = temperatureToHumidityMap;
        this.humidityToLocationMap = humidityToLocationMap;
        this.currLocation = currLocation;
    }

    public void run() {
        for (int j = 0; j < this.seedsEnd; j++) {
            var seed = this.seedsStart + j;

            var soil = optionalGetFromList(this.seedsToSoilMap, seed);
            var fertilizer = optionalGetFromList(this.soilToFertilizerMap, soil);
            var water = optionalGetFromList(this.fertilizerToWaterMap, fertilizer);
            var light = optionalGetFromList(this.waterToLightMap, water);
            var temperature = optionalGetFromList(this.lightToTemperatureMap, light);
            var humidity = optionalGetFromList(this.temperatureToHumidityMap, temperature);
            var location = optionalGetFromList(this.humidityToLocationMap, humidity);

//            System.out.println("[" + seedsStart + "]: " + seed + ", " + soil + ", " + fertilizer + ", " + water + ", " + light + ", " + temperature
//                    + ", " + humidity + ", " + location);
            System.out.println(j);

            if (this.currLocation == null || location < this.currLocation)
                this.currLocation = location;
        }

        System.out.println(this.currLocation);
    }
}