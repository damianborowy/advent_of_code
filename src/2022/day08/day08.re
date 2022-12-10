let grid =
  Js.String2.split(input_grid, "\n")
  |> Js.Array.filter(row => row !== "")
  |> Js.Array.map(row =>
       Js.String2.split(row, "") |> Js.Array.map(Belt.Int.fromString)
     );

let grid_height = Js.Array.length(grid) - 1;
let grid_length = Js.Array.length(grid[0]) - 1;
let directions = [|"top", "bottom", "left", "right"|];

let is_edge_tree = (row, col, direction) =>
  switch (direction) {
  | "top" => row === 0
  | "bottom" => row === grid_height
  | "left" => col === 0
  | "right" => col === grid_length
  };

let are_indices_in_range = (row, col) =>
  row >= 0 && col >= 0 && row <= grid_height && col <= grid_length;

let traverse_trees = (direction, sourceHeight, sourceRow, sourceCol, mode) => {
  let rec traverse = (row, col, smallerCount) =>
    if (are_indices_in_range(row, col) && grid[row][col] >= sourceHeight) {
      switch (mode) {
      | "visibility" => (-1)
      | "score" => smallerCount + 1
      };
    } else {
      switch (direction) {
      | "top" =>
        row <= (-1) ?
          max(smallerCount, 1) : traverse(row - 1, col, smallerCount + 1)
      | "bottom" =>
        row >= grid_height ?
          max(smallerCount, 1) : traverse(row + 1, col, smallerCount + 1)
      | "left" =>
        col <= (-1) ?
          max(smallerCount, 1) : traverse(row, col - 1, smallerCount + 1)
      | "right" =>
        col >= grid_length ?
          max(smallerCount, 1) : traverse(row, col + 1, smallerCount + 1)
      };
    };

  if (is_edge_tree(sourceRow, sourceCol, direction)) {
    1;
  } else {
    switch (direction) {
    | "top" => traverse(sourceRow - 1, sourceCol, 0)
    | "bottom" => traverse(sourceRow + 1, sourceCol, 0)
    | "left" => traverse(sourceRow, sourceCol - 1, 0)
    | "right" => traverse(sourceRow, sourceCol + 1, 0)
    };
  };
};

let visible_trees_count = ref(0);
let highest_fov = ref(0);
for (row in 0 to grid_height) {
  for (col in 0 to grid_length) {
    let visibility_states =
      directions
      |> Array.map(direction =>
           traverse_trees(direction, grid[row][col], row, col, "visibility")
         );

    let is_visible =
      visibility_states
      |> Array.to_list
      |> List.exists(element => element >= 0);

    if (is_visible) {
      visible_trees_count := visible_trees_count^ + 1;
    };

    let scores =
      directions
      |> Array.map(direction =>
           traverse_trees(direction, grid[row][col], row, col, "score")
         );

    let product = ref(1);

    for (i in 0 to 3) {
      product := product^ * scores[i];
    };

    if (product^ > highest_fov^) {
      highest_fov := product^;
    };
  };
};

Js.log([|"Part 1:", string_of_int(visible_trees_count^)|]);
Js.log([|"Part 2:", string_of_int(highest_fov^)|]);
