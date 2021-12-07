const { lanternfish } = require("./inputData");

const DAYS_COUNT = 256;

const fishIntervals = lanternfish.reduce((acc, val) => {
  acc[val] = (acc[val] || 0) + 1;
  return acc;
}, {});

let day = 1;
while (day !== DAYS_COUNT + 1) {
  const fishesWithZeroInterval = fishIntervals[0];

  for (let i = 0; i < 8; i++) {
    fishIntervals[i] = fishIntervals[i + 1] || 0;
  }

  fishIntervals[6] += fishesWithZeroInterval;
  fishIntervals[8] = fishesWithZeroInterval;

  day++;
}

const result = Object.values(fishIntervals).reduce((acc, val) => acc + val, 0);
console.log(result);
