from functools import cache

with open('smallInput.txt', 'r') as file:
    patterns, _, *designs = [line.strip() for line in file]
    patterns = [pattern.strip() for pattern in patterns.split(",")]

@cache
def check_design(design: str):
    if design == "":
        return 1

    return sum(
        check_design(design[len(pattern):])
        for pattern in patterns
        if design.startswith(pattern)
    )

designs_combinations = [check_design(design) for design in designs]

possible_designs_count = len([_ for combinations in designs_combinations if combinations > 0])
print(f"Day 19, part 1: {possible_designs_count}")

all_designs_count = sum(combinations for combinations in designs_combinations if combinations > 0)
print(f"Day 19, part 2: {all_designs_count}")