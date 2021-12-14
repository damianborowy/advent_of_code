const { inputOctopuses } = require("./inputData");

const STEPS = 100;
const FLASH_THRESHOLD = 9;

function flashOctopuses(octopuses) {
  const resultOctopuses = [...octopuses.map((row) => [...row])];
  let flashesCount = 0;
  let step = 0;

  function flash({ row, column }) {
    if (!resultOctopuses?.[row]?.[column]) return;

    if (resultOctopuses[row][column] <= FLASH_THRESHOLD) {
      resultOctopuses[row][column] += 1;
    }

    if (resultOctopuses[row][column] === 10) {
      flashesCount++;
      resultOctopuses[row][column] = 0;

      [
        { row: row - 1, column: column - 1 },
        { row: row - 1, column: column },
        { row: row - 1, column: column + 1 },
        { row: row, column: column - 1 },
        { row: row, column: column + 1 },
        { row: row + 1, column: column - 1 },
        { row: row + 1, column: column },
        { row: row + 1, column: column + 1 },
      ]
        .filter(({ row, column }) => resultOctopuses?.[row]?.[column] <= FLASH_THRESHOLD)
        .forEach(flash);
    }
  }

  while (step < STEPS) {
    const flashedOctopusesPositions = [];

    for (let row = 0; row < octopuses.length; row++) {
      for (let column = 0; column < octopuses[row].length; column++) {
        resultOctopuses[row][column] += 1;

        if (resultOctopuses[row][column] > FLASH_THRESHOLD) {
          flashedOctopusesPositions.push({ row, column });
        }
      }
    }

    flashedOctopusesPositions.forEach(flash);

    step++;
  }

  return flashesCount;
}

const flashesCount = flashOctopuses(inputOctopuses);
console.log(flashesCount);
