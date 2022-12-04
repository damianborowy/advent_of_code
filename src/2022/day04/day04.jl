import Pkg;

Pkg.add("Pipe")
Pkg.add("MLStyle")

using MLStyle
using Pipe: @pipe

inputfile = open(joinpath(@__DIR__, "input.txt"))
input = read(inputfile, String)

parseintvector(vector) = [parse(Int64, element) for element in vector]

assignments = @pipe input |> 
  split(_, "\n") |>
  map(line -> split(line, ","), _) |>
  map(pairs -> [split(assignment, "-") for assignment in pairs], _)

# PART 1

function checkifpairsfullyoverlap(pairs)
  start1, end1 = parseintvector(pairs[1])
  start2, end2 = parseintvector(pairs[2])

  (start1 <= start2 && end1 >= end2) || (start2 <= start1 && end2 >= end1)
end

@pipe assignments |> 
  filter(checkifpairsfullyoverlap, _) |>
  length |>
  print

# PART 2

function checkifpairsoverlap(pairs)
  start1, end1 = parseintvector(pairs[1])
  start2, end2 = parseintvector(pairs[2])

  commontasks = intersect(start1:end1, start2:end2)
  
  length(commontasks) > 0
end

@pipe assignments |> 
  filter(checkifpairsoverlap, _) |>
  length |>
  print