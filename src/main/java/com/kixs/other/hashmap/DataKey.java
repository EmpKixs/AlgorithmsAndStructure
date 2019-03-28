package com.kixs.other.hashmap;

import java.io.Serializable;

/**
 * @author wangbing
 * @version 1.0
 * @date 2019/3/28 14:51
 * Copyright: Copyright (c) 2019
 */
public class DataKey implements Serializable {

    private static final long serialVersionUID = -2174096162514279483L;
    private String key;

    public String getKey() {
        return key;
    }

    public DataKey() {
    }

    public DataKey(String key) {
        this.key = key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
