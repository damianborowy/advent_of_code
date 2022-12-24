extern crate overload;
use overload::overload;
use std::collections::HashMap;
use std::fs::File;
use std::io::{prelude::*, BufReader};
use std::ops;

#[derive(Hash, Eq, PartialEq, Debug)]
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
            x: self.x,
            y: -self.y,
        }
    }

    fn counter_clockwise(&self) -> Point {
        Point {
            x: -self.x,
            y: self.y,
        }
    }

    fn score(&self) -> i32 {
        1000 * (self.y + 1) + 4 * (self.x + 1)
    }
}

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

fn part1(input: &Vec<String>) -> i32 {
    let moves = parse_moves(&input);
    let tiles = parse_tiles(&input);
    
    // TODO:: implement

    return 5;
}

fn part2(input: &Vec<String>, block: i32) -> i32 {
    // TODO:: implement

    return 10;
}

fn main() {
    let input = parse_input();

    println!("Part 1: {}", part1(&input));
    println!("Part 2: {}", part2(&input, 50));
}
