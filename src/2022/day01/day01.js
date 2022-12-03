const fs = require('fs');

const input = fs.readFileSync(`${__dirname}/input.txt`).toString();

const parsedData = input.split("\n");

const elves = [];
let temp = [];
for (const elfCal of parsedData) {
    if (elfCal === "") {
        elves.push(temp);
        temp = [];
        continue;
    }

    temp.push(+elfCal);
}

const sums = elves.map((calsArr) => calsArr.reduce((acc, val) => acc + val, 0));

// task 1 - highest sum
console.log(Math.max(...sums));

// task 2 - highest 3 sums
console.log(
    [...sums]
        .sort((a, b) => b - a)
        .slice(0, 3)
        .reduce((acc, val) => acc + val, 0)
);
