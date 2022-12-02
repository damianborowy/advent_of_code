const { inputPassages } = require("./inputData");
const { isUpperCase } = require("../utils");

function preparePassages(passages, specialPassageName) {
  return Object.fromEntries(
    Object.entries(passages).map(([cave, possibleCaves]) => [
      cave,
      possibleCaves.map((possibleCaveName) => ({
        name: possibleCaveName,
        possibleVisits: possibleCaveName === specialPassageName ? 2 : 1,
      })),
    ])
  );
}

function removeCaveFromPossiblePassages(passages, removedCave) {
  if (isUpperCase(removedCave.name)) {
    return passages;
  } else {
    if (removedCave.possibleVisits === 1) {
      return Object.fromEntries(
        Object.entries(passages).map(([cave, possibleCaves]) => [
          cave,
          possibleCaves.filter(
            (possibleCave) => possibleCave.name !== removedCave.name
          ),
        ])
      );
    } else {
      return Object.fromEntries(
        Object.entries(passages).map(([cave, possibleCaves]) => [
          cave,
          possibleCaves.map((possibleCave) => {
            if (possibleCave.name === removedCave.name)
              return { name: possibleCave.name, possibleVisits: 1 };
            else return possibleCave;
          }),
        ])
      );
    }
  }
}

function findAllPossiblePaths(passages) {
  const allPaths = new Set();

  function visitCave(possiblePassages, cave, currentPath) {
    if (possiblePassages[cave.name].length === 0) return;
    if (cave.name === "end") {
      allPaths.add([...currentPath, "end"].join(","));
      return;
    }

    const filteredPassages = removeCaveFromPossiblePassages(
      possiblePassages,
      cave
    );

    filteredPassages[cave.name].forEach((passage) => {
      visitCave(filteredPassages, passage, [...currentPath, cave.name]);
    });
  }

  Object.keys(passages)
    .filter(
      (passage) => !["start", "end"].includes(passage) && !isUpperCase(passage)
    )
    .forEach((passage) => {
      const preparedPassages = preparePassages(passages, passage);

      visitCave(preparedPassages, { name: "start" }, []);
    });

  return allPaths;
}

const allPossiblePaths = findAllPossiblePaths(inputPassages);
console.log(allPossiblePaths.size);
