package wraith.rpgify;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wraith.rpgify.entity.CustomEntities;
import wraith.rpgify.event.MobSpawnEvent;
import wraith.rpgify.item.CustomItems;
import wraith.rpgify.variable.CustomVariables;

public class RPGify implements ModInitializer {

    public static final String MOD_ID = "rpgify";
    public static final String MOD_NAME = "RPGify";
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        Utils.saveFilesFromJar("configs", "", true);
        Utils.saveFilesFromJar("configs/items", "items", true);
        Utils.saveFilesFromJar("configs/guides", "guides", true);
        Utils.saveFilesFromJar("configs/mobs", "mobs", true);
        loadConfigs();
        registerEvents();
        LOGGER.info("[" + MOD_NAME + "] has been initiated.");
    }

    private void loadConfigs() {
        DimensionGroups.loadDimensionGroups();
        CustomVariables.loadAll();
        MobGroups.loadMobGroups();
        CustomItems.loadItems();
        CustomEntities.loadEntities();
    }

    public void registerEvents() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            MobSpawnEvent.getInstance().onSpawn(world, entity);
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {

            dispatcher.register(CommandManager.literal("eyo")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(CommandManager.argument("item", StringArgumentType.string())
                        .executes(context -> {
                            String item = StringArgumentType.getString(context, "item");
                            ServerPlayerEntity player = context.getSource().getPlayer();
                            try {
                                if (player != null && CustomItems.itemExists(item)) {
                                    player.giveItemStack(CustomItems.getItem(item).getStack());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return 1;
                        })
                    )
            );

            dispatcher.register(CommandManager.literal("rpgify")
                    .then(CommandManager.literal("reload")
                    .requires(source -> source.hasPermissionLevel(2))
                        .executes(context -> {
                            loadConfigs();
                            ServerPlayerEntity player = context.getSource().getPlayer();
                            if (player != null) {
                                player.sendMessage(new LiteralText("§6[§eRPGify§6] §3has successfully reloaded!"), false);
                            }
                            return 1;
                        })
                    )
            );

        });
    }

}
