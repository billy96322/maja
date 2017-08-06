package com.salmonzhg.maja;

import com.salmonzhg.maja.core.annotation.SerializedRule;

/**
 * author: Salmon
 * date: 2017-08-04 10:47
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */

public class GarbageBean {
    @SerializedRule(start = 0, length = 4)
    public int[] trash;
}
