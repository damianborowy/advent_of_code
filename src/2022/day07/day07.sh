top_dir=$(pwd)
base_dir="$top_dir/fs"

rm -rf "$base_dir"
mkdir "$base_dir"
cd "$base_dir"

ls_output=0
while read -r line
do
  if [[ $ls_output -eq 1 ]]
  then
    if [[ $line =~ ^\$.*$ ]]
    then
        ls_output=0
    else
        if [[ $line =~ ^dir.([a-z]*)$ ]]
        then
            mkdir -p "${BASH_REMATCH[1]}"
        elif [[ $line =~ ^([0-9]+).(.+)$ ]]
        then
            touch "${BASH_REMATCH[1]}_${BASH_REMATCH[2]}"
        fi
    fi
  fi

  if [[ $ls_output -eq 0 ]]
  then
    if [[ $line =~ ^\$.cd.(.*)$ ]]
    then
        cd ./"${BASH_REMATCH[1]}"
    elif [[ $line =~ ^\$.*ls$ ]]
    then
        ls_output=1
    fi
  fi
done < ../input.txt

get_directory_size () {
    directory_sum=0
    
    for file in $(find $1 -type f)
    do
        if [ -f $file ]
        then
            if [[ $file =~ ^.*/([0-9]+).*$ ]]
            then
                directory_sum=$((directory_sum + "${BASH_REMATCH[1]}"))
            fi
        fi
    done
}

# PART 1

sum=0
for directory in $(find $base_dir -type d)
do
    get_directory_size $directory

    if [[ $directory_sum -le 100000 ]]
    then
        sum=$((sum + directory_sum))
    fi
done
echo $sum

# PART 2

get_directory_size $base_dir
taken_space="$directory_sum"

free_space=$((70000000 - taken_space))
space_needed=$((30000000 - free_space))

lowest_sufficient_dir_size=70000000
for directory in $(find $base_dir -type d)
do
    get_directory_size $directory

    if [[ $directory_sum -ge $space_needed ]]
    then
        lowest_sufficient_dir_size="$directory_sum"
    fi
done
echo $lowest_sufficient_dir_size

rm -rf "$base_dir"
