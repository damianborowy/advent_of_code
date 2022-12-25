const inputString = ``;

const snafuToDecimal = {
  "=": -2,
  "-": -1,
  "0": 0,
  "1": 1,
  "2": 2
}

const decimalToSnafu = Object.fromEntries(
  Object.entries(snafuToDecimal).map(([key, value]) => [value, key])
);

let decimalSum = inputString
  .split("\n")
  .map((row) =>
    row
      .trim()
      .split("")
      .map(snafu => snafuToDecimal[snafu])
      .reduce((acc, val, index, self) => acc + (val * 5 ** (self.length - index - 1)), 0)
  )
  .reduce((acc, val) => acc + val, 0);

const snafuSum: string[] = [];
while (decimalSum > 0) {
  const remainder = decimalSum % 5;

  if (remainder > 2) {
    decimalSum += remainder;
    snafuSum.push(decimalToSnafu[remainder - 5]);
  } else {
    snafuSum.push(remainder.toString());
  }

  decimalSum = Math.floor(decimalSum / 5);
}

console.log(snafuSum.reverse().join(""));
