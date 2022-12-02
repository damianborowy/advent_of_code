exports.mean = (values) =>
  values.reduce((acc, val) => acc + val, 0) / values.length;

exports.isUpperCase = (string) => string === string?.toUpperCase();

exports.chunkArray = (array) => {
  const chunks = [];

  for (let i = 0; i < array.length - 1; i++) {
    chunks.push([array[i], array[i + 1]]);
  }

  return chunks;
};

exports.countElementOccurrences = (array) =>
  array.reduce((acc, element) => {
    acc[element] = (acc[element] || 0) + 1;
    return acc;
  }, {});
