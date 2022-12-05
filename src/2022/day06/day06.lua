local function map(f, array)
  local new_array = {}

  for key, value in pairs(array) do
    new_array[key] = f(value)
  end

  return new_array
end

local function filter(f, array)
  local new_array = {}
  local new_index = 1

  for _key, value in pairs(array) do
    if f(value) then
      new_array[new_index] = value
      new_index = new_index + 1
    end
  end

  return new_array
end

local function head(array)
  return array[1]
end

local function tail(array)
  if #array < 1 then
    return nil

  else
    local new_array = {}
    local arraysize = #array
    local k = 2

    while (k <= arraysize) do
      table.insert(new_array, k - 1, array[k])
      k = k + 1
    end

    return new_array
  end
end

local function foldr(f, acc, array)
  for _key, value in pairs(array) do
    acc = f(acc, value)
  end

  return acc
end

local function reduce(f, array)
  return foldr(f, head(array), tail(array))
end

local function split(inputstr, sep)
  sep = sep or '%s'
  local result = {}

  for field, s in string.gmatch(inputstr, "([^" .. sep .. "]*)(" .. sep .. "?)") do
    table.insert(result, field)

    if s == "" then return result end
  end
end

-- TODO:: REMOVE
local function dump(o)
  if type(o) == 'table' then
    local s = '{ '
    for k, v in pairs(o) do
      if type(k) ~= 'number' then k = '"' .. k .. '"' end
      s = s .. '[' .. k .. '] = ' .. dump(v) .. ', '
    end
    return s .. '} '
  end

  return tostring(o)
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

local input_rows = split(input, "\n")

print(
  dump(
    foldr(function(acc, elem) return acc + elem end, 0,
      filter(function(elem) return elem % 2 == 0 end,
        map(tonumber, input_rows)
      )
    )
  )
);
