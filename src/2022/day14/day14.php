<?php

class SandCave {
  const MAX_X = 1000;
  const MAX_Y = 1000;
  const START_X = 500;
  const START_Y = 0;

  public $grid;

  public function __construct(public $part, $input_rows) {
    $this->grid = $this->create_grid($part, $input_rows);
  }

  function create_grid($part, $input_rows) {
    $grid = array_fill(0, self::MAX_Y, array_fill(0, self::MAX_X, "."));
    $floor = 0;

    for ($row_index = 0; $row_index < count($input_rows); $row_index++) {
      $point_pairs = explode(" -> ", $input_rows[$row_index]);
      $points_in_line = [];

      for ($pair_index = 0; $pair_index < count($point_pairs) - 1; $pair_index++) {
        [$start_x, $start_y, $end_x, $end_y] = $this->get_point_pairs_indices($point_pairs, $pair_index);
        [$min_x, $max_x] = $this->get_min_max($start_x, $end_x);
        [$min_y, $max_y] = $this->get_min_max($start_y, $end_y);
        $floor = max($floor, $max_y);

        if ($start_x == $end_x) {
          for ($y = $min_y; $y <= $max_y; $y++) {
            $grid[$start_x][$y] = "#";
          }
        }
        elseif ($start_y == $end_y) {
          for ($x = $min_x; $x <= $max_x; $x++) {
            $grid[$x][$start_y] = "#";
          }
        }
      }
    }

    if ($part == "part2") {
      for ($x = 0; $x < self::MAX_X; $x++) {
        $grid[$x][$floor + 2] = "#";
      }
    }

    return $grid;
  }

  private function get_min_max($value1, $value2) {
    return [min($value1, $value2), max($value1, $value2)];
  }

  private function get_point_pairs_indices($point_pairs, $pair_index) {
    $start = explode(",", $point_pairs[$pair_index]);
    $end = explode(",", $point_pairs[$pair_index + 1]);
    $start_indices = array_map("intval", $start);
    $end_indices = array_map("intval", $end);

    return [...$start_indices, ...$end_indices];
  }

  function drop_sand() {
    $count = 0;
    $x = self::START_X;
    $y = self::START_Y;

    while (true) {
      if ($y + 1 == self::MAX_Y && $this->part == "part1") {
        break;
      }

      if ($this->grid[$x][$y + 1] == ".") {
        $y++;
      }
      elseif ($x > 0 && $this->grid[$x - 1][$y + 1] == ".") {
        $x--;
        $y++;
      }
      elseif ($x < self::MAX_X - 1 && $this->grid[$x + 1][$y + 1] == ".") {
        $x++;
        $y++;
      }
      else {
        $this->grid[$x][$y] = "o";
        $count++;

        if ($x == self::START_X && $y == self::START_Y && $this->part == "part2") {
          break;
        }

        $x = self::START_X;
        $y = self::START_Y;
      }
    }

    return $count;
  }
}

$stdin = fopen("php://stdin", "r");
$input_string = stream_get_contents($stdin);
$input_rows = explode("\n", $input_string);

$part1Cave = new SandCave("part1", $input_rows);
print "Part 1: " . $part1Cave->drop_sand() . "\n";

$part2Cave = new SandCave("part2", $input_rows);
print "Part 2: " . $part2Cave->drop_sand();

?>
