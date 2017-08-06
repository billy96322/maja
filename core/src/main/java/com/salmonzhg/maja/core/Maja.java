package com.salmonzhg.maja.core;


/**
 * author: Salmon
 * date: 2017-08-01 10:02
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */
public class Maja {

    private static BaseDispatcher dispatcher;
    private static StrategyAdapter strategyAdapter;

    public static void init(StrategyAdapter strategyAdapter,
                            BaseDispatcher dispatcher) {
        if (strategyAdapter == null)
            throw new RuntimeException("StrategyAdapter cannot be null");
        if (dispatcher == null)
            throw new RuntimeException("dispatcher cannot be null, try call MajaSerializerDispatcher.instance() after rebuild");

        Maja.dispatcher = dispatcher;
        Maja.strategyAdapter = strategyAdapter;

        Maja.dispatcher.setStrategyAdapter(strategyAdapter);
        Maja.dispatcher.setup();
    }

    public static <T>T to(byte[] data, Class<T> clazz) {
        if (dispatcher == null)
            throw new RuntimeException("have you call \'init\' ?");

        if (data == null)
            throw new RuntimeException("data cannot be null");
        if (clazz == null)
            throw new RuntimeException("class cannot be null");

        return (T) dispatcher.dispatch(clazz).to(data);
    }

    public static byte[] from(Object object) {
        if (dispatcher == null)
            throw new RuntimeException("have you call \'init\' ?");

        if (object == null)
            throw new RuntimeException("object cannot be null");

        return dispatcher.dispatch(object.getClass()).from(object);
    }
}
