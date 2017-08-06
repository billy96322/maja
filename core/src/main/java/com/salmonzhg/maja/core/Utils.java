package com.salmonzhg.maja.core;

import java.util.Arrays;

/**
 * author: Salmon
 * date: 2017-08-05 15:05
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */

public class Utils {

    public static final byte[] pick(byte[] data, int start, int length) {
        return Arrays.copyOfRange(data, start, start + length);
    }

    public static final void place(byte[] src, byte[] dest, int destStartPos, int length) {
        if (src == null || src.length != length) return;
        System.arraycopy(src, 0, dest, destStartPos, length);
    }
}
