exports.mean = (values) =>
  values.reduce((acc, val) => acc + val, 0) / values.length;
