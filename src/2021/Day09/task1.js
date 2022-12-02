const { heightsMap } = require("./inputData");

function nullSafeMin(...values) {
  return Math.min(
    ...values.map((value) =>
      value?.toString() ? value : Number.MAX_SAFE_INTEGER
    )
  );
}

function getMinAdjacentHeight(point) {
  return nullSafeMin(
    point?.height,
    point?.up?.height,
    point?.right?.height,
    point?.down?.height,
    point?.left?.height
  );
}

function findLowPoints(points) {
  const lowPoints = [];

  for (let row = 0; row < points.length; row++) {
    for (let column = 0; column < points[row].length; column++) {
      const currentPoint = points[row][column];
      const minAdjacentHeight = getMinAdjacentHeight(currentPoint);

      if (minAdjacentHeight === points[row][column]?.height) {
        lowPoints.push(points[row][column]);
      }
    }
  }

  return lowPoints;
}
exports.findLowPoints = findLowPoints;

const lowPoints = findLowPoints(heightsMap);
console.log(
  lowPoints
    .map((point) => point.height)
    .reduce((acc, val) => acc + (val + 1), 0)
);
