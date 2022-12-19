package main

import (
	"fmt"
	"strings"
)

var input = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"

var figures = [][]byte{
	{
		0b0011110,
	},
	{
		0b0001000, 
		0b0011100, 
		0b0001000,
	},
	{
		0b0000100,
		0b0000100,
		0b0011100,
	},
	{
		0b0010000,
		0b0010000,
		0b0010000,
		0b0010000,
	},
	{
		0b0011000, 
		0b0011000,
	},
}

type StoredState struct {
	rounds int64
	score int64
}

func main() {
	evaluate := func(rounds int64) int64 {
		windDirections := strings.Split(input, "")
		fmt.Println(windDirections)
		fmt.Println(figures)
		// previouslyAchievedScore := 0
		currentShape := 0
		// currentInstruction := 0
		// topOfTower := 0
		// tower := []byte{}
		// fullLine := -1
		// states := make(map[string]*StoredState)

		// moveWithWind := func(instruction string, rockIndex int, position int) {
		// 	rock := figures[rockIndex]

		// 	if instruction == ">" {
		// 		for i := 0; i < len(rock); i++ {
		// 			flowsOverEdge := rock[len(rock) - i - 1] & 0b1000000 != 0
		// 			collidesWithFloor := tower[position + 1] & rock[len(rock) - i - 1] << 1 != 0

		// 			if flowsOverEdge || collidesWithFloor {
		// 				return
		// 			}
		// 		}

		// 		for i := 0; i < len(rock); i++ {
		// 			rock[i] <<= 1
		// 		}
		// 	}

		// 	if instruction == "<" {
		// 		for i := 0; i < len(rock); i++ {
		// 			flowsOverEdge := rock[len(rock) - i - 1] & 0b0000001 != 0
		// 			collidesWithFloor := tower[position + 1] & rock[len(rock) - i - 1] >> 1 != 0

		// 			if flowsOverEdge || collidesWithFloor {
		// 				return
		// 			}
		// 		}

		// 		for i := 0; i < len(rock); i++ {
		// 			rock[i] >>= 1
		// 		}
		// 	}
		// }

		detectCycle := func(round int64) *StoredState {
			return &StoredState{}
		}

		placeRock := func(currentShape int) {
			rock := figures[currentShape]
			fmt.Println(rock)
		}

		var round int64
		for round = 0; round < rounds; round++ {
			previous := detectCycle(round)

			if previous != nil {
				// cycleLength := round - previous.rounds
			}

			placeRock(currentShape)
		}

		return 0
	}

	// PART 1
	evaluate(2022)

	// PART 2
	// evaluate(1000000000000)
}