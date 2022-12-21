import java.util.*;
import java.util.stream.*;

record Point(int x, int y, int z) implements Comparable<Point> {
    public List<Point> getNeighbors() {
        final List<Point> neighbors = new ArrayList<>();

        neighbors.add(new Point(x + 1, y, z));
        neighbors.add(new Point(x - 1, y, z));
        neighbors.add(new Point(x, y + 1, z));
        neighbors.add(new Point(x, y - 1, z));
        neighbors.add(new Point(x, y, z + 1));
        neighbors.add(new Point(x, y, z - 1));

        return neighbors;
    }

    public int sidesTouching(List<Point> otherPoints) {
        return (int) getNeighbors()
            .stream()
            .filter(point -> otherPoints.contains(point))
            .count();
    }

    @Override
    public int compareTo(final Point o) {
        if (this.z != o.z) {
            return Integer.compare(this.z, o.z);
        } else if (this.y != o.y) {
            return Integer.compare(this.y, o.y);
        } else {
            return Integer.compare(this.x, o.x);
        }
    }
}

class BoundingBox {
    private int maxX, minX, maxY, minY, maxZ, minZ;
    
    public BoundingBox(List<Point> points) {
        maxX = points.stream().map(Point::x).reduce(Integer.MIN_VALUE, Math::max) + 1;
        minX = points.stream().map(Point::x).reduce(Integer.MAX_VALUE, Math::min) - 1;
        maxY = points.stream().map(Point::y).reduce(Integer.MIN_VALUE, Math::max) + 1;
        minY = points.stream().map(Point::y).reduce(Integer.MAX_VALUE, Math::min) - 1;
        maxZ = points.stream().map(Point::z).reduce(Integer.MIN_VALUE, Math::max) + 1;
        minZ = points.stream().map(Point::z).reduce(Integer.MAX_VALUE, Math::min) - 1;
    }

    private boolean isPointInside(Point point) {
        return point.x() >= minX && point.x() <= maxX &&
            point.y() >= minY && point.y() <= maxY &&
            point.z() >= minZ && point.z() <= maxZ;    
    }

    public List<Point> outsidePoints(List<Point> points) {
        var outsidePoints = new ArrayList<Point>();
        var queue = new LinkedList<Point>(Arrays.asList(new Point(minX, minY, minZ)));

        while (queue.size() > 0) {
            var point = queue.pop();

            if (!points.contains(point) && !outsidePoints.contains(point) && isPointInside(point)) {
                outsidePoints.add(point);
                point.getNeighbors().stream().forEach(queue::add);
            }
        }

        return outsidePoints;
    }
}

public class Main {
    static String inputString = """""";

    public static void main(String[] args) {
        var points = Stream.of(inputString.split("\n"))
            .map(String::trim)
            .map(row -> Stream.of(row.split(",")).map(Integer::parseInt).toArray(Integer[]::new))
            .map(coords -> new Point(coords[0], coords[1], coords[2]))
            .toList();
        
        // PART 1
        var part1 = points.stream().map(point -> 6 - point.sidesTouching(points)).reduce(0, Integer::sum);
        System.out.println("Part 1: " + part1);

        // PART 2
        var outsidePoints = new BoundingBox(points).outsidePoints(points);
        var part2 = points.stream().map(point -> point.sidesTouching(outsidePoints)).reduce(0, Integer::sum);
        System.out.println("Part 2: " + part2);
    }
}