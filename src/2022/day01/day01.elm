elfSeparatorRegex =
  Maybe.withDefault Regex.never <|
    Regex.fromString "\\s{2,}"

parseInt intString =
    intString
    |> String.toInt
    |> Maybe.withDefault 0

sumCalories elfCalories =
    elfCalories
    |> String.split "\n"
    |> List.map parseInt
    |> List.foldl (+) 0

caloriesPerElf =
    input
    |> Regex.split elfSeparatorRegex
    |> List.map sumCalories

-- PART 1

highestValue =
    List.maximum caloriesPerElf
    |> Maybe.withDefault 0
    |> String.fromInt

-- PART 2

flippedComparison a b =
    case compare a b of
      LT -> GT
      EQ -> EQ
      GT -> LT

sumOfTopValues = 
    caloriesPerElf
    |> List.sortWith flippedComparison
    |> List.take 3
    |> List.foldl (+) 0
    |> String.fromInt

-- DISPLAY RESULTS

main =
    text ("Part 1: " ++ highestValue ++ ", Part 2: " ++ sumOfTopValues)