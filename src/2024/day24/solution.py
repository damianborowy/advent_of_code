import operator
import re

OPS = {"AND": operator.and_, "OR": operator.or_, "XOR": operator.xor}

with open("smallInput.txt", "r") as file:
    data = file.read()

wires, gates = data.split("\n\n")
wires = {n: int(v) for n, v in re.findall(r"(\w\d{2}): ([01])", wires)}
gates = {n3: (n1, n2, op) for n1, op, n2, n3 in re.findall(r"(\w{3}) (AND|OR|XOR) (\w{3}) -> (\w{3})", gates)}


def part_one():
    def dfs(gate):
        n1, n2, operation = gate
        for wire in (n1, n2):
            if wire not in wires:
                wires[wire] = dfs(gates[wire])
        return OPS[operation](wires[n1], wires[n2])

    z_gates = [str(dfs(gates[gate])) for gate in sorted(gates.keys(), reverse=True) if gate.startswith("z")]

    return int("".join(z_gates), 2)


def is_valid_gate(name, gate):
    n1, n2, operation = gate

    return (
        (name.startswith("z") and operation != "XOR" and name != "z45") or
        (operation == "XOR" and all(not node.startswith(("x", "y", "z")) for node in (n1, n2, name))) or
        (
            operation == "AND"
            and "x00" not in (n1, n2)
            and any(
                name in (other_n1, other_n2) and other_operation != "OR"
                for (other_n1, other_n2, other_operation) in gates.values()
            )
        ) or
        (
            operation == "XOR"
            and any(
                name in (other_n1, other_n2) and other_operation == "OR"
                for (other_n1, other_n2, other_operation) in gates.values()
            )
        )
    )


def part_two():
    return ",".join(sorted(name for name, gate in gates.items() if is_valid_gate(name, gate)))


print(f"Part 1: {part_one()}")
print(f"Part 2: {part_two()}")
