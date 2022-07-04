package com.junwei.msmservice.service;

import java.util.Map;

public interface MsmServie {
    boolean send(String phone, Map<String, Object> param);
}
