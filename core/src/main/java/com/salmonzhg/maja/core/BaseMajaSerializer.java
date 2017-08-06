package com.salmonzhg.maja.core;

/**
 * author: Salmon
 * date: 2017-08-04 15:58
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */

public abstract class BaseMajaSerializer<T> implements MajaSerializer<T> {
    protected StrategyAdapter strategyAdapter;

    public BaseMajaSerializer(StrategyAdapter strategyAdapter) {
        this.strategyAdapter = strategyAdapter;
    }
}
