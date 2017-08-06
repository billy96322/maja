package com.salmonzhg.maja;

import com.salmonzhg.maja.core.annotation.SerializedRule;

/**
 * author: Salmon
 * date: 2017-08-03 17:16
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */

public class MajaBean {

    @SerializedRule(start = 0, length = 4)
    public String name;

    @SerializedRule(start = 4, length = 1)
    public int age;

    @SerializedRule(start = 5, length = 1)
    public int bea;

}
