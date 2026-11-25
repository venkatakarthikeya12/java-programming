# Task B3 - JDBC Insert Performance

Run the SQL script, add MySQL Connector/J to the classpath, set `DB_URL`, `DB_USER`, and
`DB_PASSWORD`, then run `PassLoaderDemo` or `JdbcInsertBenchmark`.

The benchmark compares `Statement`, `PreparedStatement`, and prepared batches at 1,000,
10,000, and 100,000 records. Auto-commit is measured separately from a transaction.
