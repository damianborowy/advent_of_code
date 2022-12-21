record Monkey(string n1, string n2);
record Number(long n) : Monkey("", "");
record Add(string n1, string n2) : Monkey(n1, n2);
record Sub(string n1, string n2) : Monkey(n1, n2);
record Mul(string n1, string n2) : Monkey(n1, n2);
record Div(string n1, string n2) : Monkey(n1, n2);

class Day21Solver
{
    private Dictionary<string, Monkey> monkeys;

    public Day21Solver(Dictionary<string, Monkey> monkeys)
    {
        this.monkeys = monkeys;
    }

    public long Calc(string name) =>
        monkeys[name] switch
        {
            Number(var n) => n,
            Add(var n1, var n2) => Calc(n1) + Calc(n2),
            Sub(var n1, var n2) => Calc(n1) - Calc(n2),
            Mul(var n1, var n2) => Calc(n1) * Calc(n2),
            Div(var n1, var n2) => Calc(n1) / Calc(n2),
            _ => throw new Exception("Some weird monkey found")
        };

    public long Humans(string name)
    {
        if (name == "humn") return 1;

        return monkeys[name] switch
        {
            Number => 0,
            { n1: var n1, n2: var n2 } => Humans(n1) + Humans(n2),
        };
    }

    public long Solve(string name, long value)
    {
        if (name == "humn") return value;

        long PerformAdd(string n1, string n2) =>
            Humans(n1) == 1 ? Solve(n1, value - Calc(n2)) : Solve(n2, value - Calc(n1));

        long PerformSub(string n1, string n2) =>
            Humans(n1) == 1 ? Solve(n1, value + Calc(n2)) : Solve(n2, Calc(n1) - value);

        long PerformMul(string n1, string n2) =>
            Humans(n1) == 1 ? Solve(n1, value / Calc(n2)) : Solve(n2, value / Calc(n1));

        long PerformDiv(string n1, string n2) =>
            Humans(n1) == 1 ? Solve(n1, value * Calc(n2)) : Solve(n2, Calc(n1) / value);

        return monkeys[name] switch
        {
            Number(var n) => n,
            Add(var n1, var n2) => PerformAdd(n1, n2),
            Sub(var n1, var n2) => PerformSub(n1, n2),
            Mul(var n1, var n2) => PerformMul(n1, n2),
            Div(var n1, var n2) => PerformDiv(n1, n2),
            _ => throw new Exception("")
        };
    }

    public long CalculatePart2(string n1, string n2)
    {
        if (Humans(n1) == 1) return Solve(n1, Calc(n2));
        if (Humans(n2) == 1) return Solve(n2, Calc(n1));

        throw new Exception("There should be exactly one human.");
    }
}

class Runner
{
    private static Dictionary<string, Monkey> ParseInput() =>
        File
            .ReadLines(@"C:\Users\Damian\input.txt")
            .Select<string, (string, Monkey)>(line =>
            {
                var splitLine = line.Split(": ");
                var name = splitLine[0];
                var equation = splitLine[1].Split(" ");

                if (equation.Length == 1)
                {
                    return (name, new Number(long.Parse(equation[0])));
                }

                return equation[1] switch
                {
                    "+" => (name, new Add(equation[0], equation[2])),
                    "-" => (name, new Sub(equation[0], equation[2])),
                    "*" => (name, new Mul(equation[0], equation[2])),
                    "/" => (name, new Div(equation[0], equation[2])),
                    _ => throw new Exception("Unknown input operator")
                };
            })
            .ToDictionary(tuple => tuple.Item1, tuple => tuple.Item2);

    static void Main()
    {
        var input = ParseInput();
        var solver = new Day21Solver(input);

        // PART 1
        Console.WriteLine("Part 1: " + solver.Calc("root"));

        // PART 2
        var part2 = input["root"] switch
        {
            Number => throw new Exception("Root must be an operation!"),
            { n1: var n1, n2: var n2 } => solver.CalculatePart2(n1, n2),
        };

        Console.WriteLine("Part 2: " + part2);
    }
}