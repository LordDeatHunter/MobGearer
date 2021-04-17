package wraith.rpgify.variable;

public class Region extends AbstractVariable {

    private final String name;
    private final int minX;
    private final int minY;
    private final int minZ;
    private final int maxX;
    private final int maxY;
    private final int maxZ;

    public Region(String name, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        super(null, "region");
        this.value = this;
        this.name = name;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    @Override
    public Object getValue() {
        return this;
    }

    public boolean isInside(int x, int y, int z) {
        return minX <= x && minY <= y && minZ <= z && maxX >= x && maxY >= y && maxZ >= z;
    }

}