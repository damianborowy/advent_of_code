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
      smallerCount + 1;
    } else {
      switch (direction) {
      | "top" =>
        row <= (-1) ?
          Pervasives.max(smallerCount, 1) :
          helper(row - 1, col, smallerCount + 1)
      | "bottom" =>
        row >= gridHeight ?
          Pervasives.max(smallerCount, 1) :
          helper(row + 1, col, smallerCount + 1)
      | "left" =>
        col <= (-1) ?
          Pervasives.max(smallerCount, 1) :
          helper(row, col - 1, smallerCount + 1)
      | "right" =>
        col >= gridLength ?
          Pervasives.max(smallerCount, 1) :
          helper(row, col + 1, smallerCount + 1)
      };
    };

  if (sourceRow === 0
      && direction === "top"
      || sourceRow === gridLength
      && direction === "bottom"
      || sourceCol === 0
      && direction === "left"
      || sourceCol === gridLength
      && direction === "right") {
    0;
  } else {
    switch (direction) {
    | "top" => helper(sourceRow - 1, sourceCol, 0)
    | "bottom" => helper(sourceRow + 1, sourceCol, 0)
    | "left" => helper(sourceRow, sourceCol - 1, 0)
    | "right" => helper(sourceRow, sourceCol + 1, 0)
    };
  };
};

let visible_trees_count = ref(0);
let highest_fov = ref(0);
for (row in 0 to gridHeight) {
  for (col in 0 to gridLength) {
    let states =
      directions
      |> Array.map(direction =>
           isVisible(direction, grid[row][col], row, col)
         );

    Js.log([|row, col|]);
    Js.log(states);

    let isVisible =
      states |> Array.to_list |> List.exists(element => element >= 0);

    let product = ref(1);

    for (i in 0 to 3) {
      product := product^ * states[i];
    };

    if (product^ > highest_fov^) {
      highest_fov := product^;
    };

    if (isVisible) {
      visible_trees_count := visible_trees_count^ + 1;
    };
  };
};

Js.log(highest_fov);
Js.log(visible_trees_count);
