const lines = `
{[[((<<[[<[<{(()[])([]{})}({[]<>}{<>()})><{<()[]>>{({}())({}{})}>]>{<{<(<><>)<<>()>><{(){}}<{}>>}(<{{}()}><
[{{([{[({((<[{<><>}]><<({}())((){})>{[[]]{{}<>}}>){{({{}<>}<{}[]>){{[]()}(<>)}}<[[<><>](<>[])]{{
[([{<[(<[(((<<{}<>><()<>>>[([]())[{}<>]])[<<<>>[[]<>]>{[<>()]}])[({<{}[]><[]<>>}<(<>[])<()()>>)
{<({{<<[<{{((<<>{}><{}()>)<<()()>>)}}<<({(()[])[()<>]}([[]<>]))[[<(){}>[[]{}]]<<<>[]>>]>[<[({}
{[[[([[<(<<(([[]()]<()[]>)[<[][]>{<>}])<({<><>}<{}{}>)[<<>{}>{[][]}]>><({[<><>][<>()]}[((){}]<[][]>]){([<
[{<[([{{<[<{<{[]()}(()[])>[[[]<>]<[]{}>]}{[{(){}}](<<>[]>{(){}})}>]>}([{[[{([]())<(){}>}<[<>[]
{(((<{[<{[{<{{[]{}}}{(<>[])<<><>>}>}{({(()())({}[])}([[]{}][[][]]))<<<(){}>[<>{}]><[<>{}]<()[]>
<(((<<({[<([{([][])<[][]>}[{(){}}{<><>}]]<<{()()}<<><>>>>)><[[({{}()}(()[]))[([]())<<>()>]]]{{{({}())[{}<
[(({[[[[<(([{<()()>[<>[]]}({()<>}<<>{}>)]){{[<[]()>[[]<>]][(<>[])<{}<>>)}})>]]]]})[({<[{({[[<{<>}(()
[{<(<<{<<[{<<<<>[]>([][])>>([{[]{}}(()[])]([{}<>](()[])))}]><[({<[{}()]{()[]})(<{}()>{[]{}})}{{[{}()][{}[]]}}
[<([[<(<{[(([{{}[]}<()[]>][<(){}>{{}[]}])[(<{}{}><<>[]>)<([]()>>])][{{{({}[])([][])}<[<>()]{[]<>}>}{<(()<>)[{
{({([{<<[[{<[(<><>)<[]{}>]<<()<>>>>[[({}<>)[{}()]]{[{}<>][[][]]}])[({{()[]}<[][]>}{<<><>><{}[]>})
({<<{[[{([[[<{<>()}[()[]]>{({}())<[]{}>}]<(((){})({}{})){{[]()}({}{})}>]((<({}[])(()())><([]{})(<>(
{{{(({{{<{<{(<<>[]>[<>()])}[[<{}()>]}>([[<<>()><{}[]>]]{{{<>[]}<[]<>>}{(<>[])[<><>]}})}>}{{(<[{<
(<{[{<({[[{[(<<>[]>{(){})){[<><>]}][{{{}{}}(<>[])}{[[]<>][<>()]}]}]{[{{(<>[])<<>()>}}[{([]<>){{}[]}}(<<>
{<([<<{((([([<{}<>>[[]()]]{(()[])(()<>)})<[[()<>][{}{}]]>]({{[[]()](<>{})}{(()[])}}<{<[]{}>{()<>}}[([]()){{}
(({<<[[({[[<((()()))>({<[]()>{[]())}{[<>()]{(){}}})]<<{[[]<>]<()[]>}({{}[]}[[]{}])>({{{}<>}[<
[{[(<<<{({<[<{<>()}>[[(){}]{[]()}]][(<<>[]>{<><>})(([]())[[]{}])]>[({{<>{}}}<<(){}>[<>[]}>)({{{
[{((<<{<[[{{[([]{})[[]()]]<([][]){()}>}{<{<>{}}<()[]>><<<><>>[[][]]>}}[([[<>][<>[]]))(<({}
((([[[<[({{[{{[]()}}]<<<<>[])[{}()]>>}<[[{{}()}{[]()}]<{<>[]}{()<>}>][[[<>{}][{}[]]]]>}{([(<(){}>([]<>)
(<{[[({[<((<(<<>[]>[()[]]){<<>[]><{}{}>}>))>]}([[[[[<(()<>)>(((){}){<>()})]<<<[]()>{[]<>}>[[[]
<[<[([{<{({<[({}<>)<{}<>>]{<<>>}><[<<>[]>{{}<>}]{<(){}><{}{}>}>}){[([<()<>>](<[]<>>))<{({}{})<<>()>}]](
[[(([<{{({<{{{<><>}(()())}(<{}<>>)}{<{[]()}{()()}>[[()()][{}()]]}>})(<{<[<{}()>([]<>)]{([]<>)}>}>{[<({()[]}<
(<(({(<[[(((<<<>{}>(<>[])>((()<>)<{}[]>))){[(<<>[]>{[][]})]{[{(){}}[[][]]]{<[][]>}}})]([{((
[<{{[<(([<[<([<>()]{<>})([{}{}][{}[]>)>(<[{}[]]{[][]}>[{(){}}({}{})])]>([{[[{}[]]][<<>{}>[()<>]]}{[<{}
({[[{{({[[<<({{}}{[]()})<<<><>>[(){}]>>[<<{}{}>{<>[]}>]>]<[[{[[]]{<>{}}}]{{<<>[]><[]()>}{[{}<>]<()<>>}
(<<((<[([({[[{{}()}<()<>>]({{}<>>([]()))]<{(()())((){})}{[[]<>]{{}{}}}>}<[[[<>]<<><>>]<[(){}][<>()]>]
<(({((<(({[{{{{}()}(<>[])}}]{<<[[]{}]([]{})><(()[])[(){}]>>[{(<>()){[]{}}}<<[]<>>[()()]>]}}({[[<
<{<[[{<{[<{<((<>[])[<>{}])><({{}{}}[()<>])>}([{({}{}){{}[]}}{{<>()}[<>()]}])>{{[[({}[])<{}[]>]
[[<{(((<[<{<[<[][]><[][]>][([][])]>}({<[{}()]<[]<>>>})>]([<[[([]<>)]<{()<>>>]<{(()())}>>]<[
<[({([([([<{<<[][]>{{}<>}><<()><<>()>>}{{([]<>){[]{}}}}>[[[[[]][<>[]]]]]]{[{<(()<>)<{}()>>}
[<[{{({[<<[<(([][]){[]{}})((<>())<()[]>)>({[{}<>](<><>)}<<<><>>[{}()]>)][{<{<>{}}[{}()]>[[{}[]>[(){}]]}(((
{<{({<[<[({{<{<><>}{{}{}}>[{<><>}<<>{}>]}{{[()<>](<>{})}{<[][]><<>{}>}}})<({(([]()))<<[]{}>{{
([({<[<{<(<[(<{}{}>{{}<>})[({}[])(<>{})]]([[[][]][{}]])><(([(){}](<>{}))){(<[]<>>[<>{}])}>)][({{((()[])((
(<{[(<<({{{{[<<><>>{<>[]}]}{{<[][]>[{}<>]}<[{}<>]({}())>}}[{({()()}({}()))[[<>[]][<>[]]]}{<<[]()><<>
(({((<(<{{[{(([]<>)[{}()])((()[]))}[<([]<>){()<>}>]])[<{({{}[]}({}<>))<[<>()](<>())>}>{<{[{}[]](<>[])}[{[][]
<[<[<<<<([{{{(()())<[]()>}{[[]<>][<><>]}}(<{()[]}<{}()>><([]{})[{}{}]>)}[[<{[]{}}([]<>)>][[[<><>]<<>{}>]({{}
<<{((({<[[<[<{<><>}<{}{}>>]>]]{{(<([<>()]{{}})><{{[]{}}{()[]}}[<[]()>[[]]]>)<{{(()[])([]())}[[()[]][
[{<<<{{{(<<(({<>[]}[{}()])<[[]{}]>)[[[{}[]]{{}()}][([][]){{}[]}]]>>)}}}>>><(((({{<[{([[]<>]{[][]})[<()()>[{}{
((<[([<[([{{[[{}{}][(){}]](([][])(<><>)>}{<(<>{})(<>{})>}}[(<[()<>]><[{}()](<>())>)<{[[]<>]<(){}
({{[<<([[<([[<<><>>[[]]]{(<>{}){()<>}}]([{(){}}[(){}]]([[]<>]<{}<>>)))[{([{}[]][{}[]])[(()<>)(<>)]}[
({[[{<[<[[[<[(<>[])<()>][([]())]><[([]<>)<<>{}>]>]{{({()<>}{(){}})[{<><>}(()[])]}}]({[<{{}()}<()()>>[
{[<((([[{({[([[][]][[]<>])([[]<>][<>()])]{[[()[]](<><>)]}}{{(<[]()><{}{}>)[<()[]><[][]>)}<{{<><>}{<>[]}
(([{([[{[([[((<><>)<<>()>)<[[]]>]<([()]<<>{}>)[({}())<<>>]>]((<<<>()>{()()]>{{{}}})[{<<>()>({}())}
[<<[[<<<(<([[{()[]>[[]()]]([(){}](<><>))]{<{[]{}}((){})>{{[]}[{}]}})>{(<(([][]))({()()})>(<([]<>)({}<>)>)
<(<(<({({(<<([[]<>]{{}<>})({[][]}[()()])>>{[<<(){}>[{}<>]>{({}{})(<>())}]{<<<>[]><<>()>><{<><>}{()<>}>}})<
<{<<{[<[([<<[[{}](<>{})]{<(){}>{()()}}><{([]<>)}]>]<{{<{(){}}<<>{}>>}}{{<[[][]][<>{}]><(())({}{})>}[{[
({(<<<<([<{{{([]{}){(){}}}[[(){}]])[<{<>()}[{}<>]>{<[]>}]}>{[{[[{}{}][{}[]]](<{}[]><()()>)}
(([{<<[{{<[({<[]>[<>[])}({{}()}{()()}))](<((<><>)[{}[]])<<[]<>>((){})>>({<{}()>}))>{[<([{}<>]<[]<>>)[((){
{{[[<{[[<<{(<<<>()>{{}]>(<{}[]>([][])))<{[{}{}]{(){}}}([[]{}])>}[{<({}[]){()()}>}[[<()[]>{()<>}]
{([<<<<[(((<(<{}()>)((()[]){[][]})>{({[]}(()()))[{[][]}[{}{}]]})(<{[()()]([][])}><{(()[])<()<>>}<{(
((([<<[{{(<(<{[]}<()<>>>(([]())({}())))>)}<[({<{<>()}(<>{})>])]>}]{[[{<{[[<>[]]<<>{}>]{[{}()][{
(({{[({<<({{[{<><>}(())][[()]<{}{}>]}{{[()[]]{{}{}}}[<[][]>{()()}]}}{{(<[]()><[][]>)([[][]]({}<>
([{{([((([({{<[][]>[{}()]}{([]<>)<[]()>}}<{[()()]{<>()}}>)<<([<><>](()()))[(()())<(){}>]>>]){[
[((({([(<{[({{<>()}{[]}}{([]<>](<>[])}){[(()[])[[]]][([][])[<>]]}]{<<{[]()}(<>{})><{{}[]}[<>(
<{{(<<{<((([[{<><>>]<[{}<>]((){})>]{[[()<>]{{}()}]<{[]}<<>()>>})<<[[[][]]{[][]}]{{{}[]}<()
<({(<({({[(<[[<>{}]<<>[]>]>(<[[]()][()()]>{<(){}>[[][]]>)){<{{{}}}<({})<{}()>>>{{{()<>}<[]<>>}(<()
({<<([<<<[{<([[][]]{[]<>})<<<>{}>((){})>>}{{<(<>[])[<>()]><([][])(()())>}[[{[]<>}{()()}]{({}{}){{}{}}}]}]{{(
{{{<{[({[[<[([()[]])<<{}[]>{()[]}>]>]((<({<>()}<[]<>>)[{<>[]}]>[{[()<>]<<>>}<{()()}{{}[]}>
({<[[<<([<{{[<[][]>{{}{}}]{[[]()][[]()]}}<{{{}{}}<{}[]}}[{()<>}(()[])]>}[([(<>())]){(<[]()><<>()>)}]
{<{((<<{<({<<{{}<>}{<>[]}>[{[]<>}{()}]>([(<>[]){{}{}}][[<>{}](<><>)])}[(<[[][]]({}[])><<<>{}]>){<[{}[]]<
(([<[<{<({<[{[()()]<{}<>>}{<<><>>{()<>}}]{{(()[])[[]<>]}<({}{})<{}[]>>}>({({<><>}[{}<>])(<()<>>)}[(<{}(
[{<<<<[[{{<{([[]()]<[]<>>)}>{<({()()}<{}[]>)>[{{[][]}[{}<>]}({<><>}(<>()))]}}{[[{<<>{}>><([]())(()
{([(<{{[{{[((<[]()>[{}<>])(<{}<>>[(){}>))]([<<[]<>>[<>[]]>{{[][]}{{}{}}}]{<({}{}){<>[]}>([<>
[<{[{(((({<[{{{}{}}[[]<>]}[{[]<>}<(){}>]]<{({}())[()<>]}<<[]()>([][])>>>([<<()[]><()<>>>]<([()<>][<>[]
{<{[{<<{<<{(((<><>)[[]()]){({})<[]()>})[[<<>{}>(<>())]<(()<>){[]()}>]}{{<({}[]){(){}}>{[()<>]({}{})}}}>>[[
(({<<{{<[<<<<[[]<>]<[]{}>>[((){})({}[])]>[<(<>())([]{})><[{}<>][[]{}]>]>((([<>[]]<<>[]>)([<><>][[][]]}))>][(<
[[{<{{{(([{({([]<>)[<>[]]}{[[]<>][()<>]}){{<<>()>([]{})}[{<>()}(<><>)]}>(([<[]<>>[()()]]{[[]{}]{{}<>}}){(
((<[[([{<<{{{([]{}){<>{}}}({<><>}{{}{}})}<<<[]{}>(<>[])>([()<>])>}{<([(){}]({}[]))[({}<>)((){})]>[[<<
[[{<[((<[<{(([[]()][[][]]))}>]><[{([(<[][]>{(){}})<{{}[]}[{}{}]>])((<<{}()>[[][]]>{{()[]}((){})
{{{[(({{<<{({<{}<>><[]{}>}[<{}<>>({}[])])}>>{{{((<[]()>[(){}]))({<[][]>[<>[]]}[[<><>](()[])
<<((<({{{{<({({}()){<>()}}<[<>()]>){([[]<>]([]<>))[[()<>]]}>]{[({[{}()]{[][]}})({[[]()]{()()}}[(()[
[{<{{{[<[<<{{(()())({}{})}(<<><>>[[]<>])}><[<[<><>]<{}<>>>([[]()}{[][]})]>>[[{<[[]<>]<<>{}>><<(){}>{[]
<<[<<[[(([({[{()[]}{<>}]<(()())[[]()]>}<((<><>)<[]()>]{(<><>){(){}}}>)])({[<<([]{})<{}[]>>[[{}[]](<>
<[{{{<<<{<{({<{}[]>[<><>]})}<{([[]<>]([]<>))<[()<>]({}{})>}{{[{}()]({}<>)}}>>}<<((<{<><>}[()<>]>
({([{(((((([[<[]{}>((){})]<[()<>]<{}()>)]))[(<<([]()){[]<>}>[({}[])<{}{}>]><[[<>]{{}()}]<(()<>)[{}()]>>)]))<<
<[[<((([<[(((([][]){{}<>})(<<>()>{<>()}))<[[(){}]<{}()>]<{()<>}[<>]>>)<([<[]{}>[{}]]){<{{}{}}>(<<>()>{[]<>})}
({({(<(({([{{<<>()>}[<{}{}><{}{}>]}[(<[][]>[()()]){([]{})[<>[]]}]])})){<{(<(<{<>()}>)[[{<>()]<()[]
(<<<{{{{({[<{<()>[[]()]}<[<>]>>][{([()<>](()())){(<>())[<>[]]}}<{{()()}({}<>)}{[<><>]}>]}(<{[(()<>){()[]
<{[([({{[{({{[<>()]<<>{}>}([[]()]([]<>))}<<{<><>}>[([]<>)([]{})]>)(<[{[]<>}(<>[])]>[([()[]][{}()])[[{
[<<<(({<{{[<<([]<>)((){})>{(()()){<>{}}}>{[{[]()}<<>{}>]}]<<[{()<>}((){})}>[[(<>{})]]>}<{[<{<>{}}(())
[([({([<<{<[<<{}()>{<><>}>{<()[]>}]>([([[]()]([]{}))([[][]]{[]()})])}<{{(<[]{}>{()<>})<(<><>)<[
[({(<<{(<[[{<{<><>}<()[]>>}[<[[]{}][[][]]>(<<><>>{<>{}})]]<{{[<>]<()<>>}[([][])[<>()]]}<((()
<([[{(({[[<<((()[]){<>()})[<<><>]{()()}]>[[(()[]){{}<>}]{[()[]](()())}]>[(<[{}{}]<<>[]>><({}())>)({[()]})]]]}
[(<<<{(({({<[((){}}({}())]<<{}{}>((){})>>}<{<([]{})>({<>}<{}[]>)}[<<[]<>>[()()]><(()())[<>()]>]>){[{(
{<[[[{{<[{{{<<(){}>{[]<>}>[<(){}>({}[])]}<(<<>{}>)({()()})>}<([([][])<{}<>>]<{()[]}(<>())>)[([()<
(<<(<{[[(<{<<[()()]([]<>)>{<{}{}>([]{})}>}[([(<>{})[()()]][([]())[<>[]]]){([{}<>]{()[]})(<()[]>)}]>)]]
{<([[(<((<({{<(){}>([])}<[{}()][()<>]>}[[(()())]<[<>[]]((){})>])>([(<<[]><[][]>><({}{})[<>[]]>)])){{({[[[]<
[[[<<{([[{[(<<{}<>>>)]}<<<{{<>()}<<><>>}{[{}[]]{<>{}}}>>>][<{{{<[][]>[<>()]}[([]<>){{}<>}]}<({[](
({{({<<(<(([{{{}[]}([]<>)}{[()[]]([]<>)}]([{{}[]}<{}()>]{{{}<>}{<>[]}}))(<{<<>{}>}[{<>()}[<>[]]]>{(<()()>({}
{{<<{{({{[{<(<{}<>>){{{}<>}<()[]>}>}](<[[<{}[]><[][]>][(()())([]())]]({{{}{}}<(){}>}{{(){}
[{[<<{{[(<([[[{}{}]<()[]>]{((){})[{}()]}][(<[][]>{()})[<[]()>[[]{}]]>)>(<[<[[]()]<<>[]>>][[({}[])<
[[<<[{<<{{{<[{<><>}][{()[]}]>[(<<>{}><<>()))[[<><>]([]<>)]]}}}>[{<[{<([]{}){[]<>}>({(){}}{[]{}})}]([[(
[<([[<{<<([([(()())(()())])((((){})[[]{}]))][{[[[]()]]}]))>}>](<{({{(<{({}[]){[]{}}}<{{}[]}[{}[]]>
({<<{{<{(({[<([]())({}[])>(<<>[]><[]<>>)]{[([]<>){{}<>}][{{}()}<[]<>>]}})([({<[]()>{{}}]{<
<{{{{[<{[<([[({}{})<<>()>]{{()()}[{}{}]}][{[<><>](<>())}[[<><>][{}{}]]])(<({<>})>[<[{}{}]{
{[[[([[[{(<<{(<>[])[{}[]]}{<<>{}>(<>())}>({<()()>[{}[]]}(<{}[]>[[]<>]))>)}]{[{({(<{}{}>)<(()
{[(<{{[(<((<([<><>]{()[]})<{[]<>}(()<>)>><[(()())]<[()()][<>{}]>>))>)(<([[({[]<>}{()[]})({[]()}[{}()])]<<{
[{{<[{[[<<<<{(<>()){()()}}[({}())((){})]>((([]{})))><<[(<>[]){{}[]}][{{}{}}(()<>)]><[{[]{}}[[]{
[[<{<(<[([<[(<<><>>[{}<>])[<()()>{<>[]}]]{({{}()}{<>[]})({()<>}[{}<>])}>(([[{}{}]<(){}>]{{()()}
({<([[{{<<{<<[[]{}]((){})>[{<>()}[[]()]]>[[[[]()][[]{}]][<(){}>(<>())]]}(<{[{}()]{{}()}}{{{}
{{<<<(<{<[<[({{}{})<{}<>>)<{<><>}<()[]>>]{<(()())[{}]>}><<([<>{}]((){}))<[()[]]<<>()>>>(<{<>()}[<><>]>
<([{{{((((<[[<{}[]>{<><>}]<[()]<<>{}>>]<<<[][]>[[]<>]>[(()[]){()[]}]>>){[[([<>{}][<>{}])(([]
([(({{<<(({(({()}{{}()>))([(<>{})]<({}[]){<>()}>)}{<{{()()}<()<>>}<<[][]>(<>())>>})([[{[[]()]<<>
{[<(<{<({{({((<><>))[[[]{}]{[][]}]}<({{}<>}[[]<>]]<(()<>)<{}()>>>)}})[{{([{(<><>)[<>{}]}[[{}()]]]{[<
[<(<<[(({([[<<[][]><{}()>><{<><>>[<>[]]>]]((<(<>[]){<>{}}>{{()[]}(<>[])})))<((((()[])[()<>])))
`;

exports.lines = lines.split("\n").filter(Boolean);