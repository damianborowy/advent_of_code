const { boards, BOARD_SIDE_SIZE, numbers } = require("./inputData");

function getBoardIfWon(board) {
  for (let i = 0; i < BOARD_SIDE_SIZE; i++) {
    const row = board[i];
    const column = [...new Array(BOARD_SIDE_SIZE)].map(
      (_, index) => board[index][i]
    );

    if (
      row.map((elem) => elem.selected).every(Boolean) ||
      column.map((elem) => elem.selected).every(Boolean)
    ) {
      return board;
    } else {
      return null;
    }
  }
}

function getBingoWinner() {
  let result;
  const finishedBoardsIds = new Set();

  for (const num of numbers) {
    for (let i = 0; i < boards.length; i++) {
      if (finishedBoardsIds.size === boards.length) break;

      for (const row of boards[i]) {
        for (const item of row) {
          if (item.num === num) {
            item.selected = true;
          }
        }
      }

      const winningBoard = getBoardIfWon(boards[i]);

      if (winningBoard && !finishedBoardsIds.has(i)) {
        finishedBoardsIds.add(i);
        result = [
          winningBoard.map((row) => row.map((elem) => ({ ...elem }))),
          num,
        ];
      }
    }
  }

  return result;
}

const [winningBoard, winningNumber] = getBingoWinner();

console.log(
  winningBoard
    .flat()
    .filter((val) => !val.selected)
    .reduce((acc, elem) => acc + elem.num, 0) * winningNumber
);
