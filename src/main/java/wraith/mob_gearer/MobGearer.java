package wraith.mob_gearer;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobGearer implements ModInitializer {

    public static final String MOD_ID = "mob_gearer";
    public static final String MOD_NAME = "Mob Gearer";
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info("[" + MOD_NAME + "] has been initiated.");
    }

}
