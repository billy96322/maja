package com.salmonzhg.maja;

import com.salmonzhg.maja.core.StrategyAdapter;

/**
 * author: Salmon
 * date: 2017-08-07 15:57
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */

public class MyStrategyAdapter extends StrategyAdapter {

    @Override
    public byte toByte(byte[] data, int index) {
        return 0;
    }

    @Override
    public short toShort(byte[] data, int index) {
        return 0;
    }

    @Override
    public int toInteger(byte[] data, int index) {
        return 0;
    }

    @Override
    public long toLong(byte[] data, int index) {
        return 0;
    }

    @Override
    public float toFloat(byte[] data, int index) {
        return 0;
    }

    @Override
    public double toDouble(byte[] data, int index) {
        return 0;
    }

    @Override
    public boolean toBoolean(byte[] data, int index) {
        return false;
    }

    @Override
    public char toChar(byte[] data, int index) {
        return 0;
    }

    @Override
    public String toString(byte[] data, int index) {
        return null;
    }

    @Override
    public <T> T toCommon(byte[] data, int index) {
        return null;
    }

    @Override
    public byte[] from(byte b, int lengthRequired, int index) {
        return new byte[0];
    }

    @Override
    public byte[] from(short s, int lengthRequired, int index) {
        return new byte[0];
    }

    @Override
    public byte[] from(int i, int lengthRequired, int index) {
        return new byte[0];
    }

    @Override
    public byte[] from(long l, int lengthRequired, int index) {
        return new byte[0];
    }

    @Override
    public byte[] from(float f, int lengthRequired, int index) {
        return new byte[0];
    }

    @Override
    public byte[] from(double d, int lengthRequired, int index) {
        return new byte[0];
    }

    @Override
    public byte[] from(boolean b, int lengthRequired, int index) {
        return new byte[0];
    }

    @Override
    public byte[] from(char c, int lengthRequired, int index) {
        return new byte[0];
    }

    @Override
    public byte[] from(String s, int lengthRequired, int index) {
        return new byte[0];
    }

    @Override
    public byte[] from(Object s, int lengthRequired, int index) {
        return new byte[0];
    }
}
