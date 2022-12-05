const fs = require("fs");

const steps = fs
    .readFileSync(`${__dirname}/steps.txt`)
    .toString()
    .split("\n")
    .map((step) =>
        step.replaceAll(/\D+/g, " ").split(" ").map(Number).filter(Boolean)
    );

const stacks = fs
    .readFileSync(`${__dirname}/stacks.txt`)
    .toString()
    .split("\n");

const stacksCount = stacks.at(-1).split("").map(Number).filter(Boolean).at(-1);

const currentStacks = [...new Array(stacksCount)].map((_) => []);

for (let i = 0; i < stacks.length - 1; i++) {
    for (let j = 1; j < stacksCount * 4; j += 4) {
        const element = stacks[i][j];

        if (element && element !== " ") {
            const stackIndex = Math.floor(j / 4);

            currentStacks[stackIndex].push(element);
        }
    }
}

// PART 1

const part1Stacks = currentStacks.map((stack) => [...stack]);

steps.forEach(([count, from, to]) => {
    for (let i = 0; i < count; i++) {
        const shiftedCargo = part1Stacks[from - 1].shift();

        part1Stacks[to - 1].unshift(shiftedCargo);
    }
});

console.log(part1Stacks.map((stack) => stack[0]).join(""));

// PART 2

const part2Stacks = currentStacks.map((stack) => [...stack]);

steps.forEach(([count, from, to]) => {
    const shiftedCargo = part2Stacks[from - 1].splice(0, count);

    part2Stacks[to - 1].unshift(...shiftedCargo);
});

console.log(part2Stacks.map((stack) => stack[0]).join(""));
