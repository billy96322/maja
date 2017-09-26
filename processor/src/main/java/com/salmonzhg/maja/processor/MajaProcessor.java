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
    private boolean hasGenerated = false;
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
        if (!hasGenerated && mainGenerator != null) {
            try {
                mainGenerator.generate(processingEnv);
                hasGenerated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
