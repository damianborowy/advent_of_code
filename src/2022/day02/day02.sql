-- Part 1

SELECT 
	CASE
		WHEN value = 'A X' THEN 3 + 1
		WHEN value = 'A Y' THEN 6 + 2
		WHEN value = 'A Z' THEN 0 + 3
		WHEN value = 'B X' THEN 0 + 1
		WHEN value = 'B Y' THEN 3 + 2
		WHEN value = 'B Z' THEN 6 + 3
		WHEN value = 'C X' THEN 6 + 1
		WHEN value = 'C Y' THEN 0 + 2
		WHEN value = 'C Z' THEN 3 + 3
	END AS score
INTO #rounds
FROM STRING_SPLIT(REPLACE(@STRING, CHAR(13), ''), CHAR(10));

SELECT SUM(score) AS total_score FROM #rounds;

-- Part 2

SELECT 
	CASE
		WHEN value = 'A X' THEN 0 + 3
		WHEN value = 'A Y' THEN 3 + 1
		WHEN value = 'A Z' THEN 6 + 2
		WHEN value = 'B X' THEN 0 + 1
		WHEN value = 'B Y' THEN 3 + 2
		WHEN value = 'B Z' THEN 6 + 3
		WHEN value = 'C X' THEN 0 + 2
		WHEN value = 'C Y' THEN 3 + 3
		WHEN value = 'C Z' THEN 6 + 1
	END AS score
INTO #rounds
FROM STRING_SPLIT(REPLACE(@STRING, CHAR(13), ''), CHAR(10));

SELECT SUM(score) AS total_score FROM #rounds;