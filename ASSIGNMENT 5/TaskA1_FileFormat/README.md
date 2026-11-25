# Task A1 - Bus-Pass File Format

## Delimited text layout

One record per line, five fields in this order:

```text
passId|registerNumber|name|routeNumber|boardingStop|validityDate|amountPaid
```

Use `|` as the delimiter. The name is escaped: a literal `|` becomes `\|`, and `\` becomes `\\`.
The validity date can use ISO `YYYY-MM-DD`. Amount uses decimal text.

## Binary layout with DataOutputStream

```text
UTF passId
UTF registerNumber
UTF name
UTF routeNumber
UTF boardingStop
long validityEpochDay
double amountPaid
```

`writeUTF()` includes its own UTF length, so separate fixed field lengths are not required.

## Pseudocode

```text
ALGORITHM ADD_PASS(file, pass)
  open BufferedWriter(file, APPEND)
  line <- ENCODE(pass)
  write line
  write newline
  flush and close

ALGORITHM FIND_PASS(file, passId)
  open BufferedReader(file, READ)
  for each line:
    fields <- SPLIT_ESCAPED(line, "|")
    if valid(field count) AND fields[0] = passId:
      return fields
  return NOT_FOUND

ALGORITHM LIST_ROUTE(file, route)
  open BufferedReader(file, READ)
  for each line:
    fields <- SPLIT_ESCAPED(line, "|")
    if valid AND fields[3] = route:
      append fields
  return records
```

Use `BufferedReader`/`BufferedWriter` because text processing needs line iteration and
buffered disk access. `FileReader`/`FileWriter` would require extra handling for buffering.
For 8,000 records, one pass is O(n) for ID search and route listing.
