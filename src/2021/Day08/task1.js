const { digits } = require("./inputData");

const targetDigitsLengths = new Set([2, 3, 4, 7]);

const selectedDigitsOccurrences = digits
  .flatMap((digitsRow) => digitsRow.output)
  .reduce((acc, digit) => {
    if (targetDigitsLengths.has(digit.length)) return acc + 1;
    else return acc;
  }, 0);

console.log(selectedDigitsOccurrences);
