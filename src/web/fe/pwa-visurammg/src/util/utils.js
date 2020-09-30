
export const uniqueElementsBy = (arr, fn) => {
  return arr.reduce((acc, v) => {
    if (!acc.some(x => fn(v, x))) acc.push(v)
    return acc
  }, [])
}

export const groupBy = (arr, fn) => {
  return arr.reduce((out, el) => {
    const key = fn(el)
    out[key] = (out[key] || []).concat([el])
    return out
  }, {})
}
