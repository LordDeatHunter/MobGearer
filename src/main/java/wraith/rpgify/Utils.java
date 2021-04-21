package wraith.rpgify;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.util.Identifier;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Utils {

    private Utils(){}

    public static final Random RANDOM = new Random();
    public static int getRandomIntInRange(int min, int max) {
        return min + RANDOM.nextInt(max - min + 1);
    }
    public static float getRandomFloatInRange(float min, float max) {
        return min + (RANDOM.nextFloat() * (max - min));
    }
    public static Identifier ID(String path) {
        return new Identifier(RPGify.MOD_ID, path);
    }
    public static boolean isAlphaNumeric(char c) {
        return Character.isDigit(c) || Character.isAlphabetic(c);
    }
    public static boolean isValidVariable(char c) {
        return Character.isAlphabetic(c) || c == '_' || c == '-';
    }

    public static void saveFilesFromJar(String dir, String outputDir, boolean overwrite) {
        JarFile jar = null;
        if (dir.endsWith("/")) {
            dir = dir.substring(0, dir.length() - 1);
        }
        if (outputDir.endsWith("/")) {
            outputDir = outputDir.substring(0, dir.length() - 1);
        }
        try {
            jar = new JarFile(Utils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (IOException | URISyntaxException e) {
            ModMetadata metadata = FabricLoader.getInstance().getModContainer(RPGify.MOD_ID).get().getMetadata();
            String filename = "mods/wraith-" + RPGify.MOD_ID + "-" + metadata.getVersion() + ".jar";
            try {
                jar = new JarFile(new File(filename));
            } catch (IOException ignored) {
            }
        }

        if (jar != null) {
            Enumeration<JarEntry> entries = jar.entries();
            while(entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                long entrySeparatorCount = entry.getName().chars().filter(c -> c == '/').count();
                long dirSeparatorCount = dir.chars().filter(c -> c == '/').count();
                if (entrySeparatorCount - 1 != dirSeparatorCount || !entry.getName().startsWith(dir) || !entry.getName().endsWith(".yaml")) {
                    continue;
                }
                String[] segments = entry.getName().split("/");
                String filename = segments[segments.length - 1];
                InputStream is = Utils.class.getResourceAsStream("/" + entry.getName());
                String path = "config/" + RPGify.MOD_ID + "/" + outputDir + ("".equals(outputDir) ? "" : File.separator) + filename;
                try {
                    inputStreamToFile(is, new File(path), overwrite);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Launched from IDE.");
            File[] files = FabricLoader.getInstance().getModContainer(RPGify.MOD_ID).get().getPath(dir).toFile().listFiles();
            for(File file : files) {
                if (file.isDirectory()) {
                    continue;
                }
                String[] segments = file.getName().split("/");
                String filename = segments[segments.length - 1];
                String path = "config/" + RPGify.MOD_ID + "/" + outputDir + ("".equals(outputDir) ? "" : File.separator) + filename;
                try {
                    Config.createFile(path, FileUtils.readFileToString(file, StandardCharsets.UTF_8), overwrite);
                } catch (IOException e) {
                    RPGify.LOGGER.warn("ERROR OCCURRED WHILE REGENERATING " + filename + " TEXTURE");
                    e.printStackTrace();
                }
            }
        }
    }

    private static void inputStreamToFile(InputStream inputStream, File file, boolean overwrite) throws IOException {
        if (!file.exists() || overwrite) {
            FileUtils.copyInputStreamToFile(inputStream, file);
        }
    }

    public static InputStream stringToInputStream(String s) {
        return new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean simpleFunctionCheck(String s) {
        return s.contains("(") && s.endsWith(")");
    }

    public static String stripQuotations(String s) {
        if (s.startsWith("\"")) {
            s = s.substring(1);
        }
        if (s.endsWith("\"")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
