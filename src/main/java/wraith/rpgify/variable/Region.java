package wraith.rpgify.variable;

import net.fabricmc.loader.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import wraith.rpgify.Utils;

import java.util.HashSet;

public class Region extends AbstractVariable {

    private final String name;
    private final String dimension;
    private final int minX;
    private final int minY;
    private final int minZ;
    private final int maxX;
    private final int maxY;
    private final int maxZ;

    public Region(String name, String dimension, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        super(null, "region");
        this.value = this;
        this.name = name;
        this.dimension = dimension;
        this.minX = Math.min(minX, maxX);
        this.minY = Math.min(minY, maxY);
        this.minZ = Math.min(minZ, maxZ);
        this.maxX = Math.max(minX, maxX);
        this.maxY = Math.max(minY, maxY);
        this.maxZ = Math.max(minZ, maxZ);
    }

    public static Region findRegion(BlockPos pos) {
        for (Region region : CustomVariables.getRegions().values()) {
            if (region.isInside(pos)) {
                return region;
            }
        }
        return null;
    }

    public static HashSet<Region> findRegions(BlockPos pos) {
        HashSet<Region> regions = new HashSet<>();
        for (Region region : CustomVariables.getRegions().values()) {
            if (region.isInside(pos)) {
                regions.add(region);
            }
        }
        return regions;
    }

    @Override
    public Object getValue() {
        return this;
    }

    public boolean isInside(int x, int y, int z) {
        return minX <= x && minY <= y && minZ <= z && maxX >= x && maxY >= y && maxZ >= z;
    }

    public boolean isInside(BlockPos pos) {
        return minX <= pos.getX() && minY <= pos.getY() && minZ <= pos.getZ() && maxX >= pos.getX() && maxY >= pos.getY() && maxZ >= pos.getZ();
    }

    public BlockPos getMinPosition() {
        return new BlockPos(minX, minY, minZ);
    }

    public BlockPos getMaxPosition() {
        return new BlockPos(maxX, maxY, maxZ);
    }

    public BlockPos getRandomPositionInside() {
        int x = Utils.getRandomIntInRange(minX, maxX);
        int y = Utils.getRandomIntInRange(minY, maxY);
        int z = Utils.getRandomIntInRange(minZ, maxZ);
        return new BlockPos(x, y, z);
    }

    public BlockPos getRandomSafePositionInside() {
        MinecraftServer server;
        if (FabricLoader.INSTANCE.getGameInstance() instanceof MinecraftServer) {
            server = (MinecraftServer) FabricLoader.INSTANCE.getGameInstance();
        } else {
            server = MinecraftClient.getInstance().getServer();
        }
        World currentWorld = null;
        for (World world : server.getWorlds()) {
            if (world.getRegistryKey().getValue().toString().equals(this.dimension)) {
                currentWorld = world;
                break;
            }
        }
        if (currentWorld == null) {
            return null;
        }
        int x = Utils.getRandomIntInRange(minX, maxX);
        int z = Utils.getRandomIntInRange(minZ, maxZ);
        int y;
        for (y = minY; y <= maxY; ++y) {
            if(currentWorld.isSpaceEmpty(new Box(x, y, z, x + 1, y + 2, z + 1))) {
                break;
            }
        }
        return new BlockPos(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Region)) {
            return false;
        }
        Region region = (Region) obj;

        return region.minX == this.minX && region.minY == this.minY && region.minZ == this.minZ && region.maxX == this.maxX && region.maxY == this.maxY && region.maxZ == this.maxZ && region.name.equals(this.name);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash * Integer.hashCode(minX);
        hash = 31 * hash * Integer.hashCode(minY);
        hash = 31 * hash * Integer.hashCode(minZ);
        hash = 31 * hash * Integer.hashCode(maxX);
        hash = 31 * hash * Integer.hashCode(maxY);
        hash = 31 * hash * Integer.hashCode(maxZ);
        hash = 31 * hash * name.hashCode();
        return hash;
    }
}