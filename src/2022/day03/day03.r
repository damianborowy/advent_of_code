install.packages("magrittr")
library(magrittr)

current_work_directory = getwd()
file_path = "/src/2022/day03/input.txt"
total_path = paste(current_work_directory, file_path, sep="")

input <- scan(total_path, what="", sep="")

split_string <- function(text, separator) strsplit(text, separator)[[1]]

rows <- sapply(input, function(row) split_string(row, ""))

map_char_to_ascii_code <- function(char) sapply(char, utf8ToInt)

map_to_item_priorities <- function(items) 
    items %>% 
        sapply(map_char_to_ascii_code) %>%
        sapply(function(char_code) if (char_code >= 97) char_code - 96 else char_code - 38)

sum_priorities <- function(items) 
    items %>%
        sapply(map_to_item_priorities) %>%
        unlist %>%
        sum

get_intersecting_parts <- function(rows) 
    sapply(rows, function(parts) Reduce(intersect, parts))

# PART 1

split_rows_in_half <- function(rows) 
    lapply(
        rows,
        function(row) {
            half_length = length(row) %/% 2
            
            list(head(row, half_length), tail(row, half_length))
        }
    )

rows %>%
    split_rows_in_half %>%
    get_intersecting_parts %>%
    sum_priorities %>%
    print

# PART 2

chunk_list <- function(list, chunk_size) split(list, ceiling(seq_along(list) / chunk_size))

rows %>%
    chunk_list(3) %>%
    get_intersecting_parts %>%
    sum_priorities %>%
    print
