package com.salmonzhg.maja.core;

/**
 * author: Salmon
 * date: 2017-08-01 10:15
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */
public abstract class StrategyAdapter {
    public static final int DEFAULT_STRATAGE = -1000;

    public abstract byte toByte(byte[] data, int index);

    public abstract short toShort(byte[] data, int index);

    public abstract int toInteger(byte[] data, int index);

    public abstract long toLong(byte[] data, int index);

    public abstract float toFloat(byte[] data, int index);

    public abstract double toDouble(byte[] data, int index);

    public abstract boolean toBoolean(byte[] data, int index);

    public abstract char toChar(byte[] data, int index);

    public abstract String toString(byte[] data, int index);

    public abstract <T> T toCommon(byte[] data, int index);

    public abstract byte[] from(byte b, int lengthRequired, int index);

    public abstract byte[] from(short s, int lengthRequired, int index);

    public abstract byte[] from(int i, int lengthRequired, int index);

    public abstract byte[] from(long l, int lengthRequired, int index);

    public abstract byte[] from(float f, int lengthRequired, int index);

    public abstract byte[] from(double d, int lengthRequired, int index);

    public abstract byte[] from(boolean b, int lengthRequired, int index);

    public abstract byte[] from(char c, int lengthRequired, int index);

    public abstract byte[] from(String s, int lengthRequired, int index);

    public abstract byte[] from(Object s, int lengthRequired, int index);
}
