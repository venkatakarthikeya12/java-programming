# Alternative Concepts and Complexity Notes

## Task 1

One listener with an action-command chain takes up to k comparisons for k buttons. Separate listener
objects or Swing `Action` objects dispatch directly and normally take O(1) listener selection.
Each listener object has O(k) space in the button-based approach, while a shared `Action` uses O(1)
state plus O(k) references to its attached components or buttons.

## Task 2

Loading the doctor map once requires O(d*m) memory for d departments and m doctors per department
and O(m) time to populate the current department. Fetching the database for every department change
costs O(d*m) over repeated distinct queries in the worst case and incurs network/I/O latency on
every event.

## Task 3

Nested `BorderLayout` and `FlowLayout` containers resize predictably: the centre grows, fixed
edges retain their natural size. `GridBagLayout` provides finer control but has O(C) layout work
over a larger constraint table; absolute positioning performs few layout calculations but does not
adapt to resizing and has no extra containers. The nested approach uses a moderate number of
containers, O(C), where C is the component/container count.

## Task 4

Point storage redraws n points in O(n) repaint time and uses O(n) point memory. An off-screen
`BufferedImage` copies an approximately fixed image to the screen in O(width*height) or
O(1) low-level copy complexity depending on implementation and hardware, but stores
width*height pixels, O(width*height) memory.

## Task 5

A shared `Action` or method reference creates no new listener per trigger: each source can share
the same action, so new triggers require configuration changes rather than new listener objects.
Separate lambda listeners or anonymous listeners create additional objects and access frame state
less explicitly. Named inner or service-based handlers provide the best reuse for large applications.

## Task 6

Loading all 100,000 rows into the table costs O(n) transfer and memory, approximately
O(n) objects, and can freeze or delay the EDT on large datasets. SQL `LIMIT/OFFSET` with
`SwingWorker` loads O(p) rows per page, p being the page size, and keeps database work off the EDT.
Database memory remains bounded while window responsiveness is improved. The in-memory demo
implementation is for local execution; JDBC is used when the MySQL driver and database are available.
