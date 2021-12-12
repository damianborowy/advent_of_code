const { inputPassages } = require("./inputData");
const { isUpperCase } = require("../utils");

function removeCaveFromPossiblePassages(passages, removedCave) {
  if (isUpperCase(removedCave)) {
    return passages;
  } else {
    return Object.fromEntries(
      Object.entries(passages).map(([cave, possibleCaves]) => [
        cave,
        possibleCaves.filter((possibleCave) => possibleCave !== removedCave),
      ])
    );
  }
}

function findAllPossiblePaths(passages) {
  const allPaths = new Set();

  function visitCave(possiblePassages, cave, currentPath) {
    if (possiblePassages[cave].length === 0) return;
    if (cave === "end") {
      allPaths.add([...currentPath, "end"].join(","));
      return;
    }

    const filteredPassages = removeCaveFromPossiblePassages(
      possiblePassages,
      cave
    );

    filteredPassages[cave].forEach((passage) => {
      visitCave(filteredPassages, passage, [...currentPath, cave]);
    });
  }

  visitCave(passages, "start", []);

  return allPaths;
}

const allPossiblePaths = findAllPossiblePaths(inputPassages);
console.log(allPossiblePaths.size);
