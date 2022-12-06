local function contains(table, element)
  for _, value in pairs(table) do
    if value == element then
      return true
    end
  end
  return false
end

local function split_string_into_chars(input_string)
  local result = {}

  for letter in input_string:gmatch(".") do table.insert(result, letter) end

  return result
end

local function is_every_value_unique(array)
  local values = {}

  for _key, value in pairs(array) do
    if contains(values, value) then return false end

    table.insert(values, value)
  end

  return true
end

local function read_file(path)
  local file = io.open(path, "rb")
  if not file then return nil end
  local content = file:read "*a"

  file:close()

  return content
end

local input = read_file("input.txt");

if not input then return nil end

local function find_marker(target_length)
  local current_buffer = {}
  local total_index = 0

  for _key, value in pairs(split_string_into_chars(input)) do
    total_index = total_index + 1
    table.insert(current_buffer, value)

    if #current_buffer == target_length and is_every_value_unique(current_buffer) then
      break
    end

    if #current_buffer >= target_length then
      table.remove(current_buffer, 1)
    end
  end

  return total_index
end

-- PART 1

print(find_marker(4))

-- PART 2

print(find_marker(14))