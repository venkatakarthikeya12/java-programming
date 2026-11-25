# Task 4 - Drawing Pad Event Forensics

## Defects

The original method is misspelled (`mouseclicked`) instead of the `MouseListener` method `mouseClicked`.
It does not override a method, so Java treats it as a new method. Because no `mouseClicked`
override is registered, the click and label update never occur.

## Failing Interaction

After restoring the misspelled method, dots painted with `getGraphics()` disappear after minimize,
restore, resize or repaint because that graphics context is temporary and not reapplied in `paint`.
The corrected implementation stores `Point` objects and redraws every point in `paint`.

Adding `@Override` to `mouseclicked(MouseEvent...)` produces a compile error:
"method does not override or implement a method from a supertype".
