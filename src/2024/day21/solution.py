import re
from functools import cache
from itertools import chain, pairwise
from typing import Tuple

with open('smallInput.txt', 'r') as file:
    codes = [line.strip() for line in file]

numerical_keypad = [
    ["7", "8", "9"],
    ["4", "5", "6"],
    ["1", "2", "3"],
    ["X", "0", "A"]
]

directional_keypad = [
    ["X", "^", "A"],
    ["<", "v", ">"]
]


def find_symbol_position(symbol: str, keypad: list[list[str]]) -> Tuple[int, int]:
    for row_index, row in enumerate(keypad):
        for col_index, cell in enumerate(row):
            if cell == symbol:
                return row_index, col_index


def get_element_at_point(keypad: list[list[str]], position: Tuple[int, int]) -> str | None:
    y, x = position
    row = get_element_at_index(keypad, y, [])

    return get_element_at_index(row, x, None)


def get_element_at_index(iterable: list, index: int, default):
    return next(iter(iterable[index:index + 1]), default)


def is_valid_element(element: str | None) -> bool:
    return element is not None and element != "X"


def get_path_symbols(path: list[Tuple[Tuple[int, int], str]]) -> str:
    return "".join(symbol for _, symbol in path)


def find_paths(start_symbol: str, target_symbol: str, keypad: list[list[str]]) -> list[str]:
    start_position = find_symbol_position(start_symbol, keypad)
    end_position = find_symbol_position(target_symbol, keypad)
    paths = []

    queue = [(start_position, [], {start_position})]
    while len(queue) > 0:
        position, path, visited_points = queue.pop()
        y, x = position

        if position == end_position:
            paths.append(path)
            continue

        for dir_y, dir_x, dir_symbol in (1, 0, "v"), (0, 1, ">"), (-1, 0, "^"), (0, -1, "<"):
            new_position = (y + dir_y, x + dir_x)
            new_position_element = get_element_at_point(keypad, new_position)

            if not is_valid_element(new_position_element) or new_position in visited_points:
                continue

            new_visited_points = visited_points.union({new_position})
            new_path = path + [(new_position, dir_symbol)]
            queue.append((new_position, new_path, new_visited_points))

    shortest_path_len = min(len(path) for path in paths)

    return [get_path_symbols(path) for path in paths if len(path) == shortest_path_len]


@cache
def find_numerical_paths(start_symbol: str, target_symbol: str) -> list[str]:
    return find_paths(start_symbol, target_symbol, numerical_keypad)


@cache
def find_directional_paths(start_symbol: str, target_symbol: str) -> list[str]:
    return find_paths(start_symbol, target_symbol, directional_keypad)


def join_segments(segments: list[list[str]]) -> list[str]:
    first_variants, *rest_variants = segments

    paths = [variant + "A" for variant in first_variants]
    for segment_variants in rest_variants:
        segment_paths = []

        for path in paths:
            for variant in segment_variants:
                segment_paths.append(path + variant + "A")

        paths = segment_paths

    return paths


@cache
def calculate_directional_paths_length(sequence: str, depth: int) -> int:
    if depth == 0:
        return len(sequence)

    lengths_sum = 0
    for start, end in pairwise("A" + sequence):
        segments = find_directional_paths(start, end)
        min_path_len = min(calculate_directional_paths_length(segment + "A", depth - 1) for segment in segments)
        lengths_sum += min_path_len

    return lengths_sum


def get_instructions(directional_robots_count: int) -> int:
    code_paths = {}

    for code in codes:
        entry_path_segments = [find_numerical_paths(start, target) for start, target in pairwise("A" + code)]
        paths = join_segments(entry_path_segments)

        code_paths[code] = min(
            calculate_directional_paths_length(sequence, directional_robots_count)
            for sequence in paths
        )

    complexity = 0
    for code, path_len in code_paths.items():
        code_number = int(re.sub(r"\D", "", code))
        complexity += code_number * path_len

    return complexity


print(f"Day 21, part 1: {get_instructions(2)}")
print(f"Day 21, part 2: {get_instructions(25)}")
