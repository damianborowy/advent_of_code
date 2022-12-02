-- https://elm-lang.org/examples/hello

import Html exposing (text)

input =
  """
  2348
  3248
  2358

  3249
  2348
  2347
  """

val =
  String.split "\n" input

main =
  text (String.concat val)