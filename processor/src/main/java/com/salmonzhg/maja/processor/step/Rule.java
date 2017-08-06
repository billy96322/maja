package com.salmonzhg.maja.processor.step;

import javax.lang.model.type.TypeMirror;

/**
 * author: Salmon
 * date: 2017-08-05 18:55
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */

public class Rule {
    private String paramName;
    private int start;
    private int length;
    private int strategyIndex;
    private TypeMirror typeMirror;

    public Rule(String paramName, int start, int length, int strategyIndex, TypeMirror typeMirror) {
        this.paramName = paramName;
        this.start = start;
        this.length = length;
        this.strategyIndex = strategyIndex;
        this.typeMirror = typeMirror;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }

    public String getParamName() {
        return paramName;
    }

    public int getStart() {
        return start;
    }

    public int getLength() {
        return length;
    }

    public int getStrategyIndex() {
        return strategyIndex;
    }
}
