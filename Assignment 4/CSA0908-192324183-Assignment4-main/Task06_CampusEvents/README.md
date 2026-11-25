# Task 6 - Campus Event Registration

## Requirements

- Student, committee and system users interact through a Swing desktop screen.
- Registration requires a non-empty student ID, name and event.
- A student cannot register twice for the same event.
- Database work runs in `SwingWorker` and never blocks the event-dispatch thread.
- Invalid and duplicate registrations show clear dialogs.
- Registrations can be filtered and exported.

## Run with MySQL

1. Execute `sql/schema.sql`.
2. Add MySQL Connector/J to the compiler/runtime classpath.
3. Set `DB_URL`, `DB_USER` and `DB_PASSWORD`, or edit the defaults in `JdbcRegistrationDao`.

If JDBC is unavailable, the application starts with an in-memory data source so the interface remains executable.
