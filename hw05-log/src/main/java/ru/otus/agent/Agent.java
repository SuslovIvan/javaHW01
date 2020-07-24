package ru.otus.agent;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.security.ProtectionDomain;


public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain");
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) {
                if (className.equals("ru/otus/proxy/MyClassImpl")) {
                    return changeMethod(classfileBuffer);
                }
                return classfileBuffer;
            }
        });
    }

    private static byte[] changeMethod(byte[] originalClass) {
        ClassReader cr = new ClassReader(originalClass);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM7, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new ChangeMethodVisitor(methodVisitor, access, name, descriptor);
            }
        };
        cr.accept(cv, Opcodes.ASM7);

        return cw.toByteArray();
    }

    private static class ChangeMethodVisitor extends AdviceAdapter {

        public boolean isLogAnnotated = false;

        ChangeMethodVisitor(MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(Opcodes.ASM7, methodVisitor, access, name, descriptor);
        }

        @Override
        protected void onMethodEnter() {
            if (isLogAnnotated) {
                Handle handle = new Handle(
                        H_INVOKESTATIC,
                        Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                        "makeConcatWithConstants",
                        MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                        false);
                visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System", "out", "Ljava/io/PrintStream;");

                Type[] types = getArgumentTypes();
                visitArguments(types);

                String msg = "asm executed method: " + getName()  + ", param: " + "\u0001, ".repeat(types.length);
                visitInvokeDynamicInsn("makeConcatWithConstants","(" + retrieveArgs(types) + ")Ljava/lang/String;",handle,msg);
                visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/io/PrintStream", "println",  "(Ljava/lang/String;)V", false);
            }

            super.onMethodEnter();
        }

        private void visitArguments(Type[] types) {
            int position = 1;

            for (Type type : types) {
                String typeDescriptor = type.getDescriptor();
                int opcode = retrieveOpcode(typeDescriptor);

                visitVarInsn(opcode, position);

                position += getOffset(opcode);
            }
        }

        private int retrieveOpcode(String descriptor) {
            switch (descriptor) {
                case "I":
                case "C":
                case "Z": return Opcodes.ILOAD;
                case "J": return Opcodes.LLOAD;
                case "D": return Opcodes.DLOAD;
                case "F": return Opcodes.FLOAD;
                default: return Opcodes.ALOAD;
            }
        }

        private int getOffset(int opcode) {
            switch (opcode) {
                case Opcodes.LLOAD:
                case Opcodes.DLOAD: return 2;
                case Opcodes.FLOAD:
                case Opcodes.ILOAD:
                default: return 1;
            }
        }

        private String retrieveArgs(Type[] types) {
            StringBuilder args = new StringBuilder();

            for (Type type : types) {
                args.append(type.getDescriptor());
            }

            return args.toString();
        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            if (descriptor.equals("Lru/otus/annotations/Log;")) {
                isLogAnnotated = true;
            }

            return super.visitAnnotation(descriptor, visible);
        }
    }

}
