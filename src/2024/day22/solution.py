from typing import Tuple


class MonkeyTickData:
    def __init__(self, secret: int, price: int, single_price_change: int | None, last_price_changes: str):
        self.secret = secret
        self.price = price
        self.single_price_change = single_price_change
        self.last_price_changes = last_price_changes


with open('smallInput.txt', 'r') as file:
    initial_secrets = [int(line.strip()) for line in file]

bananas_per_change: dict[str, int] = {}


def derive_next_secret(initial_secret: int, iterations_count: int) -> Tuple[int, list[MonkeyTickData]]:
    secret = initial_secret
    tick_data = [MonkeyTickData(secret, secret % 10, None, "")]
    visited_changes = set()

    for index in range(iterations_count):
        secret = ((secret * 64) ^ secret) % 16777216
        secret = ((secret // 32) ^ secret) % 16777216
        secret = ((secret * 2048) ^ secret) % 16777216

        price = secret % 10
        previous_price = tick_data[index].price
        price_change = price - previous_price
        price_changes = ""

        if index >= 3:
            previous_ticks_data = tick_data[index - 2:index + 1]
            previous_prices = [tick_data.single_price_change for tick_data in previous_ticks_data]
            price_changes = ",".join(str(change) for change in [*previous_prices, price_change])

        if price_changes != "" and price_changes not in visited_changes:
            visited_changes.add(price_changes)
            current_bananas_per_change = bananas_per_change.get(price_changes) or 0
            bananas_per_change[price_changes] = current_bananas_per_change + price

        tick_data.append(MonkeyTickData(secret, price, price_change, price_changes))

    return secret, tick_data


derived_data = [derive_next_secret(initial_secret, 2000) for initial_secret in initial_secrets]

print(f"Day 22, part 1: {sum(secret for secret, _ in derived_data)}")
print(f"Day 22, part 2: {max(bananas_per_change.values())}")
