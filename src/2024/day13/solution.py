import re
from sympy import symbols, Eq
from sympy.solvers import solve

with open('smallInput.txt', 'r') as file:
    instructions = [line for line in file]

def get_x_y_from_line(line):
    line_search = re.search('(X.\d+).*(Y.\d+)', line)

    x, y = [int(re.sub('\D', '', value)) for value in line_search.groups()]

    return x, y

def get_machines(prize_offset):
    machines = []
    for prize_index in range(0, len(instructions), 4):
        prize_x, prize_y = get_x_y_from_line(instructions[prize_index + 2])

        machines.append((
            get_x_y_from_line(instructions[prize_index]),
            get_x_y_from_line(instructions[prize_index + 1]),
            (prize_x + prize_offset, prize_y + prize_offset)
        ))

    return machines

def get_minimal_price(machines):
    minimal_winning_price = 0

    for a, b, prize in machines:
        a_x, a_y = a
        b_x, b_y = b
        prize_x, prize_y = prize
        x, y = symbols('x, y')

        solution = solve(
            [
                Eq(a_x * x + b_x * y, prize_x),
                Eq(a_y * x + b_y * y, prize_y)
            ],
            (x, y)
        )

        a_count, b_count = list(solution.values())

        if a_count.is_Integer and b_count.is_Integer:
            minimal_winning_price += int(a_count) * 3 + int(b_count)

    return minimal_winning_price

part1_price = get_minimal_price(get_machines(0))
part2_price = get_minimal_price(get_machines(10000000000000))

print(f"Day 13, part 1: {part1_price}")
print(f"Day 13, part 2: {part2_price}")