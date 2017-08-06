package com.salmonzhg.maja.processor;

import com.salmonzhg.maja.core.BaseMajaSerializer;
import com.salmonzhg.maja.core.StrategyAdapter;
import com.salmonzhg.maja.processor.step.Rule;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * author: Salmon
 * date: 2017-08-04 14:47
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */

public class SerializerGenerator implements Generator {
    public static final String SUB_SERIALIZER_POSTFIX = "_MajaSerializer";
    private GeneratorInfo info;
    private ClassName targetClassName;
    private List<Rule> rules = new LinkedList<>();

    public SerializerGenerator(GeneratorInfo info) {
        this.info = info;
        targetClassName = ClassName.get(info.getPackagePath(), info.getSimpleCanonicalName());
    }

    public void addRule(Rule rule) {
        this.rules.add(rule);
    }

    public String getFullPathClassName() {
        return info.getCanonicalName() + SUB_SERIALIZER_POSTFIX;
    }

    public GeneratorInfo getInfo() {
        return info;
    }

    @Override
    public void generate(ProcessingEnvironment processingEnvironment) throws IOException {
        JavaFile.builder(info.getPackagePath(), TypeSpec.classBuilder(info.getSimpleCanonicalName() + SUB_SERIALIZER_POSTFIX)
                .addModifiers(Modifier.PUBLIC)
                .superclass(ParameterizedTypeName.get(ClassName.get(BaseMajaSerializer.class), targetClassName))
                .addMethod(constructorSpec())
                .addMethod(methodSpecOfFrom())
                .addMethod(methodSpecOfTo())
                .build())
                .build().writeTo(processingEnvironment.getFiler());
    }

    private MethodSpec constructorSpec() {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(StrategyAdapter.class, "strategyAdapter", Modifier.FINAL)
                .addStatement("super(strategyAdapter)");
        return builder.build();
    }

    private MethodSpec methodSpecOfTo() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("to")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(byte[].class, "data", Modifier.FINAL)
                .returns(targetClassName)
                .addCode(codeBlockOfTo());
        return builder.build();
    }

    private CodeBlock codeBlockOfTo() {
        CodeBlock.Builder builder = CodeBlock.builder();
        builder.addStatement(info.getSimpleCanonicalName() + " result = new " + info.getSimpleCanonicalName() + "()");
        for (Rule r : rules) {
            builder.beginControlFlow("if (data.length >= " + (r.getStart() + r.getLength()) + ")")
                    .addStatement("result." + r.getParamName() +
                            " = strategyAdapter." + adapterFuncStr(r) + "($T.pick(data, " +
                            r.getStart() + ", " + r.getLength() + "), " +
                            r.getStrategyIndex() + ")", com.salmonzhg.maja.core.Utils.class)
                    .endControlFlow();
        }
        builder.addStatement("return result");
        return builder.build();
    }

    private String adapterFuncStr(Rule rule) {
        TypeMirror typeMirror = rule.getTypeMirror();
        TypeKind typeKind = typeMirror.getKind();
        switch (typeKind) {
            case BOOLEAN:
                return "toBoolean";
            case BYTE:
                return "toByte";
            case SHORT:
                return "toShort";
            case INT:
                return "toInteger";
            case LONG:
                return "toLong";
            case CHAR:
                return "toChar";
            case FLOAT:
                return "toFloat";
            case DOUBLE:
                return "toDouble";
        }
        if (typeMirror instanceof DeclaredType) {
            if ("java.lang.String".equals(((TypeElement) ((DeclaredType) typeMirror).asElement()).getQualifiedName().toString())) {
                return "toString";
            }
        }
        return "toCommon";
    }

    private MethodSpec methodSpecOfFrom() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("from")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(targetClassName, "object", Modifier.FINAL)
                .returns(byte[].class)
                .addCode(codeBlockOfFrom());
        return builder.build();
    }

    private CodeBlock codeBlockOfFrom() {
        CodeBlock.Builder createArrayCodeBuilder = CodeBlock.builder();
        CodeBlock.Builder builder = CodeBlock.builder();
        int arrayLength = 0;
        for (Rule r : rules) {
            builder.addStatement("Utils.place(strategyAdapter.from(object." + r.getParamName() + ", " + r.getLength()+ ", " + r.getStrategyIndex() + "), result, " + r.getStart() + ", " + r.getLength() + ")",
                    com.salmonzhg.maja.core.Utils.class);
            int length = r.getStart() + r.getLength();
            if (length > arrayLength) arrayLength = length;
        }
        createArrayCodeBuilder.addStatement("byte[] result = new byte[" + arrayLength + "]");
        builder.addStatement("return result");
        createArrayCodeBuilder.add(builder.build());
        return createArrayCodeBuilder.build();
    }
}
