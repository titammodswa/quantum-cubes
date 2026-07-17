package com.titammods.quantumcubes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.titammods.quantumcubes.block.CubeTier;
import com.titammods.quantumcubes.block.entity.QuantumCubeBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class QuantumCubeRenderer
        implements BlockEntityRenderer<QuantumCubeBlockEntity, QuantumCubeRenderer.TesseractRenderState> {

    public static final float DEGREES_PER_TICK = 0.5f;

    private static final int FULL_BRIGHT = 0xF000F0;

    public static class TesseractRenderState extends BlockEntityRenderState {
        public float angle;
        public CubeTier tier = CubeTier.TIER_1;
    }

    public QuantumCubeRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public TesseractRenderState createRenderState() {
        return new TesseractRenderState();
    }

    @Override
    public void extractRenderState(QuantumCubeBlockEntity be, TesseractRenderState state, float partialTick,
                                   Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumbling) {
        BlockEntityRenderer.super.extractRenderState(be, state, partialTick, cameraPos, crumbling);
        long time = be.getLevel() != null ? be.getLevel().getGameTime() : 0L;
        state.angle = ((time % 720L) + partialTick) * DEGREES_PER_TICK;
        state.tier = be.getTier();
    }

    @Override
    public void submit(TesseractRenderState state, PoseStack poseStack, SubmitNodeCollector collector,
                       CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees(45.0f + state.angle));
        poseStack.translate(-0.5f, -0.5f, -0.5f);

        collector.submitCustomGeometry(poseStack, RenderTypes.entityCutoutCull(textureFor(state.tier)), (pose, vc) -> {
            emitCube(pose, vc, 4f, 12f, OUTER_UV, true);
            emitCube(pose, vc, 5f, 11f, INNER_UV, false);
        });

        poseStack.popPose();
    }

    private static Identifier textureFor(CubeTier tier) {
        return switch (tier) {
            case TIER_1 -> Identifier.fromNamespaceAndPath("quantumcubes", "textures/block/tesseract_copper.png");
            case TIER_2 -> Identifier.fromNamespaceAndPath("quantumcubes", "textures/block/tesseract_iron.png");
            case TIER_3 -> Identifier.fromNamespaceAndPath("quantumcubes", "textures/block/tesseract_gold.png");
            case TIER_4 -> Identifier.fromNamespaceAndPath("quantumcubes", "textures/block/tesseract_diamond.png");
            case TIER_5 -> Identifier.fromNamespaceAndPath("quantumcubes", "textures/block/tesseract_netherite.png");
        };
    }

    private static final float[][] OUTER_UV = {
            {4, 4, 6, 6}, {4, 6, 6, 8}, {6, 4, 8, 6}, {6, 6, 8, 8}, {2, 10, 0, 8}, {10, 0, 8, 2}
    };
    private static final float[][] INNER_UV = {
            {2, 8, 3.5f, 9.5f}, {8, 2, 9.5f, 3.5f}, {3.5f, 8, 5, 9.5f}, {8, 3.5f, 9.5f, 5},
            {6.5f, 9.5f, 5, 8}, {9.5f, 5, 8, 6.5f}
    };

    private static void emitCube(PoseStack.Pose pose, VertexConsumer vc,
                                 float min, float max, float[][] uv, boolean inverted) {
        float n = min / 16f;
        float x = max / 16f;

        quad(pose, vc, uv[0], inverted, 0, 0, -1,
                x, x, n, x, n, n, n, n, n, n, x, n);
        quad(pose, vc, uv[1], inverted, 1, 0, 0,
                x, x, x, x, n, x, x, n, n, x, x, n);
        quad(pose, vc, uv[2], inverted, 0, 0, 1,
                n, x, x, n, n, x, x, n, x, x, x, x);
        quad(pose, vc, uv[3], inverted, -1, 0, 0,
                n, x, n, n, n, n, n, n, x, n, x, x);
        quad(pose, vc, uv[4], inverted, 0, 1, 0,
                n, x, n, n, x, x, x, x, x, x, x, n);
        quad(pose, vc, uv[5], inverted, 0, -1, 0,
                n, n, x, n, n, n, x, n, n, x, n, x);
    }

    private static void quad(PoseStack.Pose pose, VertexConsumer vc,
                             float[] uv, boolean inverted, float nx, float ny, float nz,
                             float x1, float y1, float z1,
                             float x2, float y2, float z2,
                             float x3, float y3, float z3,
                             float x4, float y4, float z4) {
        float u1 = uv[0] / 16f;
        float v1 = uv[1] / 16f;
        float u2 = uv[2] / 16f;
        float v2 = uv[3] / 16f;

        float[][] verts = {
                {x1, y1, z1, u1, v1},
                {x2, y2, z2, u1, v2},
                {x3, y3, z3, u2, v2},
                {x4, y4, z4, u2, v1}
        };

        if (inverted) {
            nx = -nx;
            ny = -ny;
            nz = -nz;
        }
        for (int i = 0; i < 4; i++) {
            float[] v = verts[inverted ? 3 - i : i];
            vc.addVertex(pose, v[0], v[1], v[2])
                    .setColor(0xFFFFFFFF)
                    .setUv(v[3], v[4])
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setLight(FULL_BRIGHT)
                    .setNormal(pose, nx, ny, nz);
        }
    }
}