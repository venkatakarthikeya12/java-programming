package com.campus.event.model;

public record Registration(long id, String studentId, String studentName,
                          String eventName, String registeredAt) {
    public Registration(String studentId, String studentName, String eventName) {
        this(0, studentId, studentName, eventName, "");
    }
}
