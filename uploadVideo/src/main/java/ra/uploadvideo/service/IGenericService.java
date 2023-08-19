package ra.uploadvideo.service;

import java.util.List;

public interface IGenericService<T, E> {
    List<T> findAll();

    void save(T t);
    T findById(E e);
    void deleteById(E e);

}
