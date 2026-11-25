# Task B1 - Instrumented Bus-Pass File Store

## Tests

| Test | Input | Expected |
|---|---|---|
| Existing pass | `P1003` | Five fields returned |
| Missing pass | `P9999` | `null` |
| Damaged line | Wrong field count or amount | Line skipped |
| Empty line | Blank line | Line skipped |

Run `PassStore` for the complete-code result and `LineAndGrowthBenchmark` for measurements.
