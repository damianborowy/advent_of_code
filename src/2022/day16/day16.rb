input_string = "Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
Valve BB has flow rate=13; tunnels lead to valves CC, AA
Valve CC has flow rate=2; tunnels lead to valves DD, BB
Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
Valve EE has flow rate=3; tunnels lead to valves FF, DD
Valve FF has flow rate=0; tunnels lead to valves EE, GG
Valve GG has flow rate=0; tunnels lead to valves FF, HH
Valve HH has flow rate=22; tunnel leads to valve GG
Valve II has flow rate=0; tunnels lead to valves AA, JJ
Valve JJ has flow rate=21; tunnel leads to valve II"

ROW_REGEX = /Valve (..) has flow rate=(\d+); tunnels? leads? to valves? (.*)/
FIXNUM_MAX = (2**(0.size * 8 -2) -1)
START_VALVE = 1

input = input_string
  .split("\n")
  .map{|row| row.match(ROW_REGEX)}
  .map{|match| match.captures}
  .sort_by(&:first)

parsed_input = input
  .each_with_index
  .map{|(valve, rate, valves), index| [
    1 << index,
    [
      rate.to_i,
      valves
        .split(", ")
        .map{|target_valve| 1 << input.index(input.find{|valve, *rest| target_valve == valve})},
      valve
    ]
  ]}
  .to_h

def memo(func)
  cache = Hash.new

  return lambda do |*args|
    key = args.join("")

    if cache.has_key?(key)
      return cache[key]
    end
    
    val = func.call(*args)
    cache[key] = val
    return val
  end
end

def shortest_path(graph)
  keys = graph.keys
  dist_hash = keys
    .map{|key| [
      key, 
      keys.map{|dist| [dist, FIXNUM_MAX]}.to_h
    ]}
    .to_h
  
  keys.each{|u| graph[u].each{|v| dist_hash[u][v] = 1}}
  keys.each{|k| dist_hash[k][k] = 0}
  keys.each{|k| 
    keys.each{|i| 
      keys.each{|j|
        dist_hash[i][j] = [dist_hash[i][j], dist_hash[i][k] + dist_hash[k][j]].min
      }
    }
  }

  return dist_hash
end

def evaluate(input, time = 30, first_run = false)
  dist_graph = input.map{|(key, data)| [key, data[1]]}.to_h
  dist_hash = shortest_path(dist_graph)
  keys = input.keys
  flow = keys.map{|key| [key, input[key][0]]}.to_h

  dfs = memo(lambda {|valve, minutes, open, first_run|
    keys
      .filter{|key| open & key == 0 && flow[key] != 0 && dist_hash[valve][key] < minutes}
      .map do |key|
        time_left = minutes - dist_hash[valve][key] + 1
        time_left * flow[key] + dfs.call(key, time_left, open | key, first_run)
      end
      .reduce(first_run ? dfs.call(START_VALVE, time, open, false) : 0) {|acc, val| acc > val ? acc : val}
  })

  return dfs.call(START_VALVE, time, 0, first_run)
end

puts ["Part 1:", evaluate(parsed_input)].join(" ")
puts ["Part 2:", evaluate(parsed_input, 26, true)].join(" ")
