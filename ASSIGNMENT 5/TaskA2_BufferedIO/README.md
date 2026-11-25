# Task A2 - Buffered I/O

## Cost Prediction

Calling `FileInputStream.read()` for every byte makes about one operating-system/read-call
interaction for every byte in a 50 MB file. That can be tens of millions of calls, not 50 million
bytes of useful output, and is much slower than using the kernel or a Java buffer.

## Buffered Strategy

```text
open BufferedInputStream(FileInputStream(file), 64 KB)
count <- 0
byte <- read()
while byte != EOF:
  if byte = LF: count <- count + 1
  byte <- read()
close
```

The stream fills the 64 KB array on one underlying call and Java supplies subsequent bytes from memory.

## Limitation

Buffering helps little for a stream that is already buffered or for extremely tiny files where setup
overhead dominates. `readAllLines()` may be appropriate for one-time processing of small files, but
it retains all lines (O(file size)); buffered streaming uses O(buffer size).
