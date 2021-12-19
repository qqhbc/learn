package com.yc.jvm.annotationProcessor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * 通过插入式注解处理器可以实现像lombok生成代码的功能，在编译器进行
 */
// 支持的注解类型
@SupportedAnnotationTypes("*")
// 支持的jdk版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NameCheckProcessor extends AbstractProcessor {
    private NameChecker nameChecker;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        nameChecker = new NameChecker(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // processingOver表示如果此轮生产的类型不需要做后续的处理就返回true
        if (!roundEnv.processingOver()) {
            for (Element element : roundEnv.getRootElements()) {
                nameChecker.checkNames(element);
            }
        }
        return false;
    }
}
