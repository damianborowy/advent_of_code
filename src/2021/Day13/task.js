const { inputOrigamiBoard, inputInstructions } = require("./inputData");

function makeOrigami(origamiBoard, instructions) {
  let resultBoard = origamiBoard;

  for (const instruction of instructions) {
    const foldedBoard =
      instruction.direction === "x"
        ? [...new Array(resultBoard.length)].map(() => [
            ...new Array(instruction.position),
          ])
        : [...new Array(instruction.position)].map(() => [
            ...new Array(resultBoard[0].length),
          ]);

    for (let row = 0; row < foldedBoard.length; row++) {
      for (let column = 0; column < foldedBoard[0].length; column++) {
        if (instruction.direction === "x") {
          foldedBoard[row][column] =
            resultBoard[row][column] ||
            resultBoard[row][instruction.position * 2 - column];
        } else if (instruction.direction === "y") {
          foldedBoard[row][column] =
            resultBoard[row][column] ||
            resultBoard[instruction.position * 2 - row]?.[column];
        }
      }
    }

    resultBoard = foldedBoard;
  }

  return resultBoard;
}

// task 1 - find number of points after first fold

const origami1 = makeOrigami(inputOrigamiBoard, inputInstructions.slice(0, 1));
console.log("\n----- TASK 1 -----\n");
console.log(
  origami1
    .flatMap((row) => row.reduce((acc, val) => acc + (val || 0), 0))
    .reduce((acc, val) => acc + val, 0)
);

// task 2 - perform all folds and print resulting board

const origami2 = makeOrigami(inputOrigamiBoard, inputInstructions);
console.log("\n----- TASK 2 -----\n");
console.log(
  origami2.map((row) => row.map((val) => val || " ").join(" ")).join("\n")
);
