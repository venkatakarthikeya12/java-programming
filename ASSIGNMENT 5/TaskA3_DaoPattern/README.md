# Task A3 - DAO Pattern

Common steps:

1. open connection
2. prepare SQL
3. bind parameters
4. execute
5. map `ResultSet`
6. close resources

Only the SQL and result mapping differ.

```java
public interface Dao<T, K> {
    boolean insert(T value) throws Exception;
    T findById(K id) throws Exception;
    List<T> findAll() throws Exception;
    boolean update(T value) throws Exception;
    boolean delete(K id) throws Exception;
}

class StudentDao implements Dao<Student, String> {
    // implements Student operations
}
```

The `Student` primary key `registerNumber` maps to `student.register_no`.
