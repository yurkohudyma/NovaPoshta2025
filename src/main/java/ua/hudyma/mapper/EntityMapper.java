package ua.hudyma.mapper;

import java.util.List;

public interface EntityMapper <D,E> {
    D toDto (E e);
    List<D> toDtoList (List<E> list);
}
