inputString = """
"""

input = inputString
  .split("\n\n")
  .map((row) => row.split("\n").map(JSON.parse))
	
compare = (a,b) ->
	if !Array.isArray(a) && !Array.isArray(b) then return a - b 

	a = [a] if !Array.isArray(a)
	b = [b] if !Array.isArray(b)

	for i in [0...Math.min(a.length,b.length)]
		res = compare(a[i],b[i])
		return res if res != 0 

	return a.length - b.length

# PART 1

orderedRowsCount = input.reduce(
	(acc, row, index) -> acc + (if compare(row[0], row[1]) > 0 then 0 else index + 1),
	0
)

console.log orderedRowsCount

# PART 2

input.push([[[2]], [[6]]])

sortedPackets = input.flat().sort(compare).map((elem) -> elem.toString())
decoderKey = (sorted.indexOf('2') + 1) * (sorted.indexOf('6') + 1)

console.log decoderKey