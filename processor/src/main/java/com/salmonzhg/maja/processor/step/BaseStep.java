package com.salmonzhg.maja.processor.step;

import com.google.auto.common.BasicAnnotationProcessor;
import com.salmonzhg.maja.processor.DispatcherGenerator;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * author: Salmon
 * date: 2017-08-04 14:36
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */

public abstract class BaseStep implements BasicAnnotationProcessor.ProcessingStep {
    protected DispatcherGenerator dispatcherGenerator;
    protected ProcessingEnvironment processingEnvironment;

    public BaseStep(DispatcherGenerator dispatcherGenerator, ProcessingEnvironment processingEnvironment) {
        this.dispatcherGenerator = dispatcherGenerator;
        this.processingEnvironment = processingEnvironment;
    }
}
