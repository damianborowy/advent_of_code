extern crate overload;
use grouping_by::GroupingBy;
use itertools::Itertools;
use overload::overload;
use std::collections::{HashMap, HashSet};
use std::fs::File;
use std::io::{prelude::*, BufReader};
use std::ops;

#[derive(Hash, Eq, PartialEq, Debug, Clone, Copy, Ord, PartialOrd)]
struct Point {
    x: i32,
    y: i32,
}

overload!((a: Point) + (b: Point) -> Point {
  Point { x: a.x + b.x, y: a.y + b.y }
});

impl Point {
    fn clockwise(&self) -> Point {
        Point {
            x: -self.y,
            y: self.x,
        }
    }

    fn counter_clockwise(&self) -> Point {
        Point {
            x: self.y,
            y: -self.x,
        }
    }

    fn score(&self) -> i32 {
        1000 * (self.y + 1) + 4 * (self.x + 1)
    }
}

#[derive(Debug, Eq, Hash, PartialEq, Copy, Clone)]
struct Vector {
    x: i32,
    y: i32,
    z: i32,
}

overload!((a: Vector) + (b: Vector) -> Vector {
  Vector { x: a.x + b.x, y: a.y + b.y, z: a.z + b.z }
});

overload!((v: Vector) * (k: i32) -> Vector {
  Vector { x: v.x * k, y: v.y * k, z: v.z * k }
});

impl Vector {
    fn cross(&self, other_vector: Vector) -> Vector {
        Vector {
            x: self.y * other_vector.z - self.z * other_vector.y,
            y: self.z * other_vector.x - self.x * other_vector.z,
            z: self.x * other_vector.y - self.y * other_vector.x,
        }
    }
}

#[derive(Debug, Copy, Clone)]
struct Info {
    point: Point,
    i: Vector,
    j: Vector,
    k: Vector,
}

fn parse_input() -> Vec<String> {
    let file = File::open("input.txt").expect("no such file");
    let buf = BufReader::new(file);

    buf.lines()
        .map(|line| line.expect("Could not parse line"))
        .collect()
}

fn parse_tiles(input: &Vec<String>) -> HashMap<Point, bool> {
    let sub_vector = input.get(0..input.len() - 2).unwrap().to_vec();

    sub_vector
        .iter()
        .enumerate()
        .flat_map(|(y, row)| {
            row.chars()
                .enumerate()
                .filter(|(_, cell)| *cell != ' ')
                .map(move |(x, cell)| {
                    (
                        Point {
                            x: x as i32,
                            y: y as i32,
                        },
                        (cell == '.'),
                    )
                })
        })
        .collect()
}

fn parse_moves(input: &Vec<String>) -> Vec<char> {
    input
        .last()
        .unwrap()
        .replace("L", " L ")
        .replace("R", " R ")
        .split(" ")
        .map(|token| {
            let first_char = token.split(" ").nth(0).unwrap().to_string();

            if is_digit(&first_char) {
                "F".repeat(first_char.parse::<usize>().unwrap())
            } else {
                token.to_string()
            }
        })
        .collect::<String>()
        .chars()
        .collect::<Vec<char>>()
}

fn is_digit(num_string: &String) -> bool {
    num_string.parse::<i32>().is_ok()
}

fn get_grouped_tiles_max(
    tiles: &HashMap<Point, bool>,
    get_grouping_key: fn(&Point) -> i32,
    get_map_key: fn(&Point) -> i32,
) -> HashMap<i32, (i32, i32)> {
    tiles
        .keys()
        .cloned()
        .collect::<Vec<_>>()
        .into_iter()
        .grouping_by(|point| get_grouping_key(&point))
        .into_iter()
        .map(|(key, points)| {
            (
                key,
                points
                    .into_iter()
                    .map(|point| get_map_key(&point))
                    .minmax()
                    .into_option()
                    .unwrap(),
            )
        })
        .collect()
}

fn get_top_left_point(tiles: &HashMap<Point, bool>) -> Point {
    tiles
        .keys()
        .cloned()
        .collect::<Vec<_>>()
        .into_iter()
        .filter(|point| point.y == 0)
        .min_by_key(|point| point.x)
        .unwrap()
}

