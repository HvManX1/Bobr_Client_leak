/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.ClassVisitor
 *  org.objectweb.asm.MethodVisitor
 */
package me.thef1xer.gateclient.asm.visitors;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class BlockLiquidVisitor
extends ClassVisitor {
    final String GET_COLLISION;
    final String GET_COLLISION_DESC;

    public BlockLiquidVisitor(ClassVisitor cv, boolean isObfuscated) {
        super(327680, cv);
        this.GET_COLLISION = isObfuscated ? "a" : "getCollisionBoundingBox";
        this.GET_COLLISION_DESC = isObfuscated ? "(Lawt;Lamy;Let;)Lbhb;" : "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/AxisAlignedBB;";
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals(this.GET_COLLISION) && desc.equals(this.GET_COLLISION_DESC)) {
            System.out.println("Method " + name + " transformed");
            mv = new GetCollisionBoundingBoxVisitor(mv);
        }
        return mv;
    }

    public static class GetCollisionBoundingBoxVisitor
    extends MethodVisitor {
        public GetCollisionBoundingBoxVisitor(MethodVisitor mv) {
            super(327680, mv);
        }

        public void visitInsn(int opcode) {
            if (opcode == 176) {
                this.mv.visitMethodInsn(184, "me/thef1xer/gateclient/util/EventFactory", "getCollisionBoundingBox", "()Lnet/minecraft/util/math/AxisAlignedBB;", false);
            }
            super.visitInsn(opcode);
        }
    }
}

