# Task 1 - Fee Payment Window Repair

## Deficiencies

1. UI and database/payment logic are mixed, making the class difficult to test and reuse.
2. `==` is used for action strings; it compares object identity, not content.
3. Empty/non-numeric/negative amounts and invalid register numbers are not validated.
4. Database work and `Thread.sleep(3000)` block the event-dispatch thread, freezing the window.
5. The user receives only console output and no status or receipt.
6. Receipt does not perform a meaningful retrieval action.
7. SQL is constructed by concatenation, allowing SQL injection.
8. Database and statement resources are not shown being closed.
9. `SQLException` and `InterruptedException` are checked exceptions requiring explicit handling.
10. Window close behavior is not handled explicitly.

## Repair

`FeeWindow` only constructs controls and dispatches button events. `FeeService` validates input and simulates a safely separated payment operation.
