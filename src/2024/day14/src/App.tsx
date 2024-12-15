import { useEffect, useMemo, useState } from "react";

type Point = [number, number];

type Robot = { id: number; position: Point; velocity: Point };

type CoordinateRange = { from: number; to: number };

type PointRange = { x: CoordinateRange; y: CoordinateRange };

const MAX_MAP_X = 101 - 1;
const MAX_MAP_Y = 103 - 1;

const MAP_RANGE: PointRange = {
  x: { from: 0, to: MAX_MAP_X },
  y: { from: 0, to: MAX_MAP_Y },
};

const CELL_SIZE = 10;

const QUADRANTS_RANGES: PointRange[] = [
  {
    x: { from: 0, to: MAX_MAP_X / 2 - 1 },
    y: { from: 0, to: MAX_MAP_Y / 2 - 1 },
  },
  {
    x: { from: MAX_MAP_X / 2 + 1, to: MAX_MAP_X },
    y: { from: 0, to: MAX_MAP_Y / 2 - 1 },
  },
  {
    x: { from: 0, to: MAX_MAP_X / 2 - 1 },
    y: { from: MAX_MAP_Y / 2 + 1, to: MAX_MAP_Y },
  },
  {
    x: { from: MAX_MAP_X / 2 + 1, to: MAX_MAP_X },
    y: { from: MAX_MAP_Y / 2 + 1, to: MAX_MAP_Y },
  },
];

