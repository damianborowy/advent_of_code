import java.util.*;
import java.util.stream.*;
import java.lang.Math;

public record Point(long x, long y) {}
public record Row(Point sensor, Point beacon, long distance) {}
public record Interval(long start, long end) {}

def dist = { long x1, long y1, long x2, long y2 -> Math.abs(y2 - y1) + Math.abs(x2 - x1) };

def file = new File("input.txt");
def input = new ArrayList();
def line;

file.withReader { reader ->
  while ((line = reader.readLine()) != null) {
    def numbers = line.replaceAll(/[^\d-]/, ",").split(",");
    def coordinates = Stream.of(numbers)
      .filter(number -> number != "")
      .map(Integer::parseInt)
      .collect(Collectors.toList());

    input.add(
      new Row(
        sensor: new Point(coordinates[0], coordinates[1]), 
        beacon: new Point(coordinates[2], coordinates[3]), 
        distance: dist(coordinates[0], coordinates[1], coordinates[2], coordinates[3])
      )
    );
  }
};

def getIntervals(long y, ArrayList<Row> input) {
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
  return LongStream.range(0L, new Long(regions.size()))
    .map(index -> {
      def region = regions.get(Math.toIntExact(index));

      if (!regionsIntersect(interval, region)) return 0L;

      def minMaxBounds = new Interval(
        start: Math.min(region.end, interval. end), 
        end: Math.max(region.start, interval.start)
      );
      ArrayList<Interval> subRegions = regions.subList(Math.toIntExact(index + 1), Math.toIntExact(regions.size()));

      return volume(minMaxBounds) - intersectionWithRegionsVolume(minMaxBounds, subRegions);
    })
    .reduce(0, Long::sum);
}

// PART 1

def part1(ArrayList<Row> input) {
  long nonBeaconablePlacesCount = 0;
  def regions = new ArrayList<Interval>();
  long TARGET_Y = 2000000;

  for (var interval in getIntervals(TARGET_Y, input)) {
    nonBeaconablePlacesCount += volume(interval) - intersectionWithRegionsVolume(interval, regions);
    regions.add(interval);
  }

  return nonBeaconablePlacesCount;
}

println "Part 1: " + part1(input);

// PART 2

def countBlindsInRow(long y, long min, long max, ArrayList<Row> input) {
  return max - intersectionWithRegionsVolume(new Interval(start: min, end: max), getIntervals(y, input));
}

def part2(ArrayList<Row> input) {
  long MIN = 0;
  long MAX = 4000000;

  def targetY = LongStream.range(MIN, MAX)
    .parallel()
    .filter(y -> countBlindsInRow(y, MIN, MAX, input) != 0)
    .findFirst()
    .orElse(0L);

  def intervals = getIntervals(targetY, input);

  def targetX = LongStream.range(MIN, MAX)
    .parallel()
    .filter(x -> intervals.stream().allMatch(interval -> interval.start > x || interval.end < x))
    .findFirst()
    .orElse(0L);
  
  return 1L * targetX * MAX + targetY;
}

println "Part 2: " + part2(input);
