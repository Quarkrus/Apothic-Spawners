package dev.shadowsoffire.apothic_spawners.compat;

import dev.shadowsoffire.apothic_spawners.ApothicSpawners;
import dev.shadowsoffire.apothic_spawners.block.ApothSpawnerBlock;
import dev.shadowsoffire.apothic_spawners.block.ApothSpawnerTile;
import dev.shadowsoffire.apothic_spawners.stats.SpawnerStats;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin
public class SpawnerHwylaPlugin implements IWailaPlugin, IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    @Override
    public void register(IWailaCommonRegistration reg) {
        reg.registerBlockDataProvider(this, ApothSpawnerTile.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration reg) {
        reg.registerBlockComponent(this, ApothSpawnerBlock.class);
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (Screen.hasControlDown()) {
            ApothSpawnerTile tile = new ApothSpawnerTile(BlockPos.ZERO, Blocks.AIR.defaultBlockState());
            tile.loadAdditional(accessor.getServerData(), accessor.getLevel().registryAccess());
            SpawnerStats.generateTooltip(tile, tooltip::add);
        }
        else tooltip.add(Component.translatable("misc.apothic_spawners.ctrl_stats"));
    }

    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor access) {
        if (access.getBlockEntity() instanceof ApothSpawnerTile spw) {
            spw.saveAdditional(tag, access.getLevel().registryAccess());
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ApothicSpawners.loc("spawner");
    }

}
