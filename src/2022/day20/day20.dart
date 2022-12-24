var inputString = """""";

class Number {
  int originalIndex;
  int value;

  Number(this.originalIndex, this.value);
}

List<int> range(int from, int to) {
  return [for (var i = from; i < to; i += 1) i];
}

List<Number> parseInput(int decryptionKey) {
  return inputString
    .split("\n")
    .map(int.parse)
    .toList()
    .asMap()
    .entries
    .map((entry) => Number(entry.key, entry.value * decryptionKey))
    .toList();
}

void decrypt(List<Number> input) {
  input.asMap().entries.forEach((entry) {
    var index = input.indexWhere((number) => number.originalIndex == entry.key);
    var toBeMoved = input[index];

    input.removeAt(index);
    input.insert((index + toBeMoved.value) % input.length, toBeMoved);
  });
}

int groveCoordinates(List<Number> input) {
  var zero = input.indexWhere((number) => number.value == 0);

  return [1000, 2000, 3000]
    .map((number) => input[(zero + number) % input.length].value)
    .fold(0, (acc, val) => acc + val);
}

int solve(int key, int rounds) {
  var input = parseInput(key);

  range(0, rounds).forEach((round) => decrypt(input));

  return groveCoordinates(input);
}

void main() {
  var part1 = solve(1, 1);
  print("Part 1: ${part1}");

  var part2 = solve(811589153, 10);
  print("Part 2: ${part2}");
}
