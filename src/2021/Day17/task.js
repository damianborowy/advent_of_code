const { xInputRange, yInputRange } = require("./inputData");

function findValidVelocities(xRange, yRange) {
    const [minTargetX, maxTargetX] = xRange;
    const [minTargetY, maxTargetY] = yRange;
    const solutions = new Set();

    for (let yVelocity = 0; yVelocity < Math.abs(maxTargetY); yVelocity++) {
        for (let xVelocity = 1; xVelocity <= maxTargetX; xVelocity++) {
            let currentX = 0;
            let currentY = 0;
            let currentVelocityX = xVelocity;
            let currentVelocityY = yVelocity;
            let maxHeight = 0;

            while (currentVelocityX > 0 && currentX < minTargetX || currentY > maxTargetY) {
                currentX += currentVelocityX;
                currentY += currentVelocityY;

                currentVelocityX -= 1;
                currentVelocityY -= 1;

                maxHeight = Math.max(maxHeight, currentY);
            }

            if (currentX <= maxTargetX && currentX >= minTargetX && currentY <= maxTargetY && currentY >= minTargetY) {
                solutions.add(`${xVelocity},${yVelocity}`)
            }
        }
    }

    return solutions;
}


// task 1 - find highest vertical position for a throw that eventually hits the given range

console.log("\n----- TASK 1 -----\n");
console.log((Math.abs(yInputRange[0]) - 1) * (Math.abs(yInputRange[0])) / 2);

// task 2 - find all possible velocities count

console.log("\n----- TASK 2 -----\n");
const validVelocities = findValidVelocities(xInputRange, yInputRange);
console.log(validVelocities, validVelocities.size);
