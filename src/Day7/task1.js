const { crabs } = require("./inputData");

const minPosition = Math.min(...crabs);
const maxPosition = Math.max(...crabs);

const results = [...new Array(maxPosition - minPosition)]
  .map((_, index) => minPosition + index)
  .map((offset) =>
    crabs
      .map((crabPosition) => Math.abs(crabPosition - offset))
      .reduce((acc, val) => acc + val, 0)
  );

console.log(Math.min(...results));
