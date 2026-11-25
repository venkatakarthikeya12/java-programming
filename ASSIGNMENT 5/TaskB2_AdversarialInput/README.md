# Task B2 - Adversarial Input

## SQL Payload

```text
id = ' OR '1'='1' --
password = anything
```

The unsafe SQL becomes a tautology and can comment out the password predicate.
`PreparedStatement` sends both values as data and rejects the payload.

The damaged pass cases are a wrong field count, a non-numeric amount and an empty line.
The file store skips each record and continues searching.
