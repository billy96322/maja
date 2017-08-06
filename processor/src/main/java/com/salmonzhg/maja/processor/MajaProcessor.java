package com.salmonzhg.maja.processor;

import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.salmonzhg.maja.processor.step.SerializedRuleStep;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import java.io.IOException;

/**
 * author: Salmon
 * date: 2017-08-01 09:59
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */
@AutoService(Processor.class)
public class MajaProcessor extends BasicAnnotationProcessor {
    private DispatcherGenerator mainGenerator = new DispatcherGenerator();

    @Override
    protected Iterable<? extends ProcessingStep> initSteps() {
        return ImmutableSet.of(
                new SerializedRuleStep(mainGenerator, processingEnv)
        );
    }

    @Override
    protected void postRound(RoundEnvironment roundEnv) {
        super.postRound(roundEnv);
        if (mainGenerator != null)
            try {
                mainGenerator.generate(processingEnv);
            } catch (IOException e) {
                e.printStackTrace();
            }
//        CodeGenerator.generate(new ArrayList<Generator>(generatorMap.values()), processingEnv);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