function App() {
  const [maps, setMaps] = useState<Robot[][]>([]);
  const [selectedMapIndex, setSelectedMapIndex] = useState(0);
  const [selectedRobotIndex, setSelectedRobotIndex] = useState(0);

  useEffect(() => {
    (async () => {
      const input = await fetch("/input.txt").then((data) => data.text());

      const robots = parseInput(input);

      setMaps([robots]);
      calculateNextPositions(robots);
    })();
  }, []);

  const parseInput = (input: string): Robot[] =>
    input
      .split("\n")
      .filter(Boolean)
      .map<Robot>((row, index) => {
        const [px, py, vx, vy] = row
          .replaceAll(/[^-\d]/g, " ")
          .split(/\s/)
          .filter(Boolean)
          .map(Number);

        return {
          id: index,
          position: [py, px],
          velocity: [vy, vx],
        };
      });

  // const cloneRobots = (robots: Robot[]): Robot[] =>
  //   JSON.parse(JSON.stringify(robots));

  const addPoints = ([y1, x1]: Point, [y2, x2]: Point): Point => [
    y1 + y2,
    x1 + x2,
  ];

  const isPointInRange = ([y, x]: Point, range = MAP_RANGE): boolean => {
    const {
      x: { from: fromX, to: toX },
      y: { from: fromY, to: toY },
    } = range;

    return x >= fromX && x <= toX && y >= fromY && y <= toY;
  };

  const wrapCoordinate = (value: number, maxValue: number): number => {
    if (value < 0) {
      return maxValue - Math.abs(value);
    }

    if (value >= maxValue) {
      return value - maxValue;
    }

    return value;
  };

  const wrapRobotPosition = ([y, x]: Point): Point => {
    const newX = wrapCoordinate(x, MAX_MAP_X + 1);
    const newY = wrapCoordinate(y, MAX_MAP_Y + 1);

    return [newY, newX];
  };

  const moveRobotOnce = (robot: Robot): Robot => {
    const { position, velocity } = robot;
    const naiveRobotPosition = addPoints(position, velocity);

    const newRobotPosition = isPointInRange(naiveRobotPosition)
      ? naiveRobotPosition
      : wrapRobotPosition(naiveRobotPosition);

    return { ...robot, position: newRobotPosition };
  };

  const calculateNextPositions = (
    inputRobots: Robot[],
    iterations = 100
  ): Robot[][] => {
    const newMaps: Robot[][] = [];

    [...Array(iterations)].reduce((robots) => {
      const movedRobots = robots.map(moveRobotOnce);

      newMaps.push(movedRobots);

      return movedRobots;
    }, inputRobots);

    setMaps((previousMaps) => [...previousMaps, ...newMaps]);

    return newMaps;
  };

  const getRobotsCountPerQuadrant = (robots: Robot[]): number[] =>
    QUADRANTS_RANGES.map(
      (range) =>
        robots.filter(({ position }) => isPointInRange(position, range)).length
    );

  const selectedMap = useMemo(
    () => maps[selectedMapIndex] ?? [],
    [maps, selectedMapIndex]
  );

  const robotsCountPerQuadrant = useMemo(
    () => getRobotsCountPerQuadrant(selectedMap),
    [selectedMap]
  );

  const stringifyPosition = (position?: Point): string =>
    position ? `(${position[0]},${position[1]})` : "";

  const robotsMap = useMemo(
    () =>
      Object.fromEntries(
        [...selectedMap]
          .sort((a, b) =>
            a.id === selectedRobotIndex
              ? 1
              : b.id === selectedRobotIndex
              ? -1
              : 0
          )
          .map((robot) => [stringifyPosition(robot.position), robot])
      ),
    [selectedMap, selectedRobotIndex]
  );

  const selectedRobot = selectedMap[selectedRobotIndex];

  const safetyFactor = robotsCountPerQuadrant.reduce(
    (acc, val) => acc * val,
    1
  );

  return (
    <div style={{ display: "flex", gap: 16 }}>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          border: "1px solid grey",
        }}
      >
        {[...Array(MAX_MAP_Y + 1)].map((_row, rowIndex) => (
          <div key={rowIndex} style={{ display: "flex", width: "100%" }}>
            {[...Array(MAX_MAP_X + 1)].map((_col, colIndex) => {
              const robot = robotsMap[stringifyPosition([rowIndex, colIndex])];

              return (
                <div
                  key={colIndex}
                  onClick={() =>
                    robot ? setSelectedRobotIndex(robot.id) : null
                  }
                  style={{
                    width: CELL_SIZE,
                    height: CELL_SIZE,
                    backgroundColor:
                      robot?.id === selectedRobotIndex
                        ? "red"
                        : robot
                        ? "green"
                        : "white",
                    border: "1px solid gray",
                  }}
                />
              );
            })}
          </div>
        ))}
      </div>
      <div style={{ display: "flex", flexDirection: "column", gap: 8 }}>
        <div>Robots per quadrant: {robotsCountPerQuadrant.toString()}</div>
        <div>Safety factor: {safetyFactor}</div>
        <div>Selected map index: {selectedMapIndex}</div>
        <input
          type="range"
          min={0}
          max={maps.length - 1}
          value={selectedMapIndex}
          onChange={(event) => setSelectedMapIndex(+event.target.value)}
        />
        <div>
          <button
            onClick={() => setSelectedMapIndex((prevIndex) => prevIndex - 1)}
          >
            Previous index
          </button>
          <button
            onClick={() => setSelectedMapIndex((prevIndex) => prevIndex + 1)}
          >
            Next index
          </button>
        </div>
        <div style={{ display: "flex", flexDirection: "column", gap: 4 }}>
          <div>
            <span>Selected robot:</span>
            <select
              onChange={(event) => setSelectedRobotIndex(+event.target.value)}
              value={selectedRobotIndex}
            >
              {selectedMap.map(({ id }) => (
                <option value={id}>{id}</option>
              ))}
            </select>
          </div>
          <span>Position: {stringifyPosition(selectedRobot?.position)}</span>
          <span>Velocity: {stringifyPosition(selectedRobot?.velocity)}</span>
        </div>
        <button
          onClick={() => calculateNextPositions(maps[maps.length - 1], 1000)}
        >
          Generate 1000 more iterations
        </button>
      </div>
    </div>
  );
}

export default App;
