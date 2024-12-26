import networkx as nx

with open('smallInput.txt', 'r') as file:
    connection_pairs = [line.strip().split("-") for line in file]

connections = nx.MultiGraph(connection_pairs)

three_pc_cycles = [
    cycle for cycle
    in nx.chordless_cycles(connections, length_bound=3)
    if len(cycle) == 3 and any([pc.startswith("t") for pc in cycle])
]

print(f"Day 23, part 1: {len(three_pc_cycles)}")

cliques = list(nx.find_cliques(connections))
longest_clique = max(cliques, key=len)
password = ",".join(sorted(longest_clique))

print(f"Day 23, part 2: {password}")
