package com.campus.event.dao;

import com.campus.event.model.Registration;
import java.util.List;

public interface RegistrationDao {
    boolean register(Registration registration) throws Exception;
    List<Registration> filter(String eventName, int offset, int limit) throws Exception;
    List<Registration> all() throws Exception;
}
