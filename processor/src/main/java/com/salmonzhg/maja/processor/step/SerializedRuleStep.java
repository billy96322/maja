package com.salmonzhg.maja.processor.step;

import com.google.auto.common.MoreElements;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import com.salmonzhg.maja.core.annotation.SerializedRule;
import com.salmonzhg.maja.processor.DispatcherGenerator;
import com.salmonzhg.maja.processor.GeneratorInfo;
import com.salmonzhg.maja.processor.SerializerGenerator;
import com.salmonzhg.maja.processor.Utils;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * author: Salmon
 * date: 2017-08-01 10:19
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */
public class SerializedRuleStep extends BaseStep {

    public SerializedRuleStep(DispatcherGenerator mainGenerator, ProcessingEnvironment processingEnvironment) {
        super(mainGenerator, processingEnvironment);
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        return ImmutableSet.of(SerializedRule.class);
    }

    @Override
    public Set<Element> process(SetMultimap<Class<? extends Annotation>, Element> setMultimap) {
        Set<Map.Entry<Class<? extends Annotation>, Collection<Element>>> entries = setMultimap.asMap().entrySet();
        Iterator<Map.Entry<Class<? extends Annotation>, Collection<Element>>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Class<? extends Annotation>, Collection<Element>> entry = iterator.next();
            Collection<Element> value = entry.getValue();
            Iterator<Element> iterator1 = value.iterator();
            while (iterator1.hasNext()) {
                Element executableElement = iterator1.next();
                Element enclosingElement = executableElement.getEnclosingElement();

                GeneratorInfo info = Utils.resolveGeneratorInfo(processingEnvironment.getElementUtils(), enclosingElement);

                String canonicalName = info.getCanonicalName();

                Map<String, SerializerGenerator> map = dispatcherGenerator.getSerializerGeneratorMap();

                SerializerGenerator serializerGenerator = map.get(canonicalName);

                if (!executableElement.getModifiers().contains(Modifier.PUBLIC)) continue;

                if (serializerGenerator == null) {
                    serializerGenerator = new SerializerGenerator(info);
                    map.put(canonicalName, serializerGenerator);
                }

                serializerGenerator.addRule(new Rule(
                        executableElement.getSimpleName().toString(),
                        MoreElements.asVariable(executableElement).getAnnotation(SerializedRule.class).start(),
                        MoreElements.asVariable(executableElement).getAnnotation(SerializedRule.class).length(),
                        MoreElements.asVariable(executableElement).getAnnotation(SerializedRule.class).strategyIndex(),
                        executableElement.asType()
                ));
            }
        }
        return new HashSet<>();
    }
}
