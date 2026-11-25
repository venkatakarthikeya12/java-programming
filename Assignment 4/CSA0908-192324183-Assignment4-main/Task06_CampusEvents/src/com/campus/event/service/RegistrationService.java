package com.campus.event.service;

import com.campus.event.dao.RegistrationDao;
import com.campus.event.model.Registration;

public class RegistrationService {
    private final RegistrationDao dao;

    public RegistrationService(RegistrationDao dao) {
        this.dao = dao;
    }

    public boolean register(String studentId, String studentName, String eventName)
            throws Exception {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID is required.");
        }
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name is required.");
        }
        if (eventName == null || eventName.trim().isEmpty()) {
            throw new IllegalArgumentException("Event is required.");
        }
        return dao.register(new Registration(studentId.trim(), studentName.trim(), eventName));
    }

    public java.util.List<Registration> filter(String eventName, int offset, int limit)
            throws Exception {
        return dao.filter(eventName, offset, limit);
    }

    public java.util.List<Registration> all() throws Exception {
        return dao.all();
    }
}
