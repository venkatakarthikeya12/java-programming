# Task A4 - Serialization

`password` must be `transient` because plaintext credentials should not be written to an object file.
`Connection` must be `transient` because it is OS/session-specific and cannot be safely serialized.
Live database objects should be reconstructed after deserialization, not persisted.

Room, Resident and Hostel objects reachable from Hostel become part of the object graph. Java
serializes each object once and preserves shared references.

Add `serialVersionUID = 1L` to the serializable classes. Adding a new field with the same UID is
compatible by default: deserializing an old file gives the new field its default value. Changing
UID makes Java reject an old class-stream as incompatible.

CSV/JSON is safer for human-readable data and evolving fields, but text parsing is slower and
larger than Java binary serialization in many graphs. Serialize n residents costs O(n) time and
O(n) graph/storage, while CSV/JSON similarly costs O(n) time and often O(n) text data.
