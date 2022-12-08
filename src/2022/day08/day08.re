let input_grid = {|
30373
25512
65332
33549
35390
|};

let grid =
  Js.String2.split(input_grid, "\n")
  |> Js.Array.filter(row => row !== "")
  |> Js.Array.map(row =>
       Js.String2.split(row, "")
       |> Js.Array.map(char => Belt.Int.fromString(char))
     );

let gridHeight = Js.Array.length(grid) - 1;
let gridLength = Js.Array.length(grid[0]) - 1;

let directions = [|"top", "bottom", "left", "right"|];

let isVisible = (direction, sourceHeight, sourceRow, sourceCol) => {
  let rec helper = (row, col, smallerCount) =>
    if (row >= 0
        && col >= 0
        && row <= gridHeight
        && col <= gridLength
        && grid[row][col] >= sourceHeight) {
      -1
    } else {
      switch (direction) {
      | "top" =>
        row <= (-1) ? smallerCount : helper(row - 1, col, smallerCount + 1)
      | "bottom" =>
        row >= gridHeight ?
          smallerCount : helper(row + 1, col, smallerCount + 1)
      | "left" =>
        col <= (-1) ? smallerCount : helper(row, col - 1, smallerCount + 1)
      | "right" =>
        col >= gridLength ?
          smallerCount : helper(row, col + 1, smallerCount + 1)
      };
    };

    switch (direction) {
    | "top" => helper(sourceRow - 1, sourceCol, 0)
    | "bottom" => helper(sourceRow + 1, sourceCol, 0)
    | "left" => helper(sourceRow, sourceCol - 1, 0)
    | "right" => helper(sourceRow, sourceCol + 1, 0)
    };
  
};

let visible_trees_count = ref(0);
for (row in 0 to gridHeight) {
  for (col in 0 to gridLength) {
    let states =
      directions
      |> Array.map(direction =>
           isVisible(direction, grid[row][col], row, col)
         );

    Js.log([|row, col|])
    Js.log(states);
    
    let isVisible =
      states |> Array.to_list |> List.exists(element => element >= 0);

    if (isVisible) {
      visible_trees_count := visible_trees_count^ + 1;
    };
  };
};

Js.log(visible_trees_count);
