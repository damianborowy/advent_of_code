# use strict;
use warnings;
use feature qw(switch);

# Change $length to 2 for part1
my $length = 10;

my $filename = 'input.txt';
my @knots;
my %visited_places = ();

for my $knot_index (0..$length - 1) {
  $knots[$knot_index] = [0, 0];
}

sub move_head 
{      
  given($_[0]) {
    when("U") { @{$knots[0]}[0]++; }
    when("D") { @{$knots[0]}[0]--; }
    when("R") { @{$knots[0]}[1]++; }
    when("L") { @{$knots[0]}[1]--; }
  }
}

sub sign
{
  given($_[0]) {
    when(0) { return(0); }
    when($_ > 0) { return(1); }
    when($_ < 0) { return(-1); }
  }
}

sub is_tail_close_to_next_element
{
  @target = @{$knots[$_[0]]};
  @source = @{$knots[$_[0] - 1]};

  return (abs($source[0] - $target[0]) <= 1 && abs($source[1] - $target[1]) <= 1);
}

sub get_visited_places_count 
{
  @keys = keys %visited_places;
  $size = @keys;

  return $size;
}

open(FH, '<', $filename) or die $!;
while(<FH>){
  my @instructions = split ' ', $_;

  for (1..$instructions[1]) {
    move_head($instructions[0]);

    for my $tail_index (1..$length - 1) {
      @previous_knot = @{$knots[$tail_index - 1]};

      if (!is_tail_close_to_next_element($tail_index)) {
        @{$knots[$tail_index]}[0] += sign($previous_knot[0] - @{$knots[$tail_index]}[0]);
        @{$knots[$tail_index]}[1] += sign($previous_knot[1] - @{$knots[$tail_index]}[1]);
      }

      if ($tail_index == $length - 1) {
        $knot_x = @{$knots[$tail_index]}[0];
        $knot_y = @{$knots[$tail_index]}[1];
        $visited_places{"$knot_x - $knot_y"} = ();
      }
    }
  }
}
close(FH);

print get_visited_places_count();