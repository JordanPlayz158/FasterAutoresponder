package me.JordanPlayz158.FasterAutoresponder;

import me.JordanPlayz158.Utils.loadJson;

import java.io.File;
import java.util.List;

public class Variables {
    public static final String config = "config.json";
    public static final File file = new File("warnImage.png");
    public static final String copypastasFile = "copypastas.json";
    public static final String warnsRole = "warnsRole";

    //List of copypastas
    public static List<String> copypastas = loadJson.array(copypastasFile, "detectionStrings");

    public static void reloadCopypastasList() {
        copypastas = loadJson.array(copypastasFile, "detectionStrings");
    }
}
