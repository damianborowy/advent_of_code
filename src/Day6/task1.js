const { lanternfish } = require("./inputData");
const DAYS_COUNT = 80;

for (let day = 1; day <= DAYS_COUNT; day++) {
  const newFish = [];

  for (let fishIndex = 0; fishIndex < lanternfish.length; fishIndex++) {
    if (lanternfish[fishIndex] === 0) {
      lanternfish[fishIndex] = 6;
      newFish.push(8);
    } else {
      lanternfish[fishIndex] -= 1;
    }
  }

  lanternfish.push(...newFish);
}

console.log(lanternfish.length);
