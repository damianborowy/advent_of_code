# use strict;
use warnings;
use feature qw(switch);
use Data::Dump qw(dump);

my $filename = 'input.txt';
my $length = 10;
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

  # print $source[0], ", ", $target[0], ", ", abs($source[0] - $target[0]), abs($source[1] - $target[1]) <= 1, "\n";

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
      @knot = @{$knots[$tail_index]};
      @previous_knot = @{$knots[$tail_index - 1]};

      if (!is_tail_close_to_next_element($tail_index)) {
        $knot[0] += sign($previous_knot[0] - $knot[0]);
        $knot[1] += sign($previous_knot[1] - $knot[1]);

        dump(@knot);
      }

      if ($tail_index == $length - 1) {
        $visited_places{"$knot[0] - $knot[1]"} = ();
      }
    }
  }
}
close(FH);

print get_visited_places_count();