package com.astronautics.create;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(CreateAstronautics.MODID)
public class CreateAstronautics {
    public static final String MODID = "create_astronautics";
    public static final Logger LOGGER = LogUtils.getLogger();

    // ── Creative Tab ──────────────────────────────────────────────────────
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ASTRONAUTICS_TAB =
            CREATIVE_MODE_TABS.register("astronautics_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.create_astronautics"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .displayItems((parameters, output) -> {
                        // Register all mod items here as they are added.
                    })
                    .build());

    // ── Constructor ───────────────────────────────────────────────────────
    // Register DeferredRegisters in dependency order:
    //   FluidTypes → Fluids → Blocks → Items → BlockEntityTypes
    public CreateAstronautics(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        CREATIVE_MODE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        LOGGER.info("╔══════════════════════════════════════╗");
        LOGGER.info("║     Create: Astronautics — Init      ║");
        LOGGER.info("╚══════════════════════════════════════╝");
    }

    // ── Common Setup ──────────────────────────────────────────────────────
    // Runs after registries finalise on both sides. Use for cross-mod compat.
    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            LOGGER.info("Common setup is complete. Create: Astronautics is go for launch.");
        });
    }

    // ── Creative Tab Injection ────────────────────────────────────────────
    // Use this to inject items into vanilla or other mods' existing tabs.
    // For this mod's own tab, use the displayItems lambda above.
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) { ... }
    }

    // ── Game Events ───────────────────────────────────────────────────────
    // Move event handlers to dedicated @EventBusSubscriber classes as the mod grows.
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Create: Astronautics server starting.");
    }
}