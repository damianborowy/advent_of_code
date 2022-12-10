# use strict;
use warnings;
use feature qw(switch);

my $filename = 'input.txt';
open(FH, '<', $filename) or die $!;

my @headposition = (0, 0);
my @tailposition = (0, 0);
my %visited_places = ();

sub move_head 
{      
    given($_[0]) {
      when("U") { $headposition[0]++; }
      when("D") { $headposition[0]--; }
      when("R") { $headposition[1]++; }
      when("L") { $headposition[1]--; }
    }
}

sub is_tail_close_to_head
{
  return (abs($tailposition[0] - $headposition[0]) <= 1 && abs($tailposition[1] - $headposition[1]) <= 1);
}

sub get_visited_places_count 
{
  @keys = sort keys %visited_places;
  $size = @keys;

  return $size;
}

while(<FH>){
  my @instructions = split ' ', $_;

  for (1..$instructions[1]) {
    @previous_head_position = ($headposition[0], $headposition[1]);

    move_head($instructions[0]);

    if (!is_tail_close_to_head()) {
      @tailposition = ($previous_head_position[0], $previous_head_position[1]);
    }

    $visited_places{"$tailposition[0] - $tailposition[1]"} = get_visited_places_count;
  }
}
close(FH);

print get_visited_places_count();