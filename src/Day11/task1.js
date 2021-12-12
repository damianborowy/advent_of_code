const { inputOctopuses } = require('./inputData');

const STEPS = 100;

// function flash(octopuses, )

function flashOctopuses(octopuses) {
    let flashesCount = 0;
    let resultOctopuses = [...octopuses];
    let step = 0;

    while (step < STEPS) {
        

        step++;
    }

    return [resultOctopuses, flashesCount];
}

const [_flashedOctopuses, flashesCount] = flashOctopuses(inputOctopuses);
console.log(flashesCount);