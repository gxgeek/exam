package com.gx.common;

import java.util.HashMap;

/**
 * Created by gx on 2017/2/8.
 */
public class UUIDMap {
    private static UUIDMap ourInstance = new UUIDMap();
    private static HashMap map;
    private   UUIDMap() {
        map = new HashMap();
    }
    public static UUIDMap getInstance() {
        return ourInstance;
    }

    public void put (String key,String value){
        map.put(key,value);
    }

    public Object get (String key){
        return  map.get(key);
    }

}
