package com.binance.api.domain.utils;

import com.binance.api.client.domain.event.AggTradeEvent;

import java.util.ArrayList;
import java.util.List;

public final class MyCache<T> {

    private List list = new ArrayList<T>();
    private final static long DEFAULT_CACHE_TIME= 15*1000;//默认15秒

    public void add(T o){
        list.add(o);
    }
}
