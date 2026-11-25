package com.campus.event.dao;

import com.campus.event.model.Registration;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryRegistrationDao implements RegistrationDao {
    private final List<Registration> rows = new CopyOnWriteArrayList<>();

    @Override
    public synchronized boolean register(Registration registration) {
        for (Registration row : rows) {
            if (row.studentId().equalsIgnoreCase(registration.studentId())
                    && row.eventName().equalsIgnoreCase(registration.eventName())) {
                return false;
            }
        }
        rows.add(new Registration(rows.size() + 1L, registration.studentId(),
                registration.studentName(), registration.eventName(),
                new java.util.Date().toString()));
        return true;
    }

    @Override
    public List<Registration> filter(String eventName, int offset, int limit) {
        List<Registration> result = new ArrayList<>();
        for (Registration row : rows) {
            if ("All".equals(eventName) || row.eventName().equals(eventName)) {
                result.add(row);
            }
        }
        int from = Math.min(offset, result.size());
        int to = Math.min(from + limit, result.size());
        return new ArrayList<>(result.subList(from, to));
    }

    @Override
    public List<Registration> all() {
        return new ArrayList<>(rows);
    }
}
