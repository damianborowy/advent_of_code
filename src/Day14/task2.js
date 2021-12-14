const { inputPairInsertionRules, polymerTemplate } = require("./inputData");
const { chunkArray, countElementOccurrences } = require("../utils");

const STEPS = 40;

function insertPairs(pairInsertionRules, pairOccurrences) {
  return Object.entries(pairOccurrences).reduce(
    (acc, [elementsPair, occurrences]) => {
      const resultElement = pairInsertionRules[elementsPair];

      acc[elementsPair[0] + resultElement] =
        (acc[elementsPair[0] + resultElement] || 0) + occurrences;
      acc[resultElement + elementsPair[1]] =
        (acc[resultElement + elementsPair[1]] || 0) + occurrences;

      return acc;
    },
    {}
  );
}

function countPairOccurrences(polymer) {
  const polymerPairs = chunkArray(polymer).map((chunk) => chunk.join(""));
  return countElementOccurrences(polymerPairs);
}

function insertMultiplePairs(pairInsertionRules, polymer, steps) {
  let step = 0;
  let resultPairOccurrences = countPairOccurrences(polymer);

  while (step < steps) {
    resultPairOccurrences = insertPairs(
      pairInsertionRules,
      resultPairOccurrences
    );
    step++;
  }

  return resultPairOccurrences;
}

function countOccurrencesInPairs(pairOccurrences) {
  return Object.entries(pairOccurrences).reduce((acc, [pair, occurrences]) => {
    acc[pair[0]] = (acc[pair[0]] || 0) + occurrences;
    acc[pair[1]] = (acc[pair[1]] || 0) + occurrences;

    return acc;
  }, {});
}

const transformedPolymerOccurrences = insertMultiplePairs(
  inputPairInsertionRules,
  polymerTemplate,
  STEPS
);

const elementOccurrences = countOccurrencesInPairs(
  transformedPolymerOccurrences
);

const elementsSortedByOccurrences = Object.entries(elementOccurrences)
  .map(([element, occurrences]) => [element, Math.ceil(occurrences / 2)])
  .sort((a, b) => b[1] - a[1]);

const [_mostCommonElement, mostCommonElementOccurrences] =
  elementsSortedByOccurrences[0];
  
const [_leastCommonElement, leastCommonElementOccurrences] =
  elementsSortedByOccurrences[elementsSortedByOccurrences.length - 1];

console.log(mostCommonElementOccurrences - leastCommonElementOccurrences);
