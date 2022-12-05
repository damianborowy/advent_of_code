import Html exposing (text)
import Regex
import Array

stacksInput = 
  """
  [D]    
  [N] [C]    
  [Z] [M] [P]
   1   2   3 
  """

input =
  """
  move 1 from 2 to 1
  move 3 from 1 to 3
  move 2 from 2 to 1
  move 1 from 1 to 2
  """

digitRegex =
  Maybe.withDefault Regex.never <|
    Regex.fromString "\\d"

commasRegex =
  Maybe.withDefault Regex.never <|
    Regex.fromString ",+"

stepRegex =
  Maybe.withDefault Regex.never <|
    Regex.fromString "\\D"

parseInt intString =
  intString
  |> Maybe.withDefault ""
  |> String.toInt
  |> Maybe.withDefault 0

mapStepToRecord stepsArray =
  {
    count = parseInt (Array.get 1 stepsArray),
    from = parseInt (Array.get 2 stepsArray),
    to = parseInt (Array.get 3 stepsArray)
  }

steps =
  input 
  |> String.split "\n"
  |> List.map (Regex.replace stepRegex (\_ -> ","))
  |> List.filter (Regex.contains digitRegex)
  |> List.map (Regex.split commasRegex)
  |> List.map Array.fromList
  |> List.map mapStepToRecord

stacks =
  stacksInput
  |> String.split "\n"
  |> List.map (String.split "")
  |> List.map Array.fromList
  |> Array.fromList

main =
  text (String.join " - " stacks)