fn part1(input: &Vec<String>) -> i32 {
    let moves = parse_moves(&input);
    let tiles = parse_tiles(&input);

    let minmax_x = get_grouped_tiles_max(&tiles, |point| point.y, |point| point.x);
    let minmax_y = get_grouped_tiles_max(&tiles, |point| point.x, |point| point.y);

    let right = Point { x: 1, y: 0 };
    let down = Point { x: 0, y: 1 };
    let left = Point { x: -1, y: 0 };
    let up = Point { x: 0, y: -1 };
    let facing = vec![&right, &down, &left, &up];
    let top_left = get_top_left_point(&tiles);

    let (position, direction) =
        moves
            .iter()
            .fold(
                (top_left, right),
                |(position, direction), &current_move| match current_move {
                    'L' => (position, direction.counter_clockwise()),
                    'R' => (position, direction.clockwise()),
                    _ => {
                        let next = position + direction;

                        match tiles.get(&next) {
                            Some(true) => (next, direction),
                            Some(false) => (position, direction),
                            None => {
                                let mut new_pos = position.clone();

                                match direction {
                                    Point { x: 1, y: 0 } => new_pos.x = minmax_x[&position.y].0,
                                    Point { x: -1, y: 0 } => new_pos.x = minmax_x[&position.y].1,
                                    Point { x: 0, y: 1 } => new_pos.y = minmax_y[&position.x].0,
                                    Point { x: 0, y: -1 } => new_pos.y = minmax_y[&position.x].1,
                                    _ => {}
                                };

                                if *tiles.get(&new_pos).unwrap_or(&false) {
                                    (new_pos, direction)
                                } else {
                                    (position, direction)
                                }
                            }
                        }
                    }
                },
            );

    position.score() + facing.iter().position(|&face| face == &direction).unwrap() as i32
}

fn part2(input: &Vec<String>, block: i32) -> i32 {
    let moves = parse_moves(&input);
    let tiles = parse_tiles(&input);

    let scale_ij = block - 1;
    let scale_k = block + 1;
    let starting_position = Vector {
        x: -scale_ij,
        y: -scale_ij,
        z: -scale_k,
    };
    let starting_direction = Vector { x: 2, y: 0, z: 0 };

    let top_left = get_top_left_point(&tiles);
    let start = Info {
        point: top_left,
        i: Vector { x: 1, y: 0, z: 0 },
        j: Vector { x: 0, y: 1, z: 0 },
        k: Vector { x: 0, y: 0, z: 1 },
    };

    let mut todo = vec![start];
    let mut visited = HashSet::from([top_left]);
    let mut points: HashMap<Vector, Info> = HashMap::new();

    while let Some(Info { point, i, j, k }) = todo.pop() {
        for x in 0..block {
            for y in 0..block {
                let key = (i * (2 * x - scale_ij)) + (j * (2 * y - scale_ij)) + (k * -scale_k);
                
                points.insert(
                    key,
                    Info {
                        point: point + Point { x, y },
                        i,
                        j,
                        k,
                    },
                );
            }
        }

        let neighbors = vec![
            Info {
                point: point + Point { x: -block, y: 0 },
                i: j.cross(i),
                j,
                k: j.cross(k),
            },
            Info {
                point: point + Point { x: block, y: 0 },
                i: i.cross(j),
                j,
                k: k.cross(j),
            },
            Info {
                point: point + Point { x: 0, y: -block },
                i,
                j: j.cross(i),
                k: k.cross(i),
            },
            Info {
                point: point + Point { x: 0, y: block },
                i,
                j: i.cross(j),
                k: i.cross(k),
            },
        ];

        for neighbor in neighbors {
            if tiles.contains_key(&neighbor.point) && !visited.contains(&neighbor.point) {
                todo.push(neighbor);
                visited.insert(neighbor.point);
            }
        }
    }

    let (position, direction) = moves.iter().fold(
        (starting_position, starting_direction),
        |(position, direction), &current_move| match current_move {
            'L' => (position, direction.cross(points[&position].k)),
            'R' => (position, points[&position].k.cross(direction)),
            _ => {
                let next = position + direction;

                if points.contains_key(&next) {
                    if tiles[&points[&next].point] {
                        (next, direction)
                    } else {
                        (position, direction)
                    }
                } else {
                    let wrap_direction = points[&position].k * 2;
                    let wrap_position = next + wrap_direction;

                    if tiles[&points[&wrap_position].point] {
                        (wrap_position, wrap_direction)
                    } else {
                        (position, direction)
                    }
                }
            }
        },
    );

    let Info { point, i, j, k: _ } = *points.get(&position).unwrap();
    let vectors = vec![i * 2, j * 2, i * -2, j * -2];

    point.score()
        + vectors
            .iter()
            .position(|&vector| vector == direction)
            .unwrap() as i32
}

fn main() {
    let input = parse_input();

    println!("Part 1: {}", part1(&input));
    println!("Part 2: {}", part2(&input, 50));
}
