var inputGrid: [[Int]] = []

let START_AS_ASCII = Int(Character("S").asciiValue!)
let END_AS_ASCII = Int(Character("E").asciiValue!)
let MINIMUM_HEIGHT = Int(Character("a").asciiValue!)
let MAXIMUM_HEIGHT = Int(Character("z").asciiValue!)
let DIRECTIONS = [(1, 0), (0, 1), (-1, 0), (0, -1)]

let inputString = """
  Sabqponm
  abcryxxl
  accszExk
  acctuvwj
  abdefghi
  """

typealias Index = (Int, Int)

enum HikingDirection {
  case up
  case down
}

for line in inputString.split(whereSeparator: \.isNewline) {
  inputGrid.append(Array(line).map { Int($0.asciiValue!) })
}

func getElementIndex(element: Int) -> Index {
  for i in 0...inputGrid.count - 1 {
    for j in 0...inputGrid[i].count - 1 {
      if inputGrid[i][j] == element {
        return (i, j)
      }
    }
  }

  return (-1, -1)
}

func getPossibleDirections(
  index: Index, currentHeight: Int, visitedNodes: [Index], direction: HikingDirection
) -> [Index] {
  let possibleDirections = DIRECTIONS.map({ (index.0 + $0.0, index.1 + $0.1) })
    .filter({ $0.0 >= 0 && $0.0 < inputGrid.count && $0.1 >= 0 && $0.1 < inputGrid[0].count })
    .filter({ direction in !visitedNodes.contains(where: { $0 == direction }) })
    
  switch direction {
  case .up:
    return possibleDirections.filter({
      [0, 1].contains(inputGrid[$0.0][$0.1] - currentHeight) ||
      (inputGrid[$0.0][$0.1] == END_AS_ASCII && MAXIMUM_HEIGHT - currentHeight <= 1)
    })
  case .down:
    return possibleDirections.filter({ [0, 1].contains(currentHeight - inputGrid[$0.0][$0.1]) })
  }
}

let startPosition = getElementIndex(element: START_AS_ASCII)
let endPosition = getElementIndex(element: END_AS_ASCII)

func traversePaths(direction: HikingDirection) -> Int {
  let initialPosition = direction == .up ? startPosition : endPosition
  let targetPosition = direction == .up ? endPosition : startPosition
  let initialHeight = direction == .up ? MINIMUM_HEIGHT : MAXIMUM_HEIGHT
    
  var queue = [(initialPosition, initialHeight, 0)]
  var visitedNodes = [initialPosition]
    
  while queue.count != 0 {
    let (index, currentHeight, pathLength) = queue.removeFirst()
    let isHikingUpFinished = direction == .up && index == targetPosition
    let isHikingDownFinished = direction == .down && inputGrid[index.0][index.1] == MINIMUM_HEIGHT
      
    if isHikingUpFinished || isHikingDownFinished {
      return pathLength
    }
      
    let possibleDirections = getPossibleDirections(
      index: index,
      currentHeight: currentHeight,
      visitedNodes: visitedNodes,
      direction: direction
    )

    for nextIndex in possibleDirections {
      queue.append((nextIndex, inputGrid[nextIndex.0][nextIndex.1], pathLength + 1))
      visitedNodes.append(nextIndex)
    }
  }

  return -1
}

print("Part 1: ", traversePaths(direction: .up))
print("Part 2: ", traversePaths(direction: .down))
