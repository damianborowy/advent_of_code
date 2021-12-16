import { inputPacket } from "./inputData";

const binaryDic = {
  0: "0000",
  1: "0001",
  2: "0010",
  3: "0011",
  4: "0100",
  5: "0101",
  6: "0110",
  7: "0111",
  8: "1000",
  9: "1001",
  A: "1010",
  B: "1011",
  C: "1100",
  D: "1101",
  E: "1110",
  F: "1111",
};

const typeDecoder = {
  0: (a: number, b: number) => a + b,
  1: (a: number, b: number) => a * b,
  2: (a: number, b: number) => (a < b ? a : b),
  3: (a: number, b: number) => (a > b ? a : b),
  5: (a: number, b: number) => (a > b ? 1 : 0),
  6: (a: number, b: number) => (a < b ? 1 : 0),
  7: (a: number, b: number) => (a === b ? 1 : 0),
};

type DecodedPacket = {
  data?: number;
  consumed: number;
  version: number;
  versionTotal: number;
  typeId: number;
};

function decodePacket(packet: string): DecodedPacket {
  const version = parseInt(packet.slice(0, 3), 2);
  const id = parseInt(packet.slice(3, 6), 2);
  const data = packet.slice(6);

  const decodedPacket: DecodedPacket = {
    version: version,
    versionTotal: version,
    typeId: id,
    consumed: 6,
  };

  return decodedPacket;
}

const decodedPacket = decodePacket(inputPacket);
console.log(decodedPacket);
