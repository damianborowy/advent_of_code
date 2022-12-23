import re

input_regex = re.compile(
    "Blueprint (\d+): Each ore robot costs (\d+) ore. Each clay robot costs (\d+) ore. Each obsidian robot costs (\d+) ore and (\d+) clay. Each geode robot costs (\d+) ore and (\d+) obsidian."
)

input_string = """"""

input = []
for line in input_string.split("\n"):
    ore_strings = input_regex.findall(line)[0]
    _, ore, clay, obs_ore, obs_clay, geode_ore, geode_ops = [
        int(ore) for ore in ore_strings
    ]

    input.append(
        [
            {"type": "ore", "cost": [{"resource": "ore", "amount": ore}]},
            {"type": "clay", "cost": [{"resource": "ore", "amount": clay}]},
            {
                "type": "obsidian",
                "cost": [
                    {"resource": "ore", "amount": obs_ore},
                    {"resource": "clay", "amount": obs_clay},
                ],
            },
            {
                "type": "geode",
                "cost": [
                    {"resource": "ore", "amount": geode_ore},
                    {"resource": "obsidian", "amount": geode_ops},
                ],
            },
        ]
    )


def get_first_or_none(array):
    return next(iter(array), None)


def can_afford(costs, ore, clay, obsidian):
    ore_cost = get_first_or_none(
        [cost for cost in costs if cost.get("resource") == "ore"]
    )
    clay_cost = get_first_or_none(
        [cost for cost in costs if cost.get("resource") == "clay"]
    )
    obsidian_cost = get_first_or_none(
        [cost for cost in costs if cost.get("resource") == "obsidian"]
    )

    return (
        (ore_cost is None or ore_cost.get("amount") <= ore)
        and (clay_cost is None or clay_cost.get("amount") <= clay)
        and (obsidian_cost is None or obsidian_cost.get("amount") <= obsidian)
    )


def craft_robot(robot_cost, ore_prod, clay_prod, obs_prod, new_time_left):
    ore, ore_per_second = ore_prod
    clay, clay_per_second = clay_prod
    obsidian, obsidian_per_second = obs_prod

    while not can_afford(robot_cost, ore, clay, obsidian) and new_time_left > 0:
        ore += ore_per_second
        clay += clay_per_second
        obsidian += obsidian_per_second
        new_time_left -= 1

    ore += ore_per_second
    clay += clay_per_second
    obsidian += obsidian_per_second
    new_time_left -= 1

    for cost in robot_cost:
        resource = cost.get("resource")
        amount = cost.get("amount")

        if resource == "ore":
            ore -= amount
        if resource == "clay":
            clay -= amount
        if resource == "obsidian":
            obsidian -= amount

    return (ore, clay, obsidian, new_time_left)


robot_max_times_per_part = {
    "part1": {"ore": 16, "clay": 6, "obsidian": 3, "geode": 2},
    "part2": {"ore": 24, "clay": 12, "obsidian": 6, "geode": 2},
}

ores = ["ore", "clay", "obsidian", "geode"]


def next_optimal_robot(
    ore_prod, clay_prod, obs_prod, geode_prod, time_left, blueprint, part
):
    geode_produced = 0

    for robot in blueprint:
        max_times = robot_max_times_per_part.get(part)
        robot_type = robot.get("type")

        if any(robot_type == ore and time_left < max_times.get(ore) for ore in ores):
            continue

        ore, clay, obsidian, new_time_left = craft_robot(
            robot.get("cost"), ore_prod, clay_prod, obs_prod, time_left
        )

        if new_time_left <= 0:
            continue

        _, new_ore_per_second = ore_prod
        _, new_clay_per_second = clay_prod
        _, new_obsidian_per_second = obs_prod
        new_geode, new_geode_per_second = geode_prod

        if robot_type == "ore":
            new_ore_per_second += 1

        if robot_type == "clay":
            new_clay_per_second += 1

        if robot_type == "obsidian":
            new_obsidian_per_second += 1

        if robot_type == "geode":
            new_geode_per_second += 1

        score = new_time_left if robot_type == "geode" else 0

        score += next_optimal_robot(
            (ore, new_ore_per_second),
            (clay, new_clay_per_second),
            (obsidian, new_obsidian_per_second),
            (new_geode, new_geode_per_second),
            new_time_left,
            blueprint,
            part,
        )

        if score > geode_produced:
            geode_produced = score

    return geode_produced


input_productions = [(0, 1), (0, 0), (0, 0), (0, 0)]

part1 = 0
for i, blueprint in enumerate(input):
    score = next_optimal_robot(*input_productions, 24, blueprint, "part1")
    part1 += score * (i + 1)
print(f"Part 1: {part1}")

part2 = 1
for blueprint in input[:3]:
    score = next_optimal_robot(*input_productions, 32, blueprint, "part2")
    part2 *= score
print(f"Part 2: {part2}")
