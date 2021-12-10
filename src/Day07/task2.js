const { crabs } = require("./inputData");
const { mean } = require("../utils");

const crabPositionsMean = Math.floor(mean(crabs));

const results = [crabPositionsMean, crabPositionsMean + 1].map((offset) =>
  crabs
    .map((crabPosition) => Math.abs(crabPosition - offset))
    .map((positionOffset) => (positionOffset * (positionOffset + 1)) / 2)
    .reduce((acc, val) => acc + val, 0)
);

console.log(Math.min(...results));
