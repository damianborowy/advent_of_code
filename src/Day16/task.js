const { inputPacket } = require("./inputData");

let versionsAdded = 0;

function encodeHexToBin(hex) {
  return hex
    .split("")
    .map((h) => parseInt(h, 16).toString(2).padStart(4, "0"))
    .join("");
}

function operation(id, params) {
  switch (id) {
    case 0:
      return params.reduce((acc, { val }) => acc + val, 0);
    case 1:
      return params.reduce((acc, { val }) => acc * val, 1);
    case 2:
      return Math.min(...params.map((param) => param.val));
    case 3:
      return Math.max(...params.map((p) => p.val));
    case 5:
      return params[0].val > params[1].val ? 1 : 0;
    case 6:
      return params[0].val < params[1].val ? 1 : 0;
    case 7:
      return params[0].val == params[1].val ? 1 : 0;
    default:
      return 0;
  }
}

function decodePacket(packet) {
  const version = parseInt(packet.slice(0, 3), 2);
  const typeId = parseInt(packet.slice(3, 6), 2);

  versionsAdded += version;

  if (typeId == 4) {
    let pos = 6;
    let substring = "";

    while (packet[pos] === "1") {
      substring += packet.slice(pos + 1, pos + 5);
      pos += 5;
    }

    substring += packet.slice(pos + 1, pos + 5);

    return { val: parseInt(substring, 2), len: pos + 5 };
  }

  const mode = parseInt(packet.slice(6, 7), 2);
  const subPackets = [];
  let reachedLen = 0;

  if (mode == 0) {
    const length = parseInt(packet.slice(7, 22), 2);

    while (reachedLen < length) {
      const res = decodePacket(packet.slice(22 + reachedLen));
      reachedLen += res.len;
      subPackets.push(res);
    }

    return { val: operation(typeId, subPackets), len: 22 + reachedLen };
  } else {
    const length = parseInt(packet.slice(7, 18), 2);

    while (subPackets.length < length) {
      const res = decodePacket(packet.slice(18 + reachedLen));
      reachedLen += res.len;
      subPackets.push(res);
    }

    return { val: operation(typeId, subPackets), len: 18 + reachedLen };
  }
}

const decodedPacket = decodePacket(encodeHexToBin(inputPacket));

// task 1 - find lowest path cost

console.log("\n----- TASK 1 -----\n");
console.log(versionsAdded);

// task 2 - find lowest path cost, but for a grid that's five times larger

console.log("\n----- TASK 2 -----\n");
console.log(decodedPacket.val);
