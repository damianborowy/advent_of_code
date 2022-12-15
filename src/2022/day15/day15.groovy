import java.util.*;
import java.util.stream.*;
import java.lang.Math;

public record Point(int x, int y) {}
public record Row(Point sensor, Point beacon, int distance) {}
public record Interval(int start, int end) {}

def dist = { int x1, int y1, int x2, int y2 -> Math.abs(y2 - y1) + Math.abs(x2 - x1) };

def file = new File("input.txt");
def input = new ArrayList();
def line;

file.withReader { reader ->
  while ((line = reader.readLine()) != null) {
    def numbers = line.replaceAll(/[^\d-]/, ",").split(",");
    def coordinates = Stream.of(numbers).filter(number -> number != "").map(Integer::parseInt).collect(Collectors.toList());

    input.add(
      new Row(
        sensor: new Point(coordinates[0], coordinates[1]), 
        beacon: new Point(coordinates[2], coordinates[3]), 
        distance: dist(coordinates[0], coordinates[1], coordinates[2], coordinates[3])
      )
    );
  }
};

def getIntervals(int y, ArrayList<Row> input) {
  return input.stream()
    .map(row -> {
      def spareX = row.distance - Math.abs(row.sensor.y - y);

      return spareX >= 0 ? new Interval(start: row.sensor.x - spareX, end: row.sensor.x + spareX) : null;
    })
    .filter(Objects::nonNull)
    .collect(Collectors.toList()) 
};

def volume(Interval interval) { 
  return Math.abs(interval.end - interval.start) 
}

def regionsIntersect(Interval region1, Interval region2) {
  return region2.start <= region1.end && region2.end >= region1.start;
}

def intersectionWithRegionsVolume(Interval interval, ArrayList<Interval> regions) {
  return IntStream.range(0, regions.size())
    .map(index -> {
      def region = regions.get(index);

      if (!regionsIntersect(interval, region)) return 0;

      def minMaxBounds = new Interval(start: Math.max(region.start, interval.start), end: Math.min(region.end, interval. end));
      ArrayList<Interval> subRegions = regions.subList(index + 1, regions.size());

      return volume(minMaxBounds) - intersectionWithRegionsVolume(minMaxBounds, subRegions);
    })
    .reduce(0, Integer::sum);
}

// PART 1

def part1(ArrayList<Row> input) {
  int nonBeaconablePlacesCount = 0;
  def regions = new ArrayList<Interval>();
  int TARGET_Y = 2000000;

  for (var interval in getIntervals(TARGET_Y, input)) {
    nonBeaconablePlacesCount += volume(interval) - intersectionWithRegionsVolume(interval, regions);
    regions.add(interval);
  }

  return nonBeaconablePlacesCount;
}

println "Part 1: " + part1(input);

// PART 2

def countBlindsInRow(int y, int max, int min, ArrayList<Row> input) {
  return max - intersectionWithRegionsVolume(new Interval(start: min, end: max), getIntervals(y, input));
}

def part2(ArrayList<Row> input) {
  int MAX = 4000000;
  int MIN = 0;
  int y = 0;

  while (countBlindsInRow(y, MAX, MIN, input) != 0) y++;

  def intervals = getIntervals(y, input);

  for (int x = MIN; x <= MAX; x++) {
    if (intervals.stream().allMatch(interval -> interval.start > x || interval.end < x)) {
      return x * MAX * y;
    }
  }
}

println "Part 2: " + part2(input);
