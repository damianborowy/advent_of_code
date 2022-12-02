const { heightsMap } = require("./inputData");
const { findLowPoints } = require("./task1");

function connectBasin(rootPoint) {
  const basin = [];

  function connectBasinHelper(point) {
    if (point?.isAlreadyInBasin || !point || !point?.height?.toString()) return;

    point.isAlreadyInBasin = true;
    basin.push(point.height);

    connectBasinHelper(point.up);
    connectBasinHelper(point.right);
    connectBasinHelper(point.down);
    connectBasinHelper(point.left);
  }

  connectBasinHelper(rootPoint);

  return basin;
}

function mapPointsToBasins(points) {
  return points.map(connectBasin);
}

const lowPoints = findLowPoints(heightsMap);
const basins = mapPointsToBasins(lowPoints);
console.log(
  basins
    .sort((a, b) => b.length - a.length)
    .slice(0, 3)
    .map((basin) => basin.length)
    .reduce((acc, val) => acc * val, 1)
);
