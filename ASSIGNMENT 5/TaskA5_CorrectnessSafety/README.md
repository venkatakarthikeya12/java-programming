# Task A5 - Transaction and Injection Safety

## Transaction pseudocode

```text
setAutoCommit(false)
begin
  update hostel_wallet -= 500
  update canteen_wallet += 500
if both succeed:
  commit
else:
  rollback
finally:
  restore old autoCommit
```

PreparedStatement prevents the SQL text from changing. The login query parameter is sent as data.

An unsafe input `id=' OR '1'='1' --` can alter the SQL predicate and comment out the password check.
