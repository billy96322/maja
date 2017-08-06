package com.salmonzhg.maja.core;

/**
 * author: Salmon
 * date: 2017-08-04 16:33
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */

public interface MajaSerializer<T> {
    T to(byte[] data);

    byte[] from(T t);
}
