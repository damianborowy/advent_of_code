local function read_file(path)
  local file = io.open(path, "rb")
  if not file then return nil end
  local content = file:read "*a"

  file:close()

  return content
end

local input = read_file("input.txt");

if not input then return nil end

local function is_every_value_unique(array)
  local values = {}

  for _key, value in pairs(array) do
    if values[value] then return false end

    values[value] = true
  end

  return true
end

local function find_marker(target_length)
  local current_buffer = {}

  for i = 1, #input do
    table.insert(current_buffer, input:sub(i, i))

    if #current_buffer == target_length and is_every_value_unique(current_buffer) then return i end

    if #current_buffer >= target_length then table.remove(current_buffer, 1) end
  end
end

-- PART 1

print(find_marker(4))

-- PART 2

print(find_marker(14))
