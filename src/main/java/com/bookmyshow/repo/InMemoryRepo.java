package com.bookmyshow.repo;

import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryRepo<T> {

    private final Map<Long, T> store = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public long save(T obj) {
        long id = idCounter.getAndIncrement();
        store.put(id, obj);
        return id;
    }

    public T update(Long id, T obj){
        store.put(id, obj);
        return obj;
    }

    public Optional<T> find(long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(store.values());
    }
}
