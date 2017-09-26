package com.salmonzhg.maja.processor;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;

/**
 * author: Salmon
 * date: 2017-08-04 15:46
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */

public class Utils {

    public static final GeneratorInfo resolveGeneratorInfo(Elements elementUtilS, Element enclosingElement) {
        String canonicalName = enclosingElement.asType().toString();
        String simpleCanonicalName = enclosingElement.getSimpleName().toString();
        String packagePath = elementUtilS.getPackageOf(enclosingElement).getQualifiedName().toString();

        GeneratorInfo info = new GeneratorInfo();

        info.setCanonicalName(canonicalName);
        info.setSimpleCanonicalName(simpleCanonicalName);
        info.setPackagePath(packagePath);

        return info;
    }
}
