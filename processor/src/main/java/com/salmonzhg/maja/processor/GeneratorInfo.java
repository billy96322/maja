package com.salmonzhg.maja.processor;

/**
 * author: Salmon
 * date: 2017-08-04 15:44
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */

public class GeneratorInfo {
    private String simpleCanonicalName;
    private String packagePath;
    private String canonicalName;

    public String getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getSimpleCanonicalName() {

        return simpleCanonicalName;
    }

    public void setSimpleCanonicalName(String simpleCanonicalName) {
        this.simpleCanonicalName = simpleCanonicalName;
    }
}
