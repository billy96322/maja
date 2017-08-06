package com.salmonzhg.maja.processor;

import com.salmonzhg.maja.core.BaseDispatcher;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * author: Salmon
 * date: 2017-08-01 10:39
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */
public class DispatcherGenerator implements Generator {
    public static final String PACKAGE_NAME = "com.salmonzhg.maja";
    public static final String MAJA_SERIALIZER_NAME = "MajaSerializerDispatcher";

    private Map<String, SerializerGenerator> serializerGeneratorMap = new LinkedHashMap<>();

    public Map<String, SerializerGenerator> getSerializerGeneratorMap() {
        return serializerGeneratorMap;
    }

    @Override
    public void generate(ProcessingEnvironment processingEnvironment) throws IOException {
        List<Generator> serializerGenerators = new ArrayList<Generator>(serializerGeneratorMap.values());

        for (Generator generator : serializerGenerators) {
            generator.generate(processingEnvironment);
        }

        JavaFile.builder(PACKAGE_NAME, TypeSpec.classBuilder(MAJA_SERIALIZER_NAME)
                .superclass(BaseDispatcher.class)
                .addMethod(constructorSpec())
                .addField(singleInstanceFieldSpec())
                .addMethod(singleInstanceMethodSpec())
                .addMethod(methodSpecOfSetup())
                .build())
                .build().writeTo(processingEnvironment.getFiler());
    }

    private FieldSpec singleInstanceFieldSpec() {
        return FieldSpec.builder(BaseDispatcher.class, "sInstance", Modifier.PRIVATE, Modifier.STATIC).build();
    }

    private MethodSpec singleInstanceMethodSpec() {
        return MethodSpec.methodBuilder("instance")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.SYNCHRONIZED)
                .returns(BaseDispatcher.class)
                .beginControlFlow("if (null == sInstance)")
                .addStatement("sInstance = new "+MAJA_SERIALIZER_NAME+"()")
                .endControlFlow()
                .addStatement("return sInstance")
                .build();
    }

    private MethodSpec constructorSpec() {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE);
        return builder.build();
    }

    private CodeBlock codeBlockOfPutSerializers() {
        List<SerializerGenerator> serializerGenerators = new ArrayList<>(serializerGeneratorMap.values());
        CodeBlock.Builder builder = CodeBlock.builder();
        for (SerializerGenerator generator : serializerGenerators) {
            // serializerMap.put(MajaBean.class, new MajaBean$MajaSerializer(strategyAdapter));
            GeneratorInfo info = generator.getInfo();
            builder.addStatement("serializerMap.put("+info.getCanonicalName()+".class, new "+generator.getFullPathClassName()+"(strategyAdapter))");
        }
        return builder.build();
    }

    private MethodSpec methodSpecOfSetup() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("setup")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addCode(codeBlockOfPutSerializers());
        return builder.build();
    }
}
