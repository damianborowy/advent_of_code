import fs from "fs";
import path from "path";

const [rawRules, rawUpdates] = fs
  .readFileSync(path.join(__dirname, "smallInput.txt"), "utf-8")
  .split("\n\n")
  .map((data) => data.split("\n"));

const updates = rawUpdates.map((updates) => updates.split(",").map(Number));

type Rules = Record<number, Set<number>>;

const rules: Rules = {};
rawRules
  .map((update) => update.split("|").map(Number))
  .forEach(([firstPage, secondPage]) => {
    rules[firstPage] ??= new Set();

    rules[firstPage].add(secondPage);
  });

const isProperlyOrdered = (pages: number[]): boolean => {
  for (let pageIndex = 0; pageIndex < pages.length; pageIndex++) {
    const page = pages[pageIndex];
    const previousPages = pages.slice(0, pageIndex);
    const pageRules = rules[page] ?? new Set();

    for (const previousPage of previousPages) {
      const isPreviousPageIncorrect = pageRules.has(previousPage);

      if (isPreviousPageIncorrect) return false;
    }
  }

  return true;
};

const sumMiddlePages = (allPages: number[][]) =>
  allPages.reduce((sum, pages) => sum + pages[Math.floor(pages.length / 2)], 0);

const findFirstOrderedSublist = (): number[] => {

}

const fixPagesOrder = (pages: number[]): number[] => {
  let orderedPages = [pages[0]];

  pages.slice(1).forEach((page) => {
    const newOrderedPages = findFirstOrderedSublist(orderedPages);

    orderedPages = newOrderedPages;
  })

  pages.slice(1).reduce((orderedPages, page) => {
    for ()
  }, [pages[0]])
};

const validUpdates = updates.filter(isProperlyOrdered);
const unorderedUpdates = updates.filter((pages) => !isProperlyOrdered(pages));

const sumOfValidUpdatesMiddlePages = sumMiddlePages(validUpdates);

console.log(`Day 5, part 1: ${sumOfValidUpdatesMiddlePages}`);

const sumOfUnorderedMiddlePages = sumMiddlePages(
  unorderedUpdates.map(fixPagesOrder)
);

console.log(`Day 5, part 2: ${sumOfUnorderedMiddlePages}`);
