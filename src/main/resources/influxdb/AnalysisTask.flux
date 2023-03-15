import "date"

measured = from(bucket: "temperature")
  |> range(start: -1m)
  |> filter(fn: (r) => r["_measurement"] == "measured")
  |> filter(fn: (r) => r["_field"] == "temp")
  |> mean()

prognosed = from(bucket: "temperature")
  |> range(start: -1m)
  |> filter(fn: (r) => r["_measurement"] == "prognosed")
  |> filter(fn: (r) => r["_field"] == "temp")
  |> mean()

previousMinuteRounded = date.sub(d: 1m, from: date.truncate(t: now(), unit: 1m))

join(tables: {m:measured, p:prognosed}, on: ["chalet"])
  |> map(fn: (r) => ({
      _time: previousMinuteRounded,
      _measurement: "analysis",
      _field: "analysis",
      _value: r["_value_p"] / r["_value_m"],
      chalet: r["chalet"]
    }))
  |> to(bucket: "temperature")
