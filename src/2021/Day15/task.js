const { inputCave } = require("./inputData");

function buildCaveGrid(cave, sizeMultiplier) {
  return [...new Array(cave.length * sizeMultiplier)].map((_, rowIndex) =>
    [...new Array(cave[0].length * sizeMultiplier)].map(
      (_, columnIndex) =>
        (
          cave[rowIndex % cave.length][columnIndex % cave[0].length] +
          Math.floor(rowIndex / cave.length) +
          Math.floor(columnIndex / cave[0].length) - 1
        ) % 9 + 1
    )
  );
}

function getNeighbours(row, column) {
  return [
    [row + 1, column],
    [row - 1, column],
    [row, column + 1],
    [row, column - 1],
  ];
}

function calculateTotalGridScore(grid) {
  let sum = 0;

  for (const row of grid) {
    for (const element of row) {
      sum += element;
    }
  }

  return sum;
}

function findLowestPathCost(cave, caveSizeMultiplier = 1) {
  const caveGrid = buildCaveGrid(cave, caveSizeMultiplier);
  const scoreBoard = caveGrid.map((row) => row.map(() => Infinity));

  scoreBoard[0][0] = 0;
  caveGrid[0][0] = 0;

  let prevBoard = [];
  while (
    calculateTotalGridScore(scoreBoard) !== calculateTotalGridScore(prevBoard)
  ) {
    prevBoard = scoreBoard.map((r) => [...r]);

    for (let row = 0; row < caveGrid.length; row++) {
      for (let column = 0; column < caveGrid[0].length; column++) {
        if (row === 0 && column === 0) continue;

        scoreBoard[row][column] =
          Math.min(
            ...getNeighbours(row, column).map(
              ([x, y]) => scoreBoard[x]?.[y] ?? Infinity
            )
          ) + caveGrid[row][column];
      }
    }
  }

  return scoreBoard[scoreBoard.length - 1][scoreBoard[0].length - 1];
}

// task 1 - find lowest path cost

console.log("\n----- TASK 1 -----\n");
console.log(findLowestPathCost(inputCave));

// task 2 - find lowest path cost, but for a grid that's five times larger

console.log("\n----- TASK 2 -----\n");
console.log(findLowestPathCost(inputCave, 5));
