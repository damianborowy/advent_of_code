package main

import (
	"fmt"
	"encoding/base64"
	"strconv"
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

func evaluate(rounds int64) int64 {
	windDirections := strings.Split(input, "")
	currentShape := 0
	currentInstruction := 0
	var previouslyAchievedScore, topOfTower, fullLine int64
	previouslyAchievedScore = 0
	topOfTower = 0
	fullLine = -1
	tower := []byte{}
	states := make(map[string]*StoredState)

	moveWithWind := func(instruction string, position int64) {
		rock := figures[currentShape]

		if instruction == ">" {
			for i := 0; i < len(rock); i++ {
				flowsOverEdge := rock[len(rock) - i - 1] & 0b1000000 != 0
				collidesWithFloor := tower[position + 1] & rock[len(rock) - i - 1] << 1 != 0

				if flowsOverEdge || collidesWithFloor {
					return
				}
			}

			for i := 0; i < len(rock); i++ {
				rock[i] <<= 1
			}
		}

		if instruction == "<" {
			for i := 0; i < len(rock); i++ {
				flowsOverEdge := rock[len(rock) - i - 1] & 0b0000001 != 0
				collidesWithFloor := tower[position + 1] & rock[len(rock) - i - 1] >> 1 != 0

				if flowsOverEdge || collidesWithFloor {
					return
				}
			}

			for i := 0; i < len(rock); i++ {
				rock[i] >>= 1
			}
		}
	}

	detectCycle := func(round int64) *StoredState {
		bufferStr := strconv.FormatInt(fullLine + 1, 10)
		bufferStr += strconv.FormatInt(topOfTower - (fullLine + 1), 10)
		bufferStr += strconv.Itoa(currentShape)
		bufferStr += strconv.Itoa(currentInstruction)
		buffer := []byte(bufferStr)
		encodedStr := base64.StdEncoding.EncodeToString(append(buffer, tower...))

		previous := states[encodedStr]

		if previous != nil {
			return previous
		}

		states[encodedStr] = &StoredState{rounds: round, score: previouslyAchievedScore + topOfTower}

		return nil
	}

	ensureSize := func() {
		tower = append(tower, 0, 0, 0, 0, 0)
	}
	
	// ensureSize := func(size int) {
	// 	if size >= len(tower) {
	// 		if fullLine >= size - len(tower) {
	// 			previouslyAchievedScore += fullLine + 1
	// 			newTower := []byte{}
	// 			copy(tower, newTower[(fullLine + 1):(len(tower) - fullLine - 1)])
	// 			tower = newTower
	// 			topOfTower -= fullLine + 1
	// 			fullLine = -1
	// 		} else {
	// 			newTower := []byte{}
	// 			copy(newTower, tower)
	// 			tower = newTower
	// 		}
	// 	}
	// }

	isPositionValid := func(position int64) bool {
		if position < 0 {
			return false
		}

		rock := figures[currentShape]

		var layer int64
		rockLayersCount := int64(len(rock))
		for layer = 0; layer < rockLayersCount; layer++ {
			if (tower[position + layer] & rock[rockLayersCount - layer - 1]) != 0 {
				return false
			}
		}

		return true
	}

	max := func(x, y int64) int64 {
		if x < y {
			return y
		}

		return x
	}

	stopRock := func(position int64) {
		rock := figures[currentShape]
		rockLayersCount := int64(len(rock))
		topOfTower = max(topOfTower, position + rockLayersCount)
		
		var layer int64
		for layer = 0; layer < rockLayersCount; layer++ {
			tower[position + 1] |= rock[rockLayersCount - layer - 1]

			if tower[position + 1] == 0b1111111 {
				fullLine = position + layer
			}
		}
	}

	placeRock := func() {
		currentShape = (currentShape + 1) % len(figures)
		currentPosition := topOfTower + 3
		ensureSize()

		for true {
			instruction := windDirections[currentInstruction]
			currentInstruction = (currentInstruction + 1) % len(windDirections)

			moveWithWind(instruction, currentPosition)

			if isPositionValid(currentPosition - 1) {
				currentPosition--
			} else {
				break
			}
		}

		stopRock(currentPosition)
	}

	var round int64
	for round = 0; round < rounds; round++ {
		previous := detectCycle(round)

		if previous != nil {
			cycleLength := round - previous.rounds
			skippedCycles := (rounds - round) / cycleLength
			round += cycleLength * skippedCycles
			previouslyAchievedScore += skippedCycles * (previouslyAchievedScore + topOfTower - previous.score)
		}

		placeRock()
	}

	return previouslyAchievedScore + topOfTower
}

func main() {
	// PART 1
	fmt.Println(evaluate(2022))

	// PART 2
	fmt.Println(evaluate(1000000000000))
}