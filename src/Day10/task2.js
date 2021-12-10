const { lines } = require("./inputData");

const characterPairs = {
  "(": ")",
  "[": "]",
  "{": "}",
  "<": ">",
};

const openingCharacters = Object.keys(characterPairs);
const closingCharacters = Object.values(characterPairs);

const repairingClosingCharacterPoints = {
  ")": 1,
  "]": 2,
  "}": 3,
  ">": 4,
};

function repairIncompleteLines(linesOfCode) {
  return linesOfCode
    .map((line) => {
      const currentOpeningCharactersStack = [];

      for (const char of line.split("")) {
        if (openingCharacters.includes(char)) {
          currentOpeningCharactersStack.push(char);
        } else if (closingCharacters.includes(char)) {
          const lastCharOnStack = currentOpeningCharactersStack.pop();

          if (char !== characterPairs[lastCharOnStack]) {
            return;
          }
        }
      }

      return currentOpeningCharactersStack
        .reverse()
        .map((char) => characterPairs[char]);
    })
    .filter(Boolean);
}

const repairedLines = repairIncompleteLines(lines);
const repairedLineScores = repairedLines
  .map((line) =>
    line
      .map((char) => repairingClosingCharacterPoints[char])
      .reduce((acc, val) => acc * 5 + val, 0)
  )
  .sort((a, b) => a - b);
console.log(repairedLineScores[Math.floor(repairedLineScores.length / 2)]);
