const { lines } = require("./inputData");

const characterPairs = {
  "(": ")",
  "[": "]",
  "{": "}",
  "<": ">",
};

const openingCharacters = Object.keys(characterPairs);
const closingCharacters = Object.values(characterPairs);

const illegalCharacterPoints = {
  ")": 3,
  "]": 57,
  "}": 1197,
  ">": 25137,
};

function findFirstIllegalCharacters(linesOfCode) {
  return linesOfCode
    .map((line) => {
      const currentOpeningCharactersStack = [];

      for (const char of line.split("")) {
        if (openingCharacters.includes(char)) {
          currentOpeningCharactersStack.push(char);
        } else if (closingCharacters.includes(char)) {
          const lastCharOnStack = currentOpeningCharactersStack.pop();

          if (char !== characterPairs[lastCharOnStack]) {
            return char;
          }
        }
      }
    })
    .filter(Boolean);
}

const illegalCharacters = findFirstIllegalCharacters(lines);
console.log(
  illegalCharacters
    .map((char) => illegalCharacterPoints[char] || 0)
    .reduce((acc, val) => acc + val, 0)
);
