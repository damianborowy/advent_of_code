const octopusesString = `
8826876714
3127787238
8182852861
4655371483
3864551365
1878253581
8317422437
1517254266
2621124761
3473331514
`;

exports.inputOctopuses = octopusesString.split("\n").filter(Boolean).map(row => row.split("").map(Number));