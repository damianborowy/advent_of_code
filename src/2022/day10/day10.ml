let next_noop_value = ref(1);;

let add_to_noop value =
  next_noop_value := !next_noop_value + value;
  [!next_noop_value - value; !next_noop_value - value];;

let cycles = input_string 
  |> String.split_on_char '\n'
  |> List.filter (fun s -> s <> "")
  |> List.map (fun row -> String.split_on_char ' ' row)
  |> List.map (fun operation -> match operation with
    | ["noop"] -> [!next_noop_value];
    | ["addx"; x] -> add_to_noop(int_of_string(x));
    | _ -> [];
  )
  |> List.flatten
  |> Array.of_list;;
  
(* PART 1 *)

[20; 60; 100; 140; 180; 220]
  |> List.map (fun cycle -> cycle * cycles.(cycle - 1))
  |> List.fold_left (+) 0
  |> string_of_int
  |> print_endline;;

(* PART 2 *)

let range n = Array.init n succ;; 

let crt_values = range 240
  |> Array.map (fun cycle -> if abs((cycle - 1) mod 40 - cycles.(cycle - 1)) <= 1 then "#" else ".");;

for i = 0 to 6 do
  Array.sub crt_values (i * 40) 40
  |> Array.to_list
  |> String.concat ""
  |> print_endline; 
done;;