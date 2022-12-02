const { inputPairInsertionRules, polymerTemplate } = require("./inputData");
const { chunkArray, countElementOccurrences } = require("../utils");

const STEPS = 10;

function insertPairs(pairInsertionRules, polymer) {
  let resultPolymer = chunkArray(polymer);

  for (const chunk of resultPolymer) {
    const insertElement = pairInsertionRules[chunk[0] + chunk[1]];

    if (insertElement) {
      chunk.splice(1, 0, insertElement);
    }
  }

  return [
    ...resultPolymer.map(([elem1, elem2, ...rest]) => [elem1, elem2]).flat(),
    resultPolymer.slice(-1)[0].slice(-1)[0],
  ];
}

function insertMultiplePairs(pairInsertionRules, polymer, steps) {
  let step = 0;
  let resultPolymer = polymer.split("");

  while (step < steps) {
    resultPolymer = insertPairs(pairInsertionRules, resultPolymer);
    step++;
  }

  return resultPolymer.join("");
}

const transformedPolymer = insertMultiplePairs(
  inputPairInsertionRules,
  polymerTemplate,
  STEPS
);

const elementOccurrences = countElementOccurrences(
  transformedPolymer.split("")
);

const elementsSortedByOccurrences = Object.entries(elementOccurrences).sort(
  (a, b) => b[1] - a[1]
);

const [_mostCommonElement, mostCommonElementOccurrences] =
  elementsSortedByOccurrences[0];

const [_leastCommonElement, leastCommonElementOccurrences] =
  elementsSortedByOccurrences[elementsSortedByOccurrences.length - 1];

console.log(mostCommonElementOccurrences - leastCommonElementOccurrences);
