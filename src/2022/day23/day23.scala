case class Point2D(x: Int, y: Int) {
  def neighbors(): List[Point2D] = {
    List(
      Point2D(x - 1, y - 1),
      Point2D(x, y - 1),
      Point2D(x + 1, y - 1),
      Point2D(x - 1, y),
      Point2D(x + 1, y),
      Point2D(x - 1, y + 1),
      Point2D(x, y + 1),
      Point2D(x + 1, y + 1)
    )
  }

  def +(other: Point2D) = Point2D(x + other.x, y + other.y)
  def -(other: Point2D) = Point2D(x - other.x, y - other.y)
}

class Day23Solver(input: List[String]):
  private val startingPositions = parseInput(input)
  private val nextTurnOffsets = createOffsets()

  def solvePart1(): Int = {
    val locations = (0 until 10).foldLeft(startingPositions)((acc, round) => playRound(acc, round))
    val gridSize = ((locations.maxBy(_.x).x - locations.minBy(_.x).x + 1) * (locations.maxBy(_.y).y - locations.minBy(_.y).y + 1))

    gridSize - locations.size
  }

  def solvePart2(): Int = {    
    var thisTurn = startingPositions
    var previousTurn: Set[Point2D] = null
    var roundId = 0

    while
      previousTurn != thisTurn
    do
      previousTurn = thisTurn
      thisTurn = playRound(previousTurn, roundId)
      roundId += 1

    roundId
  }

  private def playRound(turn: Set[Point2D], roundNumber: Int): Set[Point2D] = {
    val nextPositions = turn.to(collection.mutable.Set)

    val movers = turn
      .filter { elf => elf.neighbors().toList.exists { turn.contains(_) } }
      .map { elf => 
        nextTurnOffsets.indices
          .map { direction => nextTurnOffsets((roundNumber + direction) % 4) }
          .map { offsets =>
            if (offsets.exists { offset => turn.contains(elf + offset) }) null
            else elf -> (elf + offsets.head)
          }.filter { _ != null }
          .headOption
          .getOrElse(null)
      }
      .filter {_ != null}
      .toMap

    val safeDestinations = movers.valuesIterator.toArray.groupBy(identity).mapValues(_.size).filter(_._2 == 1).keySet

    movers
      .filter { (_, target) => safeDestinations.contains(target) }
      .foreach { (source, target) => 
        nextPositions.remove(source)
        nextPositions.add(target)
      }

    nextPositions.toSet
  }

  private def createOffsets(): Array[Array[Point2D]] = 
    Array(
      Array(Point2D(0, -1), Point2D(-1, -1), Point2D(1, -1)),
      Array(Point2D(0, 1), Point2D(-1, 1), Point2D(1, 1)),
      Array(Point2D(-1, 0), Point2D(-1, -1), Point2D(-1, 1)),
      Array(Point2D(1, 0), Point2D(1, -1), Point2D(1, 1))
    )

  private def parseInput(input: List[String]): Set[Point2D] = 
    input.zipWithIndex.flatMap { case (row, y) =>
      row.zipWithIndex.map { case (char, x) =>
        if (char == '#') Point2D(x, y) else null
      }.filter(_ != null)
    }.toSet
end Day23Solver

@main def hello() = {
  val inputString = """"""

  val solver = Day23Solver(inputString.split("\n").toList)
  
  println(s"Part 1: ${solver.solvePart1()}")
  println(s"Part 2: ${solver.solvePart2()}")
}