<?php

global $input_rows;

$stdin = fopen('php://stdin', 'r');
$input_string = stream_get_contents($stdin);
$input_rows = explode("\n", $input_string);

function create_grid($with_floor, $input_rows) {
  $MAX_X = 1000;
  $MAX_Y = 1000;
  $grid = array_fill(0, $MAX_Y, array_fill(0, $MAX_X, "."));
  $floor = 0;

  for ($row_index = 0; $row_index < count($input_rows); $row_index++) {
    $point_pairs = explode(" -> ", $input_rows[$row_index]);
    $points_in_line = [];
    
    for ($pair_index = 0; $pair_index < count($point_pairs) - 1; $pair_index++) {
      $start = explode(",", $point_pairs[$pair_index]);
      $end = explode(",", $point_pairs[$pair_index + 1]);
      $start_x = intval($start[0]);
      $start_y = intval($start[1]);
      $end_x = intval($end[0]);
      $end_y = intval($end[1]);
      $min_x = min($start_x, $end_x);
      $max_x = max($start_x, $end_x);
      $min_y = min($start_y, $end_y);
      $max_y = max($start_y, $end_y);
      $floor = max($floor, $max_y);

      if ($start_x == $end_x) {
        for ($y = $min_y; $y <= $max_y; $y++) {
          $grid[$start_x][$y] = "#";
        }
      } else if ($start_y == $end_y) {
        for ($x = $min_x; $x <= $max_x; $x++) {
          $grid[$x][$start_y] = "#";
        }
      }
    }
  }

  if ($with_floor) {
    for ($x = 0; $x < $MAX_X; $x++) {
      $grid[$x][$floor + 2] = "#";
    }
  }

  return $grid;
}

function drop_sand($part, $input_rows) {
  $START_X = 500;
  $START_Y = 0;
  $MAX_X = 1000;
  $MAX_Y = 1000;
  $grid = create_grid($part == "part2", $input_rows);
  $count = 0;
  $x = $START_X;
  $y = $START_Y;

  while (true) {
    if ($y + 1 == $MAX_Y && $part == "part1") {
      break;
    }
    
    if ($grid[$x][$y + 1] == ".") $y++;
    else if ($x > 0 && $grid[$x - 1][$y + 1] == ".") {
      $x--;
      $y++;
    } else if ($x < ($MAX_X - 1) && $grid[$x + 1][$y + 1] == ".") {
      $x++;
      $y++;
    } else {
      $grid[$x][$y] = "o";
      $count++;

      if ($x == $START_X && $y == $START_Y && $part == "part2") break;

      $x = $START_X;
      $y = $START_Y;
    }
  }

  return $count;
}

print("Part 1: " . drop_sand("part1", $input_rows) . "\n");
print("Part 2: " . drop_sand("part2", $input_rows));

?>