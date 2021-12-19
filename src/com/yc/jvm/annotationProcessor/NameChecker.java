package com.yc.jvm.annotationProcessor;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;
import java.util.EnumSet;

public class NameChecker {
    private final Messager messager;

    private NameCheckScanner nameCheckScanner = new NameCheckScanner();

    public NameChecker(ProcessingEnvironment processingEnvironment) {
        this.messager = processingEnvironment.getMessager();
    }

    public void checkNames(Element element) {
        nameCheckScanner.scan(element);
    }

    private class NameCheckScanner extends ElementScanner8<Void, Void> {
        /**
         * 检查类
         *
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitType(TypeElement e, Void aVoid) {
            scan(e.getTypeParameters(), aVoid);
            checkCamelCase(e, true);
            super.visitType(e, aVoid);
            return null;
        }

        /**
         * 检查方法
         *
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitExecutable(ExecutableElement e, Void aVoid) {
            if (e.getKind() == ElementKind.METHOD) {
                Name name = e.getSimpleName();
                if (name.contentEquals(e.getEnclosingElement().getSimpleName())) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "一个普通方法 " + name + "不应当与类名重复，避免与构造函数产生混淆", e);
                }
                checkCamelCase(e, false);
            }
            super.visitExecutable(e, aVoid);
            return null;
        }

        /**
         * 检查变量命名是否合法
         *
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            // 枚举或常量，则全部为大写
            if (e.getKind() == ElementKind.ENUM_CONSTANT || e.getConstantValue() != null || heuristicallyConstant(e)) {
                checkAllCaps(e);
            } else {
                checkCamelCase(e, false);
            }
            super.visitVariable(e, aVoid);
            return null;
        }

        /**
         * 判断一个变量是否时常量
         *
         * @param e
         * @return
         */
        private boolean heuristicallyConstant(VariableElement e) {
            if (e.getEnclosingElement().getKind() == ElementKind.INTERFACE) {
                return true;
            } else
                return e.getKind() == ElementKind.FIELD && e.getModifiers().containsAll(EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL));

        }

        /**
         * 检查传入的Element 是否符合拖式命名法
         *
         * @param e
         * @param initialCaps 为true时需要以大写开头，false以小写开头
         */
        private void checkCamelCase(Element e, boolean initialCaps) {
            String name = e.getSimpleName().toString();
            boolean previousUpper = false;
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);
            if (Character.isUpperCase(firstCodePoint)) {
                previousUpper = true;
                if (!initialCaps) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称 " + name + " 应当以小写字符开头 ", e);
                    return;
                }
            } else if (Character.isLowerCase(firstCodePoint)) {
                if (initialCaps) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称 " + name + " 应当以大写字符开头 ", e);
                    return;
                }
            } else {
                conventional = false;
            }
            if (conventional) {
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (Character.isUpperCase(cp)) {
                        if (previousUpper) {
                            conventional = false;
                            break;
                        }
                        previousUpper = true;
                    } else {
                        previousUpper = false;
                    }
                }
            }
            if (!conventional) {
                messager.printMessage(Diagnostic.Kind.WARNING, "名称 " + name + "应当符合拖式命名法(Camel Case Name)", e);
            }
        }

        /**
         * 大写命名检查，要求第一个字符必须时大学英文，其余下划线或大写
         *
         * @param e
         */
        private void checkAllCaps(Element e) {
            String name = e.getSimpleName().toString();
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);
            if (!Character.isUpperCase(firstCodePoint)) {
                conventional = false;
            } else {
                boolean previousUnderscore = false;
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (cp == (int) '_') {
                        if (previousUnderscore) {
                            conventional = false;
                            break;
                        }
                        previousUnderscore = true;
                    } else {
                        previousUnderscore = false;
                        if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
                            conventional = false;
                            break;
                        }
                    }
                }
            }
            if (!conventional) {
                messager.printMessage(Diagnostic.Kind.WARNING, "常量 " + name + "应当全部以大写字母或下划线命名，并且以字母开头", e);
            }
        }
    }

}
