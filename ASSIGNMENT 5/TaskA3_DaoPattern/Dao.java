import java.util.List;

public interface Dao<T, K> {
    boolean insert(T value) throws Exception;
    T findById(K id) throws Exception;
    List<T> findAll() throws Exception;
    boolean update(T value) throws Exception;
    boolean delete(K id) throws Exception;
}
