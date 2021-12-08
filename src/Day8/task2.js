const { digits } = require("./inputData");

function intersection(firstString, secondString) {
  const firstStringSet = new Set(firstString.split(""));

  return secondString.split("").reduce((acc, char) => {
    if (firstStringSet.has(char)) return acc + 1;
    else return acc;
  }, 0);
}

const decodedOutputs = digits.map(({ combinations, output }) => {
  const one = combinations.find((digit) => digit.length === 2) ?? "";
  const four = combinations.find((digit) => digit.length === 4) ?? "";

  return output
    .map((digit) => {
      switch (digit.length) {
        case 2:
          return "1";
        case 3:
          return "7";
        case 4:
          return "4";
        case 7:
          return "8";
        case 5:
          if (intersection(digit, one) === 2) return 3;
          else if (intersection(digit, four) === 2) return 2;
          else return 5;
        case 6:
          if (intersection(digit, four) === 4) return 9;
          else if (intersection(digit, one) === 2) return 0;
          else return 6;
      }
    })
    .join("");
});

const decodedDigitsSum = decodedOutputs
  .map(Number)
  .reduce((acc, val) => acc + val, 0);

console.log(decodedDigitsSum);
