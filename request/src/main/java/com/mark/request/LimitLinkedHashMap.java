package com.mark.request;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by Mark on 2018/7/6.
 */

public class LimitLinkedHashMap<K,V> extends LinkedHashMap<K,V> {
private int maxSize = Integer.MAX_VALUE;
   public LimitLinkedHashMap(int maxSize){
        this.maxSize = maxSize;
    }
    private synchronized Entry<K, V> getHead() {
        return entrySet().iterator().next();
    }
    private synchronized  <K, V> Entry<K, V> getTail(LinkedHashMap<K, V> map) {
        Iterator<Entry<K, V>> iterator = map.entrySet().iterator();
        Entry<K, V> tail = null;
        while (iterator.hasNext()) {
            tail = iterator.next();
        }
        return tail;
    }

    @Override
    public synchronized V put(K key, V value) {
        while (size()>=maxSize){
          remove(getHead().getKey());
        }
        return super.put(key, value);
    }
}
