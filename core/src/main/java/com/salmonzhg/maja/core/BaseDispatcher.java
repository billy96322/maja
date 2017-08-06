package com.salmonzhg.maja.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * author: Salmon
 * date: 2017-08-04 14:59
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */

public abstract class BaseDispatcher {
    protected StrategyAdapter strategyAdapter;
    protected Map<Class, MajaSerializer> serializerMap = new LinkedHashMap<>();

    public void setStrategyAdapter(StrategyAdapter strategyAdapter) {
        this.strategyAdapter = strategyAdapter;
    }

    public MajaSerializer dispatch(Class clazz) {
        MajaSerializer majaSerializer = serializerMap.get(clazz);
        if (majaSerializer == null)
            throw new RuntimeException("MajaSerializer has not create correctly");
        return majaSerializer;
    }

    public abstract void setup();
}
