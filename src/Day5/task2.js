const { lines } = require("./inputData");

function makeArrayOfVents() {
  const vents = [...new Array(1000)].map(() => new Array(1000).fill(0));

  lines.forEach(({ from, to }) => {
    if (from.y === to.y) {
      const [lowerCoordinate, higherCoordinate] = [from.x, to.x].sort(
        (a, b) => a - b
      );

      for (let i = lowerCoordinate; i <= higherCoordinate; i++) {
        vents[from.y][i] += 1;
      }
    } else if (from.x === to.x) {
      const [lowerCoordinate, higherCoordinate] = [from.y, to.y].sort(
        (a, b) => a - b
      );

      for (let i = lowerCoordinate; i <= higherCoordinate; i++) {
        vents[i][from.x] += 1;
      }
    } else {
      for (let i = 0; i <= Math.abs(from.x - to.x); i++) {
        const x = from.x > to.x ? from.x - i : from.x + i;
        const y = from.y > to.y ? from.y - i : from.y + i;
        vents[y][x]++;
      }
    }
  });

  return vents;
}

const ventsArray = makeArrayOfVents();
const numberOfPositionsWithAtLeastTwoVents = ventsArray
  .flat()
  .filter((val) => val >= 2).length;

console.log(numberOfPositionsWithAtLeastTwoVents);
