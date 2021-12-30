/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.IClassTransformer
 *  org.objectweb.asm.ClassReader
 *  org.objectweb.asm.ClassVisitor
 *  org.objectweb.asm.ClassWriter
 */
package ru.terrar.bobr.asm;

import java.util.Arrays;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import ru.terrar.bobr.asm.visitors.BlockLiquidVisitor;
import ru.terrar.bobr.asm.visitors.BlockRendererDispatcherVisitor;
import ru.terrar.bobr.asm.visitors.BlockStateContainerVisitor;
import ru.terrar.bobr.asm.visitors.EntityPlayerSPVisitor;
import ru.terrar.bobr.asm.visitors.EntityRendererVisitor;
import ru.terrar.bobr.asm.visitors.NetworkManagerVisitor;
import ru.terrar.bobr.asm.visitors.VisGraphVisitor;

public class GateClassTransformer
implements IClassTransformer {
    private static final String[] transformedClasses = new String[]{"net.minecraft.client.renderer.chunk.VisGraph", "net.minecraft.client.renderer.EntityRenderer", "net.minecraft.network.NetworkManager", "net.minecraft.client.renderer.BlockRendererDispatcher", "net.minecraft.block.state.BlockStateContainer$StateImplementation", "net.minecraft.block.BlockLiquid", "net.minecraft.client.entity.EntityPlayerSP"};

    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        boolean isObfuscated = !name.equals(transformedName);
        int index = Arrays.asList(transformedClasses).indexOf(transformedName);
        return index != -1 ? this.transform(index, basicClass, isObfuscated) : basicClass;
    }

    public byte[] transform(int index, byte[] basicClass, boolean isObfuscated) {
        System.out.println("Transforming " + transformedClasses[index]);
        try {
            ClassVisitor classVisitor;
            ClassReader classReader = new ClassReader(basicClass);
            ClassWriter classWriter = new ClassWriter(3);
            switch (index) {
                case 0: {
                    classVisitor = new VisGraphVisitor((ClassVisitor)classWriter, isObfuscated);
                    break;
                }
                case 1: {
                    classVisitor = new EntityRendererVisitor((ClassVisitor)classWriter, isObfuscated);
                    break;
                }
                case 2: {
                    classVisitor = new NetworkManagerVisitor((ClassVisitor)classWriter, isObfuscated);
                    break;
                }
                case 3: {
                    classVisitor = new BlockRendererDispatcherVisitor((ClassVisitor)classWriter, isObfuscated);
                    break;
                }
                case 4: {
                    classVisitor = new BlockStateContainerVisitor((ClassVisitor)classWriter, isObfuscated);
                    break;
                }
                case 5: {
                    classVisitor = new BlockLiquidVisitor((ClassVisitor)classWriter, isObfuscated);
                    break;
                }
                case 6: {
                    classVisitor = new EntityPlayerSPVisitor((ClassVisitor)classWriter, isObfuscated);
                    break;
                }
                default: {
                    classVisitor = new ClassVisitor(327680, (ClassVisitor)classWriter){};
                }
            }
            classReader.accept(classVisitor, 0);
            return classWriter.toByteArray();
        }
        catch (Exception e) {
            e.printStackTrace();
            return basicClass;
        }
    }
}

