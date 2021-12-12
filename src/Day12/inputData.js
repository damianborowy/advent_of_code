const passagesString = `
kc-qy
qy-FN
kc-ZP
end-FN
li-ZP
yc-start
end-qy
yc-ZP
wx-ZP
qy-li
yc-li
yc-wx
kc-FN
FN-li
li-wx
kc-wx
ZP-start
li-kc
qy-nv
ZP-qy
nv-xr
wx-start
end-nv
kc-nv
nv-XQ
`;

exports.inputPassages = passagesString
  .split("\n")
  .filter(Boolean)
  .map((row) => row.split("-"))
  .reduce((acc, [from, to]) => {
    if (!acc[from]) acc[from] = [];
    if (!acc[to]) acc[to] = [];

    if (to !== "start") acc[from].push(to);
    if (from !== "start") acc[to].push(from);

    return acc;
  }, {});
