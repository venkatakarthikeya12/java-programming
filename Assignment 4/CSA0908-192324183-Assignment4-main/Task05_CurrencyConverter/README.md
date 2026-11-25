# Task 5 - Currency Converter Event-Handling Styles

The four implementations support both the Convert button and Enter in the amount field.
Valid output uses two decimal places. Invalid input reports the error in a dialog.

| Style | Class |
|---|---|
| Frame implements `ActionListener` | `FrameListenerConverter.java` |
| Named inner class | `NamedInnerConverter.java` |
| Anonymous inner class | `AnonymousInnerConverter.java` |
| Lambda | `LambdaConverter.java` |

The Enter-key requirement changes one binding in each implementation. The lambda is the
most concise and the named inner class is the easiest to reuse. For a large application,
use an MVC-style handler or named `Action` classes rather than placing conversion logic
directly in four UI variants.
