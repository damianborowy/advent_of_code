uses
    SysUtils,
    StrUtils
;

var
  input: string;
  inputArray: array of string;
  i: Integer;
  path: string;
  isListing: boolean;
  currentListingSize: Integer;

procedure ReadStdin;
begin
  SetLength(inputArray, 0);

  while not Eof do begin
    Readln(input);
    
    if input = '' then begin break end;
    
    SetLength(inputArray, Length(inputArray) + 1);
    inputArray[High(inputArray)] := input;
  end;
end;

begin
  ReadStdin();
  
  for i := 0 to High(inputArray) do
    WriteLn(inputArray[i].Split(' '));
end.