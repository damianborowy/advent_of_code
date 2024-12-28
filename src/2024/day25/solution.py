KEY_WIDTH = 5
KEY_HEIGHT = 7

with open('smallInput.txt', 'r') as file:
    lines = [line.strip() for line in file]
    schematics = [rows.split("\n") for rows in "\n".join(lines).split("\n\n")]

locks = []
keys = []
for schematic in schematics:
    heights = []

    for col_index in range(KEY_WIDTH):
        height = -1

        for row_index in range(KEY_HEIGHT):
            if schematic[row_index][col_index] == "#":
                height += 1

        heights.append(height)

    if schematic[0] == "#" * KEY_WIDTH:
        locks.append(heights)
    else:
        keys.append(heights)

unique_pairs = 0
for lock in locks:
    for key in keys:
        fit = [lock[index] + key[index] <= KEY_HEIGHT - 2 for index in range(KEY_WIDTH)]

        if all(fit):
            unique_pairs += 1

print(f"Day 25, part 1: {unique_pairs}")

