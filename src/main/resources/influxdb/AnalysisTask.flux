import "date"

option task = {
    name: "Temperature analysis",
    cron: "*/10 * * * * *"
}

indoor = from(bucket: "temperature")
  |> range(start: -10s)
  |> filter(fn: (r) => r["_measurement"] == "indoor")
  |> filter(fn: (r) => r["_field"] == "temp")
  |> mean()

outdoor = from(bucket: "temperature")
  |> range(start: -10s)
  |> filter(fn: (r) => r["_measurement"] == "outdoor")
  |> filter(fn: (r) => r["_field"] == "temp")
  |> mean()

join(tables: {i:indoor, o:outdoor}, on: ["address"])
  |> map(fn: (r) => ({
      _time: date.sub(d: 10s, from: now()),
      _measurement: "analysis",
      _field: "analysis",
      _value: r["_value_i"] / r["_value_o"],
      address: r["address"]
    }))
  |> to(bucket: "temperature")
