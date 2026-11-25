# Task B4 - Migration and Production Decision

## Migration

`PassMigration` reads a CSV file line by line, validates exactly five fields, and creates a batch
transaction for valid records. Invalid records are written to `failed_records.txt`.

## Production Decision

A shared MySQL database is recommended. It provides central search, concurrent counter safety,
constraints and transactions. Text files do not coordinate multiple counters safely. Java
serialization neither scales to 100 campuses/counters nor supports reliable relational search.

Database time and memory should remain bounded by batch size, indexes and transaction limits.
