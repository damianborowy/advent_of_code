#include <iostream>
#include <regex>
#include <vector>
#include <numeric>

const char* input_text = R"()";

class Monkey
{
public:
	std::vector<long long> item_worry_levels;
	std::string operation;
	std::string second_operand;
	short test_dividing_by;
	short test_true_monkey_id;
	short test_false_monkey_id;
	long long inspections_count;

	long long get_worry_level(const long long initial_worry_level, bool is_worry_divided)
	{
		long long new_worry_level = initial_worry_level;
		long long parsed_second_operand = second_operand == "old" ? initial_worry_level : stoi(second_operand);

		if (operation == "*")
		{
			new_worry_level *= parsed_second_operand;
		}
		else if (operation == "+")
		{
			new_worry_level += parsed_second_operand;
		}

		if (is_worry_divided)
		{
			new_worry_level /= 3;
		}

		return new_worry_level;
	}

	short get_target_monkey(const long long initial_worry_level)
	{
		return initial_worry_level % test_dividing_by == 0 ? test_true_monkey_id : test_false_monkey_id;
	}
};

std::vector<std::string> split(const std::string& str, const std::string& regex_str)
{
	const std::regex parsed_regex(regex_str);
	std::vector<std::string> list(std::sregex_token_iterator(str.begin(), str.end(), parsed_regex, -1),
		std::sregex_token_iterator());

	return list;
}

std::vector<std::unique_ptr<Monkey>> parse_input()
{
	std::vector<std::unique_ptr<Monkey>> monkeys;
	const auto input = split(input_text, "\n\n");

	for (auto& row : input)
	{
		auto monkey_row = split(row, "\n");
		auto new_monkey = std::make_unique<Monkey>();

		auto items_string = std::regex_replace(monkey_row[1], std::regex("\\s\\D+"), "");
		for (auto& item : split(items_string, ", "))
		{
			new_monkey->item_worry_levels.push_back(stoi(item));
		}

		new_monkey->operation = split(monkey_row[2], " ")[6];
		new_monkey->second_operand = split(monkey_row[2], " ")[7];
		new_monkey->test_dividing_by = stoi(split(monkey_row[3], " ")[5]);
		new_monkey->test_true_monkey_id = stoi(split(monkey_row[4], " ")[9]);
		new_monkey->test_false_monkey_id = stoi(split(monkey_row[5], " ")[9]);
		new_monkey->inspections_count = 0;

		monkeys.push_back(std::move(new_monkey));
	}

	return monkeys;
}

int main()
{
	// `true` for part1, `false` for part2
	bool should_divide_worry_by_three = false;

	auto monkeys = parse_input();

	long long dividers_lcm = 1;
	for (auto& monkey : monkeys)
	{
		dividers_lcm = std::lcm(dividers_lcm, monkey->test_dividing_by);
	}

	for (auto i = 0; i < 20; i++)
	{
		for (auto& monkey : monkeys)
		{
			for (auto& worry_level : monkey->item_worry_levels)
			{
				auto new_worry_level = monkey->get_worry_level(worry_level % dividers_lcm, should_divide_worry_by_three);
				auto target_monkey_id = monkey->get_target_monkey(new_worry_level);

				monkeys[target_monkey_id]->item_worry_levels.push_back(new_worry_level);
				monkey->inspections_count++;
			}

			monkey->item_worry_levels.clear();
		}
	}

	std::vector<long long> inspections;

	for (auto& monkey : monkeys)
	{
		inspections.push_back(monkey->inspections_count);
	}

	std::ranges::sort(inspections, std::greater());

	std::cout << inspections[0] * inspections[1];

	return 0;
}
