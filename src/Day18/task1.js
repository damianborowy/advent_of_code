const { inputSnailNumbers } = require("./inputData");

function sum(number1, number2) {
    if (!number2 || number2?.length === 0) return number1;

    return reduceNumber([number1, number2]);
}

function reduceNumber(number) {
    let newNumber = number;
    let wasChanged = false;

    do {
        [newNumber, wasChanged] = explodeOrSplitNumber(newNumber);
    } while (wasChanged);

    return newNumber;
}

function explodeOrSplitNumber(number) {
    const [explodedNumber, wasExploded] = explodePair(number);

    if (!wasExploded) return splitNumber(number);
    else return [explodedNumber, wasExploded];
}

function explodePair(number) {
    let wasExploded = false;

    function explodeHelper(number, depth = 0) {
        if (!Array.isArray(number)) return number;

        if (depth === 4) {
            wasExploded = true;
            
        } else return number;
    }

    explodeHelper(number);
    
    return [number, wasExploded];
}

function splitNumber(number) {
    let wasSplit = false;

    function splitHelper(number) {
        if (wasSplit) return number;


    }

    return [number, wasSplit];
}

const sumOfAllNumbers = inputSnailNumbers.reduce((numberAcc, number) => sum(numberAcc, number), []);
console.log(sumOfAllNumbers);
